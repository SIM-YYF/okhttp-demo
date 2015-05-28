package com.test;

import com.squareup.okhttp.Authenticator;
import com.squareup.okhttp.Cache;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Credentials;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.test.interceptors.GzipSettingInterceptor;
import com.test.interceptors.HeaderSettingInterceptor;
import com.test.interceptors.LogSettingInterceptor;

import java.io.File;
import java.io.IOException;
import java.net.Proxy;
import java.util.concurrent.TimeUnit;

/**
 * Created by yuanwenfei on 2015/5/28.
 */
public class HttpUtil {
    private static HttpUtil ourInstance = new HttpUtil();
    private static OkHttpClient client = null;

    public static HttpUtil getInstance() {
        return ourInstance;
    }

    private HttpUtil() {
        client = new OkHttpClient();
        client.setConnectTimeout(15, TimeUnit.SECONDS);
        client.setReadTimeout(10, TimeUnit.SECONDS);

        /**
         * Application interceptors

         Don't need to worry about intermediate responses like redirects and retries.
         Are always invoked once, even if the HTTP response is served from the cache.
         Observe the application's original intent. Unconcerned with OkHttp-injected headers like If-None-Match.
         Permitted to short-circuit and not call Chain.proceed().
         Permitted to retry and make multiple calls to Chain.proceed().

         */
        client.interceptors().add(new HeaderSettingInterceptor());
        client.interceptors().add(new GzipSettingInterceptor());
        client.interceptors().add(new GzipSettingInterceptor());
        /**
         Network Interceptors

         Able to operate on intermediate responses like redirects and retries.
         Not invoked for cached responses that short-circuit the network.
         Observe the data just as it will be transmitted over the network.
         Access to the Connection that carries the request.
         */
        client.networkInterceptors().add(new LogSettingInterceptor());

        /**
         * 设置内存缓存和文件缓存
         */
        client.setCache(new Cache(new File("cacheDirectory"), 10 * 1024 * 1024));

        /**
         * 设置认证
         */
        client.setAuthenticator(new Authenticator() {
            @Override
            public Request authenticate(Proxy proxy, Response response) throws IOException {
                String credential = Credentials.basic("username", "password1");
                return response.request().newBuilder()
                        .header("Authorization", credential)
                        .build();
            }

            @Override
            public Request authenticateProxy(Proxy proxy, Response response) throws IOException {
                return null;
            }
        });
    }

    public OkHttpClient getClient(){
        return client;
    }
    /**
     * 该不会开启异步线程。
     * @param request
     * @return
     * @throws IOException
     */
    public  Response execute(Request request) throws IOException{
        return client.newCall(request).execute();
    }
    /**
     * 开启异步线程访问网络
     * @param request
     * @param responseCallback
     */
    public  void enqueue(Request request, Callback responseCallback){
        client.newCall(request).enqueue(responseCallback);
    }
}
