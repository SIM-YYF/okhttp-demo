package com.test;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.squareup.okhttp.Response;
import com.test.interceptors.ACallBack;


public class MainActivity extends AppCompatActivity implements ACallBack {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        Api.getInstance().getUserInfo("username").setCallBack(this);

    }

    @Override
    public void onSuccess(Response response) {

    }

    @Override
    public void onFailure(Response response) {

    }
}
