package com.example.weather_app;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CalendarActivity extends AppCompatActivity {

    private final List<CalendarDay> calendarDayList = new ArrayList<>();
    private String tempUnit;
    private String timeZone;
    private String city;
    private String resolvedAddress;
    private static MainActivity mainActivity;
    private static final String TAG = "CalendarActivity";
    JSONArray jsonArr;
    private SwipeRefreshLayout swiper;
    private RecyclerView recyclerView;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_calendar );

        swiper = findViewById( R.id.calendar_swiper );
        swiper.setOnRefreshListener( new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swiper.setRefreshing( false ); // This stops the busy-circle
            }
        } );

        recyclerView = findViewById( R.id.calendar_recycler );
        recyclerView.setLayoutManager( new LinearLayoutManager( this ) );

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String value = extras.getString( "jsonArray" );
            this.tempUnit = extras.getString( "tempUnit" );
            this.timeZone = extras.getString( "timeZone" );
            this.city = extras.getString( "city" );
            this.resolvedAddress = extras.getString( "resolvedAddress" );
            getSupportActionBar().setTitle( resolvedAddress + " (15 Days)" );
            try {
                jsonArr = new JSONArray( value );
                updateCalendarData( jsonArr );
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }

    public void updateCalendarData(JSONArray jsonArr) throws JSONException {

        for (int i = 0; i < 15; i++) {

            JSONObject calendarDay = jsonArr.getJSONObject( i );
            String dateTimeEpoch = calendarDay.getString( "datetimeEpoch" );
            String tempMax = calendarDay.getString( "tempmax" );
            String tempMin = calendarDay.getString( "tempmin" );
            String precipProb = calendarDay.getString( "precipprob" );
            String UVIndex = calendarDay.getString( "uvindex" );
            String description = calendarDay.getString( "description" );

            String icon = calendarDay.getString( "icon" );
            String icon1 = icon.replace( "-", "_" ); // Replace all dashes with underscores
            int calendarDayIconID =
                    this.getResources().getIdentifier( icon1, "drawable", this.getPackageName() );
            if (calendarDayIconID == 0) {
                Log.d( TAG, "parseCurrentRecord: CANNOT FIND ICON " );
            }
            JSONArray hours = calendarDay.getJSONArray( "hours" );
            String morningTemp = (( JSONObject ) hours.get( 8 )).getString( "temp" );
            String afternoonTemp = (( JSONObject ) hours.get( 13 )).getString( "temp" );
            String eveningTemp = (( JSONObject ) hours.get( 17 )).getString( "temp" );
            String nightTemp = (( JSONObject ) hours.get( 23 )).getString( "temp" );


            CalendarDay calendarDayObj = new CalendarDay( dateTimeEpoch, tempMax, tempMin, calendarDayIconID, description, precipProb,
                    UVIndex, morningTemp, afternoonTemp, eveningTemp, nightTemp );
            calendarDayList.add( calendarDayObj );

        }

        CalendarAdapter mAdapter = new CalendarAdapter( this.calendarDayList, this.tempUnit, this.timeZone, this );
        recyclerView.setAdapter( mAdapter );

    }
}
