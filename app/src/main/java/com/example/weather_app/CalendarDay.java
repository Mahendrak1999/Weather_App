package com.example.weather_app;

import java.io.Serializable;

public class CalendarDay implements Serializable {

    private String cdDay;
    private String cdMin;
    private String cdMax;
    private int icon;
    private String cdDesc;
    private String cdPrecip;
    private String cdUVIndex;
    private String cdMorning;
    private String cdAfternoon;
    private String cdEvening;
    private String cdNight;

    public CalendarDay(String cdDay, String cdMax, String cdMin, int icon, String cdDesc,
                       String cPrecip, String cUVIndex, String cMorning, String cAfternoon,
                       String cEvening, String cNight) {
        this.cdDay = cdDay;
        this.cdMin = cdMin;
        this.cdMax = cdMax;
        this.icon = icon;
        this.cdDesc = cdDesc;
        this.cdPrecip = cPrecip;
        this.cdUVIndex = cUVIndex;
        this.cdMorning = cMorning;
        this.cdAfternoon = cAfternoon;
        this.cdEvening = cEvening;
        this.cdNight = cNight;
    }

    public String getCdDay() {
        return cdDay;
    }

    public void setCdDay(String cdDay) {
        this.cdDay = cdDay;
    }

    public String getCdMin() {

        return String.valueOf( ( int ) Math.ceil( Double.parseDouble( cdMin ) ) );
    }

    public void setCdMin(String cdMin) {
        this.cdMin = cdMin;
    }

    public String getCdMax() {

        return String.valueOf( ( int ) Math.ceil( Double.parseDouble( cdMax ) ) );
    }

    public void setCdMax(String cdMax) {
        this.cdMax = cdMax;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getCdDesc() {
        return cdDesc;
    }

    public void setCdDesc(String cdDesc) {
        this.cdDesc = cdDesc;
    }

    public String getCdPrecip() {
        return String.valueOf( ( int ) Math.ceil( Double.parseDouble( cdPrecip ) ) );

    }

    public void setCdPrecip(String cdPrecip) {
        this.cdPrecip = cdPrecip;
    }

    public String getCdUVIndex() {
        return cdUVIndex;
    }

    public void setCdUVIndex(String cdUVIndex) {
        this.cdUVIndex = cdUVIndex;
    }

    public String getCdMorning() {

        return String.valueOf( ( int ) Math.ceil( Double.parseDouble( cdMorning ) ) );
    }

    public void setCdMorning(String cdMorning) {
        this.cdMorning = cdMorning;
    }

    public String getCdAfternoon() {

        return String.valueOf( ( int ) Math.ceil( Double.parseDouble( cdAfternoon ) ) );
    }

    public void setCdAfternoon(String cdAfternoon) {
        this.cdAfternoon = cdAfternoon;
    }

    public String getCdEvening() {

        return String.valueOf( ( int ) Math.ceil( Double.parseDouble( cdEvening ) ) );
    }

    public void setCdEvening(String cdEvening) {
        this.cdEvening = cdEvening;
    }

    public String getCdNight() {

        return String.valueOf( ( int ) Math.ceil( Double.parseDouble( cdNight ) ) );
    }

    public void setCdNight(String cdNight) {
        this.cdNight = cdNight;
    }
}
