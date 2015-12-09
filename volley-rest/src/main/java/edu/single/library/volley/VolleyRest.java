package edu.single.library.volley;

import android.content.Context;

import com.squareup.okhttp.OkHttpClient;
import com.wenjackp.android.lib.utils.SharedPreferencesTools;

import edu.single.library.volley.toolbox.OkHttpClientStack;
import edu.single.library.volley.toolbox.Volley;

/**
 *
 */
public class VolleyRest {

    private static RequestQueue mRequestQueue;

    private VolleyRest() {
    }

    /**
     * 初始化配置
     *
     * @param context
     */
    public static void initConfig(Context context) {
        mRequestQueue = Volley.newRequestQueue(context, new OkHttpClientStack(new OkHttpClient()));
        SharedPreferencesTools.initConfig(context);
        SharedPreferencesTools.setPriorityByDefault(true);
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
