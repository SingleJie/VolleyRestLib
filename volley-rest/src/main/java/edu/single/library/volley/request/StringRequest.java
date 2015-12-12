package edu.single.library.volley.request;

import com.wenjackp.android.lib.utils.EmptyUtils;

import java.io.UnsupportedEncodingException;

import edu.single.library.volley.CallBackListener;
import edu.single.library.volley.NetworkResponse;
import edu.single.library.volley.RequestParams;
import edu.single.library.volley.Response;
import edu.single.library.volley.VolleyLog;
import edu.single.library.volley.interfaces.OnPreHandlerListener;
import edu.single.library.volley.toolbox.HttpHeaderParser;

/**
 * 请求返回String
 *
 * @author WenJackp
 * @version 1.4
 */
public class StringRequest extends SimpleBaseRequest<String> {

    /**
     * 回调事件
     */
    private CallBackListener<String> callBackListener;

    /**
     * 在解析之前预处理
     */
    private OnPreHandlerListener mPreHandlerListener;

    /**
     * 是否开启缓存
     */
    private boolean cacheEnable;

    /**
     * 请求参数
     */
    private RequestParams urlParams;

    /**
     * StringRequest
     *
     * @param url              网络请求地址
     * @param urlParams        请求参数
     * @param callBackListener 回调事件
     */
    public StringRequest(final String url, final RequestParams urlParams, final CallBackListener<String> callBackListener) {
        super(url, urlParams, new ErrorCacheListener(urlParams, url, callBackListener, new OnResultListener<String>() {
            @Override
            public String onResult(String json) {
                return json;
            }
        }), callBackListener);

        if (urlParams != null) {
            this.mPreHandlerListener = urlParams.getPreHandlerListener();
            this.callBackListener = callBackListener;
            this.urlParams = urlParams;
            this.cacheEnable = urlParams.getCacheEnable();
        }
    }

    @Override
    protected Response<String> parseNetworkResponse(NetworkResponse response) {
        String json;

        try {
            json = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
        } catch (UnsupportedEncodingException e) {
            json = new String(response.data);
        }
        VolleyLog.logEMsg("Response Data : " + json);

        if (callBackListener != null) {
            callBackListener.onStringResult(json);
        }

        if (mPreHandlerListener != null) {
            json = mPreHandlerListener.onPreHandler(json);
        }

        if (cacheEnable && !EmptyUtils.emptyOfString(json)) {

            String url = null;

            switch (getMethod()) {
                case Method.POST:

                    url = this.getUrl() + "?" + new String(encodeParameters(this.urlParams.getParams(), urlParams.getEncodeType()));
                    break;

                case Method.GET:

                    url = this.getUrl();
                    break;
            }

            //本地缓存
            saveCacheData(url, json);
        }

        return Response.success(json, HttpHeaderParser.parseCacheHeaders(response));
    }

    @Override
    protected void deliverResponse(String response) {
        if (callBackListener != null) {
            callBackListener.onResponse(response);
        }
    }
}
