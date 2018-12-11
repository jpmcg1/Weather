package com.example.android.weather;

/*
* {@Event} represents a weather event.
*/
public class Event {

    // The current temperature
    private String temperature;

    // The current weather
    private String weather;

    // The weather icon from the API
    private String weatherID;

    // The date of the forecast weather
    private String date;

    // Constructs a new weather Event
    public Event(String eventTemperature, String eventWeather, String eventWeatherID) {
        temperature = eventTemperature;
        weather = eventWeather;
        weatherID = eventWeatherID;
    }

    // Constructs a new weather Event
    public Event(String eventTemperature, String eventWeather, String eventWeatherID,
                 String timeAndDate) {
        temperature = eventTemperature;
        weather = eventWeather;
        weatherID = eventWeatherID;
        date = timeAndDate;
    }

    public String getTemperature() {
        return temperature;
    }

    public String getWeather() {
        return weather;
    }

    public String getWeatherID() {
        return weatherID;
    }

    public String getDate() {
        return date;
    }

    @Override
    public String toString() {
        return "Temp: " + getTemperature() +
                " ; Weather: " + getWeather() +
                " ; WeatherID: " + getWeatherID() +
                " ; Time " + getDate() +
                "\n";
    }
}
