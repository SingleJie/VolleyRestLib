package com.wenjackp.volley.rest.example;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.wenjackp.android.lib.utils.LogUtils;

import edu.single.library.volley.CallBackListener;
import edu.single.library.volley.VolleyRest;
import edu.single.library.volley.error.VolleyError;

/**
 * Created by Administrator on 2015/12/7.
 */
public class VolleyRestExample extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        VolleyRest.initConfig(this);
        LogUtils.initConfig(this, true);

        AllDao
                .getInstance()
                .userLogin("159020388430","123456",new CallBackListener<Object>(){
                    @Override
                    public void onResponse(Object response) {
                        if(response instanceof DefaultModel){
                            DefaultModel model = (DefaultModel) response;
                            LogUtils.toastShortMsg(model.Status);
                        }
                    }

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        LogUtils.toastShortMsg("网络异常");
                        LogUtils.logEMsg("Error -->" + error.toString());
                    }
                });
    }


}
