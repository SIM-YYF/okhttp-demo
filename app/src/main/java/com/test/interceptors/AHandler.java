package com.test.interceptors;

import com.squareup.okhttp.Request;
import com.test.Service;

/**
 * Created by yuanwenfei on 2015/5/28.
 */
public class AHandler {
    private  Request request;
    public AHandler(){};
    public AHandler(Request request){
        this.request = request;
    }
    public void commit(ACallBack callBack){
        Service.start(request, callBack);
    }

}
