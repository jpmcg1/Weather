package com.example.android.weather;

public class Event {

    // The current temperature
    private double currentTemperature;

    // The current weather
    private String currentWeather;


    // Constructs a new weather Event
    public Event(double temperature, String weather) {
        currentTemperature = temperature;
        currentWeather = weather;
    }
}
