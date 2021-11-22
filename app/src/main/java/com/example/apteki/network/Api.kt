package com.example.apteki.network

import com.example.apteki.network.pojo.*
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface Api {
    @GET("api2/login/")
    suspend fun getLogin(
        @Query("username") username: String,
        @Query("password") password: String
    ): Response<LoginResponse>

    @GET("api2/filials/")
    suspend fun getBranches(): Response<BranchesResponse>

    @GET("api2/staff/")
    suspend fun getEmployee( @Query("branch_id") id: Int): Response<EmployeeResponse>


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