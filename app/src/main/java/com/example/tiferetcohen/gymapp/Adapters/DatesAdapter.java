package com.example.tiferetcohen.gymapp.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.tiferetcohen.gymapp.Classes.Dates;
import com.example.tiferetcohen.gymapp.MainActivity;
import com.example.tiferetcohen.gymapp.R;

import java.util.ArrayList;

/**
 * Created by tiferet.cohen on 4/6/2017.
 */

public class DatesAdapter extends RecyclerView.Adapter<DatesAdapter.DatesViewHolder> {
    private ArrayList<Dates> adapterView;
    String machine;
    String today;
    MainActivity activity = new MainActivity();

    public DatesAdapter(ArrayList<Dates> dates, String today, String machine){
        this.adapterView = dates;
        this.today = today;
        this.machine = machine;
    }

    @Override
    public DatesAdapter.DatesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.single_date, parent, false);
        return new DatesViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(DatesAdapter.DatesViewHolder holder, int position) {
        Dates thisDay = adapterView.get(position);
        holder.dayOfMonth.setText(thisDay.getDayOfMonth());
        holder.dayOfWeek.setText(thisDay.getDay());
        holder.thisDay = thisDay.getDate();
    }

    @Override
    public int getItemCount() {
        return adapterView.size();
    }

    public class DatesViewHolder extends RecyclerView.ViewHolder {
        public TextView dayOfWeek;
        public Button dayOfMonth;
        String thisDay;

        public DatesViewHolder (View view){
            super(view);
            dayOfWeek = (TextView) view.findViewById(R.id.day_of_week);
            dayOfMonth = (Button) view.findViewById(R.id.day_of_month);
            dayOfMonth.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // TODO - fix
                    //dayOfMonth.setTextColor(getResources().getColor(R.color.lightBackground));
                    dayOfMonth.setSelected(true);
                    today = thisDay;
                    activity.setTimeSlots(today, machine);
                }
            });
        }
    }
}

