package com.example.huoapp.widget.album;

import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.Nullable;

import com.example.huoapp.util.AppContext;
import com.example.huoapp.util.AppFileHelper;
import com.example.huoapp.util.FileUtils;
import com.example.huoapp.util.GsonUtil;
import com.example.huoapp.util.HuoPreference;
import com.example.huoapp.util.TimeUtil;
import com.google.gson.reflect.TypeToken;
import com.socks.library.KLog;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * 本地照片管理
 * Created by tinle on 2017/11/10.
 */

public class LocalPhotoManager {
    private String TAG = LocalPhotoManager.class.getSimpleName();
    public static final String LOCAL_FILE_NAME = "LocalPhotoFile";
    public static final String APP_LAST_SCAN_IMG_TIME = "last_scan_image_time";
    private static final String ALL_PHOTO_TITLE = "所有照片";
    private LocalPhotoManager() {}

    private static final class Holder{
        private static final LocalPhotoManager INSTANCE = new LocalPhotoManager();
    }

    public static LocalPhotoManager getInstance(){
        return Holder.INSTANCE;
    }

    private static final String QUERY_ORDER = " ASC";
    private static final boolean SCAN_EXTERNAL_SD = true;

    private static final long SCAN_INTERVAL = 60 * 1000;

    private WeakHandler handler = new WeakHandler();

    boolean isScaning;
    long lastScanTime;

    //访问系统数据库的大图查询数据
    public static final String[] STORE_IMGS = {MediaStore.Images.ImageColumns._ID,
            MediaStore.Images.ImageColumns.DATA,
            MediaStore.Images.ImageColumns.ORIENTATION,
            MediaStore.Images.ImageColumns.BUCKET_DISPLAY_NAME,
            MediaStore.Images.ImageColumns.DATE_TAKEN};
    //访问系统数据库的小图查询数据
    public static final String[] THUMBNAIL_STORE_IMAGE = {MediaStore.Images.Thumbnails._ID, MediaStore.Images.Thumbnails.DATA};

    private static final LinkedHashMap<String, List<ImageInfo>> sALBUM = new LinkedHashMap<>();
    private static final LinkedHashMap<String, List<ImageInfo>> sWRITE_ALBUM = new LinkedHashMap<>();

    public synchronized void scanImg(@Nullable final OnScanListener listener) {
        ThreadPoolManager.execute(new Runnable() {
            @Override
            public void run() {
                scanImgInternal(listener);
            }
        });
    }

    private synchronized void scanImgInternal(@Nullable OnScanListener listener) {
        if (isScaning) {
            callError(listener, "scan task is running", new LPException("scan task is running"));
            return;
        }
        isScaning = true;
        callStart(listener);
        lastScanTime = HuoPreference.getAppFlagLong(APP_LAST_SCAN_IMG_TIME);

        boolean callImmediately = checkLocalSerializableFile();
        //如果本地文件已经有了，那么可以立即回调，提高用户体验。
        //然后再后台扫一次更新本地文件记录
        if (callImmediately) {
            callFinish(listener);
            reset();
            return;
        }
        long curTime = System.currentTimeMillis();
        if (curTime - lastScanTime <= SCAN_INTERVAL) {
            if (new File(AppFileHelper.getAppDataPath().concat(LOCAL_FILE_NAME)).exists()) {
                callError(listener, "1分钟内不应该再次扫描", new IllegalStateException("1分钟内不应该再次扫描"));
                reset();
                return;
            }
        }
        scan(sALBUM, listener);
    }

    public LinkedHashMap<String, List<ImageInfo>> getLocalImagesMap() {
        return new LinkedHashMap<>(sALBUM);
    }

    public List<ImageInfo> getLocalImages(String albumName) {
        List<ImageInfo> infos = sALBUM.get(albumName);
        if (infos != null) {
            return new ArrayList<ImageInfo>(infos);
        }
        return new ArrayList<ImageInfo>();
    }

    private void scanAsync(final LinkedHashMap<String, List<ImageInfo>> sALBUM, @Nullable final OnScanListener listener) {
        ThreadPoolManager.execute(new Runnable() {
            @Override
            public void run() {
                scan(sALBUM, listener);
            }
        });
    }

