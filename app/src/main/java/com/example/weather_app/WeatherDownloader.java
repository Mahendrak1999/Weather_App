package com.example.weather_app;

import android.net.Uri;

import com.android.volley.Response;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class WeatherDownloader {


    private static MainActivity mainActivity;

    private static final String weatherURL = "https://weather.visualcrossing.com/VisualCrossingWebServices/rest/services/timeline/";
    private static final String yourAPIKey = "ZX47CYKG238K6ASMQNYV38LMF";

    public static void downloadWeather(MainActivity mainActivityIn, String city, String tempUnit) {

        mainActivity = mainActivityIn;

        RequestQueue queue = Volley.newRequestQueue( mainActivity );

        Uri.Builder buildURL = Uri.parse( weatherURL + city ).buildUpon();
        buildURL.appendQueryParameter( "unitGroup", tempUnit );
        buildURL.appendQueryParameter( "key", yourAPIKey );
        String urlToUse = buildURL.build().toString();

        Response.Listener<JSONObject> listener = response -> parseJSON( response.toString() );

        Response.ErrorListener error = error1 -> {
            try {
                mainActivity.updateData( null );
            } catch (JSONException e) {
                e.printStackTrace();
            }
        };

        JsonObjectRequest jsonObjectRequest =
                new JsonObjectRequest( Request.Method.GET, urlToUse,
                        null, listener, error );

        queue.add( jsonObjectRequest );
    }

    private static void parseJSON(String s) {

        try {
            JSONObject jObjMain = new JSONObject( s );

            String address = jObjMain.getString( "address" );
            String timezone = jObjMain.getString( "timezone" );
            String resolvedAddress = jObjMain.getString( "resolvedAddress" );

            // "days" section
            JSONArray days = jObjMain.getJSONArray( "days" );

            //"hours" section
            JSONArray hours = (( JSONObject ) days.get( 0 )).getJSONArray( "hours" );

            String morningTemp = (( JSONObject ) hours.get( 8 )).getString( "temp" );
            String afternoonTemp = (( JSONObject ) hours.get( 13 )).getString( "temp" );
            String eveningTemp = (( JSONObject ) hours.get( 17 )).getString( "temp" );
            String nightTemp = (( JSONObject ) hours.get( 23 )).getString( "temp" );


            // "currentConditions" section
            JSONObject currentConditions = jObjMain.getJSONObject( "currentConditions" );
            String currentDateTimeEpoch = currentConditions.getString( "datetimeEpoch" );
            String currentTemp = currentConditions.getString( "temp" );
            String currentFeelsLike = currentConditions.getString( "feelslike" );
            String currentHumidity = currentConditions.getString( "humidity" );
            String currentWindGust = currentConditions.getString( "windgust" );
            String currentWindSpeed = currentConditions.getString( "windspeed" );
            String currentWindDir = currentConditions.getString( "winddir" );
            String currentVisibility = currentConditions.getString( "visibility" );
            String currentCloudCover = currentConditions.getString( "cloudcover" );
            String currentUVIndex = currentConditions.getString( "uvindex" );
            String currentConditionDesc = currentConditions.getString( "conditions" );
            String currentIcon = currentConditions.getString( "icon" );
            String currentSunriseEpoch = currentConditions.getString( "sunriseEpoch" );
            String currentSunsetEpoch = currentConditions.getString( "sunsetEpoch" );


            Weather weatherObj = new Weather( address, currentDateTimeEpoch, currentTemp, currentFeelsLike, currentHumidity, currentUVIndex, morningTemp, afternoonTemp
                    , eveningTemp, nightTemp, currentSunriseEpoch, currentSunsetEpoch, currentVisibility, currentWindDir, currentWindGust,
                    currentWindSpeed, currentConditionDesc, currentCloudCover, currentIcon, days, resolvedAddress, timezone );
            mainActivity.updateData( weatherObj );

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
