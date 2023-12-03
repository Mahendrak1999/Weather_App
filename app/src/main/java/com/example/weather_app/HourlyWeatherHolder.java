package com.example.weather_app;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class HourlyWeatherHolder extends RecyclerView.ViewHolder {

//    TextView hDay;
    TextView hTime;
    ImageView hIcon;
    TextView hTemp;
    TextView hDesc;

    public HourlyWeatherHolder(@NonNull View itemView) {
        super( itemView );
//        hDay = itemView.findViewById( R.id.hourly_day );
        hTime = itemView.findViewById( R.id.hourly_time );
        hIcon = itemView.findViewById( R.id.hourly_icon );
        hTemp = itemView.findViewById( R.id.hourly_temp );
        hDesc = itemView.findViewById( R.id.hourly_desc );
    }
}