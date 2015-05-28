package com.test;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.test.interceptors.ACallBack;

import java.io.IOException;

/**
 * Created by yuanwenfei on 2015/5/28.
 */
public class Service {

    public static void start(Request request, final ACallBack callBack){
        //异步请求
        HttpUtil.getInstance().enqueue(request, new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                e.printStackTrace();
                HttpUtil.getInstance().getClient().cancel(request.tag());
            }
            @Override
            public void onResponse(Response response) throws IOException {
                if(!response.isSuccessful()){//失败
                    callBack.onFailure(response);
                }else{//成功
                    callBack.onSuccess(response);
                }

            }
        });
    }

}
