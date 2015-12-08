package com.wenjackp.volley.rest.example;

import com.google.gson.Gson;
import com.wenjackp.android.lib.utils.EmptyUtils;
import com.wenjackp.android.lib.utils.LogUtils;

import org.json.JSONObject;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import edu.single.library.volley.CallBackListener;
import edu.single.library.volley.RequestParams;
import edu.single.library.volley.VolleyRest;
import edu.single.library.volley.interfaces.OnPreHandlerListener;
import edu.single.library.volley.request.GsonRequest;

/**
 * Created by Single on 2015/10/7.
 */
public class BaseDao {

    private static final String TAG = BaseDao.class.getSimpleName();
    protected static Gson mGson = new Gson();
    private static final String GSON_ERROR_PARRENT = "\"(\\{)(.*?)(\\})\"";
    private static final String GSON_ERROR_PARRENT_2 = " \"(\\[)(.*?)(\\])\"";

    /**
     * 创建Type类型
     *
     * @param raw  主类
     * @param args 泛型类
     * @return
     */
    protected ParameterizedType buildType(final Class raw, final Type... args) {
        return new ParameterizedType() {
            public Type getRawType() {
                return raw;
            }

            public Type[] getActualTypeArguments() {
                return args;
            }

            public Type getOwnerType() {
                return null;
            }
        };
    }

    /**
     * 创建Type类型
     *
     * @param raw  主类
     * @param args 泛型类
     * @return
     */
    protected ParameterizedType buildListType(final Class raw, final Type... args) {
        return new ParameterizedType() {
            public Type getRawType() {
                return raw;
            }

            public Type[] getActualTypeArguments() {
                return args;
            }

            public Type getOwnerType() {
                return null;
            }
        };
    }

    /**
     * 创建JSON格式字符串
     *
     * @param keyValues key and value 格式
     * @return
     */
    protected String buildParamsString(String... keyValues) {
        String buildJson = null;

        try {

            JSONObject json = new JSONObject();
            json.put("authCode", ConstantUtils.mAuthCode);

            int length = keyValues.length;
            if (length % 2 != 0) {
                throw new RuntimeException(" please set key and value");
            }

            for (int i = 0; i < length; i++) {
                if (i % 2 != 1)
                {
                    json.put(keyValues[i], keyValues[i + 1]);
                }
            }

            buildJson = json.toString();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return buildJson;
    }

    /**
     * 创建JSON格式字符串
     *
     * @param keyValues key and value 格式
     * @return
     */
    protected JSONObject buildParamsJson(String... keyValues) {

        JSONObject json = new JSONObject();

        try {
            json.put("authCode", ConstantUtils.mAuthCode);

            int length = keyValues.length;
            if (length % 2 != 0) {
                throw new RuntimeException(" please set key and value");
            }

            for (int i = 0; i < length; i++) {
                if (i % 2 != 1) {
                    json.put(keyValues[i], keyValues[i + 1]);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return json;
    }

    /**
     * 获得一个新的经过处理的RequestParams
     *
     * @return
     */
    protected RequestParams newParams() {
        RequestParams mParams = new RequestParams();
        mParams.setOnPreHandlerListener(new DefaultPreHandler());
        return mParams;
    }

    /**
     * 添加Gson请求
     *
     * @param url
     * @param mParams
     * @param mListener
     * @param <T>
     */
    protected <T> void addGsonRequest(String url, RequestParams mParams, CallBackListener<Object> mListener) {
        GsonRequest mRequest = new GsonRequest(url, mParams, mListener);
        VolleyRest.getRequestQueue().add(mRequest);
    }

    protected void logMsg(String msg) {
        LogUtils.logEMsg(TAG, msg);
    }

    public class DefaultPreHandler implements OnPreHandlerListener {

        @Override
        public String onPreHandler(String response) {
            String tempResponse = null;

            try {
                tempResponse = new JSONObject(response).toString();
                tempResponse = tempResponse.replace("\\","");
                Pattern mPattern = Pattern.compile(GSON_ERROR_PARRENT);
                Matcher matcher = mPattern.matcher(tempResponse);
                while (matcher.find()) {
                    String group = matcher.group();
                    tempResponse = tempResponse.replace(group, group.substring(1,group.length()-1));
                }

                mPattern = Pattern.compile(GSON_ERROR_PARRENT_2);
                matcher = mPattern.matcher(tempResponse);
                while (matcher.find()) {
                    String group = matcher.group();
                    tempResponse = tempResponse.replace(group,group.substring(1,group.length()-1));
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            return tempResponse;
        }
    }

    public class StringHandler implements OnPreHandlerListener{
        @Override
        public String onPreHandler(String response) {

            if(!EmptyUtils.emptyOfString(response)) {
                response = response.replace("\\", "");
                response = response.replace("\"\"", "\"");
            }
            return response;
        }
    }

//    public void addStringRequest(String url,RequestParams mParams,final CallBackListener<DefaultModel<String>> mListener){
//        StringRequest mRequest = new StringRequest(url, mParams,new CallBackListener<String>(){
//            @Override
//            public void onResponse(String response) {
//
//                if(mListener!=null){
//                    try {
//                        DefaultModel<String> model = new DefaultModel<>();
//                        JSONObject json = new JSONObject(response);
//                        model.Status = json.optInt("Status");
//
//                        if(model.Status==ConstantUtils.HTTP_REQUEST_SUCCESS){
//                            model.JSON = json.optString("JSON");
//                            model.JSON = model.JSON.replace("\"","");
//                        }else if(model.Status == ConstantUtils.HTTP_INFO_MSG){
//                            model.InfoMessage = json.optString("InfoMessage");
//                        }else{
//                            model.ErrorMessage = json.optString("ErrorMessage");
//                        }
//
//                        mListener.onResponse(model);
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                if(mListener!=null){
//                    mListener.onErrorResponse(error);
//                }
//            }
//        });
//
//        VolleyRest.getRequestQueue().add(mRequest);
//    }
}
