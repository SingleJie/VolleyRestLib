package edu.single.library.volley;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.wenjackp.android.lib.utils.ImageFormatUtils;

import org.apache.http.entity.ContentType;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import edu.single.library.volley.enums.RequestType;
import edu.single.library.volley.enums.ResponseType;
import edu.single.library.volley.interfaces.OnPreHandlerListener;
import edu.single.library.volley.interfaces.RetryPolicy;
import edu.single.library.volley.models.ByteArrayModels;
import edu.single.library.volley.models.FileModels;
import edu.single.library.volley.models.InputStreamModels;

public class RequestParams {

    /**
     * 存储Header参数集合
     */
    private ConcurrentHashMap<String, String> mHeaders;

    /**
     * 存储Body参数集合
     */
    private ConcurrentHashMap<String, String> mParams;

    private ConcurrentHashMap<String, FileModels> mFileParams;

    private ConcurrentHashMap<String, InputStreamModels> mStreamParams;

    private ConcurrentHashMap<String, ByteArrayModels> mByteArrayParams;

    /**
     * 网络访问模式默认为Get
     */
    private int requestMethod = edu.single.library.volley.Request.Method.GET;

    private Class<?> cls;

    private Type typeOfT;

    /**
     * 是否开启缓存
     */
    private boolean cacheEnable;

    /**
     * 请求Body
     */
    private String requestBody;

    /**
     * 是否含有文件参数
     */
    private boolean hasFileParams = false;

    /**
     * 优先级
     */
    private Request.Priority mPriority;

    /**
     * 重连方案
     */
    private RetryPolicy mRetryPolicy;

    private OnPreHandlerListener mPreHandlerListener;

    /**
     * 默认编码方式
     */
    private String encodeType = "UTF-8";

    private String contentType;

    private JSONObject jsonObjValue;

    private JSONArray jsonArrayValue;

    private ResponseType mResponseType = edu.single.library.volley.VolleyRest.mDefaultResponseType;
    private RequestType mRequestType = edu.single.library.volley.VolleyRest.mDefaultRequestType;

    private XStream mXStream;

    public RequestParams() {
        init();
    }

    private void init() {

        mHeaders = new ConcurrentHashMap<String, String>();
        mParams = new ConcurrentHashMap<String, String>();
        mFileParams = new ConcurrentHashMap<String, FileModels>();
        mByteArrayParams = new ConcurrentHashMap<String, ByteArrayModels>();
        mStreamParams = new ConcurrentHashMap<String, InputStreamModels>();
        mXStream = new XStream(new DomDriver());
    }

    public void putHeader(String key, String value) {

        if (key != null && value != null) {
            mHeaders.put(key, value);
        }
    }

    public void putHeader(String key, int value) {

        if (key != null) {
            mHeaders.put(key, String.valueOf(value));
        }
    }

    public void putHeader(String key, float value) {

        if (key != null) {
            mHeaders.put(key, String.valueOf(value));
        }
    }

    public void putHeader(String key, double value) {

        if (key != null) {
            mHeaders.put(key, String.valueOf(value));
        }
    }

    public void putHeader(String key, long value) {

        if (key != null) {
            mHeaders.put(key, String.valueOf(value));
        }

    }

    public void putHeader(String key, char value) {

        if (key != null) {
            mHeaders.put(key, String.valueOf(value));
        }
    }

    public Map<String, String> getHeaders() {

        return mHeaders;
    }

    public void put(String key, String value) {

        if (key != null && value != null) {
            mParams.put(key, value);
        }
    }

    public void put(String key, int value) {

        if (key != null) {
            mParams.put(key, String.valueOf(value));
        }

    }

    public void put(String key, float value) {

        if (key != null) {
            mParams.put(key, String.valueOf(value));
        }
    }

    public void put(String key, double value) {

        if (key != null) {
            mParams.put(key, String.valueOf(value));
        }
    }

    public void put(String key, long value) {

        if (key != null) {
            mParams.put(key, String.valueOf(value));
        }
    }

    public void put(String key, char value) {

        if (key != null) {
            mParams.put(key, String.valueOf(value));
        }
    }

    public Map<String, String> getParams() {

        return mParams;
    }

    public void remove(String key) {
        mParams.remove(key);
        mHeaders.remove(key);
    }

    public void putHeader(String key, boolean value) {

        if (key != null) {
            mHeaders.put(key, String.valueOf(value));
        }
    }

    public void put(String key, boolean value) {

        if (key != null) {
            mParams.put(key, String.valueOf(value));
        }
    }

    public void setRequestMethod(int requestMode) {
        this.requestMethod = requestMode;
    }

    public int getRequestMethod() {
        return this.requestMethod;
    }

