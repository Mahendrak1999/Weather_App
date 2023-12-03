package com.example.weather_app;

import java.io.Serializable;

public class HourlyForecast implements Serializable {

    private String day;
    private String dateTime;
    private int icon;
    private String temperature;
    private String description;

    public HourlyForecast(String day, String dateTime, int icon, String temperature, String description) {
        this.day = day;
        this.dateTime = dateTime;
        this.icon = icon;
        this.temperature = temperature;
        this.description = description;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
