package com.test.interceptors;

import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.Response;

import java.io.IOException;

/**
 * ������Ӧ��Ϣ�������ز���
 * Created by yuanwenfei on 2015/5/28.
 *




 */
public class RewritingResponseInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Response originalResponse = chain.proceed(chain.request());
        return originalResponse.newBuilder()
                .header("Cache-Control", "max-age=60")
                .build();
    }
}
