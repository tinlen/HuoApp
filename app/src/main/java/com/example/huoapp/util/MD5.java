package com.example.huoapp.util;


import com.blankj.utilcode.util.LogUtils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by tinle on 2017/11/3.
 */

public class MD5 {
    public static final String TAG = MD5.class.getSimpleName();

    public MD5() {
    }

    public static String md5(String str) {
        String result = null;

        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(str.getBytes());
            byte[] b = md.digest();
            StringBuffer buf = new StringBuffer("");

            for(int offset = 0; offset < b.length; ++offset) {
                int i = b[offset];
                if(i < 0) {
                    i += 256;
                }

                if(i < 16) {
                    buf.append("0");
                }

                buf.append(Integer.toHexString(i));
            }

            result = buf.toString();
        } catch (NoSuchAlgorithmException var7) {
            LogUtils.e(TAG, new Object[]{"MD5加密失败, ", var7});
        }

        return result;
    }
}
