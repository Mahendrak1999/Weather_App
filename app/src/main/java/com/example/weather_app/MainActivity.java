package com.example.weather_app;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class MainActivity extends AppCompatActivity {

    private final List<HourlyForecast> hourlyForecastList = new ArrayList<>();
    private static final String TAG = "MainActivity";
    private SwipeRefreshLayout swiper;
    private RecyclerView recyclerView;
    private String tempUnit = "us";
    private String timeZone;

    TextView dateTimeEpoch;
    private String city = "Chicago";
    private String resolvedAddress;
    private JSONArray daysJSONArray;
    //    private HourlyWeatherAdapter mAdapter;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        getSupportActionBar().setTitle("");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        editor = sharedPref.edit();
        if (!sharedPref.contains("tempUnit")) {
            editor.putString("tempUnit", "us");
            editor.apply();
        }

        String storeTempUnit = sharedPref.getString("tempUnit", "metric");
        if (storeTempUnit.equals("us"))
            this.tempUnit = "us";
        else
            this.tempUnit = "metric";

        if (!sharedPref.contains("city")) {
            editor.putString("city", "Chicago");
            editor.apply();
        }
        else{
            this.city = sharedPref.getString("city", "Pune");
        }

        swiper = findViewById(R.id.swiper);
        swiper.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                checkNet(null);
                Toast.makeText(MainActivity.this, "Refreshed", Toast.LENGTH_SHORT).show();
            }
        });

        recyclerView = findViewById(R.id.todays_forecast_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.HORIZONTAL, false));

        dateTimeEpoch = findViewById(R.id.current_date);

        checkNet(null);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.weather_menu, menu);
        return true;
    }

    @Override
  public boolean onOptionsItemSelected(MenuItem item) {
      int itemId = item.getItemId();

      if (itemId == R.id.temp_unit) {
          convertUnit(item);
          return true;
      } else if (itemId == R.id.weather_forecast) {
          openCalendar(item);
          return true;
      } else if (itemId == R.id.location) {
          selectCity(item);
          return true;
      } else {
          return super.onOptionsItemSelected(item);
      }
  }


