package com.test.interceptors;

import com.squareup.okhttp.Response;

/**
 * Created by yuanwenfei on 2015/5/28.
 */
public interface ACallBack {
    void onSuccess(Response response);
    void onFailure(Response response);
}
