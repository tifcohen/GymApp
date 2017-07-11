package com.example.tiferetcohen.gymapp.API;

import com.example.tiferetcohen.gymapp.Classes.Dates;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by tiferet.cohen on 4/6/2017.
 */

public interface IDatesAPI {
    @GET("/mobile_api/dates")
    Call<ArrayList<Dates>> getDates();
}
