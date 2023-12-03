package com.example.weather_app;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class HourlyWeatherAdapter extends RecyclerView.Adapter<HourlyWeatherHolder> {

    //    private static final String TAG = "NotesAdapter";
    private final List<HourlyForecast> hourlyForecasts;
    private final MainActivity mainAct;

    HourlyWeatherAdapter(List<HourlyForecast> hourlyForecasts, MainActivity ma) {
        this.hourlyForecasts = hourlyForecasts;
        mainAct = ma;
    }

    @NonNull
    @Override
    public HourlyWeatherHolder onCreateViewHolder(@NonNull final ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from( parent.getContext() )
                .inflate( R.layout.hourly_weather, parent, false );
        return new HourlyWeatherHolder( itemView );
    }

    @Override
    public void onBindViewHolder(@NonNull HourlyWeatherHolder holder, int position) {

        HourlyForecast note = hourlyForecasts.get( position );
//        holder.hDay.setText( note.getDay() );
        holder.hTime.setText( note.getDateTime() );
        holder.hIcon.setImageResource( note.getIcon() );
        holder.hTemp.setText( note.getTemperature() );
        holder.hDesc.setText( note.getDescription() );


    }

    @Override
    public int getItemCount() {
        return hourlyForecasts.size();
    }

}