    private void scan(LinkedHashMap<String, List<ImageInfo>> sALBUM, @Nullable OnScanListener listener) {
        if (sALBUM == null) return;
        KLog.i(TAG, "isWrite  >>  " + (sALBUM == sWRITE_ALBUM));
        Cursor cursor = AppContext.getAppContext()
                .getContentResolver()
                .query(SCAN_EXTERNAL_SD ? MediaStore.Images.Media.EXTERNAL_CONTENT_URI : MediaStore.Images.Media.INTERNAL_CONTENT_URI,
                        STORE_IMGS,
                        null,
                        null,
                        MediaStore.Images.ImageColumns.DATE_TAKEN.concat(QUERY_ORDER));
        if (cursor == null) {
            callError(listener, "cursor为空", null);
            reset();
            return;
        }
        sALBUM.clear();
        //构造thumb的查询语句(where id = xxx)
        final String[] thumbWhereQuery = new String[1];
        List<ImageInfo> allImageInfoLists = new ArrayList<>();
        final int cursorCount = cursor.getCount();
        sALBUM.put(ALL_PHOTO_TITLE, allImageInfoLists);
        cursor.moveToFirst();
        while (cursor.moveToNext()) {
            int imgId = cursor.getInt(cursor.getColumnIndex(MediaStore.Images.ImageColumns._ID));
            String imgPath = cursor.getString(cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA));
            int orientation = cursor.getInt(cursor.getColumnIndex(MediaStore.Images.ImageColumns.ORIENTATION));
            String albumName = cursor.getString(cursor.getColumnIndex(MediaStore.Images.ImageColumns.BUCKET_DISPLAY_NAME));
            long dateTaken = cursor.getLong(cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATE_TAKEN));

            if (!new File(imgPath).exists()) continue;

            thumbWhereQuery[0] = String.valueOf(imgId);
            String thumbImgPath = getThumbPath(thumbWhereQuery);

