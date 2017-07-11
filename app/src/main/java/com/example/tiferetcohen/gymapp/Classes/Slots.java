package com.example.tiferetcohen.gymapp.Classes;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


/**
 * Created by tiferet.cohen on 4/6/2017.
 */

public class Slots {
    @SerializedName("trainee")
    @Expose
    private String trainee;
    @SerializedName("machine")
    @Expose
    private String machine;
    @SerializedName("time")
    @Expose
    private String time;

    public String getTrainee() {
        return trainee;
    }

    public void setTrainee(String trainee) {
        this.trainee = trainee;
    }

    public String getMachine() {
        return machine;
    }

    public void setMachine(String machine) {
        this.machine = machine;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
