package edu.single.library.volley.toolbox;

import com.squareup.okhttp.Headers;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.MultipartBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Protocol;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.ResponseBody;
import com.wenjackp.android.lib.utils.EmptyUtils;
import com.wenjackp.android.lib.utils.LogUtils;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ProtocolVersion;
import org.apache.http.StatusLine;
import org.apache.http.entity.BasicHttpEntity;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicHttpResponse;
import org.apache.http.message.BasicStatusLine;

import java.io.IOException;
import java.util.Map;

import edu.single.library.volley.Request;
import edu.single.library.volley.error.AuthFailureError;
import edu.single.library.volley.interfaces.HttpStack;
import edu.single.library.volley.models.FileModels;

/**
 * Created by Single on 15-8-5.
 */
public class OkHttpClientStack implements HttpStack {

    private final static String HEADER_CONTENT_TYPE = "Content-Type";
    private final static String HEAD_BODY_CONTENT_TYPE = "Content-Disposition\", \"form-data; name=\"%s\"";
    private OkHttpClient mClient;

    public OkHttpClientStack(OkHttpClient mClient) {
        this.mClient = mClient;
    }

    @Override
    public HttpResponse performRequest(final Request<?> request, Map<String, String> additionalHeaders) throws IOException, AuthFailureError {

        com.squareup.okhttp.Request.Builder mRequestBuilder = new com.squareup.okhttp.Request.Builder();
        mRequestBuilder.url(request.getUrl());
        addHeaders(mRequestBuilder, additionalHeaders);
        getMethod(request, mRequestBuilder);
        Response okHttpResponse = mClient.newCall(mRequestBuilder.build()).execute();

        StatusLine responseStatus = new BasicStatusLine
                (
                        parseProtocol(okHttpResponse.protocol()),
                        okHttpResponse.code(),
                        okHttpResponse.message()
                );

        BasicHttpResponse response = new BasicHttpResponse(responseStatus);
        response.setEntity(entityFromOkHttpResponse(okHttpResponse));

        Headers responseHeaders = okHttpResponse.headers();

        for (int i = 0, len = responseHeaders.size(); i < len; i++) {
            final String name = responseHeaders.name(i), value = responseHeaders.value(i);
            if (name != null) {
                response.addHeader(new BasicHeader(name, value));
            }
        }

        return response;
    }

    private static HttpEntity entityFromOkHttpResponse(Response r) throws IOException {
        BasicHttpEntity entity = new BasicHttpEntity();
        ResponseBody body = r.body();

        entity.setContent(body.byteStream());
        entity.setContentLength(body.contentLength());
        entity.setContentEncoding(r.header("Content-Encoding"));

        if (body.contentType() != null) {
            entity.setContentType(body.contentType().type());
        }
        return entity;
    }

    private static ProtocolVersion parseProtocol(final Protocol p) {
        switch (p) {
            case HTTP_1_0:
                return new ProtocolVersion("HTTP", 1, 0);
            case HTTP_1_1:
                return new ProtocolVersion("HTTP", 1, 1);
            case SPDY_3:
                return new ProtocolVersion("SPDY", 3, 1);
            case HTTP_2:
                return new ProtocolVersion("HTTP", 2, 0);
        }
        throw new IllegalAccessError("Unkwown protocol");
    }

    /**
     * 添加Header
     *
     * @param mBuilder
     * @param mHeaders
     */
    private void addHeaders(com.squareup.okhttp.Request.Builder mBuilder, Map<String, String> mHeaders) {
        for (String key : mHeaders.keySet()) {
            mBuilder.addHeader(key, mHeaders.get(key));
        }
    }

    /**
     * 获得字符串提交方法名称
     *
     * @return
     * @throws AuthFailureError
     */
    private static void getMethod(final Request<?> request, com.squareup.okhttp.Request.Builder mBuilder) throws AuthFailureError {

        switch (request.getMethod()) {

            case Request.Method.DEPRECATED_GET_OR_POST:

                if (request.getPostBody() == null) {
                    mBuilder.get();
                } else {

                    RequestBody mBody = null;

                    if (request.hasFileParams()) {
                        //如果含有文件
                        MultipartBuilder mMultipartBuilder = new MultipartBuilder();
                        mMultipartBuilder.type(MultipartBuilder.FORM);

                        //添加文本参数
                        for (Map.Entry<String, String> mTempText : request.getParams().entrySet()) {
                            mMultipartBuilder.addPart(
                                    Headers.of("Content-Disposition", String.format("form-data; name=\"%s\"", mTempText.getKey())),
                                    RequestBody.create(null, mTempText.getValue()));
                            LogUtils.logEMsg("Key:" + mTempText.getKey() + "---" + mTempText.getValue());
                        }

                        //添加File文件
                        for (Map.Entry<String, FileModels> mTempFiles : request.getFileParams().entrySet()) {
                            FileModels item = mTempFiles.getValue();
                            String fileName = EmptyUtils.emptyOfString(item.dataName) ? item.data.getName() : item.dataName;
                            mMultipartBuilder.addPart(
                                    Headers.of("Content-Disposition", String.format("form-data; name=\"%s\"", fileName)),
                                    RequestBody.create(MediaType.parse("image/png"), mTempFiles.getValue().data));
                        }
                        mBody = mMultipartBuilder.build();
                    } else {
                        mBody = RequestBody.create(MediaType.parse(""), request.getPostBody());
                    }
                    mBuilder.addHeader(HEADER_CONTENT_TYPE, request.getPostBodyContentType());
                    mBuilder.method("POST", mBody);
                }

                break;

            case Request.Method.GET:

                mBuilder.get();

                break;

            case Request.Method.DELETE:

                break;
            case Request.Method.POST:

                break;
            case Request.Method.PUT:

                break;
            case Request.Method.HEAD:

                break;
            case Request.Method.OPTIONS:

                break;
            case Request.Method.TRACE:

                break;
            case Request.Method.PATCH:

                break;
            default:
                throw new IllegalStateException("Unknown request method.");
        }
    }
}