//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.weather_menu, menu);
//        if (this.tempUnit.equals("us")) {
//            menu.findItem(R.id.temp_unit).setIcon(ContextCompat.getDrawable(this, R.drawable.units_f));
//        } else {
//            menu.findItem(R.id.temp_unit).setIcon(ContextCompat.getDrawable(this, R.drawable.units_c));
//        }
//        return true;
//    }
//
//  //  ################
//  @Override
//  public boolean onOptionsItemSelected(MenuItem item) {
//      int itemId = item.getItemId();
//
//      if (itemId == R.id.temp_unit) {
//          convertUnit(item);
//          return true;
//      } else if (itemId == R.id.weather_forecast) {
//          openCalendar(item);
//          return true;
//      } else if (itemId == R.id.location) {
//          selectCity(item);
//          return true;
//      } else {
//          return super.onOptionsItemSelected(item);
//      }
//  }


    //#####################
    public void openCalendar(MenuItem menu) {
        Intent intent = new Intent(this, CalendarActivity.class);
        if(! hasNetworkConnection()){
            Toast.makeText(this,"No Internet Connection", Toast.LENGTH_SHORT).show();
            return;
        }
        if(hasNetworkConnection()) {
            menu.setEnabled(true);
            if (daysJSONArray != null) {
                intent.putExtra("jsonArray", daysJSONArray.toString());
                intent.putExtra("tempUnit", tempUnit);
                intent.putExtra("timeZone", timeZone);
                intent.putExtra("city", this.city);
                intent.putExtra("resolvedAddress", this.resolvedAddress);
                startActivity(intent);
            }
        }
        else{
            menu.setEnabled(false);
        }
    }


    public void convertUnit(MenuItem menu){
        if(! hasNetworkConnection()){
            Toast.makeText(this,"No Internet Connection", Toast.LENGTH_SHORT).show();
            return;
        }
        if(daysJSONArray!=null) {
            if (this.tempUnit.equals("metric")) {
                this.tempUnit = "us";
                menu.setIcon(ContextCompat.getDrawable(this, R.drawable.units_f));
                editor.putString("tempUnit", "us");
                editor.apply();

            } else {
                this.tempUnit = "metric";
                menu.setIcon(ContextCompat.getDrawable(this, R.drawable.units_c));
                editor.putString("tempUnit", "metric");
                editor.apply();
            }
            checkNet(null);
        }
    }


    public void selectCity(MenuItem menu){

        if(! hasNetworkConnection()){
            Toast.makeText(this,"No Internet Connection", Toast.LENGTH_SHORT).show();
            return;
        }

        if(daysJSONArray!=null) {

            final EditText editText = new EditText(this);
            editText.setGravity(Gravity.CENTER);

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                    city = String.valueOf(editText.getText());
                    editor.putString("city", city);
                    editor.apply();
                    doDownload();
                }
            });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Log.d(TAG, "onClick: positive pressed");
                }
            });
            builder.setTitle("Enter a Location");
            builder.setMessage("For US locations, enter as 'City', or 'City,State'." +System.getProperty("line.separator")+
                    System.getProperty("line.separator")+"For Internation Locations, enter as 'City,Country'");
            builder.setView(editText);
            AlertDialog dialog = builder.create();
            dialog.show();
        }
    }


    public void checkNet(View v) {
        if (hasNetworkConnection()) {
            doDownload();
            findViewById(R.id.todays_morning_label).setVisibility(View.INVISIBLE);
            findViewById(R.id.todays_noon_label).setVisibility(View.INVISIBLE);
            findViewById(R.id.todays_evening_label).setVisibility(View.INVISIBLE);
            findViewById(R.id.todays_night_label).setVisibility(View.INVISIBLE);
        }
        else {
            swiper.setRefreshing(false);
            dateTimeEpoch.setText("No Internet Connection");

            Toast.makeText(this,"No Internet Connection", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean hasNetworkConnection() {
        ConnectivityManager connectivityManager = getSystemService(ConnectivityManager.class);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnectedOrConnecting());
    }


    private void doDownload() {
        WeatherDownloader.downloadWeather(this, city, tempUnit);
        Log.d(TAG, "doDownload: ");
    }

    public void updateData(Weather weather) throws JSONException {
        if (weather == null) {
            Toast.makeText(this, "Please Enter a Valid City Name", Toast.LENGTH_SHORT).show();
            return;
        }
        else {
            swiper.setRefreshing(false);
        }

        this.daysJSONArray = weather.getDays();
        this.resolvedAddress = weather.getResolvedAddress();
        getSupportActionBar().setTitle(this.resolvedAddress);

        hourlyForecastList.removeAll(hourlyForecastList);

        this.timeZone = weather.getTimezone();

        long dtEpoch = Long.parseLong(weather.getCurrentDateTimeEpoch());
        Date dateTime = new Date(dtEpoch * 1000); // Java time values need milliseconds
        SimpleDateFormat fullDate =
                new SimpleDateFormat("EEE MMM dd h:mm a, yyyy", Locale.getDefault());
        SimpleDateFormat dayOnly =
                new SimpleDateFormat("EEEE", Locale.getDefault());
        SimpleDateFormat timeOnly = new SimpleDateFormat("h:mm a", Locale.getDefault());
        SimpleDateFormat timeOnly24 = new SimpleDateFormat( "HH");
        timeOnly24.setTimeZone(TimeZone.getTimeZone(weather.getTimezone()));
        fullDate.setTimeZone(TimeZone.getTimeZone(weather.getTimezone()));
        String timeOnlyStr = timeOnly.format(dateTime); // 12:00 AM
        String fullDateStr = fullDate.format(dateTime); // Thu Sep 29 12:00 AM, 2022
        String timeOnly24Str = timeOnly24.format(dateTime);

        String dayOnlyStr = dayOnly.format(dateTime);



        dateTimeEpoch.setText(fullDateStr);


        TextView currentTemp = findViewById(R.id.current_temp);
        currentTemp.setText(weather.getCurrentTemp()+"°"+(tempUnit.equals("us") ? "F" : "C"));

        TextView feelsLike = findViewById(R.id.feels_like);
        feelsLike.setText("Feels Like: "+weather.getCurrentFeelsLike()+"°"+(tempUnit.equals("us") ? "F" : "C"));

        TextView humidity = findViewById(R.id.current_humidity);
        humidity.setText("Humidity: "+weather.getCurrentHumidity()+"%");

        TextView UVIndex = findViewById(R.id.uv_index);
        UVIndex.setText("UV Index: "+weather.getCurrentUVIndex());

        TextView morningTemp = findViewById(R.id.morning_temp);
        morningTemp.setText(weather.getMorningTemp()+"°"+(tempUnit.equals("us") ? "F" : "C"));

        TextView afternoonTemp = findViewById(R.id.afternoon_temp);
        afternoonTemp.setText(weather.getAfternoonTemp()+"°"+(tempUnit.equals("us") ? "F" : "C"));

        TextView eveningTemp = findViewById(R.id.evening_temp);
        eveningTemp.setText(weather.getEveningTemp()+"°"+(tempUnit.equals("us") ? "F" : "C"));

        TextView nightTemp = findViewById(R.id.night_temp);
        nightTemp.setText(weather.getNightTemp()+"°"+(tempUnit.equals("us") ? "F" : "C"));

        //sunrise sunset

        long sunriseEpoch = Long.parseLong(weather.getCurrentSunriseEpoch());
        Date dateTimeSunrise = new Date(sunriseEpoch * 1000); // Java time values need milliseconds
        SimpleDateFormat timeOnlySunrise = new SimpleDateFormat("h:mm a", Locale.getDefault());
        timeOnlySunrise.setTimeZone(TimeZone.getTimeZone(weather.getTimezone()));
        String timeOnlyStrSunrise = timeOnlySunrise.format(dateTimeSunrise); // 12:00 AM

        TextView sunriseTime = findViewById(R.id.todays_sunrise);
        sunriseTime.setText("Sunrise: "+timeOnlyStrSunrise);

        long sunsetEpoch = Long.parseLong(weather.getCurrentSunsetEpoch());
        Date dateTimeSunset = new Date(sunsetEpoch * 1000); // Java time values need milliseconds
        SimpleDateFormat timeOnlySunset = new SimpleDateFormat("h:mm a", Locale.getDefault());
        timeOnlySunset.setTimeZone(TimeZone.getTimeZone(weather.getTimezone()));
        String timeOnlyStrSunset = timeOnlySunset.format(dateTimeSunset); // 12:00 AM

        TextView sunsetTime = findViewById(R.id.todays_sunset);
        sunsetTime.setText("Sunset: "+timeOnlyStrSunset);

        TextView visibility = findViewById(R.id.current_visibility);
        visibility.setText("Visibility: "+weather.getCurrentVisibility()+(tempUnit.equals("us") ? " mi" : " km"));

        String windStr = getDirection(Double.parseDouble(weather.getCurrentWindDir()));
        windStr = windStr + " at " + weather.getCurrentWindSpeed() + (tempUnit.equals("us") ? " mph" : " kmph") + " gusting to "
                + weather.getCurrentWindGust()+ (tempUnit.equals("us") ? " mph" : " kmph");
        TextView winds = findViewById(R.id.current_winds);
        winds.setText("Winds: "+windStr);

        String currentWindDesc = weather.getCurrentConditionDesc()+" ("+weather.getCurrentCloudCover()+"% Clouds) ";
        TextView cloudDesc = findViewById(R.id.weather_desc);
        cloudDesc.setText(currentWindDesc);

        String icon1 = weather.getCurrentIcon();
        icon1 = icon1.replace("-", "_"); // Replace all dashes with underscores
        int iconID =
                this.getResources().getIdentifier(icon1, "drawable", this.getPackageName());
        if (iconID == 0) {
            Log.d(TAG, "parseCurrentRecord: CANNOT FIND ICON ");
        }
        ImageView imgView = findViewById(R.id.weather_icon);
        imgView.setImageResource(iconID);

        int currentHour = Integer.parseInt(timeOnly24Str)+1;

        JSONArray hours0 = ((JSONObject) weather.getDays().get(0)).getJSONArray("hours");
        JSONArray hours1 = ((JSONObject) weather.getDays().get(1)).getJSONArray("hours");
        JSONArray hours2 = ((JSONObject) weather.getDays().get(2)).getJSONArray("hours");
        JSONArray hours3 = ((JSONObject) weather.getDays().get(3)).getJSONArray("hours");



        for (int i = currentHour; i < hours0.length(); i++) {
            String hourDay = "Today";

            long timeData = Long.parseLong(((JSONObject) hours0.get(i)).getString("datetimeEpoch"));

            Date dateTime1 = new Date(timeData * 1000);
            SimpleDateFormat dayOnly1 =
                    new SimpleDateFormat("EEEE", Locale.getDefault());
            SimpleDateFormat timeOnly1 = new SimpleDateFormat("h:mm a", Locale.getDefault());
            timeOnly1.setTimeZone(TimeZone.getTimeZone(weather.getTimezone()));
            dayOnly1.setTimeZone(TimeZone.getTimeZone(weather.getTimezone()));
            String timeOnlyStr1 = timeOnly1.format(dateTime1);

            String hourTempData = ((JSONObject) hours0.get(i)).getString("temp");
            int hourTempInt = (int) Double.parseDouble(hourTempData);
            String hourTemp = hourTempInt+"°"+(tempUnit.equals("us") ? " F" : " C");

            String hourConditions = ((JSONObject) hours0.get(i)).getString("conditions");

            String hourIcon = ((JSONObject) hours0.get(i)).getString("icon");
            String hourlyIcon = hourIcon.replace("-", "_"); // Replace all dashes with underscores
            int hourlyIconID =
                    this.getResources().getIdentifier(hourlyIcon, "drawable", this.getPackageName());
            if (hourlyIconID == 0) {
                Log.d(TAG, "parseCurrentRecord: CANNOT FIND ICON ");
            }
            hourlyForecastList.add(new HourlyForecast(hourDay, timeOnlyStr1, hourlyIconID, hourTemp, hourConditions));
        }

        for (int i = 0; i < hours1.length(); i++) {

            long timeData = Long.parseLong(((JSONObject) hours1.get(i)).getString("datetimeEpoch"));

            Date dateTime1 = new Date(timeData * 1000);
            SimpleDateFormat dayOnly1 =
                    new SimpleDateFormat("EEEE", Locale.getDefault());
            SimpleDateFormat timeOnly1 = new SimpleDateFormat("h:mm a", Locale.getDefault());
            timeOnly1.setTimeZone(TimeZone.getTimeZone(weather.getTimezone()));
            dayOnly1.setTimeZone(TimeZone.getTimeZone(weather.getTimezone()));
            String timeOnlyStr1 = timeOnly1.format(dateTime1);
            String dayOnlyStr1 = dayOnly1.format(dateTime1);

            String hourTempData = ((JSONObject) hours1.get(i)).getString("temp");
            int hourTempInt = (int) Double.parseDouble(hourTempData);
            String hourTemp = hourTempInt+"°"+(tempUnit.equals("us") ? " F" : " C");

            String hourConditions = ((JSONObject) hours1.get(i)).getString("conditions");

            String hourIcon = ((JSONObject) hours1.get(i)).getString("icon");
            String hourlyIcon = hourIcon.replace("-", "_"); // Replace all dashes with underscores
            int hourlyIconID =
                    this.getResources().getIdentifier(hourlyIcon, "drawable", this.getPackageName());
            if (hourlyIconID == 0) {
                Log.d(TAG, "parseCurrentRecord: CANNOT FIND ICON ");
            }

            hourlyForecastList.add(new HourlyForecast(dayOnlyStr1, timeOnlyStr1, hourlyIconID, hourTemp, hourConditions));
        }

        for (int i = 0; i < hours2.length(); i++) {

            long timeData = Long.parseLong(((JSONObject) hours2.get(i)).getString("datetimeEpoch"));

            Date dateTime1 = new Date(timeData * 1000);
            SimpleDateFormat dayOnly1 =
                    new SimpleDateFormat("EEEE", Locale.getDefault());
            SimpleDateFormat timeOnly1 = new SimpleDateFormat("h:mm a", Locale.getDefault());
            timeOnly1.setTimeZone(TimeZone.getTimeZone(weather.getTimezone()));
            dayOnly1.setTimeZone(TimeZone.getTimeZone(weather.getTimezone()));
            String timeOnlyStr1 = timeOnly1.format(dateTime1);
            String dayOnlyStr1 = dayOnly1.format(dateTime1);

            String hourTempData = ((JSONObject) hours2.get(i)).getString("temp");
            int hourTempInt = (int) Double.parseDouble(hourTempData);
            String hourTemp = hourTempInt+"°"+(tempUnit.equals("us") ? " F" : " C");

            String hourConditions = ((JSONObject) hours2.get(i)).getString("conditions");
            String hourIcon = ((JSONObject) hours2.get(i)).getString("icon");
            String hourlyIcon = hourIcon.replace("-", "_"); // Replace all dashes with underscores
            int hourlyIconID =
                    this.getResources().getIdentifier(hourlyIcon, "drawable", this.getPackageName());
            if (hourlyIconID == 0) {
                Log.d(TAG, "parseCurrentRecord: CANNOT FIND ICON ");
            }

            hourlyForecastList.add(new HourlyForecast(dayOnlyStr1, timeOnlyStr1, hourlyIconID, hourTemp, hourConditions));
        }

        for (int i = 0; i < hours3.length(); i++) {

            long timeData = Long.parseLong(((JSONObject) hours3.get(i)).getString("datetimeEpoch"));

            Date dateTime1 = new Date(timeData * 1000);
            SimpleDateFormat dayOnly1 =
                    new SimpleDateFormat("EEEE", Locale.getDefault());
            SimpleDateFormat timeOnly1 = new SimpleDateFormat("h:mm a", Locale.getDefault());
            timeOnly1.setTimeZone(TimeZone.getTimeZone(weather.getTimezone()));
            dayOnly1.setTimeZone(TimeZone.getTimeZone(weather.getTimezone()));
            String timeOnlyStr1 = timeOnly1.format(dateTime1);
            String dayOnlyStr1 = dayOnly1.format(dateTime1);

            String hourTempData = ((JSONObject) hours3.get(i)).getString("temp");
            int hourTempInt = (int) Double.parseDouble(hourTempData);
            String hourTemp = hourTempInt+"°"+(tempUnit.equals("us") ? " F" : " C");
            String hourConditions = ((JSONObject) hours3.get(i)).getString("conditions");
            String hourIcon = ((JSONObject) hours3.get(i)).getString("icon");
            String hourlyIcon = hourIcon.replace("-", "_"); // Replace all dashes with underscores
            int hourlyIconID =
                    this.getResources().getIdentifier(hourlyIcon, "drawable", this.getPackageName());
            if (hourlyIconID == 0) {
                Log.d(TAG, "parseCurrentRecord: CANNOT FIND ICON ");
            }

            hourlyForecastList.add(new HourlyForecast(dayOnlyStr1, timeOnlyStr1, hourlyIconID, hourTemp, hourConditions));
        }


        HourlyWeatherAdapter mAdapter = new HourlyWeatherAdapter(hourlyForecastList, this);
        recyclerView.setAdapter(mAdapter);

        RecyclerView some = findViewById(R.id.todays_forecast_recycler);
        some.setVisibility(View.VISIBLE);

    }

    private String getDirection(double degrees) {
        if (degrees >= 337.5 || degrees < 22.5)
            return "N";
        if (degrees >= 22.5 && degrees < 67.5)
            return "NE";
        if (degrees >= 67.5 && degrees < 112.5)
            return "E";
        if (degrees >= 112.5 && degrees < 157.5)
            return "SE";
        if (degrees >= 157.5 && degrees < 202.5)
            return "S";
        if (degrees >= 202.5 && degrees < 247.5)
            return "SW";
        if (degrees >= 247.5 && degrees < 292.5)
            return "W";
        if (degrees >= 292.5 && degrees < 337.5)
            return "NW";
        return "X"; // We'll use 'X' as the default if we get a bad value
    }


}