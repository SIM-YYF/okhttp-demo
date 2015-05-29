package com.test;

import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.ActionBarActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Map map = new HashMap();

        Observable.from(map.entrySet()).toMultimap(new Func1() {
            @Override
                public Observable<Person> call(Object o) {
                return getWeaterDate("name", 100);
            }
        })
        .subscribeOn(Schedulers.newThread())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Action1<Person>() {
                       @Override
                       public void call(Person o) {
                           //on ui thread do work

                       }
                   },
                new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        throwable.printStackTrace();

                    }
                }
        );

    }

    public Observable<Person> getWeaterDate(String name, int age) {
        return Observable.create(new Observable.OnSubscribe<Person>() {
            @Override
            public void call(Subscriber<? super Person> subscriber) {
                try{
                    subscriber.onStart();
                    subscriber.onNext(new Person());
                    subscriber.onCompleted();
                } catch (Throwable throwable) {

                    subscriber.onError(throwable);
                }

            }
        }).subscribeOn(Schedulers.newThread());

    }


    //----------------------------------------------------------------------------

    private static final Func1<String, String> PARSE_JSON = new Func1<String, String>() {
        @Override
        public String call(String json) {
            try {
                JSONObject jsonObject = new JSONObject(json);
                return String.valueOf(jsonObject.getInt("result"));
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }
    };










    class Person {
        private String name;
        private String age;
    }


    public void aaa(){

        Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                // simulate I/O latency
                SystemClock.sleep(100);
                final String fakeJson = "{\"result\": 42}";
                subscriber.onNext(fakeJson);
                subscriber.onCompleted();
            }
        })
        .subscribeOn(Schedulers.io())
        .map(new Func1<String, String>() {
            @Override
            public String call(String json) {
                try {
                    JSONObject jsonObject = new JSONObject(json);
                    return String.valueOf(jsonObject.getInt("result"));
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        })
        .observeOn(AndroidSchedulers.mainThread())
        .cache()
        .map(new Func1<String, Boolean>() {

            //Consume the data that comes back and then signal that we are done
            @Override
            public Boolean call(String s) {
//                textView.setText(s);
                return true;
            }
        }).startWith(false) //Before we receive data our request is not complete
                .subscribe(new Action1<Boolean>() {

                    //We update the UI based on the state of the request
                    @Override
                    public void call(Boolean completed) {
//                        setRequestInProgress(completed);
                    }
                });







    }

}
