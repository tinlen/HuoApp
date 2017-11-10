package com.example.huoapp.util;

import android.content.Context;
import android.os.Environment;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

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
}
