package com.deepak.kcl.Networking;

import com.deepak.kcl.models.BranchExpenseResponse;
import com.deepak.kcl.models.BranchResponse;
import com.deepak.kcl.models.BranchTripsResponse;
import com.deepak.kcl.models.ExpenseTypeResponse;
import com.deepak.kcl.models.JourneyIdResponse;
import com.deepak.kcl.models.LoadingResponse;
import com.deepak.kcl.models.LoginResponse;
import com.deepak.kcl.models.SplitAdvHeadResponse;
import com.deepak.kcl.models.TripAdvanceResponse;
import com.deepak.kcl.models.TripExpenseResponse;
import com.deepak.kcl.models.SplitAdvDataResponse;

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
            @Field("tripExpenseType") int tripExpenseType,
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
            @Field("branch_id") String branch_id,
            @Field("expense_id") String expense_id,
            @Field("lr_number") String lr_number,
            @Field("trip_expense_type") String trip_expense_type,
            @Field("bch_amount") String bch_amount,
            @Field("bch_desc") String bch_desc
    );

    @GET("getBranchExpense/{id}")
    Call<BranchExpenseResponse> getBranchExpense(
            @Path("id") int id
    );

    @FormUrlEncoded
    @PUT("DeleteBranchExp/{id}")
    Call<BranchExpenseResponse> DeleteBranchExp(
            @Path("id") int id,
            @Field("uid") String uid
    );

    @FormUrlEncoded
    @POST("createLoadingDetail")
    Call<LoadingResponse> createLoadingDetail(
            @Field("userid") int userid,
            @Field("tripId") int tripId,
            @Field("loadingType") String loadingType,
            @Field("loadingQuantity") String loadingQuantity,
            @Field("LoadingImage") String LoadingImage,
            @Field("imageName") String imageName
    );

    @GET("getLoadUnload/{id}")
    Call<LoadingResponse> getLoadUnload(
            @Path("id") int id
    );

    @GET("getTripByBranch/{id}")
    Call<BranchTripsResponse> getTripByBranch(
            @Path("id") int id
    );

    @GET("getTripAdvances/{id}")
    Call<TripAdvanceResponse> getTripAdvances(
            @Path("id") String id
    );

    @GET("getJourneyId/{id}")
    Call<JourneyIdResponse> getJourneyId(
            @Path("id") int id
    );

    @FormUrlEncoded
    @PUT("DeleteLoadUnload/{id}")
    Call<LoadingResponse> DeleteLoadUnload(
            @Path("id") int id,
            @Field("tripid") String tripid
    );

    @FormUrlEncoded
    @PUT("DeleteTripExp/{id}")
    Call<TripExpenseResponse> DeleteTripExp(
            @Path("id") int id,
            @Field("tripid") String tripid
    );

    @GET("getSplitAdvHeading/{id}")
    Call<SplitAdvHeadResponse> getSplitAdvHeading(
            @Path("id") String id
    );

    @FormUrlEncoded
    @POST("insertSplitAdvance")
    Call<SplitAdvDataResponse> insertSplitAdvance(
            @Field("lr_number") String lr_number,
            @Field("split_date") String split_date,
            @Field("branch_id") String branch_id,
            @Field("split_type") String split_type,
            @Field("amount") String amount,
            @Field("description") String description,
            @Field("split_head") String split_head,
            @Field("userId") int userid
    );

    @GET("getSplitAdvData/{id}")
    Call<SplitAdvDataResponse> getSplitAdvData(
            @Path("id") String id
    );

    @FormUrlEncoded
    @PUT("DeleteSplitAdvance/{id}")
    Call<SplitAdvDataResponse> DeleteSplitAdvance(
            @Path("id") int id,
            @Field("tripid") String tripid
    );
}