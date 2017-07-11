package com.example.tiferetcohen.gymapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.tiferetcohen.gymapp.API.IDatesAPI;
import com.example.tiferetcohen.gymapp.API.ITraineeAPI;
import com.example.tiferetcohen.gymapp.Adapters.DatesAdapter;
import com.example.tiferetcohen.gymapp.Adapters.ScheduleAdapter;
import com.example.tiferetcohen.gymapp.Classes.Dates;
import com.example.tiferetcohen.gymapp.Classes.Trainees;
import com.example.tiferetcohen.gymapp.Repository.ServiceGenerator;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    String userName = "tiferet.cohen"; //#TODO - delete after login

    private String authToken = "Basic ce79a94707307b604a90103d91622900";
    ITraineeAPI traineeAPI;
    Spinner machineDropdown;
    RecyclerView scheduleList;
    RecyclerView datesView;
    String machine = "Treadmill";
    String today;

    ArrayList<Trainees> timeSlots;
    ArrayList<Dates> datesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setDropdown();

        traineeAPI = ServiceGenerator.createService(ITraineeAPI.class, authToken);

        //Dropdown functionality
        machineDropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                machine = machineDropdown.getSelectedItem().toString();
                setTimeSlots(today, machine);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Log.d("Error", "Failed to fetch dates");
            }
        });

        IDatesAPI datesApi =
                ServiceGenerator.createService(IDatesAPI.class, authToken);
        Call<ArrayList<Dates>> callDates = datesApi.getDates();
        callDates.enqueue(new Callback<ArrayList<Dates>>() {
            @Override
            public void onResponse(Call<ArrayList<Dates>> call, Response<ArrayList<Dates>> response) {
                datesList = response.body();
                setCalendar();
                today = datesList.get(0).getDate();
                setTimeSlots(today, machine);
            }

            @Override
            public void onFailure(Call<ArrayList<Dates>> call, Throwable t) {
                Log.d("Error", "Failed to fetch dates");
            }
        });

    }

   protected void setCalendar(){
       datesView = (RecyclerView) findViewById(R.id.calendarView);
       datesView.setHasFixedSize(true);

       RecyclerView.LayoutManager layoutDatesManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
       datesView.setLayoutManager(layoutDatesManager);

       DatesAdapter dAdapter = new DatesAdapter(datesList, today, machine);
       datesView.setAdapter(dAdapter);
    }

    protected void setDropdown(){
        machineDropdown = (Spinner) findViewById(R.id.machine_dropdown);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.machines_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        machineDropdown.setAdapter(adapter);
    }

    public void setTimeSlots(String date, final String machine){
        scheduleList = (RecyclerView) findViewById(R.id.schedule_list);
        scheduleList.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        scheduleList.setLayoutManager(linearLayoutManager);

        Call <ArrayList<Trainees>> callTrainees = traineeAPI.getTrainees(date, machine);
        callTrainees.enqueue(new Callback<ArrayList<Trainees>>() {
            @Override
            public void onResponse(Call<ArrayList<Trainees>> call, Response<ArrayList<Trainees>> response) {
                timeSlots = response.body();
                ScheduleAdapter adapter = new ScheduleAdapter(timeSlots, getApplicationContext(), today, machine, new SlotsListener() {
                    @Override
                    public void onSlotAdded(String time) {
                        Call<Void> addCall = traineeAPI.addSlot("tiferet.cohen", machine, today+" "+time);
                        addCall.enqueue(new Callback<Void>() {
                            @Override
                            public void onResponse(Call<Void> call, Response<Void> response) {
                                setTimeSlots(today, machine);
                                Toast.makeText(getApplicationContext(),"Added successfully", Toast.LENGTH_LONG).show();
                            }
                            @Override
                            public void onFailure(Call<Void> call, Throwable t) {
                                Toast.makeText(getApplicationContext(),"Failed to add", Toast.LENGTH_LONG).show();
                            }
                        });
                    }

                    @Override
                    public void onSlotRemoved(int position) {
                        Call<Void> deleteCall = traineeAPI.deleteSlot(timeSlots.get(position).getId());
                        deleteCall.enqueue(new Callback<Void>() {
                            @Override
                            public void onResponse(Call<Void> call, Response<Void> response) {
                                setTimeSlots(today, machine);
                                Toast.makeText(getApplicationContext(),"Deleted successfully", Toast.LENGTH_LONG).show();
                            }

                            @Override
                            public void onFailure(Call<Void> call, Throwable t) {
                                Toast.makeText(getApplicationContext(),"Failed to delete", Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                });
                scheduleList.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<ArrayList<Trainees>> call, Throwable t) {
                Log.d("Error", "Failed to fetch trainees");
            }
        });
    }

    public interface SlotsListener {
        void onSlotAdded(String time);
        void onSlotRemoved(int position);
    }
}

