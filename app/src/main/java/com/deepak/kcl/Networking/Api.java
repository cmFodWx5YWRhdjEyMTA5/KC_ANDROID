package com.deepak.kcl.Networking;

import com.deepak.kcl.models.LoginResponse;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface Api {

    @FormUrlEncoded
    @POST("userlogin")
    Call<LoginResponse> userLogin(
            @Field("uemail") String uemail,
            @Field("upass") String upass,
            @Field("uimei_no1") String uimei_no1
    );

    @FormUrlEncoded
    @PUT("updateuser/{id}")
    Call<LoginResponse> updateUser(
           @Path("id") int id,
           @Field("name") String name,
           @Field("email") String email,
           @Field("mobile") String mobile,
           @Field("imei1") String imei1,
           @Field("imei2") String imei2
    );
}
