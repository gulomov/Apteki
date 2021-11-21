package com.example.apteki.di

import android.util.Log
import com.example.apteki.data.getToken
import com.example.apteki.network.Api
import com.example.apteki.network.ErrorResponse
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.readystatesoftware.chuck.ChuckInterceptor
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import org.koin.android.BuildConfig
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

const val BASE_URL: String = "http://backoffice.apteki.uz/"

val networkModule = module {
    single<Api> {
        val retrofit = get<Retrofit>()
        retrofit.create(Api::class.java)
    }
    single<Gson> {
        GsonBuilder()
            .setLenient()
            .create()
    }
    single<Retrofit> {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(get<OkHttpClient>())
            .addConverterFactory(GsonConverterFactory.create(get()))
            .build()
    }
    single<OkHttpClient> {
        val clientBuilder = OkHttpClient.Builder()
            .connectTimeout(3000, TimeUnit.SECONDS)
            .readTimeout(3000, TimeUnit.SECONDS)
            .writeTimeout(3000, TimeUnit.SECONDS)
            .addInterceptor { chain ->
                val token: String = androidContext().getToken()
                try {
                    val request = chain.request().newBuilder()
                    request.addHeader("Content-type", "application/json")
                    request.addHeader("X-Requested-With", "XMLHttpRequest")
                    if (token != "") {
                        request.addHeader("Authorization", "Token  $token")
                    }
                    return@addInterceptor chain.proceed(request.build())
                } catch (e: Throwable) {

                }
                return@addInterceptor chain.proceed(chain.request())
            }
        clientBuilder.build()
        if (BuildConfig.DEBUG) {
            clientBuilder.addInterceptor(ChuckInterceptor(get()))
        }
        clientBuilder.build()
    }

    factory<Converter<ResponseBody, ErrorResponse>> {
        get<Retrofit>().responseBodyConverter(
            ErrorResponse::class.java,
            arrayOfNulls<Annotation>(0)
        )
    }
}