package com.example.tiferetcohen.gymapp.Repository;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by tiferet.cohen on 4/4/2017.
 */

public class Repository {
    private final static Repository instance = new Repository();
    String API = "https://gym.mhutils.com:8444/";
    public Retrofit retrofit;

    public Repository() {
        retrofit = new Retrofit.Builder().baseUrl(API)
                .addConverterFactory(GsonConverterFactory.create()).build();
    }

    public static Repository getInstance(){
        return instance;
    }

}