    public void setGsonCallbackType(Class<?> cls) {
        this.cls = cls;
    }

    public <T> Class<?> getGsonCallBackByClass() {
        return cls;
    }

    public void setGsonCallBackType(Type typeOfT) {
        this.typeOfT = typeOfT;
    }

    public Type getGsonCallBackByType() {
        return typeOfT;
    }

    public void setCacheOnDisk(boolean enable) {
        this.cacheEnable = enable;
    }

    public boolean getCacheEnable() {
        return cacheEnable;
    }

    public void setRequestBody(String body) {
        this.requestBody = body;
    }

    public String getRequestBody() {
        return requestBody;
    }

    public void setPriority(Request.Priority mPriority) {
        this.mPriority = mPriority;
    }

    public Request.Priority getPriority() {
        return mPriority;
    }

    public void setRetryPolicy(RetryPolicy mRetryPolicy) {
        this.mRetryPolicy = mRetryPolicy;
    }

    public RetryPolicy getRetryPolicy() {
        return mRetryPolicy;
    }

    public void setEncodeType(String encodeType) {
        this.encodeType = encodeType;
    }

    public String getEncodeType() {
        return encodeType;
    }

    public void setOnPreHandlerListener(OnPreHandlerListener mPreHandlerListener){
        this.mPreHandlerListener = mPreHandlerListener;
    }

    public OnPreHandlerListener getPreHandlerListener(){
        return this.mPreHandlerListener;
    }

    public boolean hashFileParams() {
        return hasFileParams;
    }

    public void putFile(String key, File file) {
        FileModels item = new FileModels(file.getName(), ContentType.DEFAULT_BINARY.getMimeType(), file);
        Collections.synchronizedMap(mFileParams).put(key, item);
        hasFileParams = true;
        requestMethod = edu.single.library.volley.Request.Method.POST;
    }

    public void putFile(String key, String fileName, File file) {
        FileModels item = new FileModels(fileName, ContentType.DEFAULT_BINARY.getMimeType(), file);
        Collections.synchronizedMap(mFileParams).put(key, item);
        hasFileParams = true;
        requestMethod = edu.single.library.volley.Request.Method.POST;
    }

    public void putFile(String key, String fileName, File file, String contentType) {
        FileModels item = new FileModels(fileName, contentType, file);
        Collections.synchronizedMap(mFileParams).put(key, item);
        hasFileParams = true;
        requestMethod = edu.single.library.volley.Request.Method.POST;
    }

    public void putStream(String key, InputStream in) {
        InputStreamModels item = new InputStreamModels(generateFileName(), ContentType.DEFAULT_BINARY.getMimeType(), in);
        Collections.synchronizedMap(mStreamParams).put(key, item);
        hasFileParams = true;
        requestMethod = edu.single.library.volley.Request.Method.POST;
    }

    public void putStream(String key, String fileName, InputStream in) {
        InputStreamModels item = new InputStreamModels(fileName, ContentType.DEFAULT_BINARY.getMimeType(), in);
        Collections.synchronizedMap(mStreamParams).put(key, item);
        hasFileParams = true;
        requestMethod = edu.single.library.volley.Request.Method.POST;
    }

    public void putStream(String key, String fileName, InputStream in, String contentType) {
        InputStreamModels item = new InputStreamModels(fileName, contentType, in);
        Collections.synchronizedMap(mStreamParams).put(key, item);
        hasFileParams = true;
        requestMethod = edu.single.library.volley.Request.Method.POST;
    }

    public void putByteArray(String key, byte[] data) {
        ByteArrayModels item = new ByteArrayModels(generateFileName(), ContentType.DEFAULT_BINARY.getMimeType(), data);
        Collections.synchronizedMap(mByteArrayParams).put(key, item);
        hasFileParams = true;
    }

    public void putByteArray(String key, String fileName, byte[] data) {
        ByteArrayModels item = new ByteArrayModels(fileName, ContentType.DEFAULT_BINARY.getMimeType(), data);
        Collections.synchronizedMap(mByteArrayParams).put(key, item);
        hasFileParams = true;
        requestMethod = edu.single.library.volley.Request.Method.POST;
    }

    public void putByteArray(String key, String fileName, byte[] data, String contentType) {
        ByteArrayModels item = new ByteArrayModels(fileName, contentType, data);
        Collections.synchronizedMap(mByteArrayParams).put(key, item);
        hasFileParams = true;
        requestMethod = edu.single.library.volley.Request.Method.POST;
    }

