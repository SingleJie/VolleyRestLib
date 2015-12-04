package edu.single.library.volley.request;

import java.io.UnsupportedEncodingException;

import edu.single.library.volley.error.AuthFailureError;
import edu.single.library.volley.CallBackListener;
import edu.single.library.volley.NetworkResponse;
import edu.single.library.volley.RequestParams;
import edu.single.library.volley.Response;
import edu.single.library.volley.Response.ErrorListener;
import edu.single.library.volley.VolleyLog;

abstract class JSONRequest<T> extends SimpleBaseRequest<T> {

    private final String requestBody;
    private final RequestParams urlParams;

    public JSONRequest(String url, RequestParams urlParams, ErrorListener mErrorListener, CallBackListener<T> mListener) {
        super(url, urlParams, mErrorListener, mListener);
        this.urlParams = urlParams;
        requestBody = urlParams == null ? null : (urlParams.getJSONObject() == null ? urlParams.getJSONArray().toString() : urlParams.getJSONObject().toString());
    }

    @Override
    public String getBodyContentType() {
        return (urlParams != null && urlParams.getContentType() != null) ? urlParams.getContentType() : String.format("application/json; charset=%s", getParamsEncoding());
    }

    @Override
    public byte[] getBody() throws AuthFailureError {
        try {
            return requestBody == null ? super.getBody() : requestBody.getBytes(getParamsEncoding());
        } catch (UnsupportedEncodingException e) {
            VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", requestBody, getParamsEncoding());
            return super.getBody();
        }
    }

    @Override
    protected abstract Response<T> parseNetworkResponse(NetworkResponse response);

}
