package com.test;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.squareup.okhttp.Response;
import com.squareup.okhttp.ResponseBody;
import com.test.interceptors.ACallBack;

import java.io.IOException;


public class MainActivity extends AppCompatActivity implements ACallBack {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Api.getInstance().getUserInfo("username").commit(this);

    }

    @Override
    public void onSuccess(Response response) {
        if(response.request().urlString().equals(response.request().tag())){
            ResponseBody  responseBody = response.body();
            try {
                String body = responseBody.string();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public void onFailure(Response response) {

    }
}
