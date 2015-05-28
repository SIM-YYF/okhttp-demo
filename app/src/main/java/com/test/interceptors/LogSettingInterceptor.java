package com.test.interceptors;

import android.util.Log;

import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

/**
 * Created by yuanwenfei on 2015/5/28.
 */
public class LogSettingInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();

        long t1 = System.nanoTime();

        Log.d(this.getClass().getName(), "Sending request url = " + request.url() + "");
        Log.d(this.getClass().getName(), "Sending request headers" + request.headers() + "");

        Response response = chain.proceed(request);

        long t2 = System.nanoTime();
        Log.d(this.getClass().getName(), "Sending request response use time = " + (t2 - t1) / 1e6d + "");

        return response;
    }
}
