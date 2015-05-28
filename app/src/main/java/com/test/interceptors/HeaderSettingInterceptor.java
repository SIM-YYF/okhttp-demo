package com.test.interceptors;

import com.squareup.okhttp.Headers;
import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

/**
 * Created by yuanwenfei on 2015/5/28.
 */
public class HeaderSettingInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request originalRequest = chain.request();
        Headers headers = originalRequest.headers();
        Headers.Builder builder = headers.newBuilder();
        builder.add("deviceid", "3333");
        Request compressedRequest = originalRequest.newBuilder()
                .headers(headers)
                .build();
        return chain.proceed(compressedRequest);
    }
}
