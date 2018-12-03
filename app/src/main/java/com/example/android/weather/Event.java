package com.example.android.weather;

/*
* {@Event} represents a weather event.
*/
public class Event {

    // The current temperature
    private double currentTemperature;

    // The current weather
    private String currentWeather;


    // Constructs a new weather Event
    public Event(double eventTemperature, String eventWeather) {
        currentTemperature = eventTemperature;
        currentWeather = eventWeather;
    }

    public double getCurrentTemperature() {
        return currentTemperature;
    }

    public String getCurrentWeather() {
        return currentWeather;
    }
}
