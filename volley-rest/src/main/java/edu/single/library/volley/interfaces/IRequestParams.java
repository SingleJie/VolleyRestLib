package edu.single.library.volley.interfaces;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

import com.thoughtworks.xstream.XStream;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.Map;

import edu.single.library.volley.Request;
import edu.single.library.volley.enums.RequestType;
import edu.single.library.volley.enums.ResponseType;
import edu.single.library.volley.models.ByteArrayModels;
import edu.single.library.volley.models.FileModels;
import edu.single.library.volley.models.InputStreamModels;

@Deprecated
public interface IRequestParams {
	
	/**
	 * 存储一个String类型的Http头键值队
	 * @param key
	 * @param value
	 */
	void putHeader(String key, String value);
	
	/**
	 * 存储一个int类型的Http头键值队
	 * @param key
	 * @param value
	 */
	void putHeader(String key, int value);
	
	/**
	 * 存储一个float类型的Http头键值队
	 * @param key
	 * @param value
	 */
	void putHeader(String key, float value);
	
	/**
	 * 存储一个double类型的Http头键值队
	 * @param key
	 * @param value
	 */
	void putHeader(String key, double value);
	
	/**
	 * 存储一个long类型的Http头键值队
	 * @param key
	 * @param value
	 */
	void putHeader(String key, long value);
	
	/**
	 * 存储一个char类型的Http头键值队
	 * @param key
	 * @param value
	 */
	void putHeader(String key, char value);
	
	/**
	 * 存储一个boolean类型的Http头键值队
	 * @param key
	 * @param value
	 */
	void putHeader(String key, boolean value);

	/**
	 * 得到Http头
	 * @return
	 */
	Map<String,String> getHeaders();
	
	/**
	 * 存储一个String类型的Body键值队
	 * @param key
	 * @param value
	 */
	void put(String key, String value);
	
	/**
	 * 存储一个int类型的Body键值队
	 * @param key
	 * @param value
	 */
	void put(String key, int value);
	
	/**
	 * 存储一个float类型的Body键值队
	 * @param key
	 * @param value
	 */
	void put(String key, float value);
	
	/**
	 * 存储一个double类型的Body键值队
	 * @param key
	 * @param value
	 */
	void put(String key, double value);
	
	/**
	 * 存储一个long类型的Body键值队
	 * @param key
	 * @param value
	 */
	void put(String key, long value);
	
	/**
	 * 存储一个char类型的Body键值队
	 * @param key
	 * @param value
	 */
	void put(String key, char value);
	
	/**
	 * 存储一个boolean类型的Body键值队
	 * @param key
	 * @param value
	 */
	void put(String key, boolean value);

	/**
	 * 存储一个JSONObject对象
	 * @param value
	 */
	void putJSONObject(JSONObject value);
	
	/**
	 * 存储一个JSONArray对象
	 * @param value
	 */
	void putJSONArray(JSONArray value);

	JSONObject getJSONObject();
	
	JSONArray getJSONArray();

    //上传文件
	
	void putFile(String key, File file);
	
	void putFile(String key, String fileName, File file);
	
	void putFile(String key, String fileName, File file, String contentType);

    //上传流

	void putStream(String key, InputStream in);
	
	void putStream(String key, String fileName, InputStream in);
	
	void putStream(String key, String fileName, InputStream in, String contentType);
	
	//上传ByteArray

	void putByteArray(String key, byte[] data);
	
	void putByteArray(String key, String fileName, byte[] data);
	
	void putByteArray(String key, String fileName, byte[] data, String contentType);
	
	//上传Bitmap

    void putBitmap(String key, Bitmap mBitmap);
	
	void putBitmap(String key, String fileName, Bitmap mBitmap);
	
	void putBitmap(String key, String fileName, Bitmap mBitmap, String contentType);

    //上传drawable
	
    void putDrawable(String key, Drawable mDrawable);
	
	void putDrawable(String key, String fileName, Drawable mDrawable);
	
	void putDrawable(String key, String fileName, Drawable mDrawable, String contentType);

    /**
     * 生成以时间为单位的名称
     * @return
     */
    String generateFileName();
	
	/**
	 * 得到上传的普通参数
	 * @return
	 */
	Map<String,String> getParams();
	
	/***
	 * 移除参数
	 * @param key
	 */
	void remove(String key);
	
	/**
	 * 设置请求方式 默认为GET
	 * @param requestMode @See { @link Request.Method }
	 */
	void setRequestMethod(int requestMode);
	
	/**
	 * 获取设置的请求网络模式 默认为GET
	 * @return
	 */
	int getRequestMethod();

	/**
	 * 缓存到Disk
	 * @param enable
	 */
	void setCacheOnDisk(boolean enable);
	
	/**
	 * 获取
	 * @return
	 */
	boolean getCacheEnable();
	
	
	/**
	 * 获取文件上传参数
	 * @return
	 */
	Map<String,FileModels> getFileParams();

    /**
     * Stream上传参数
     * @return
     */
	Map<String,InputStreamModels> getStreamParams();

    /**
     * ByteArray数据上传参数
     * @return
     */
	Map<String,ByteArrayModels> getByteArrayParams();

	/**
	 * 设置请求Body
	 * @param body
	 */
	void setRequestBody(String body);
	
	/**
	 * 获取请求Body
	 * @return
	 */
	String getRequestBody();
	
	/**
	 * 设置优先级
	 * @param mPriority
	 */
	void setPriority(Request.Priority mPriority);
	
	/**
	 * 获取优先级
	 * @return
	 */
	Request.Priority getPriority();
	
	/**
	 * 设置重连次数、连接超时时间
	 * @param mRetryPolicy
	 */
	void setRetryPolicy(RetryPolicy mRetryPolicy);
	
	/**
	 * 得到重连方案
	 * @return
	 */
	RetryPolicy getRetryPolicy();

	/**
	 * 设置编码类型
	 * @param encodeType 编码类型 @See{Request.Method}
	 */
	void setEncodeType(String encodeType);
	
	/**
	 * 获取编码类型
	 * @return 编码类型
	 */
	String getEncodeType();

    /**
     * HTTP ContentType
     * @param contentType
     */
	void setContentType(String contentType);
	
	String getContentType();

    /**
     * 设置请求类型
     * @param mRequestType
     */
    void setRequestType(RequestType mRequestType);


    RequestType getRequestType();

    /**
     * 设置返回类型
     * @param mResponseType
     */
    void setResponseType(ResponseType mResponseType);

    ResponseType getResponseType();

    //Gson

    /**
     * 设置Gson回调类型
     * @param cls
     */
    void setGsonCallbackType(Class<?> cls);

    /**
     * 设置Gson回调类型
     * @param typeOfT
     */
    void setGsonCallBackType(Type typeOfT);

    /**
     * 获取 Class Gson 类型
     * @return
     */
    <T> Class<?> getGsonCallBackByClass();

    /**
     * 获取 Token Gson 类型
     * @return
     */
    Type getGsonCallBackByType();

    //XML

    XStream getXStream();

    void alias(String name, Class type);

    void aliasType(String name, Class type);

    void alias(String name, Class type, Class defaultImplementation);

    void aliasPackage(String name, String pkgName);

    void aliasField(String alias, Class definedIn, String fieldName);

    void aliasAttribute(String alias, String attributeName);

    void aliasSystemAttribute(String alias, String systemAttributeName);

    void aliasAttribute(Class definedIn, String attributeName, String alias);

    void addDefaultImplementation(Class defaultImplementation, Class ofType) ;

    void addImmutableType(Class type) ;
}
