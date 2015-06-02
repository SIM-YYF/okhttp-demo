package com.test;

import retrofit.ErrorHandler;
import retrofit.RetrofitError;

/**
 * Created by yuanwenfei on 2015/6/2.
 */
public class MyErrorHandler implements ErrorHandler {
    @Override
    public Throwable handleError(RetrofitError cause) {

        return cause;
    }
}
