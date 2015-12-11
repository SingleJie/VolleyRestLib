package com.wenjackp.volley.rest.example;

import com.wenjackp.android.lib.utils.EmptyUtils;
import com.wenjackp.android.lib.utils.LogUtils;

import java.lang.reflect.Type;

import edu.single.library.volley.CallBackListener;
import edu.single.library.volley.IParseTypeListener;
import edu.single.library.volley.Request;
import edu.single.library.volley.RequestParams;
import edu.single.library.volley.VolleyRest;
import edu.single.library.volley.request.GsonRequest;

/**
 * Created by Administrator on 2015/10/12.
 */
public class AllDao extends BaseDao {

    private static AllDao instance;

    public static AllDao getInstance() {
        if (instance == null) {
            instance = new AllDao();
        }
        return instance;
    }

    /**
     * 获取三维家方案分类
     *
     * @param mListener
     */
    public void getSVJSchemeCategory(CallBackListener<Object> mListener) {
        RequestParams mParams = newParams();
        String jsonParams = buildParamsString();
        mParams.put("Params", jsonParams);
        mParams.put("Command", UrlUtils.ACTION_SCHEME_CATEGORY);
        mParams.setCacheOnDisk(true);
        mParams.setParseTypeListener(new IParseTypeListener() {
            @Override
            public Type getParseType(String json) {
                return buildType(DefaultModel.class, SchemeCategoryModel.class);
            }
        });
        GsonRequest mRequest = new GsonRequest(HttpAddressUtils.HTTP_INTERFACE_TEXT, mParams, mListener);
        VolleyRest.getRequestQueue().add(mRequest);
    }


    /**
     * 用户登录
     *
     * @param username
     * @param pwd
     * @param mListener
     */
    public void userLogin(String username, String pwd, CallBackListener<Object> mListener) {
        RequestParams mPrams = newParams();
        mPrams.setRequestMethod(Request.Method.POST);
        final String jsonParams = buildParamsString(
                "account", username,
                "password",pwd);
        LogUtils.logEMsg("JsonParams :" + jsonParams);
        mPrams.put("Params", jsonParams);
        mPrams.put("Command", UrlUtils.USER_LOGIN);
        mPrams.setParseTypeListener(new IParseTypeListener() {
            @Override
            public Type getParseType(String json) {

                if (EmptyUtils.emptyOfString(json)){
                    return null;
                }

                    if (json.indexOf("\"JSON\":\"\"") != -1) {
                        return buildType(DefaultModel.class, String.class);
                    } else {
                        return buildType(DefaultModel.class, UserInfoBean.class);
                    }
            }
        });
        mPrams.setCacheOnDisk(true);
        addGsonRequest(HttpAddressUtils.HTTP_INTERFACE_TEXT, mPrams, mListener);
    }
}
