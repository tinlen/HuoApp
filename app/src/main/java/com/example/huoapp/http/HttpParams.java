package com.example.huoapp.http;

import android.text.TextUtils;

import com.example.huoapp.util.FileUtils;
import com.socks.library.KLog;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;

/**
 * Created by tinle on 2017/11/3.
 */

public class HttpParams {private static final char[] MULTIPART_CHARS = "-_1234567890abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
    private String mBoundary = null;
    private static final String NEW_LINE_STR = "\r\n";
    private static final String CONTENT_TYPE = "Content-Type: ";
    private static final String CONTENT_DISPOSITION = "Content-Disposition: ";
    public static final String CHARSET = "UTF-8";
    private static final String TYPE_TEXT_CHARSET = String.format("text/plain; charset=%s", new Object[]{"UTF-8"});
    private static final String TYPE_OCTET_STREAM = "application/octet-stream";
    private static final byte[] BINARY_ENCODING = "Content-Transfer-Encoding: binary\r\n\r\n".getBytes();
    private static final byte[] BIT_ENCODING = "Content-Transfer-Encoding: 8bit\r\n\r\n".getBytes();
    private final ArrayList<HttpParamsEntry> urlParams = new ArrayList(8);
    private final ArrayList<HttpParamsEntry> filePathParams = new ArrayList(4);
    private final ArrayList<HttpParamsEntry> mHeaders = new ArrayList(4);
    private final ByteArrayOutputStream mOutputStream = new ByteArrayOutputStream();
    private boolean hasFile;
    private String contentType = null;
    private String jsonParams;

    public HttpParams() {
        this.mBoundary = this.generateBoundary();
    }

    public ArrayList<HttpParamsEntry> getUrlParamsMap() {
        return this.urlParams;
    }

    public ArrayList<HttpParamsEntry> getFilePathParams() {
        return this.filePathParams;
    }

    private String generateBoundary() {
        StringBuilder buf = new StringBuilder();
        Random rand = new Random();

        for(int i = 0; i < 30; ++i) {
            buf.append(MULTIPART_CHARS[rand.nextInt(MULTIPART_CHARS.length)]);
        }

        return buf.toString();
    }

    public void putHeaders(String key, int value) {
        this.putHeaders(key, value + "");
    }

    public void putHeaders(String key, String value) {
        this.mHeaders.add(new HttpParamsEntry(key, value, false));
    }

    public void put(String key, int value) {
        this.put(key, value + "");
    }

    public void putJsonParams(String json) {
        this.jsonParams = json;
    }

    public void put(String key, String value) {
        this.urlParams.add(new HttpParamsEntry(key, value, true));
        this.writeToOutputStream(key, value.getBytes(), TYPE_TEXT_CHARSET, BIT_ENCODING, "");
    }

    public void put(String paramName, byte[] rawData) {
        this.hasFile = true;
        this.writeToOutputStream(paramName, rawData, "application/octet-stream", BINARY_ENCODING, "RxVolleyFile");
    }

    public void put(String key, File file) {
        this.filePathParams.add(new HttpParamsEntry(key, file.getAbsolutePath(), false));

        try {
            this.hasFile = true;
            this.writeToOutputStream(key, FileUtils.input2byte(new FileInputStream(file)), "application/octet-stream", BINARY_ENCODING, file.getName());
        } catch (FileNotFoundException var4) {
            KLog.d("HttpParams.put()-> file not found");
        }

    }

    public void put(String key, byte[] rawData, String type, String fileName) {
        this.hasFile = true;
        if(TextUtils.isEmpty(fileName)) {
            fileName = "RxVolleyFile";
        }

        this.writeToOutputStream(key, rawData, type, BINARY_ENCODING, fileName);
    }

    private void writeToOutputStream(String paramName, byte[] rawData, String type, byte[] encodingBytes, String fileName) {
        try {
            this.writeFirstBoundary();
            this.mOutputStream.write(("Content-Type: " + type + "\r\n").getBytes());
            this.mOutputStream.write(this.getContentDispositionBytes(paramName, fileName));
            this.mOutputStream.write(encodingBytes);
            this.mOutputStream.write(rawData);
            this.mOutputStream.write("\r\n".getBytes());
        } catch (IOException var7) {
            var7.printStackTrace();
        }

    }

    private void writeFirstBoundary() throws IOException {
        this.mOutputStream.write(("--" + this.mBoundary + "\r\n").getBytes());
    }

    private byte[] getContentDispositionBytes(String paramName, String fileName) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("--").append(this.mBoundary).append("\r\n").append("Content-Disposition: ").append("form-data; name=\"").append(paramName).append("\"");
        if(!TextUtils.isEmpty(fileName)) {
            stringBuilder.append("; filename=\"").append(fileName).append("\"");
        }

        return stringBuilder.append("\r\n").toString().getBytes();
    }

    public long getContentLength() {
        return (long)this.mOutputStream.toByteArray().length;
    }

    public String getContentType() {
        if(this.hasFile && this.contentType == null) {
            this.contentType = "multipart/form-data; boundary=" + this.mBoundary;
        }

        return this.contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public boolean isChunked() {
        return false;
    }

    public boolean isRepeatable() {
        return false;
    }

    public boolean isStreaming() {
        return false;
    }

    public void writeTo(OutputStream outstream) throws IOException {
        if(this.hasFile) {
            String endString = "--" + this.mBoundary + "--\r\n";
            this.mOutputStream.write(endString.getBytes());
            outstream.write(this.mOutputStream.toByteArray());
        } else if(!TextUtils.isEmpty(this.getUrlParams())) {
            outstream.write(this.getUrlParams().substring(1).getBytes());
        }

    }

    public void consumeContent() throws IOException, UnsupportedOperationException {
        if(this.isStreaming()) {
            throw new UnsupportedOperationException("Streaming entity does not implement #consumeContent()");
        }
    }

    public InputStream getContent() {
        return new ByteArrayInputStream(this.mOutputStream.toByteArray());
    }

    public StringBuilder getUrlParams() {
        StringBuilder result = new StringBuilder();
        boolean isFirst = true;
        Collections.sort(this.urlParams);

        HttpParamsEntry entry;
        for(Iterator var3 = this.urlParams.iterator(); var3.hasNext(); result.append(entry.k).append("=").append(entry.v)) {
            entry = (HttpParamsEntry)var3.next();
            if(!isFirst) {
                result.append("&");
            } else {
                result.append("?");
                isFirst = false;
            }
        }

        return result;
    }

    public String getJsonParams() {
        return this.jsonParams;
    }

    public ArrayList<HttpParamsEntry> getHeaders() {
        this.mHeaders.add(new HttpParamsEntry("Accept-Encoding", "identity", false));
        return this.mHeaders;
    }

    public Map<String, String> getHeaderMap() {
        HashMap map = new HashMap();
        Iterator iterator = this.mHeaders.iterator();

        while(iterator.hasNext()) {
            HttpParamsEntry httpParamsEntry = (HttpParamsEntry)iterator.next();
            map.put(httpParamsEntry.k, httpParamsEntry.v);
        }

        return map;
    }
}
