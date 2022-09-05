package ru.nitestalker.androidkinopoisk.retrofit;

import static ru.nitestalker.androidkinopoisk.utils.UrlHelper.BASE_URL;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


public class ApiFactory {

    // Singletone по-хорошему нужен

    public static final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .build();

    public static ApiService apiService = retrofit.create(ApiService.class);
}
