package com.example.tiferetcohen.gymapp.API;

import com.example.tiferetcohen.gymapp.Classes.Slots;
import com.example.tiferetcohen.gymapp.Classes.Trainees;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by tiferet.cohen on 4/4/2017.
 */

public interface ITraineeAPI {
    @GET("/mobile_api/get_trainees")
    Call<ArrayList<Trainees>> getTrainees(@Query("date") String date, @Query("machine") String machine);

    @DELETE("mobile_api/delete_trainee_slot")
    Call<Void> deleteSlot(@Query("slot_id") String slot_id);

    @GET("mobile_api/add_trainee_slot")
    Call<Void> addSlot(@Query("trainee") String trainee, @Query("machine") String machine, @Query("time") String time);
}
