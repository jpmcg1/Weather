package com.example.android.weather;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.ArrayList;

// TODO udpate app so it gives the 5 day forecast every 3 hours - need a listItem and adapter for this
// Put it in a new activity with a button on main activity saying "5 day forecast"

public class MainActivity extends AppCompatActivity {

    // URL for the weather data from the OpenWeatherMap website.
    private static final String WEATHER_REQUEST_URL =
            "http://api.openweathermap.org/data/2.5/forecast?q=Manchester,uk&APPID=51925842ffff00a9ea6b84970bd7321e";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Create an AsyncTask to perform the HTTP request on the given URL.
        // When the result is received, update the UI.
        WeatherAsyncTask task = new WeatherAsyncTask();
        task.execute(WEATHER_REQUEST_URL);
    }

    // Update the user interface with the current weather
    private void updateUi(ArrayList<Event> results) {
        // The first Event object in the array list should be the current weather
        Event currentWeatherEvent = results.get(0);

        // Update the temperature TextView
        TextView tempTextVIew = (TextView) findViewById(R.id.temp);
        tempTextVIew.setText(formatTemperature(currentWeatherEvent.getTemperature()));

        // Update the Weather TextView
        TextView weatherTextView = (TextView) findViewById(R.id.weather);
        weatherTextView.setText(currentWeatherEvent.getWeather());

        // Update the weather icon ImageView
        ImageView weatherIconImageView = (ImageView) findViewById(R.id.weather_icon);
        String weatherId = currentWeatherEvent.getWeatherID();
        char c = weatherId.charAt(0);
        int a = Character.getNumericValue(c);
        if (a == 2) {
            weatherIconImageView.setImageResource(R.drawable.thunder);
        } else if (a == 3) {
            weatherIconImageView.setImageResource(R.drawable.drizzle);
        } else if (a == 5) {
            weatherIconImageView.setImageResource(R.drawable.rain);
        } else if (a == 6) {
            weatherIconImageView.setImageResource(R.drawable.snow);
        } else if (a == 7) {
            weatherIconImageView.setImageResource(R.drawable.fog);
        } else if (a == 8) {
            char c2 = weatherId.charAt(2);
            int a2 = Character.getNumericValue(c2);
            if (a2 == 0) {
                weatherIconImageView.setImageResource(R.drawable.clear_sky);
            } else {
                weatherIconImageView.setImageResource(R.drawable.cloudy);
            }
        } else {
            weatherIconImageView.setImageResource(R.drawable.unknown);
        }
    }

    // A method to format the temperature units and dps
    private String formatTemperature(double temperature) {
        // Change the temperature from Kelvin to Degrees
        Double degreeTemperature = temperature - 273.15;

        // Temperature to 1 d.p.
        DecimalFormat magnitudeFormat = new DecimalFormat("0.0");
        return magnitudeFormat.format(degreeTemperature);
    }

    //-------------------------- AsyncTask ----------------------------------------------//

    // To perform a network request on a background threat, and update the UI.
    private class WeatherAsyncTask extends AsyncTask<String, Void, ArrayList<Event>> {
        // Runs on a background thread to make the network request.
        // Don't update the UI in the background thread, do it after it is finished.
        @Override
        protected ArrayList<Event> doInBackground(String... urls) {
            // Don't perform the task if there are no URLs, or the first URL is nul
            if (urls.length < 1 || urls[0] == null) {
                return null;
            }
            ArrayList<Event> result = Utils.fetchWeatherData(urls[0]);
            return result;
        }

        // After the background thread request is complete, update the UI.
        protected void onPostExecute(ArrayList<Event> result) {
            if (result == null) {
                return;
            }
            updateUi(result);
        }
    }
}
