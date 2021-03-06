package edu.single.library.volley.request;

import com.wenjackp.android.lib.utils.EmptyUtils;
import com.wenjackp.android.lib.utils.SharedPreferencesTools;

import java.util.Iterator;
import java.util.Map;

import edu.single.library.volley.CallBackListener;
import edu.single.library.volley.NetworkResponse;
import edu.single.library.volley.Request;
import edu.single.library.volley.RequestParams;
import edu.single.library.volley.Response;
import edu.single.library.volley.Response.ErrorListener;
import edu.single.library.volley.VolleyLog;
import edu.single.library.volley.error.AuthFailureError;
import edu.single.library.volley.error.VolleyError;
import edu.single.library.volley.interfaces.RetryPolicy;
import edu.single.library.volley.models.ByteArrayModels;
import edu.single.library.volley.models.FileModels;
import edu.single.library.volley.models.InputStreamModels;

/**
 * 请求父类
 *
 * @param <T> 泛型
 * @author Single
 * @version 1.3
 */
public abstract class SimpleBaseRequest<T> extends Request<T> {

    private final RequestParams urlParams;
    private final CallBackListener<T> mListener;

    public SimpleBaseRequest(String url, RequestParams urlParams, final ErrorListener mErrorListener, CallBackListener<T> mListener) {
        super(urlParams == null ? Method.GET : urlParams.getRequestMethod(), url, mErrorListener);
        this.urlParams = urlParams;
        this.mListener = mListener;
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
    public Map<String, FileModels> getFileParams() {
        return (urlParams != null && urlParams.getFileParams() != null) ? urlParams.getFileParams() : super.getFileParams();
    }

    @Override
    public Map<String, InputStreamModels> getStreamParams() {
        return (urlParams != null && urlParams.getStreamParams() != null) ? urlParams.getStreamParams() : super.getStreamParams();
    }

    @Override
    public Map<String, ByteArrayModels> getByteArrayParams() {
        return (urlParams != null && urlParams.getByteArrayParams() != null) ? urlParams.getByteArrayParams() : super.getByteArrayParams();
    }

    @Override
    public boolean hasFileParams() {
        return urlParams != null ? urlParams.hashFileParams() : false;
    }

    @Override
    public edu.single.library.volley.Request.Priority getPriority() {
        return (urlParams != null && urlParams.getPriority() != null) ? urlParams.getPriority() : super.getPriority();
    }

    @Override
    public RetryPolicy getRetryPolicy() {
        return (urlParams != null && urlParams.getRetryPolicy() != null) ? urlParams.getRetryPolicy() : super.getRetryPolicy();
    }

    @Override
    public String getParamsEncoding() {
        return (urlParams != null && urlParams.getEncodeType() != null) ? urlParams.getEncodeType() : super.getParamsEncoding();
    }

    @Override
    public String getBodyContentType() {
        return (urlParams != null && urlParams.getContentType() != null) ? urlParams.getContentType() : super.getBodyContentType();
    }

    @Override
    public CallBackListener<T> getListener() {
        return mListener;
    }

    protected static String getCacheData(String key) {
        return SharedPreferencesTools.getString(key);
    }

    protected static void saveCacheData(String key, String value) {
        SharedPreferencesTools.putString(key, value);
    }

    public String buildRequestLink() {
        StringBuilder mBuilder = new StringBuilder();
        mBuilder.append("*******************************************************\n");

        try {
            switch (getMethod()) {
                case Method.DELETE:
                case Method.TRACE:
                case Method.PATCH:
                case Method.PUT:
                case Method.HEAD:
                case Method.DEPRECATED_GET_OR_POST:
                case Method.OPTIONS:
                case Method.POST:
                    mBuilder.append("RequestLink : ");
                    mBuilder.append(getUrl());
                    mBuilder.append("\n");
                    mBuilder.append("RequestParams : ");
                    mBuilder.append(getEncodedUrlParams());
                    break;

                case Method.GET:
                    mBuilder.append("RequestLink : ");
                    mBuilder.append(getUrl());
                    break;
            }

            debugRequestInfo(mBuilder);

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        mBuilder.append("\n*******************************************************");
        return mBuilder.toString();
    }

    private void debugRequestInfo(StringBuilder mBuilder) throws Exception {

        mBuilder.append("\n");
        mBuilder.append("RequestMethod : ");
        mBuilder.append(getRequestNameByType(getMethod()));
        mBuilder.append("\n");
        mBuilder.append("Headers : ");
        for (Iterator<Map.Entry<String, String>> it = this.getHeaders().entrySet().iterator(); it.hasNext(); ) {
            Map.Entry<String, String> mItem = it.next();
            mBuilder.append(" ( " + mItem.getKey() + " , " + mItem.getValue() + " ) ");
        }
        mBuilder.append("\n");
        mBuilder.append("EncodeingType : ");
        mBuilder.append(getParamsEncoding());
        mBuilder.append("\n");
        mBuilder.append("ContentType : ");
        mBuilder.append(getBodyContentType());
    }

    private String getRequestNameByType(int type) {

        String strName = null;

        switch (type) {
            case Method.POST:

                strName = "POST";
                break;

            case Method.GET:

                strName = "GET";
                break;

            case Method.DELETE:

                strName = "DELETE";
                break;

            case Method.DEPRECATED_GET_OR_POST:

                strName = "DEPRECATED_GET_OR_POST";
                break;

            case Method.HEAD:

                strName = "HEAD";
                break;

            case Method.PUT:

                strName = "PUT";
                break;

            case Method.OPTIONS:

                strName = "OPTIONS";
                break;

            case Method.PATCH:

                strName = "PATCH";
                break;

            case Method.TRACE:

                strName = "TRACE";
                break;

            default:

                strName = "未知类型 : " + type;
                break;
        }

        return strName;
    }

    @Override
    protected abstract Response<T> parseNetworkResponse(NetworkResponse response);

    public static class ErrorCacheListener implements Response.ErrorListener {

        private RequestParams urlParams;
        private String url;
        private CallBackListener callBackListener;
        private OnResultListener mResultListener;

        public ErrorCacheListener(RequestParams urlParams, String url, CallBackListener callBackListener, OnResultListener mResultListener) {
            this.urlParams = urlParams;
            this.url = url;
            this.callBackListener = callBackListener;
            this.mResultListener = mResultListener;
        }

        @Override
        public void onErrorResponse(VolleyError error) {

            VolleyLog.logEMsg("Error : " + error);
            boolean networkIsConnected = (error.getCause().toString().indexOf("java.net.UnknownHostException") == -1);
            VolleyLog.logEMsg("网络状态 : " + networkIsConnected);

            if (callBackListener != null) {

                if (!networkIsConnected && urlParams != null && urlParams.getCacheEnable()) {
                    //得到Key
                    String json = (url.lastIndexOf("?") == -1 ? url + "?" : url) + ((urlParams.getParams() != null) ? new String(encodeParameters(urlParams.getParams(), urlParams.getEncodeType())) : "");
                    VolleyLog.logEMsg("Cache Key : " + json);
                    json = getCacheData(json);
                    VolleyLog.logEMsg("Cache Value : " + json);

                    if (EmptyUtils.emptyOfString(json)) {
                        //如果读取的本地数据还是为空则返回异常
                        callBackListener.onErrorResponse(error);
                    } else {
                        callBackListener.onResponse(mResultListener.onResult(json));
                    }
                } else {
                    callBackListener.onErrorResponse(error);
                }
            }
        }
    }

    public interface OnResultListener<T> {
        T onResult(String json);
    }
}
