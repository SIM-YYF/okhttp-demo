package com.test;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import retrofit.RestAdapter;
import retrofit.client.Response;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;


public class MainActivity3 extends ActionBarActivity {

    private String captcha_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_activity2);
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint("http://api-tyb.ykdljkpt.com")
                .setErrorHandler(new MyErrorHandler())
                .setLogLevel(RestAdapter.LogLevel.FULL)
//                .setConverter(new JacksonConverter(new ObjectMapper()))
               /* .setProfiler(new MyProfiler())
                .setEndpoint(new Endpoint() {
                    @Override
                    public String getUrl() {
                        return null;
                    }

                    @Override
                    public String getName() {
                        return null;
                    }
                })
                .setClient(new OkClient())
                .setRequestInterceptor(new RequestInterceptor() {
                    @Override
                    public void intercept(RequestFacade request) {

                    }
                })*/
                .build();


        final UserService service = restAdapter.create(UserService.class);

        Button button = (Button) this.findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserApi.getInstance().getCaptch();
            }
        });



        Button button1 = (Button) this.findViewById(R.id.button1);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Observable request = Observable.create(new Observable.OnSubscribe<Response>() {
                    @Override
                    public void call(Subscriber<? super Response > subscriber) {

                        subscriber.onNext(service.get_byte_captcha(captcha_id));
                        subscriber.onCompleted();
                    }
                }).subscribeOn(Schedulers.io()).cache();

                request.observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Action1<Response>() {
                            @Override
                            public void call(Response  o) {
                                try {

                                    InputStream inputStream = o.getBody().in();
                                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                                    if (inputStream != null) {
                                        byte[] buf = new byte[1024];
                                        int r;
                                        while ((r = inputStream.read(buf)) != -1) {
                                            baos.write(buf, 0, r);
                                        }
                                    }
                                    byte[] bodyBytes = baos.toByteArray();
                                    Toast.makeText(MainActivity3.this, "byte[]  length  = " + bodyBytes.length, Toast.LENGTH_LONG).show();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                            }
                        });
            }
        });


        Button butto2 = (Button) this.findViewById(R.id.button2);
        butto2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Observable request = Observable.create(new Observable.OnSubscribe<Response>() {
//                    @Override
//                    public void call(Subscriber<? super Response > subscriber) {
//
//                        subscriber.onNext(service.get_byte_captcha(captcha_id));
//                        subscriber.onCompleted();
//                    }
//                }).subscribeOn(Schedulers.io()).cache();

//                service.getRxCaptcha().observeOn(AndroidSchedulers.mainThread())
//                        .cache()
//                        .subscribe(new Action1<Captcha>() {
//                            @Override
//                            public void call(Captcha captcha) {
//                                Toast.makeText(MainActivity2.this, "captcha_id  = " + captcha.captcha_id, Toast.LENGTH_LONG).show();
//                            }
//                        });

//                service.getRxResponseCaptcha().observeOn(AndroidSchedulers.mainThread())
//                        .cache()
//                        .subscribe(new Action1<Response>() {
//                            @Override
//                            public void call(Response o) {
//                                    String url = o.getUrl();
//                                try {
//                                StringWriter stringWriter = new StringWriter();
//                                byte[] buf = new byte[1024];
//                                InputStream inputStream = o.getBody().in();
//                                int r;
//
//                                    while ((r = inputStream.read(buf)) != -1) {
//                                        stringWriter.write(new String(buf, 0, r));
//                                    }
//                                    Toast.makeText(MainActivity2.this, "captcha_id  = " + stringWriter.toString(), Toast.LENGTH_LONG).show();
//                                } catch (IOException e) {
//                                    e.printStackTrace();
//                                }
//
//                            }
//                        });

                service.getRxStringCaptcha().observeOn(AndroidSchedulers.mainThread())
                        .cache()
                        .subscribe(new Action1<String>() {
                            @Override
                            public void call(String o) {
                                    Toast.makeText(MainActivity3.this, "captcha_id  = " + o.toString(), Toast.LENGTH_LONG).show();
                            }
                        });
            }
        });
    }

}
