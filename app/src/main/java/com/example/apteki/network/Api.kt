package com.example.apteki.network

import com.example.apteki.network.pojo.*
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*

interface Api {
    @GET("api2/login/")
    suspend fun getLogin(
        @Query("username") username: String,
        @Query("password") password: String
    ): Response<LoginResponse>

    @GET("api2/filials/")
    suspend fun getBranches(): Response<BranchesResponse>

    @GET("api2/dashboard_chart/")
    suspend fun getCharts(): Response<ChartsResponse>


    @GET("api2/staff/")
    suspend fun getEmployee(@Query("branch") id: Int): Response<EmployeeResponse>

    @GET("api/get-regions/")
    suspend fun getRegions(): Response<RegionsResponse>

    @FormUrlEncoded
    @POST("api2/filial_create/")
    suspend fun addBranch(
        @Field("region") region: String,
        @Field("name") name: String,
        @Field("phone") phone: String,
        @Field("address") address: String,
        @Field("work_time") work_time: String,
        @Field("has_dastavka") has_dastavka: String,
        @Field("longtitude") longtitude: String,
        @Field("latitude") latitude: String,
        @Field("type") type: String,
    ): Response<AddBranchResponse>

    @FormUrlEncoded
    @POST("api2/staff/")
    suspend fun addEmployee(
        @Field("full_name") full_name: String,
        @Field("username") username: String,
        @Field("password") password: String,
        @Field("type") type: String,
        @Field("phone") phone: String,
        @Field("address") address: String,
        @Field("branch") branch: String,
        @Field("is_active") is_active: Boolean
    ): Response<AddEmployeeResponse>


    /* @POST("api2/staff/")
     suspend fun addEmployee(
         @Body addEmployeeRequest: AddEmployeeRequest
     ): Response<AddEmployeeResponse>*/

    @GET("api2/shops/")
    suspend fun getTrades(
        @Query("page") position: Int,
        @Query("branch_id") id: Int
    ): TradeResponse

    @GET("api2/shops/")
    suspend fun getTradeByFilter(
        @Query("page") position: Int,
        @Query("branch_id") id: Int,
        @Query("date_start") date_start: String,
        @Query("date_end") date_end: String
    ): TradeResponse

    @GET("api2/fakturas/")
    suspend fun getInvoice(
        @Query("page") position: Int,
        @Query("branch_id") id: Int
    ): InvoiceResponse

    @GET("api2/fakturas/")
    suspend fun getInvoiceByFilter(
        @Query("page") position: Int,
        @Query("branch_id") id: Int,
        @Query("date_start") date_start: String,
        @Query("date_end") date_end: String
    ): InvoiceResponse
}