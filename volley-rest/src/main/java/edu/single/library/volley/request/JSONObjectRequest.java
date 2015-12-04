/*
 * Copyright (C) 2011 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package edu.single.library.volley.request;

import com.wenjackp.android.lib.utils.EmptyUtils;

import org.apache.http.conn.HttpHostConnectException;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import edu.single.library.volley.CallBackListener;
import edu.single.library.volley.NetworkResponse;
import edu.single.library.volley.ParseError;
import edu.single.library.volley.RequestParams;
import edu.single.library.volley.Response;
import edu.single.library.volley.VolleyLog;
import edu.single.library.volley.error.VolleyError;
import edu.single.library.volley.toolbox.HttpHeaderParser;

/**
 * JsonObjectRequest
 *
 * @author Single
 * @version 1.3
 * @category
 */
public class JSONObjectRequest extends JSONRequest<JSONObject> {

    private final CallBackListener<JSONObject> callBackListener;
    private final boolean cacheEnable;

    public JSONObjectRequest(final String url, final RequestParams urlParams, final CallBackListener<JSONObject> callBackListener) {
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
                            try {
                                callBackListener.onResponse(new JSONObject(json));
                            } catch (JSONException e) {
                                callBackListener.onErrorResponse(new ParseError(e));
                            }
                        }
                    } else {
                        callBackListener.onErrorResponse(error);
                    }
                }
            }
        }, callBackListener);

        this.callBackListener = callBackListener;
        this.cacheEnable = urlParams == null ? false : urlParams.getCacheEnable();
    }

    @Override
    protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {

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
            saveCacheData(this.getUrl(), json);
        }

        try {
            return Response.success(new JSONObject(json), HttpHeaderParser.parseCacheHeaders(response));
        } catch (JSONException e) {
            return Response.error(new ParseError(e));
        }
    }

    @Override
    protected void deliverResponse(JSONObject response) {
        if (callBackListener != null) {
            callBackListener.onResponse(response);
        }
    }
}