    public void putBitmap(String key, Bitmap mBitmap) {
        InputStreamModels item = new InputStreamModels(generateFileName(), ContentType.DEFAULT_BINARY.getMimeType(), ImageFormatUtils.bitmapToStream(mBitmap));
        Collections.synchronizedMap(mStreamParams).put(key, item);
        hasFileParams = true;
        requestMethod = edu.single.library.volley.Request.Method.POST;
    }

    public void putBitmap(String key, String fileName, Bitmap mBitmap) {
        InputStreamModels item = new InputStreamModels(fileName, ContentType.DEFAULT_BINARY.getMimeType(), ImageFormatUtils.bitmapToStream(mBitmap));
        Collections.synchronizedMap(mStreamParams).put(key, item);
        hasFileParams = true;
        requestMethod = edu.single.library.volley.Request.Method.POST;
    }

    public void putBitmap(String key, String fileName, Bitmap mBitmap, String contentType) {
        InputStreamModels item = new InputStreamModels(fileName, contentType, ImageFormatUtils.bitmapToStream(mBitmap));
        Collections.synchronizedMap(mStreamParams).put(key, item);
        hasFileParams = true;
        requestMethod = edu.single.library.volley.Request.Method.POST;
    }

    public void putDrawable(String key, Drawable mDrawable) {
        InputStreamModels item = new InputStreamModels(generateFileName(), ContentType.DEFAULT_BINARY.getMimeType(), ImageFormatUtils.drawableToStream(mDrawable));
        Collections.synchronizedMap(mStreamParams).put(key, item);
        hasFileParams = true;
        requestMethod = edu.single.library.volley.Request.Method.POST;
    }

    public void putDrawable(String key, String fileName, Drawable mDrawable) {
        InputStreamModels item = new InputStreamModels(fileName, ContentType.DEFAULT_BINARY.getMimeType(), ImageFormatUtils.drawableToStream(mDrawable));
        Collections.synchronizedMap(mStreamParams).put(key, item);
        hasFileParams = true;
        requestMethod = edu.single.library.volley.Request.Method.POST;
    }

    public void putDrawable(String key, String fileName, Drawable mDrawable, String contentType) {
        InputStreamModels item = new InputStreamModels(fileName, contentType, ImageFormatUtils.drawableToStream(mDrawable));
        Collections.synchronizedMap(mStreamParams).put(key, item);
        hasFileParams = true;
        requestMethod = edu.single.library.volley.Request.Method.POST;
    }

    public String generateFileName() {
        Date mDate = new Date(System.currentTimeMillis());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
        return sdf.format(mDate);
    }

    public Map<String, InputStreamModels> getStreamParams() {
        return mStreamParams;
    }

    public Map<String, ByteArrayModels> getByteArrayParams() {
        return mByteArrayParams;
    }

    public Map<String, FileModels> getFileParams() {
        return mFileParams;
    }

    public void putJSONObject(JSONObject value) {
        this.jsonObjValue = value;
    }

    public void putJSONArray(JSONArray value) {
        this.jsonArrayValue = value;
    }

    public JSONObject getJSONObject() {
        return jsonObjValue;
    }

    public JSONArray getJSONArray() {
        return jsonArrayValue;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getContentType() {
        return contentType;
    }

    public void setRequestType(RequestType mRequestType) {
        this.mRequestType = mRequestType;
    }

    public RequestType getRequestType() {
        return mRequestType;
    }

    public void setResponseType(ResponseType mResponseType) {
        this.mResponseType = mResponseType;
    }

    public ResponseType getResponseType() {
        return mResponseType;
    }

    public XStream getXStream() {
        return mXStream;
    }

    public void alias(String name, Class type) {
        mXStream.alias(name, type);
    }

    public void aliasType(String name, Class type) {
        mXStream.aliasType(name, type);
    }

    public void alias(String name, Class type, Class defaultImplementation) {
        mXStream.alias(name, type, defaultImplementation);
    }

    public void aliasPackage(String name, String pkgName) {
        mXStream.aliasPackage(name, pkgName);
    }

    public void aliasField(String alias, Class definedIn, String fieldName) {
        mXStream.aliasField(alias, definedIn, fieldName);
    }

    public void aliasAttribute(String alias, String attributeName) {
        mXStream.aliasAttribute(alias, attributeName);
    }

    public void aliasSystemAttribute(String alias, String systemAttributeName) {
        mXStream.aliasSystemAttribute(alias, systemAttributeName);
    }

    public void aliasAttribute(Class definedIn, String attributeName, String alias) {
        mXStream.aliasAttribute(definedIn, attributeName, alias);
    }

    public void addDefaultImplementation(Class defaultImplementation, Class ofType) {
        mXStream.addDefaultImplementation(defaultImplementation, ofType);
    }

    public void addImmutableType(Class type) {
        mXStream.addImmutableType(type);
    }
}
