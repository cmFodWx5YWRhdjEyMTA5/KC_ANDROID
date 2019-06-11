package com.deepak.kcl.Networking;

import com.deepak.kcl.models.LoginResponse;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface Api {

    @FormUrlEncoded
    @POST("userlogin")
    Call<LoginResponse> userLogin(
            @Field("uemail") String uemail,
            @Field("upass") String upass,
            @Field("uimei_no1") String uimei_no1
    );
}
