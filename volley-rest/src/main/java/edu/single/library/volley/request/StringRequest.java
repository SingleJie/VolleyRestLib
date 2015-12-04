package edu.single.library.volley.request;

import com.wenjackp.android.lib.utils.EmptyUtils;

import org.apache.http.conn.HttpHostConnectException;

import java.io.UnsupportedEncodingException;

import edu.single.library.volley.CallBackListener;
import edu.single.library.volley.NetworkResponse;
import edu.single.library.volley.RequestParams;
import edu.single.library.volley.Response;
import edu.single.library.volley.VolleyLog;
import edu.single.library.volley.error.VolleyError;
import edu.single.library.volley.toolbox.HttpHeaderParser;

/**
 * 请求返回String
 *
 * @author Single
 * @version 1.2
 * @category 请求网络并用返回String对象
 */
public class StringRequest extends SimpleBaseRequest<String> {

    private final CallBackListener<String> callBackListener;
    private final boolean cacheEnable;

    public StringRequest(final String url, final RequestParams urlParams, final CallBackListener<String> callBackListener) {
        super(url, urlParams, new Response.ErrorListener() {

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
                            callBackListener.onResponse(json);
                        }
                    } else {
                        callBackListener.onErrorResponse(error);
                    }
                }
            }
        }, callBackListener);
        this.callBackListener = callBackListener;
        this.cacheEnable = urlParams.getCacheEnable();
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

        if (cacheEnable && !EmptyUtils.emptyOfString(json)) {
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
