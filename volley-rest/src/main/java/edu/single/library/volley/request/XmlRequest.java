package edu.single.library.volley.request;


import com.thoughtworks.xstream.XStream;
import com.wenjackp.android.lib.utils.EmptyUtils;

import org.apache.http.conn.HttpHostConnectException;

import edu.single.library.volley.CallBackListener;
import edu.single.library.volley.NetworkResponse;
import edu.single.library.volley.RequestParams;
import edu.single.library.volley.Response;
import edu.single.library.volley.VolleyLog;
import edu.single.library.volley.error.VolleyError;
import edu.single.library.volley.toolbox.HttpHeaderParser;

/**
 * XmlRequest
 *
 * @param <T>
 * @author Single
 * @version 1.2
 * @category XmlRequest
 */
public class XmlRequest<T> extends SimpleBaseRequest<T> {

    private final CallBackListener<T> callBackListener;
    private final static XStream mXStream = new XStream();
    private final boolean cacheEnable;
    private final RequestParams urlParams;

    public XmlRequest(final String url, final RequestParams urlParams, final CallBackListener<T> callBackListener) {
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
                            callBackListener.onResponse(((Response<T>) Response.success(mXStream.fromXML(json), HttpHeaderParser.parseCacheHeaders(error.networkResponse))).result);
                        }
                    } else {
                        callBackListener.onErrorResponse(error);
                    }
                }
            }
        }, callBackListener);

        this.urlParams = urlParams;
        this.callBackListener = callBackListener;
        this.cacheEnable = urlParams != null ? urlParams.getCacheEnable() : false;
    }

    @Override
    @SuppressWarnings("unchecked")
    protected Response<T> parseNetworkResponse(NetworkResponse response) {

        String json = null;

        try {
            json = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
            VolleyLog.logEMsg("Response Data : " + json);

            if (callBackListener != null) {
                callBackListener.onStringResult(json);
            }

            if (cacheEnable && !EmptyUtils.emptyOfString(json)) {
                saveCacheData(this.getUrl(), json);
            }

            Object obj = mXStream.fromXML(json);
            return (Response<T>) Response.success(obj, HttpHeaderParser.parseCacheHeaders(response));
        } catch (Exception ex) {
            return Response.error(new VolleyError(ex));
        }
    }

    @Override
    protected void deliverResponse(T response) {

        if (callBackListener != null) {
            callBackListener.onResponse(response);
        }
    }

    @Override
    public String getBodyContentType() {
        return (urlParams != null && urlParams.getContentType() != null) ? urlParams.getContentType() : String.format("application/xml; charset=%s", getParamsEncoding());
    }
}
