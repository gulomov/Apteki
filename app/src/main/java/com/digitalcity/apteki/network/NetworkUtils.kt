package com.digitalcity.apteki.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.util.Log
import com.google.gson.Gson
import com.google.gson.JsonParser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import java.lang.Exception
import java.net.ConnectException
import java.net.InetSocketAddress
import java.net.Socket


/*suspend fun <T : Any> safeApiCall(
    apiCall: suspend () -> Response<T>
): Resource<T> {
    try {
        val response = apiCall()
        return if (response.isSuccessful) {
            if(response.body()!=null) Resource.Success<T>(response.body() as T) else Resource.Success<T>("" as T)
        } else {
            if (response.errorBody() != null) {
                val retrofit = Retrofit.Builder().baseUrl("http://api.ussdbonus.uz/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                val converter: Converter<ResponseBody, RestError> = retrofit.responseBodyConverter(RestError::class.java, arrayOfNulls<Annotation>(0))
                val _object: RestError? = converter.convert(response.errorBody());

                Log.d("ERRORTAG", _object?.message.toString())

                val errorResponse = ErrorResponse(
                    message = _object?.message?:""
                )
                errorResponse.error = response.code()
                if (errorResponse.jsonResponse.toString().contains("message")) {
                    if (errorResponse.jsonResponse.has("error")) {
                        errorResponse.message = errorResponse.jsonResponse.getJSONObject("error").getString(
                            "message"
                        )
                    }
                }
                Resource.GenericError(errorResponse)
            } else
                Resource.GenericError(ErrorResponse("Unknown error"))
        }
    } catch (throwable: Throwable) {
        Log.d("ErrorTag", throwable.message.toString())
        return when (throwable) {
            is ConnectException,
            is NoConnectivityException -> {
                Resource.Error(NoConnectivityException())
            }
            is HttpException -> {
                val errorResponse: ErrorResponse = throwable.response()?.body() as ErrorResponse
                Resource.GenericError(errorResponse)
            }
            is IOException -> {
                Resource.Error(Exception("IOException: " + throwable.message))
            }
            else -> {
                Resource.Error(Exception(throwable.message))
            }
        }
    }
}*/

suspend fun <T : Any> safeApiCall(
    apiCall: suspend () -> Response<T>
): Resource<T> {
    try {
        val response = apiCall()
        if (response.isSuccessful && response.body() != null) {
            return Resource.Success<T>(response.body() as T)
        } else {
            return if (response.errorBody() != null) {
                Log.d("here", "here 111")
                val jsonParser = JsonParser()
                val jsonElement = jsonParser.parse(response.errorBody()!!.string())
                val errorResponse = Gson().fromJson(
                    jsonElement,
                    ErrorResponse::class.java
                )
                Log.d("here", "here 222 $errorResponse")
                Resource.GenericError(errorResponse)
            } else {
                Resource.GenericError(ErrorResponse(message = "Unknown error"))
            }
        }
    } catch (throwable: Throwable) {
        Log.d("here", "here 333")
        Log.d("ErrorTag", throwable.message.toString())
        when (throwable) {
            is ConnectException,
            is NoConnectivityException -> {
                return Resource.Error(NoConnectivityException())
            }
            is HttpException -> {
                Log.d("here", "here 444")
                val errorResponse: ErrorResponse = throwable.response()?.body() as ErrorResponse
                return Resource.GenericError(errorResponse)
            }
            is IOException -> {
                return Resource.Error(Exception("IOException: " + throwable.message))
            }
            else -> {
                Log.d("here", "here 555")
                return Resource.Error(Exception(throwable.message))
            }
        }
    }
}
//
//suspend fun <T : Any> safeApiCallTwo(
//    apiCall: suspend () -> Response<T>
//): Resource<T> {
//    try {
//        val response = apiCall()
//        if (response.isSuccessful && response.body() != null) {
////            var json = Gson()
////            if (response.body() != null) {
////                var customObject = response.body() as T
////                if (customObject != null) {
////                    var castedObject = json.toJson(customObject)
////                    Log.d("ErrorTag:", "CastedObject: $castedObject")
////                } else
////                    Log.d("ErrorTag:", "Null")
////            } else
////                Log.d("ErrorTag:", "If responsbody null raw: " + response.raw().toString())
//            return Resource.Success<T>(response.body() as T)
//        } else {
//            if (response.errorBody() != null) {
//                val errorResponse = ErrorResponse(
//                    message = response.raw().code.toString() + ":" + response.message()
//                        ?: ""
//                )
//                return Resource.GenericError(errorResponse)
//            } else
//                return Resource.GenericError(ErrorResponse("Unknown error"))
//        }
//    } catch (throwable: Throwable) {
//        Log.d("ErrorTag", throwable.message.toString())
//        when (throwable) {
//            is ConnectException,
//            is NoConnectivityException -> {
//                return Resource.Error(NoConnectivityException())
//            }
//            is HttpException -> {
//                val errorResponse: ErrorResponse = throwable.response()?.body() as ErrorResponse
//                return Resource.GenericError(errorResponse)
//            }
//            is IOException -> {
//                return Resource.Error(Exception("IOException: " + throwable.message))
//            }
//            else -> {
//                return Resource.Error(Exception(throwable.message))
//            }
//        }
//    }
//}

/**
 * Checks the availability of network connection not the actual internet connection
 * After Marshmellow new Connectivity Api is used  called networkCapabilities.
 *
 */


suspend fun Context.isNetworkConnectedSuspend(): Boolean {
    // Dispatchers.Main
    return withContext(Dispatchers.IO) {
        try {
            val timeoutMs = 1500
            val socket = Socket()
            val socketAddress = InetSocketAddress("8.8.8.8", 53)

            socket.connect(socketAddress, timeoutMs)
            socket.close()

            true
        } catch (e: IOException) {
            false
        }
    }
}

