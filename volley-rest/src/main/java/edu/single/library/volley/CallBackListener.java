package edu.single.library.volley;

import edu.single.library.volley.error.VolleyError;

/**
 * 回调接口
 *
 * @param <T>
 * @author Single
 */
public class CallBackListener<T> implements Response.Listener<T>, Response.ErrorListener {

    @Override
    public void onResponse(T response) {

    }

    @Override
    public void onErrorResponse(VolleyError error) {

    }

    public void onStringResult(String result) {

    }

    public void onUploadResponse(long current, long total) {
    }

}
