package com.wenjackp.volley.rest.example;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.wenjackp.android.lib.utils.LogUtils;

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

    }


}
