package com.example.tiferetcohen.gymapp.Classes;

/**
 * Created by tiferet.cohen on 4/6/2017.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Trainees {
    @SerializedName("hour")
    @Expose
    private String hour;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("trainee")
    @Expose
    private String trainee;

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTrainee() {
        return trainee;
    }

    public void setTrainee(String trainee) {
        this.trainee = trainee;
    }
}
