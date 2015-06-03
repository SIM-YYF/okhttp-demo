package com.test;

import android.util.Log;

import org.json.JSONObject;

import java.io.InputStream;
import java.io.StringWriter;

import retrofit.client.Response;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by yuanwenfei on 2015/6/3.
 */
public class UserApi {
    public static UserApi instance = new UserApi();
    public static UserApi getInstance(){
        return instance;
    }
    private UserApi(){
    }

    public void getCaptch(){
        Observable.create(new Observable.OnSubscribe<Response>() {
            @Override
            public void call(Subscriber<? super Response> subscriber) {
                try {
                    subscriber.onNext(API.restAdapter.create(UserService.class).getResponseCaptcha());
                    subscriber.onCompleted();
                }catch (Throwable throwable){
                    subscriber.onError(throwable);
                }
            }
        }).subscribeOn(Schedulers.io())
                .map(new Func1<Response, String>() {
                    @Override
                    public String call(Response response) {
                        InputStream inputStream = null;
                        try {
                            inputStream = response.getBody().in();
                            StringWriter stringWriter = new StringWriter();
                            byte[] buf = new byte[1024];
                            int r;
                            while ((r = inputStream.read(buf)) != -1) {
                                stringWriter.write(new String(buf, 0, r));
                            }
                            JSONObject jsonObject = new JSONObject(stringWriter.toString());
                            return String.valueOf(jsonObject.getString("captcha_id"));
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    }
                })
                .cache()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {

                        Log.d("UserApi", s);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {

                        Log.d("UserApi", "异常中...........................");
                        throwable.printStackTrace();
                    }
                });


    }
}
