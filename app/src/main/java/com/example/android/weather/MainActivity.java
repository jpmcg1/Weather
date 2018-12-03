package com.example.android.weather;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    // URL for the weather data from the OpenWeatherMap website.
    private static final String WEATHER_REQUEST_URL =
            "http://api.openweathermap.org/data/2.5/weather?q=Manchester,uk&appid=51925842ffff00a9ea6b84970bd7321e";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Create an AsyncTask to perform the HTTP request on the given URL.
        // When the result is received, update the UI.
        WeatherAsyncTask task = new WeatherAsyncTask();
        task.execute(WEATHER_REQUEST_URL);
    }

    private void updateUi(Event results) {

    }


    // To perform a network request on a background threat, and update the UI.
    private class WeatherAsyncTask extends AsyncTask<String, Void, Event> {

        // Runs on a background thread to make the network request.
        // Don't update the UI in the background thread, do it after it is finished.
        protected Event doInBackground(String... urls) {
            // Don't perform the task if there are no URLs, or the first URL is nul
            if (urls.length < 1 || urls[0] == null) {
                return null;
            }
            Event result = Utils.fetchWeatherData(urls[0]);
            return result;
            }
        }

        // After the background thread request is complete, update the UI.
        protected void onPostExecute(Event result) {
        if (result == null) {
            return;
        }
        updateUi(result);
    }
}
