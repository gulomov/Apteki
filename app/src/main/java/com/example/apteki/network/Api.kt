package com.example.apteki.network

import com.example.apteki.network.pojo.BranchesResponse
import com.example.apteki.network.pojo.LoginResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {
    @GET("api2/login/")
    suspend fun getLogin(
        @Query("username") username: String,
        @Query("password") password: String
    ): Response<LoginResponse>

    @GET("api2/filials/")
    suspend fun getBranches(): Response<BranchesResponse>
}