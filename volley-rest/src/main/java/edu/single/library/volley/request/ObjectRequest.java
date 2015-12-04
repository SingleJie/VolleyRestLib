package edu.single.library.volley.request;

import com.google.gson.Gson;
import com.wenjackp.android.lib.utils.EmptyUtils;
import com.wenjackp.android.lib.utils.SharedPreferencesTools;

import org.apache.http.conn.HttpHostConnectException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.Map;

import edu.single.library.volley.CallBackListener;
import edu.single.library.volley.NetworkResponse;
import edu.single.library.volley.RequestParams;
import edu.single.library.volley.Response;
import edu.single.library.volley.Response.ErrorListener;
import edu.single.library.volley.enums.RequestType;
import edu.single.library.volley.enums.ResponseType;
import edu.single.library.volley.error.AuthFailureError;
import edu.single.library.volley.error.VolleyError;
import edu.single.library.volley.toolbox.HttpHeaderParser;

/**
 * ObjectRequest
 *
 * @param <T>
 */
public class ObjectRequest<T> extends SimpleBaseRequest<T> {

    private final static Gson gson = new Gson();
    private final Class<T> clazz;
    private final Type typeOfT;
    private CallBackListener<T> callBackListener;
    private final RequestParams urlParams;
    private final boolean cacheEnable;

    /**
     * ObjectRequest
     *
     * @param url
     * @param urlParams
     * @param callBackListener
     */
    @SuppressWarnings("unchecked")
    public ObjectRequest(final String url, final RequestParams urlParams, final CallBackListener<T> callBackListener) {
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

    @Override
    public String getBodyContentType() {
        String contentType = null;

        RequestType mType = urlParams.getRequestType();

        switch (mType) {
            case JSONARRAY:
            case JSONOBJECT:

                contentType = String.format("application/json; charset=%s", getParamsEncoding());

                break;

            case XML:

                contentType = String.format("application/xml; charset=%s", getParamsEncoding());
                break;

            case STRING:
            default:

                contentType = String.format("application/x-www-form-urlencoded; charset=%s", getParamsEncoding());
                break;
        }

        return contentType;
    }

    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {

        try {
            String json = new String(response.data, HttpHeaderParser.parseCharset(response.headers));

            if (callBackListener != null) {
                //回调返回的字符串
                callBackListener.onStringResult(json);
            }

            if (cacheEnable && !EmptyUtils.emptyOfString(json)) {
                saveCacheData(this.getUrl(), json);
            }

            ResponseType mType = urlParams.getResponseType();

            switch (mType) {
                case STRING:

                    return parseForString(json, response);

                case GSON:

                    return parseForGson(json, response);

                case XML:

                    return parseForXml(json, response);

                case JSONOBJECT:

                    return parseForJSONObject(json, response);

                case JSONARRAY:

                    return parseForJSONArray(json, response);

                default:

                    return null;
            }


        } catch (UnsupportedEncodingException e) {
            return Response.error(new VolleyError(e));
        } catch (JSONException e) {
            return Response.error(new VolleyError(e));
        }
    }

    /**
     * 解析Gson对象
     *
     * @param result
     * @param response
     * @return
     * @throws Exception
     */
    private Response<T> parseForGson(String result, NetworkResponse response) {

        if (typeOfT != null) {
            return (Response<T>) Response.success(gson.fromJson(result, typeOfT), HttpHeaderParser.parseCacheHeaders(response));
        }
        return Response.success(gson.fromJson(result, clazz), HttpHeaderParser.parseCacheHeaders(response));
    }

    /**
     * 解析String对象
     *
     * @param result
     * @param response
     * @return
     */
    private Response<T> parseForString(String result, NetworkResponse response) {
        return (Response<T>) Response.success(result, HttpHeaderParser.parseCacheHeaders(response));
    }

    /**
     * 解析Xml对象
     *
     * @param response
     * @return
     */
    private Response<T> parseForXml(String result, NetworkResponse response) {
        Object obj = urlParams.getXStream().fromXML(result);
        return (Response<T>) Response.success(obj, HttpHeaderParser.parseCacheHeaders(response));
    }

    /**
     * 解析JSONObject对象
     *
     * @param result
     * @param response
     * @return
     * @throws JSONException
     */
    private Response<T> parseForJSONObject(String result, NetworkResponse response) throws JSONException {
        return (Response<T>) Response.success(new JSONObject(result), HttpHeaderParser.parseCacheHeaders(response));
    }

    /**
     * 解析JSONArray对象
     *
     * @param result
     * @param response
     * @return
     */
    private Response<T> parseForJSONArray(String result, NetworkResponse response) throws JSONException {
        return (Response<T>) Response.success(new JSONArray(result), HttpHeaderParser.parseCacheHeaders(response));
    }


}
