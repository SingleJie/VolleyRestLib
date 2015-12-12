package edu.single.library.volley.request;

import com.wenjackp.android.lib.utils.EmptyUtils;

import java.io.UnsupportedEncodingException;

import edu.single.library.volley.CallBackListener;
import edu.single.library.volley.NetworkResponse;
import edu.single.library.volley.RequestParams;
import edu.single.library.volley.Response;
import edu.single.library.volley.VolleyLog;
import edu.single.library.volley.error.VolleyError;
import edu.single.library.volley.interfaces.OnPreHandlerListener;
import edu.single.library.volley.toolbox.HttpHeaderParser;

/**
 * 请求返回String
 *
 * @author WenJackp
 * @version 1.3
 * @category 请求网络并用返回String对象
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

    private RequestParams urlParams;

    public StringRequest(final String url, final RequestParams urlParams, final CallBackListener<String> callBackListener) {
        super(url, urlParams, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

                VolleyLog.logEMsg("Error : " + error);
                boolean networkIsConnected = (error.getCause().toString().indexOf("java.net.UnknownHostException")==-1);
                VolleyLog.logEMsg("网络状态 : "+ networkIsConnected);

                if (callBackListener != null) {

                    if (!networkIsConnected && urlParams != null && urlParams.getCacheEnable() ) {
                        //得到Key
                        String json = (url.lastIndexOf("?")==-1?url+"?":url)  + ((urlParams.getParams() != null) ? new String(encodeParameters(urlParams.getParams(), urlParams.getEncodeType())) : "");
                        VolleyLog.logEMsg("Cache Key : " + json);
                        json = getCacheData(json);
                        VolleyLog.logEMsg("Cache Value : " + json);

                        if (EmptyUtils.emptyOfString(json)) {
                            //如果读取的本地数据还是为空则返回异常
                            callBackListener.onErrorResponse(error);
                        } else {
                            callBackListener.onResponse(json);
                        }
                    } else {
                        callBackListener.onErrorResponse(error);
                    }
                }
            }

        }, callBackListener);

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

            switch (getMethod()){
                case Method.POST:

                 //   url = this.getUrl() + "?" + new String(encodeParameters(getParams(), getEncodeType());
                    break;

                case Method.GET:

                    url = this.getUrl();
                    break;
            }

            //本地缓存
            saveCacheData(this.getUrl(), json);
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