            List<ImageInfo> imageInfoList = sALBUM.get(albumName);
            ImageInfo imageInfo = new ImageInfo(imgPath, thumbImgPath, albumName, dateTaken, orientation);
            try {
                if (imageInfo.checkValided()) {
                    allImageInfoLists.add(imageInfo.clone());
                }
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
                KLog.e(e);
            }
            if (imageInfoList == null) {
                imageInfoList = new ArrayList<>();
                sALBUM.put(albumName, imageInfoList);
            } else {
                if (imageInfo.checkValided()) {
                    imageInfoList.add(imageInfo);
                }
            }
        }
        lastScanTime = System.currentTimeMillis();
        HuoPreference.setAppFlag(APP_LAST_SCAN_IMG_TIME, lastScanTime);
        cursor.close();
        callFinish(listener);
        reset();
        KLog.i(TAG, "scan完成");
        if (sALBUM == sWRITE_ALBUM && !sALBUM.isEmpty()) {
            sALBUM.clear();
            sALBUM.putAll(sWRITE_ALBUM);
        }
        //事实上io流的速度也是杠杠的，所以这里可以采取写入到本地文件的方法来存储扫描结果
        ThreadPoolManager.execute(new WriteToLocalRunnable(sALBUM));
    }

    public boolean hasData() {
        return !sALBUM.isEmpty();
    }

    public String getAllPhotoTitle() {
        return ALL_PHOTO_TITLE;
    }

    private static class WriteToLocalRunnable implements Runnable {
        LinkedHashMap<String, List<ImageInfo>> write;

        public WriteToLocalRunnable(LinkedHashMap<String, List<ImageInfo>> write) {
            if (write != null) {
                this.write = new LinkedHashMap<>(write);
            } else {
                this.write = new LinkedHashMap<>();
            }
        }

        @Override
        public void run() {
            if (!write.isEmpty()) {
                FileUtils.writeToFile(AppFileHelper.getAppDataPath().concat(LOCAL_FILE_NAME), GsonUtil.INSTANCE.toString(write).getBytes());
            }
        }
    }

    public void writeToLocal() {
        KLog.i(TAG, "图库记录写入本地");
        ThreadPoolManager.execute(new WriteToLocalRunnable(sALBUM));
    }

    private String getThumbPath(String[] whereQuery) {
        String result = null;
        //通过大图的id，并且构造cursor的查询语句来获取大图对应的小图
        Cursor cursor = AppContext.getAppContext()
                .getContentResolver()
                .query(SCAN_EXTERNAL_SD ? MediaStore.Images.Thumbnails.EXTERNAL_CONTENT_URI : MediaStore.Images.Thumbnails.INTERNAL_CONTENT_URI,
                        THUMBNAIL_STORE_IMAGE,
                        MediaStore.Images.Thumbnails.IMAGE_ID.concat(" = ?"),
                        whereQuery,
                        null);
        if (cursor == null) return null;

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            result = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Thumbnails.DATA));
        }
        cursor.close();
        return result;
    }

    private boolean checkLocalSerializableFile() {
        if (!sALBUM.isEmpty()) return true;
        File file = new File(AppFileHelper.getAppDataPath().concat(LOCAL_FILE_NAME));
        if (file.exists()) {
            KLog.i(TAG, "当前时间  >>>  " + TimeUtil.longToTimeStr(System.currentTimeMillis(), TimeUtil.YYYYMMDDHHMMSS));
            KLog.i(TAG, "文件修改时间  >>>  " + TimeUtil.longToTimeStr(file.lastModified(), TimeUtil.YYYYMMDDHHMMSS));
            //每30分钟进行一次静默扫描
            boolean reScanSilent = System.currentTimeMillis() - file.lastModified() > 30 * TimeUtil.MINUTE * 1000;
            //每周进行一次回调扫描
            boolean reScanFullAndCallBack = System.currentTimeMillis() - file.lastModified() > 24 * 7 * TimeUtil.HOUR * 1000;
            KLog.i(TAG, "需要重新扫描？  >>>  " + reScanSilent);
            try {
                LinkedHashMap<String, List<ImageInfo>> map = GsonUtil.INSTANCE.toLinkHashMap(FileUtils.Read(file.getAbsolutePath()),
                        new TypeToken<LinkedHashMap<String, List<ImageInfo>>>() {
                        }.getType());
                if (!map.isEmpty()) {
                    sALBUM.clear();
                    sALBUM.putAll(map);
                    if (reScanFullAndCallBack) {
                        KLog.i(TAG, "全量回调扫描");
                        return false;
                    }
                    if (reScanSilent) {
                        KLog.i(TAG, "静默扫描");
                        scanAsync(sWRITE_ALBUM, null);
                    }
                    return true;
                }
            } catch (Exception e) {
                KLog.e(e);
                return false;
            }
        }
        return false;
    }

    private void callStart(final OnScanListener listener) {
        if (listener != null) {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    listener.onStart();
                }
            });
        }
    }

    private void callFinish(final OnScanListener listener) {
        if (listener != null) {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    listener.onFinish();
                }
            });
        }
    }

    private void callError(final OnScanListener listener, final String message, final Exception e) {
        if (listener != null) {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    listener.onError(new LPException(message, e));
                }
            });
        }
    }


    public interface OnScanListener {
        void onStart();

        void onFinish();

        void onError(LPException e);
    }

    private void reset() {
        isScaning = false;
    }

    public void registerContentObserver(Handler handler) {
        AppContext.getAppContext()
                .getContentResolver()
                .registerContentObserver(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, false, new MediaImageContentObserver(handler));
    }

    /**
     * 本监听存在于整个app生命期
     */
    class MediaImageContentObserver extends ContentObserver {

        /**
         * Creates a content observer.
         *
         * @param handler The handler to run {@link #onChange} on, or null if none.
         */
        public MediaImageContentObserver(Handler handler) {
            super(handler);
        }

        @Override
        public void onChange(boolean selfChange, Uri uri) {
            super.onChange(selfChange, uri);
            if (uri.compareTo(MediaStore.Images.Media.EXTERNAL_CONTENT_URI) != 0) return;
            KLog.i(TAG, "监听到系统图库更新  >>>  " + uri.toString());

            //查询5分钟内更新的数据
            long queryTime = System.currentTimeMillis() - (5 * 60 * 1000);
            final String[] whereQuery = {String.valueOf(queryTime)};


            Cursor cursor = AppContext.getAppContext()
                    .getContentResolver()
                    .query(uri, STORE_IMGS, MediaStore.Images.ImageColumns.DATE_TAKEN.concat(" > ?"),
                            whereQuery, MediaStore.Images.ImageColumns.DATE_TAKEN.concat(QUERY_ORDER));
            if (cursor == null) return;
            KLog.i(TAG, "查询到  >>  " + cursor.getCount() + " 条数据");
            // FIXME: 2017/3/24 没错。。。他喵的又是上面的重复步骤，有空把它抽取出来
            final String[] thumbWhereQuery = new String[1];
            List<ImageInfo> allImageInfoLists = sALBUM.get(ALL_PHOTO_TITLE);
            if (cursor.moveToLast()) {
                int imgId = cursor.getInt(cursor.getColumnIndex(MediaStore.Images.ImageColumns._ID));
                String imgPath = cursor.getString(cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA));
                int orientation = cursor.getInt(cursor.getColumnIndex(MediaStore.Images.ImageColumns.ORIENTATION));
                String albumName = cursor.getString(cursor.getColumnIndex(MediaStore.Images.ImageColumns.BUCKET_DISPLAY_NAME));
                long dateTaken = cursor.getLong(cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATE_TAKEN));

                if (!new File(imgPath).exists()) return;

                thumbWhereQuery[0] = String.valueOf(imgId);
                String thumbImgPath = getThumbPath(thumbWhereQuery);

                List<ImageInfo> imageInfoList = sALBUM.get(albumName);
                ImageInfo imageInfo = new ImageInfo(imgPath, thumbImgPath, albumName, dateTaken, orientation);
                try {
                    if (allImageInfoLists != null && imageInfo.checkValided()) {
                        allImageInfoLists.add(imageInfo.clone());
                    }
                } catch (CloneNotSupportedException e) {
                    e.printStackTrace();
                    KLog.e(e);
                }
                if (imageInfoList == null) {
                    imageInfoList = new ArrayList<>();
                    sALBUM.put(albumName, imageInfoList);
                } else {
                    if (imageInfo.checkValided()) {
                        imageInfoList.add(imageInfo);
                    }
                }

                KLog.i(TAG, "成功刷新到一条数据 >>>  " + imageInfo.toString());
            }
            cursor.close();
        }
    }
}
