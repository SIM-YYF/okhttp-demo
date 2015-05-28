package com.test.interceptors;

import com.squareup.okhttp.Request;
import com.test.Service;

/**
 * Created by yuanwenfei on 2015/5/28.
 */
public class AHandler {
    public static void Commit(Request request, ACallBack callBack){
        Service.start(request, callBack);
    }
}
