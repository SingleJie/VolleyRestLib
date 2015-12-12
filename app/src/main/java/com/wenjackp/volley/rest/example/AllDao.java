package com.wenjackp.volley.rest.example;

import java.lang.reflect.Type;

import edu.single.library.volley.CallBackListener;
import edu.single.library.volley.IParseTypeListener;
import edu.single.library.volley.RequestParams;
import edu.single.library.volley.VolleyRest;
import edu.single.library.volley.request.GsonRequest;
import edu.single.library.volley.request.StringRequest;

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
    public void getSVJSchemeCategoryWithGson(CallBackListener<Object> mListener) {
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
     * 获取三维家方案分类
     *
     * @param mListener
     */
    public void getSVJSchemeCategoryWithString(CallBackListener<String> mListener) {
        RequestParams mParams = newParams();
        String jsonParams = buildParamsString();
        mParams.put("Params", jsonParams);
        mParams.put("Command", UrlUtils.ACTION_SCHEME_CATEGORY);
        mParams.setCacheOnDisk(true);
        StringRequest mRequest = new StringRequest(HttpAddressUtils.HTTP_INTERFACE_TEXT, mParams, mListener);
        VolleyRest.getRequestQueue().add(mRequest);
    }
}
