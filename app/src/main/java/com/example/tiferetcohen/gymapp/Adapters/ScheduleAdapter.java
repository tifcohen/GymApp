package com.example.tiferetcohen.gymapp.Adapters;

import android.content.Context;
import android.graphics.PointF;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSmoothScroller;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tiferetcohen.gymapp.API.ITraineeAPI;
import com.example.tiferetcohen.gymapp.Classes.Trainees;
import com.example.tiferetcohen.gymapp.MainActivity;
import com.example.tiferetcohen.gymapp.R;
import com.example.tiferetcohen.gymapp.Repository.ServiceGenerator;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by tiferet.cohen on 5/14/2017.
 */

public class ScheduleAdapter extends RecyclerView.Adapter<ScheduleAdapter.AdapterViewHolder> {

    private ArrayList<Trainees> timeSlots;
    private Context context;
    private ITraineeAPI traineeAPI;
    private MainActivity activity = new MainActivity();
    private String machine;
    private String today;
    private MainActivity.SlotsListener listener;

    public ScheduleAdapter(ArrayList<Trainees> timeSlots, Context applicationContext, String today, String machine, MainActivity.SlotsListener listener) {
        this.timeSlots = timeSlots;
        this.context = applicationContext;
        this.today = today;
        this.machine = machine;
        this.listener = listener;
    }

    @Override
    public AdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.schedule_single_row, parent, false);
        return new ScheduleAdapter.AdapterViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(AdapterViewHolder holder, int position) {

        holder.timeSlot.setText(timeSlots.get(position).getHour());
        if(!timeSlots.get(position).getId().equals("")){
            final String trainee = timeSlots.get(position).getTrainee();
            holder.traineeName.setText(trainee);
            if(!trainee.equals("tiferet.cohen")){ //TODO: fix once there's a real username
                setUsersSlot(holder, position);
            } else{
                setTakenSlot(holder, position);
            }
        } else{
            setAvailableSlot(holder, position);
        }
        holder.setIsRecyclable(false);
    }

    private void setAvailableSlot(AdapterViewHolder holder, final int position){
        Log.d("Debug", "Position: " + position + " timeSlots.get(position).getId().equals(\"\")");

        holder.actionButton.setVisibility(View.VISIBLE);
        holder.traineeName.setText(R.string.available_time_slot);
        holder.traineeName.setTextColor(context.getResources().getColor(R.color.lightText));
        holder.traineeName.setTextSize(context.getResources().getDimension(R.dimen.availableSlot));
        final String time = timeSlots.get(position).getHour()+":00";
        holder.actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { //TODO - inside "add slot" fix to username
                if (listener != null) {
                    listener.onSlotAdded(time);
                }
                else{
                    Toast.makeText(context,"Failed to add", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void setTakenSlot(AdapterViewHolder holder, final int position){
        Log.d("Debug", "Position: " + position +" !timeSlots.get(position).getId().equals(\"\") + else");
        holder.actionButton.setVisibility(View.VISIBLE);
        holder.nameArea.setBackgroundColor(context.getResources().getColor(R.color.background));
        holder.actionButton.setBackgroundColor(context.getResources().getColor(R.color.background));
        holder.traineeName.setTextColor(context.getResources().getColor(R.color.colorPrimaryDark));
        holder.traineeName.setTextSize(context.getResources().getDimension(R.dimen.takenSlot));
        holder.actionButton.setImageDrawable(context.getDrawable(R.drawable.ic_delete));
        holder.actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            if (listener != null) {
                listener.onSlotRemoved(position);
            }
            else{
                Toast.makeText(context,"Failed to remove", Toast.LENGTH_LONG).show();
            }
            }
        });
    }

    private void setUsersSlot (AdapterViewHolder holder, final int position){
        Log.d("Debug", "Position: "+position +" !timeSlots.get(position).getId().equals(\"\") + !trainee.equals(userName)");
        holder.actionButton.setVisibility(View.GONE);
        holder.traineeName.setTextColor(context.getResources().getColor(R.color.colorAccent));
        holder.traineeName.setTextSize(context.getResources().getDimension(R.dimen.availableSlot));
        holder.nameArea.setBackgroundColor(context.getResources().getColor(R.color.colorPrimary));
        holder.actionButton.setBackgroundColor(context.getResources().getColor(R.color.colorPrimary));
    }

    @Override
    public int getItemCount() {
        return timeSlots.size();
    }

    class AdapterViewHolder extends RecyclerView.ViewHolder {
        TextView traineeName;
        TextView timeSlot;
        ImageButton actionButton;
        RelativeLayout nameArea;

        public AdapterViewHolder(View itemView) {
            super(itemView);
            traineeName = (TextView) itemView.findViewById(R.id.trainee_name);
            timeSlot = (TextView) itemView.findViewById(R.id.time_slot);
            actionButton = (ImageButton) itemView.findViewById(R.id.action_button);
            nameArea = (RelativeLayout) itemView.findViewById(R.id.name_area);
        }
    }
}