package com.example.weather_app;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class CalendarAdapter extends RecyclerView.Adapter<CalendarHolder> {


    private final List<CalendarDay> calendarDays;
    private final CalendarActivity mainAct;
    private final String tempUnit;
    private final String timeZone;

    CalendarAdapter(List<CalendarDay> calendarDays, String tempUnit, String timeZone, CalendarActivity ma) {
        this.calendarDays = calendarDays;
        mainAct = ma;
        this.tempUnit = tempUnit;
        this.timeZone = timeZone;
    }

    @NonNull
    @Override
    public CalendarHolder onCreateViewHolder(@NonNull final ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from( parent.getContext() ).inflate( R.layout.calendar_day, parent, false );
        return new CalendarHolder( itemView );
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull CalendarHolder holder, int position) {

        CalendarDay cd = calendarDays.get( position );

        long datetimeEpoch = Long.parseLong( cd.getCdDay() );
        Date dateTime = new Date( datetimeEpoch * 1000 );
        SimpleDateFormat dayDate = new SimpleDateFormat( "EEEE MM/dd", Locale.getDefault() );
        dayDate.setTimeZone( TimeZone.getTimeZone( timeZone ) );
        String dayDateStr = dayDate.format( dateTime );

        holder.cDay.setText( dayDateStr );

        String tempMinMaxUnit = "°" + (tempUnit.equals( "us" ) ? "F" : "C");

        holder.cTemp.setText( cd.getCdMax() + tempMinMaxUnit + "/" + cd.getCdMin() + tempMinMaxUnit );
        holder.cDesc.setText( cd.getCdDesc() );
        holder.cIcon.setImageResource( cd.getIcon() );
        String precipProb = "(" + cd.getCdPrecip() + "% prepip.)";
        holder.cPrecip.setText( precipProb );
        holder.cUVIndex.setText( "UV Index: " + cd.getCdUVIndex() );
        holder.cMorning.setText( cd.getCdMorning() + tempMinMaxUnit );
        holder.cAfternoon.setText( cd.getCdAfternoon() + tempMinMaxUnit );
        holder.cEvening.setText( cd.getCdEvening() + tempMinMaxUnit );
        holder.cNight.setText( cd.getCdNight() + tempMinMaxUnit );

    }

    @Override
    public int getItemCount() {
        return calendarDays.size();
    }

}
