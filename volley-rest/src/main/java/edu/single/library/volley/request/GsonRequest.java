package edu.single.library.volley.request;

import com.google.gson.Gson;
import com.wenjackp.android.lib.utils.EmptyUtils;
import com.wenjackp.android.lib.utils.SharedPreferencesTools;

import java.lang.reflect.Type;
import java.util.Map;

import edu.single.library.volley.CallBackListener;
import edu.single.library.volley.IParseClassListener;
import edu.single.library.volley.IParseTypeListener;
import edu.single.library.volley.NetworkResponse;
import edu.single.library.volley.RequestParams;
import edu.single.library.volley.Response;
import edu.single.library.volley.VolleyLog;
import edu.single.library.volley.error.AuthFailureError;
import edu.single.library.volley.error.VolleyError;
import edu.single.library.volley.toolbox.HttpHeaderParser;

/**
 * Gson请求
 *
 * @author Single
 * @version 1.5
 * @category 请求网络并用Gson返回指定对象
 */
public class GsonRequest extends SimpleBaseRequest<Object> {

    private final static Gson gson = new Gson();
    private CallBackListener<Object> callBackListener;
    private RequestParams urlParams;
    private boolean cacheEnable;
    private IParseClassListener mParseClassListener;
    private IParseTypeListener mParseTypeListener;

    /**
     * Gson请求
     * @param url 网络请求地址
     * @param urlParams 请求参数
     * @param callBackListener 回调事件
     */
    public GsonRequest(final String url, final RequestParams urlParams, final CallBackListener<Object> callBackListener) {
        super(url, urlParams, new ErrorCacheListener(urlParams, url, callBackListener, new OnResultListener<Object>() {
            @Override
            public Object onResult(String json) {
                IParseClassListener mIParseClassListener = urlParams.getParseClassListener();
                if (mIParseClassListener != null) {
                    return gson.fromJson(json, mIParseClassListener.getParseClass(json));
                }

                IParseTypeListener mIParseTypeListener = urlParams.getParseTypeListener();
                if (mIParseTypeListener != null) {
                    return gson.fromJson(json, mIParseTypeListener.getParseType(json));
                }
                return null;
            }
        }), callBackListener);

        if (urlParams != null) {
            this.urlParams = urlParams;
            this.callBackListener = callBackListener;
            this.mParseTypeListener = urlParams.getParseTypeListener();
            this.mParseClassListener = urlParams.getParseClassListener();
            this.cacheEnable = urlParams.getCacheEnable();
            SharedPreferencesTools.setPriorityByDefault(true);
        }
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        return (urlParams != null && urlParams.getHeaders() != null) ? urlParams.getHeaders() : super.getHeaders();
    }

    @Override
    public Map<String, String> getParams() throws AuthFailureError {
        return (urlParams != null && urlParams.getParams() != null) ? urlParams.getParams() : super.getParams();
    }

    @Override
    public CallBackListener<Object> getListener() {
        return callBackListener;
    }

    @Override
    protected void deliverResponse(Object response) {
        if (null != callBackListener) {
            callBackListener.onResponse(response);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    protected Response<Object> parseNetworkResponse(NetworkResponse response) {

        String json = null;

        try {
            json = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
            VolleyLog.logEMsg("Response Data : " + json);

            if (urlParams.getPreHandlerListener() != null) {
                json = urlParams.getPreHandlerListener().onPreHandler(json);
                VolleyLog.logEMsg(" PreHandler Data : " + json);
            }

            if (callBackListener != null) {
                //回调返回的字符串
                callBackListener.onStringResult(json);
            }

            if (cacheEnable && !EmptyUtils.emptyOfString(json)) {
                String url = null;

                switch (getMethod()) {
                    case Method.POST:

                        url = this.getUrl() + "?" + new String(encodeParameters(urlParams.getParams(), urlParams.getEncodeType()));
                        break;

                    case Method.GET:

                        url = this.getUrl();
                        break;
                }

                VolleyLog.logEMsg("保存Key:" + url);
                saveCacheData(url, json);
            }

            if (mParseClassListener != null) {
                Class clazz = mParseClassListener.getParseClass(json);
                return Response.success(gson.fromJson(json, clazz), HttpHeaderParser.parseCacheHeaders(response));
            }

            if (mParseTypeListener != null) {
                Type typeOfT = mParseTypeListener.getParseType(json);
                return Response.success(gson.fromJson(json, typeOfT), HttpHeaderParser.parseCacheHeaders(response));
            }

            return null;

        } catch (Exception e) {
            return Response.error(new VolleyError(e));
        }
    }
}