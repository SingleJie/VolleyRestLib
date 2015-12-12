package com.wenjackp.volley.rest.example;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.wenjackp.android.lib.utils.LogUtils;

import edu.single.library.volley.CallBackListener;
import edu.single.library.volley.VolleyRest;

/**
 * Created by Administrator on 2015/12/7.
 */
public class VolleyRestExample extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        VolleyRest.initConfig(this);
        LogUtils.initConfig(this, true);

//        AllDao
//                .getInstance()
//                .getSVJSchemeCategoryWithString(new CallBackListener<String>(){
//                    @Override
//                    public void onResponse(String response) {
//                        LogUtils.logEMsg("Response : "+ response);
//                    }
//
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        LogUtils.logEMsg("Error : "+ error);
//                    }
//                });


        AllDao
                .getInstance()
                .getSVJSchemeCategoryWithGson(new CallBackListener<Object>(){
                    @Override
                    public void onResponse(Object response) {
                        if(response instanceof DefaultModel){
                            DefaultModel model = (DefaultModel) response;
                            if(model.JSON instanceof SchemeCategoryModel){
                                SchemeCategoryModel sModel = (SchemeCategoryModel) model.JSON;
                                LogUtils.logEMsg(sModel.roomType.get(0).RoomTypeName);
                            }
                        }
                        super.onResponse(response);
                    }
                });
    }


}
