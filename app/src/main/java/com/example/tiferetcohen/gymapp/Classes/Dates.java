package com.example.tiferetcohen.gymapp.Classes;

/**
 * Created by tiferet.cohen on 4/6/2017.
 */
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Dates {
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("day_of_month")
    @Expose
    private String dayOfMonth;
    @SerializedName("day")
    @Expose
    private String day;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDayOfMonth() {
        return dayOfMonth;
    }

    public void setDayOfMonth(String dayOfMonth) {
        this.dayOfMonth = dayOfMonth;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }
}
