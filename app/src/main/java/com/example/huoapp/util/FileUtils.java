package com.example.huoapp.util;

import android.content.Context;
import android.os.Environment;
import android.os.storage.StorageManager;
import android.text.TextUtils;

import com.socks.library.KLog;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by tinle on 2017/11/3.
 */

public class FileUtils {
    public FileUtils() {
    }

    public static File getSaveFolder(String folderName) {
        File file = new File(getSDCardPath() + File.separator + folderName + File.separator);
        file.mkdirs();
        return file;
    }

    public static String getSDCardPath() {
        return Environment.getExternalStorageDirectory().getAbsolutePath();
    }

    public static String Read(String fileName) {
        String res = "";
        try {
            FileInputStream fin = new FileInputStream(fileName);
            int length = fin.available();
            byte[] buffer = new byte[length];
            fin.read(buffer);
            res = EncodingUtils.getString(buffer, "UTF-8");
            fin.close();
        } catch (Exception e) {
            KLog.e(e);
        }
        return res;
    }

    /**
     * 写入文件
     *
     * @param strFileName 文件名
     * @param bytes       bytes
     */
    public static boolean writeToFile(String strFileName, byte[] bytes) {
        try {
            File file = new File(strFileName);

            FileOutputStream fouts = new FileOutputStream(file);
            fouts.write(bytes, 0, bytes.length);
            fouts.flush();
            fouts.close();
            return true;
        } catch (IOException e) {
            KLog.e(e);
        }
        return false;
    }

    public static byte[] input2byte(InputStream inStream) {
        if(inStream == null) {
            return null;
        } else {
            byte[] in2b = null;
            BufferedInputStream in = new BufferedInputStream(inStream);
            ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
            boolean var4 = false;

            try {
                int rc;
                while((rc = in.read()) != -1) {
                    swapStream.write(rc);
                }

                in2b = swapStream.toByteArray();
            } catch (IOException var9) {
                var9.printStackTrace();
            } finally {
                closeIO(new Closeable[]{inStream, in, swapStream});
            }

            return in2b;
        }
    }

    public static void closeIO(Closeable... closeables) {
        if(null != closeables && closeables.length > 0) {
            Closeable[] var1 = closeables;
            int var2 = closeables.length;

            for(int var3 = 0; var3 < var2; ++var3) {
                Closeable cb = var1[var3];

                try {
                    if(null != cb) {
                        cb.close();
                    }
                } catch (IOException var6) {
                    throw new RuntimeException(FileUtils.class.getClass().getName(), var6);
                }
            }

        }
    }

    public static long getDirectorySize(File directory) {
        if(!directory.exists()) {
            return 0L;
        } else if(!directory.isDirectory()) {
            return directory.length();
        } else {
            long directorySize = 0L;
            File[] var3 = directory.listFiles();
            int var4 = var3.length;

            for(int var5 = 0; var5 < var4; ++var5) {
                File file = var3[var5];
                directorySize += getDirectorySize(file);
            }

            return directorySize;
        }
    }

    public static void clearCache(Context context){
        clearDirectory(context.getCacheDir());
    }

    public static void clearDirectory(File directory) {
        if(directory.exists() && directory.isDirectory()) {
            File[] var1 = directory.listFiles();
            int var2 = var1.length;

            for(int var3 = 0; var3 < var2; ++var3) {
                File file = var1[var3];
                if(file.exists() && file.isFile()) {
                    file.delete();
                } else if(file.exists() && file.isDirectory()) {
                    clearDirectory(file);
                    file.delete();
                }
            }
        }

    }


    /**
     * 得到内置或外置SD卡的路径
     *
     * @param mContext
     * @param isExSD   true=外置SD卡
     * @return
     */
    public static String getStoragePath(Context mContext, boolean isExSD) {
        StorageManager mStorageManager = (StorageManager) mContext.getSystemService(Context.STORAGE_SERVICE);
        Class<?> storageVolumeClazz = null;
        try {
            storageVolumeClazz = Class.forName("android.os.storage.StorageVolume");
            Method getVolumeList = mStorageManager.getClass().getMethod("getVolumeList");
            Method getPath = storageVolumeClazz.getMethod("getPath");
            Method isRemovable = storageVolumeClazz.getMethod("isRemovable");
            Object result = getVolumeList.invoke(mStorageManager);
            final int length = Array.getLength(result);
            for (int i = 0; i < length; i++) {
                Object storageVolumeElement = Array.get(result, i);
                String path = (String) getPath.invoke(storageVolumeElement);
                boolean removable = (Boolean) isRemovable.invoke(storageVolumeElement);
                if (isExSD == removable) {
                    return path;
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 如果文件末尾有了"/"则判断是否有多个"/"，是则保留一个，没有则添加
     *
     * @param path
     * @return
     */
    public static String checkFileSeparator(String path) {
        if (!TextUtils.isEmpty(path)) {
            if (!path.endsWith(File.separator)) {
                return path.concat(File.separator);
            } else {
                final int sourceStringLength = path.length();
                int index = sourceStringLength;
                if (index >= 0) {
                    while (index >= 0) {
                        index--;
                        if (path.charAt(index) != File.separatorChar) {
                            break;
                        }
                    }
                }
                if (index < sourceStringLength) {
                    path = path.substring(0, index + 1);
                    return path.concat(File.separator);
                }
            }
        }
        return path;
    }

}
