package edu.single.library.volley.request;
import com.google.gson.Gson;
import com.wenjackp.android.lib.utils.EmptyUtils;
import com.wenjackp.android.lib.utils.SharedPreferencesTools;

import org.apache.http.conn.HttpHostConnectException;

import java.lang.reflect.Type;
import java.util.Map;

import edu.single.library.volley.CallBackListener;
import edu.single.library.volley.NetworkResponse;
import edu.single.library.volley.RequestParams;
import edu.single.library.volley.Response;
import edu.single.library.volley.Response.ErrorListener;
import edu.single.library.volley.VolleyLog;
import edu.single.library.volley.error.AuthFailureError;
import edu.single.library.volley.error.VolleyError;
import edu.single.library.volley.toolbox.HttpHeaderParser;

/**
 * Gson请求
 *
 * @param <T>
 * @author Single
 * @version 1.4
 * @category 请求网络并用Gson返回指定对象
 */
public class GsonRequest<T> extends SimpleBaseRequest<T> {

    private final static Gson gson = new Gson();
    private final Class<T> clazz;
    private final Type typeOfT;
    private CallBackListener<T> callBackListener;
    private final RequestParams urlParams;
    private final boolean cacheEnable;

    /**
     * Gson请求
     * @param url
     * @param urlParams
     * @param callBackListener
     */
    public GsonRequest(final String url, final RequestParams urlParams, final CallBackListener<T> callBackListener) {
        super(url, urlParams, new ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                if (callBackListener != null) {
                    if ((error.getCause() instanceof HttpHostConnectException) && urlParams != null && urlParams.getCacheEnable()) {
                        //连接超时的情况,读取本地存储的资源
                        String json = url + ((urlParams != null && urlParams.getParams() != null) ? new String(encodeParameters(urlParams.getParams(), urlParams.getEncodeType())) : "");
                        json = getCacheData(json);

                        if (EmptyUtils.emptyOfString(json)) {
                            //如果读取的本地数据还是为空则返回异常
                            callBackListener.onErrorResponse(error);
                        } else {
                            try {
                                Type typeOfT = (urlParams != null && urlParams.getGsonCallBackByType() != null) ? urlParams.getGsonCallBackByType() : null;

                                if (typeOfT != null) {
                                    callBackListener.onResponse(((Response<T>) Response.success(gson.fromJson(json, typeOfT), HttpHeaderParser.parseCacheHeaders(error.networkResponse))).result);
                                }

                                Class<T> clazz = (urlParams != null && urlParams.getGsonCallBackByClass() != null) ? (Class<T>) urlParams.getGsonCallBackByClass() : null;

                                if (clazz != null) {
                                    callBackListener.onResponse(Response.success(gson.fromJson(json, clazz), HttpHeaderParser.parseCacheHeaders(error.networkResponse)).result);
                                }
                            } catch (Exception ex) {
                                callBackListener.onErrorResponse(new VolleyError(ex));
                            }
                        }
                    } else {
                        callBackListener.onErrorResponse(error);
                    }
                }
            }
        }, callBackListener);

        this.urlParams = urlParams;
        this.callBackListener = callBackListener;
        this.clazz = (urlParams != null && urlParams.getGsonCallBackByClass() != null) ? (Class<T>) urlParams.getGsonCallBackByClass() : null;
        this.typeOfT = (urlParams != null && urlParams.getGsonCallBackByType() != null) ? urlParams.getGsonCallBackByType() : null;
        this.cacheEnable = urlParams != null ? urlParams.getCacheEnable() : false;
        SharedPreferencesTools.setPriorityByDefault(true);
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
    public CallBackListener<T> getListener() {
        return callBackListener;
    }

    @Override
    protected void deliverResponse(T response) {
        if (null != callBackListener) {
            callBackListener.onResponse(response);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        String json = null;

        try {
            json = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
            VolleyLog.logEMsg("Response Data : " + json);

            if(urlParams.getPreHandlerListener()!=null){
                json = urlParams.getPreHandlerListener().onPreHandler(json);
                VolleyLog.logEMsg(" PreHandler Data : " + json);
            }

            if (callBackListener != null) {
                //回调返回的字符串
                callBackListener.onStringResult(json);
            }

            if (cacheEnable && !EmptyUtils.emptyOfString(json)) {
                saveCacheData(this.getUrl(), json);
            }

            if (typeOfT != null) {
                return (Response<T>) Response.success(gson.fromJson(json, typeOfT), HttpHeaderParser.parseCacheHeaders(response));
            }

            return Response.success(gson.fromJson(json, clazz), HttpHeaderParser.parseCacheHeaders(response));

        } catch (Exception e) {
            return Response.error(new VolleyError(e));
        }
    }
}