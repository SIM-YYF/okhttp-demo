package com.test;

import retrofit.Profiler;

/**
 * Created by yuanwenfei on 2015/6/2.
 */
public class MyProfiler implements Profiler<String> {
    @Override
    public String beforeCall() {
        return null;
    }

    @Override
    public void afterCall(RequestInformation requestInfo, long elapsedTime, int statusCode, String beforeCallData) {

    }
}
