package com.example.android.weather;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    // URL for the weather data from the OpenWeatherMap website for CURRENT weather in Manchester.
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

        // Find the button for the 5 day forecast
        Button forecastButton = (Button) findViewById(R.id.forecast_button);
        // Set a click listener to the Button to tell the system to create an Intent to the
        // WeatherForecastActivity when clicked on
        forecastButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent weatherForecatIntent =
                        new Intent(MainActivity.this, WeatherForecastActivity.class);
                startActivity(weatherForecatIntent);
            }
        });
    }

    // Update the user interface with the current weather
    private void updateUi(Event result) {
        // Update the temperature TextView
        TextView tempTextVIew = (TextView) findViewById(R.id.temp);
        tempTextVIew.setText(result.getTemperature() + "\u00b0C");

        // Update the Weather TextView
        TextView weatherTextView = (TextView) findViewById(R.id.weather);
        weatherTextView.setText(result.getWeather());

        // Update the weather icon ImageView
        ImageView weatherIconImageView = (ImageView) findViewById(R.id.weather_icon);
        String weatherId = result.getWeatherID();
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

    //-------------------------- AsyncTask ----------------------------------------------//

    // To perform a network request on a background threat, and update the UI.
    private class WeatherAsyncTask extends AsyncTask<String, Void, Event> {
        // Runs on a background thread to make the network request.
        // Don't update the UI in the background thread, do it after it is finished.
        @Override
        protected Event doInBackground(String... urls) {
            // Don't perform the task if there are no URLs, or the first URL is nul
            if (urls.length < 1 || urls[0] == null) {
                return null;
            }
            Event result = Utils.fetchWeatherData(urls[0]);
            return result;
        }

        // After the background thread request is complete, update the UI.
        protected void onPostExecute(Event result) {
            if (result == null) {
                return;
            }
            updateUi(result);
        }
    }
}
