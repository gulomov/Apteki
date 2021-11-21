package com.example.apteki.repository

import android.util.Log
import com.example.apteki.network.Api
import com.example.apteki.network.ErrorResponse
import com.example.apteki.network.Resource
import com.example.apteki.network.safeApiCall
import kotlinx.coroutines.flow.flow
import okhttp3.ResponseBody
import retrofit2.Converter

class Repository constructor(
    var errorConverter: Converter<ResponseBody, ErrorResponse>,
    private val api: Api,
) {
    suspend fun getLogin(username: String, password: String) = flow {
        emit(Resource.Loading)
        emit(safeApiCall(errorConverter) { api.getLogin(username, password) })
    }

    suspend fun getBranches() = flow {
        emit(Resource.Loading)
        emit(safeApiCall(errorConverter) { api.getBranches() })
        Log.d(
            "here",
            "here2 ${api.getBranches()}"
        )
    }
}