package com.example.android.weather;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

/*
* {@Event} represents a weather event.
*/
public class Event {

    // The current temperature
    private double currentTemperature;

    // The current weather
    private String currentWeather;

    // The weather icon from the API
    private String currentWeatherID;


    // Constructs a new weather Event
    public Event(double eventTemperature, String eventWeather, String eventWeatherID) {
        currentTemperature = eventTemperature;
        currentWeather = eventWeather;
        currentWeatherID = eventWeatherID;
    }

    public double getCurrentTemperature() {
        return currentTemperature;
    }

    public String getCurrentWeather() {
        return currentWeather;
    }

    public String getCurrentWeatherID() {
        return currentWeatherID;
    }
}
