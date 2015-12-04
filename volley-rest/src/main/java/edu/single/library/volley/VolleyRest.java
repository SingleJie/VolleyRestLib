package edu.single.library.volley;

import android.content.Context;

import com.squareup.okhttp.OkHttpClient;

import edu.single.library.volley.enums.RequestType;
import edu.single.library.volley.enums.ResponseType;
import edu.single.library.volley.toolbox.OkHttpClientStack;
import edu.single.library.volley.toolbox.Volley;

/**
 *
 */
public class VolleyRest {

    private static RequestQueue mRequestQueue;
    public static RequestType mDefaultRequestType = RequestType.STRING;
    public static ResponseType mDefaultResponseType = ResponseType.STRING;

    private VolleyRest() {
    }

    /**
     * 初始化配置
     *
     * @param context
     */
    public static void initConfig(Context context) {
        mRequestQueue = Volley.newRequestQueue(context,new OkHttpClientStack(new OkHttpClient()));
    }

    public static void setDefaultRequestType(RequestType mDefaultRequestType) {
        VolleyRest.mDefaultRequestType = mDefaultRequestType;
    }

    public static void setmDefaultResponseType(ResponseType mDefaultResponseType) {
        VolleyRest.mDefaultResponseType = mDefaultResponseType;
    }

    /**
     * 获取请求队列
     *
     * @return
     */
    public static RequestQueue getRequestQueue() {
        if (mRequestQueue != null) {
            return mRequestQueue;
        } else {
            throw new IllegalStateException("RequestQueue not initialized");
        }
    }

    /**
     * 取消任务
     *
     * @param tag
     */
    public static void cancel(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }

}
