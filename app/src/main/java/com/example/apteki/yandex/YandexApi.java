package com.example.apteki.yandex;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface YandexApi {
    @GET
    Call<Yandex> getData (@Url String url);
}
