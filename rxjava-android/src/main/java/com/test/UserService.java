package com.test;


import retrofit.client.Response;
import retrofit.http.GET;
import retrofit.http.Path;
import rx.Observable;

/**
 * Created by yuanwenfei on 2015/6/1.
 */
public interface UserService  {
    @GET("/tyb/api/v1/captcha/request")
    Captcha getCaptcha();
    @GET("/tyb/api/v1/captcha/request")
    Response getResponseCaptcha();
    @GET("/tyb/api/v1/captcha/request")
    Observable<Captcha> getRxCaptcha();
    @GET("/tyb/api/v1/captcha/request")
    Observable<String> getRxStringCaptcha();

    @GET("/tyb/api/v1/captcha/request")
    Observable<Response> getRxResponseCaptcha();
    @GET("/tyb/api/v1/captcha/show/{captcha_id}")
    Response get_byte_captcha(@Path("captcha_id") String captcha_id);


}
