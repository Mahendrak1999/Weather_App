package com.example.weather_app;

import android.util.Log;

import org.json.JSONArray;

public class Weather {

    private static final String TAG = "Weather";

    String address;
    String timezone;
    int tzOffset;

    JSONArray days;

    String daysDateTimeEpoch;
    String daysTempMax;
    String daysTempMin;
    String daysPrecipProb;
    String daysUVIndex;
    String daysDescription;
    String daysIcon;


    //"hours" section
    JSONArray hours;

    String morningTemp;
    String afternoonTemp;
    String eveningTemp;
    String nightTemp;
    int hoursSize;
    String hoursDateTimeEpoch;
    String hoursTemp;
    String hoursConditions;
    String hoursIcon;

    String currentDateTimeEpoch;
    String currentTemp;
    String currentFeelsLike;
    String currentHumidity;
    String currentWindGust;
    String currentWindSpeed;
    String currentWindDir;
    String currentVisibility;
    String currentCloudCover;
    String currentUVIndex;
    String currentConditionDesc;
    String currentIcon;
    String currentSunriseEpoch;
    String currentSunsetEpoch;
    String resolvedAddress;


    Weather(String address, String currentDateTimeEpoch, String currentTemp, String currentFeelsLike, String currentHumidity, String currentUVIndex, String morningTemp, String afternoonTemp
            , String eveningTemp, String nightTemp, String currentSunriseEpoch, String currentSunsetEpoch, String currentVisibility, String currentWindDir, String currentWindGust,
            String currentWindSpeed, String currentConditionDesc, String currentCloudCover, String currentIcon, JSONArray days, String resolvedAddress, String timezone) {

        this.address = address;
        this.currentDateTimeEpoch = currentDateTimeEpoch;
        this.currentTemp = currentTemp;
        this.currentFeelsLike = currentFeelsLike;
        this.currentHumidity = currentHumidity;
        this.currentUVIndex = currentUVIndex;
        this.morningTemp = morningTemp;
        this.afternoonTemp = afternoonTemp;
        this.eveningTemp = eveningTemp;
        this.nightTemp = nightTemp;
        this.hours = hours;
        this.currentSunriseEpoch = currentSunriseEpoch;
        this.currentSunsetEpoch = currentSunsetEpoch;
        this.currentVisibility = currentVisibility;
        this.currentWindDir = currentWindDir;
        this.currentWindGust = currentWindGust;
        this.currentWindSpeed = currentWindSpeed;
        this.currentConditionDesc = currentConditionDesc;
        this.currentCloudCover = currentCloudCover;
        this.currentIcon = currentIcon;
        this.days = days;
        this.resolvedAddress = resolvedAddress;
        this.timezone = timezone;

        Log.d( TAG, "Weather: " );

    }

    public String getAddress() {
        return address;
    }

    public String getTimezone() {
        return timezone;
    }

    public int getTzOffset() {
        return tzOffset;
    }

    public JSONArray getDays() {
        return days;
    }

    public String getDaysDateTimeEpoch() {
        return daysDateTimeEpoch;
    }

    public String getDaysTempMax() {
        return daysTempMax;
    }

    public String getDaysTempMin() {
        return daysTempMin;
    }

    public String getDaysPrecipProb() {
        return daysPrecipProb;
    }

    public String getDaysUVIndex() {
        return daysUVIndex;
    }

    public String getDaysDescription() {
        return daysDescription;
    }

    public String getResolvedAddress() {
        return resolvedAddress;
    }

    public String getDaysIcon() {
        return daysIcon;
    }

    public JSONArray getHours() {
        return hours;
    }

    public String getMorningTemp() {

        return String.valueOf( ( int ) Math.ceil( Double.parseDouble( morningTemp ) ) );
    }

    public String getAfternoonTemp() {

        return String.valueOf( ( int ) Math.ceil( Double.parseDouble( afternoonTemp ) ) );
    }

    public String getEveningTemp() {

        return String.valueOf( ( int ) Math.ceil( Double.parseDouble( eveningTemp ) ) );
    }

    public String getNightTemp() {

        return String.valueOf( ( int ) Math.ceil( Double.parseDouble( nightTemp ) ) );
    }

    public int getHoursSize() {
        return hoursSize;
    }

    public String getHoursDateTimeEpoch() {
        return hoursDateTimeEpoch;
    }

    public String getHoursTemp() {
        return hoursTemp;
    }

    public String getHoursConditions() {
        return hoursConditions;
    }

    public String getHoursIcon() {
        return hoursIcon;
    }

//    public JSONObject getCurrentConditions() {
//        return currentConditions;
//    }

    public String getCurrentDateTimeEpoch() {
        return currentDateTimeEpoch;
    }

    public String getCurrentTemp() {
        return String.valueOf( ( int ) Math.ceil( Double.parseDouble( currentTemp ) ) );
    }

    public String getCurrentFeelsLike() {

        return String.valueOf( ( int ) Math.ceil( Double.parseDouble( currentFeelsLike ) ) );
    }

    public String getCurrentHumidity() {

        return String.valueOf( ( int ) Math.ceil( Double.parseDouble( currentHumidity ) ) );
    }

    public String getCurrentWindGust() {

        return currentWindGust;
    }

    public String getCurrentWindSpeed() {

        return String.valueOf( ( int ) Math.ceil( Double.parseDouble( currentWindSpeed ) ) );
    }

    public String getCurrentWindDir() {
        return currentWindDir;
    }

    public String getCurrentVisibility() {

        return String.valueOf( ( int ) Math.round( Double.parseDouble( currentVisibility ) ) );
    }

    public String getCurrentCloudCover() {

        return String.valueOf( ( int ) Math.ceil( Double.parseDouble( currentCloudCover ) ) );
    }

    public String getCurrentUVIndex() {
        return currentUVIndex;
    }

    public String getCurrentConditionDesc() {
        return currentConditionDesc;
    }

    public String getCurrentIcon() {
        return currentIcon;
    }

    public String getCurrentSunriseEpoch() {
        return currentSunriseEpoch;
    }

    public String getCurrentSunsetEpoch() {
        return currentSunsetEpoch;
    }

}
