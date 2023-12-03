package com.example.weather_app;


import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CalendarHolder extends RecyclerView.ViewHolder {

    TextView cDay;
    TextView cTemp;
    ImageView cIcon;
    TextView cDesc;
    TextView cPrecip;
    TextView cUVIndex;
    TextView cMorning;
    TextView cAfternoon;
    TextView cEvening;
    TextView cNight;

    public CalendarHolder(@NonNull View itemView) {
        super( itemView );
        cDay = itemView.findViewById( R.id.calendar_date );
        cTemp = itemView.findViewById( R.id.calendar_minmax );
        cIcon = itemView.findViewById( R.id.calendar_day_icon );
        cDesc = itemView.findViewById( R.id.calendar_day_desc );
        cPrecip = itemView.findViewById( R.id.calendar_day_precip );

        cUVIndex = itemView.findViewById( R.id.calendar_day_uv_index );
        cMorning = itemView.findViewById( R.id.calendar_day_morning );
        cAfternoon = itemView.findViewById( R.id.calendar_day_afternoon );
        cEvening = itemView.findViewById( R.id.calendar_day_evening );
        cNight = itemView.findViewById( R.id.calendar_day_night );
    }
}
