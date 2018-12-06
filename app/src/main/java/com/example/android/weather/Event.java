package com.example.android.weather;

/*
* {@Event} represents a weather event.
*/
public class Event {

    // The current temperature
    private double temperature;

    // The current weather
    private String weather;

    // The weather icon from the API
    private String weatherID;

    private int time;


    // Constructs a new weather Event
    public Event(double eventTemperature, String eventWeather, String eventWeatherID,
                 int timeAndDate) {
        temperature = eventTemperature;
        weather = eventWeather;
        weatherID = eventWeatherID;
        time = timeAndDate;
    }

    public double getTemperature() {
        return temperature;
    }

    public String getWeather() {
        return weather;
    }

    public String getWeatherID() {
        return weatherID;
    }

    public int getTime() {
        return time;
    }

    @Override
    public String toString() {
        return "Temp: " + getTemperature() +
                " ; Weather: " + getWeather() +
                " ; WeatherID: " + getWeatherID() +
                " ; Time " + getTime() +
                "\n";
    }
}
