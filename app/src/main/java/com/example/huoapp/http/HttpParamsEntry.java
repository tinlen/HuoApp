package com.example.huoapp.http;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by tinle on 2017/11/3.
 */

public class HttpParamsEntry implements Comparable<HttpParamsEntry> {
    public String k;
    public String v;

    public boolean equals(Object o) {
        return o instanceof HttpParamsEntry?this.k.equals(((HttpParamsEntry)o).k):super.equals(o);
    }

    public int hashCode() {
        return this.k.hashCode();
    }

    public HttpParamsEntry(String key, String value, boolean requestEncode) {
        this.k = key;
        if(requestEncode) {
            try {
                this.v = URLEncoder.encode(value, "UTF-8");
            } catch (UnsupportedEncodingException var5) {
                var5.printStackTrace();
                this.v = value;
            }
        } else {
            this.v = value;
        }

    }

    public int compareTo(HttpParamsEntry another) {
        return this.k == null?-1:this.k.compareTo(another.k);
    }
}