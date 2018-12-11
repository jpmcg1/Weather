package com.example.android.weather;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class WeatherForecastActivity extends AppCompatActivity{
    ArrayList<Event> mResults;

    // URL for the weather data from the OpenWeatherMap website for 5 DAY FORECAST.
    private static final String WEATHER_FORECAST_REQUEST_URL =
            "http://api.openweathermap.org/data/2.5/forecast?q=Manchester,uk&APPID=51925842ffff00a9ea6b84970bd7321e";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weather_list);

        // Create an AsyncTask to perform the HTTP request on the given URL.
        // When the result is received, update the UI.
        WeatherForecastAsyncTask task = new WeatherForecastAsyncTask();
        task.execute(WEATHER_FORECAST_REQUEST_URL);
    }

    private void updateUi(ArrayList<Event> results) {
        // Create new adapter for the weather forecast
        WeatherForecastAdapter adapter =
                new WeatherForecastAdapter(this, results);

        // Create ListView to attach adapter to the list_item XML
        ListView listView = (ListView) findViewById(R.id.list);

        // Set the adapter to the listView
        listView.setAdapter(adapter);
    }

    //------------------WeatherForecastAsyncTask---------------------------------//

    private class WeatherForecastAsyncTask
            extends AsyncTask<String, Void, ArrayList<Event>> {

        @Override
        protected ArrayList<Event> doInBackground(String... urls) {
            if (urls.length < 1 || urls[0] == null) {
                return null;
            }
            ArrayList<Event> results = Utils.fetchWeatherForecastData(urls[0]);
            return results;
        }

        @Override
        protected void onPostExecute(ArrayList<Event> results) {
            if (results == null) {
                return;
            }
            updateUi(results);
        }
    }
}
