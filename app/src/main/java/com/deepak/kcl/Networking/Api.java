package com.deepak.kcl.Networking;

import com.deepak.kcl.models.BranchExpenseResponse;
import com.deepak.kcl.models.BranchResponse;
import com.deepak.kcl.models.ExpenseTypeResponse;
import com.deepak.kcl.models.LoginResponse;
import com.deepak.kcl.models.TripExpenseResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
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

    @FormUrlEncoded
    @PUT("updateProfile/{id}")
    Call<LoginResponse> updateProfile(
            @Path("id") int id,
            @Field("profileImg") String profileImg
    );

    @GET("getBranches")
    Call<BranchResponse> getAllBranch();

    @GET("getExpenseType")
    Call<ExpenseTypeResponse> getExpenseType();

    @FormUrlEncoded
    @POST("createTripExpense")
    Call<TripExpenseResponse> createTripExpense(
            @Field("userid") int userid,
            @Field("tripId") int tripId,
            @Field("tripExpenseType") String tripExpenseType,
            @Field("tripExpenseAmount") String tripExpenseAmount,
            @Field("ExpenseImage") String ExpenseImage,
            @Field("imageName") String imageName
    );

    @GET("getTripExpense/{id}")
    Call<TripExpenseResponse> getTripExpenses(
            @Path("id") int id
    );

    @FormUrlEncoded
    @POST("createBranchExpense")
    Call<BranchExpenseResponse> createBranchExpense(
            @Field("userid") int userid,
            @Field("bch_date") String bch_date,
            @Field("branch_id") int branch_id,
            @Field("expense_id") int expense_id,
            @Field("lr_number") String lr_number,
            @Field("trip_expense_type") String trip_expense_type,
            @Field("bch_amount") String bch_amount,
            @Field("bch_desc") String bch_desc
    );

    @GET("getBranchExpense/{id}")
    Call<BranchExpenseResponse> getBranchExpense(
            @Path("id") int id
    );
}
