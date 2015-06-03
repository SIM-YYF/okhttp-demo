package com.test;

import retrofit.RestAdapter;

/**
 * Created by yuanwenfei on 2015/6/3.
 */
public interface API {
    RestAdapter restAdapter = new DefaultRestAdapter().get();
    class DefaultRestAdapter{
        public RestAdapter get(){
            RestAdapter restAdapter = new RestAdapter.Builder()
                    .setEndpoint("http://api-tyb.ykdljkpt.com")
                    .setLogLevel(RestAdapter.LogLevel.FULL).build();
            return restAdapter;
        }
    }
}
