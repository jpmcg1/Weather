package com.example.android.weather;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class Utils {

    private static final String TAG = "Utils";

    // Retrieves the weather data from the API for current weather and returns an Event
    public static Event fetchWeatherData(String urlRequest) {
        // Create URL object
        URL url = createUrlObject(urlRequest);

        // Perform a HTTP request to the URL and return a JSON response
        String jsonResponse = "";
        try {
            jsonResponse = makeHttpRequest(url);
            Log.e(TAG, "Success making HTTP request");
        } catch (IOException e) {
            Log.e(TAG, "Problem making the HTTP request", e);
        }
        Event weather = extractDataFromJson(jsonResponse);
        return weather;
    }

    // Retrieves the weather data from the API for 5 day forecast weather and
    // returns an ArrayList<Event>
    public static ArrayList<Event> fetchWeatherForecastData(String urlRequest) {
        // Create URL object
        URL url = createUrlObject(urlRequest);

        // Perform a HTTP request to the URL and return a JSON response
        String jsonResponse = "";
        try {
            jsonResponse = makeHttpRequest(url);
            Log.e(TAG, "Success making HTTP request");
        } catch (IOException e) {
            Log.e(TAG, "Problem making the HTTP request", e);
        }

        ArrayList<Event> weatherForecastList = extractDataFromJsonForecast(jsonResponse);

        return weatherForecastList;
    }

    // Create a URL object from a URL String
    private static URL createUrlObject(String urlRequest) {
        URL url = null;

        try {
            url = new URL(urlRequest);
            Log.e(TAG, "Success creating URL");
        } catch (MalformedURLException exception) {
            Log.e(TAG, "Error creating URL", exception);
            return null;
        }
        return url;
    }

    // Make a HTTP request to the given URL and return a String.
    // The String is the entire JSON response from the server which needs to be parsed.
    private static String makeHttpRequest(URL url) throws IOException {
        // Create an empty String to put the JSON response into
        String jsonResponse = "";

        if (url == null) {
            return jsonResponse;
        }

        // Create a HttpURLConnection and InputStream object ready for assignment
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;

        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.connect();

            // If the request was successful (response code 200) read the input stream
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
                Log.e(TAG, "Success resolving JSON results");
            } else {
                Log.e(TAG, "Error response code " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(TAG, "Problem resolving the weather JSON results", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    // Covert the InputStream into a String which contains the whole JSON response
    // for the server
    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    // Access the appropriate data in the API and returns and Event object
    // with the current weather data.
    public static Event extractDataFromJson(String weatherJSON) {
        // If the JSON URL is empty, return null.
        if (TextUtils.isEmpty(weatherJSON)) {
            Log.v(TAG, "JSON string is empty or null");
            return null;
        }

        try {
            // Create a JSON object to hold the data
            JSONObject object = new JSONObject(weatherJSON);

            // Access the "weather" array
            JSONArray weather = object.getJSONArray("weather");
            JSONObject firstPosition = weather.getJSONObject(0);
            String weatherMain = firstPosition.getString("main");
            String weatherId = firstPosition.getString("id");

            // Access the "main" object
            JSONObject main = object.getJSONObject("main");
            Double temperature = main.getDouble("temp");

            Event currentWeather = new Event(temperature, weatherMain, weatherId);

            // Print out the retrieved current weather Event to check it worked
            System.out.println(currentWeather.toString());
            return currentWeather;
        } catch (JSONException e) {
            Log.e(TAG, "Problem parsing the weather JSON results", e);
        }
        return null;
    }

    // Access the appropriate data in the API and returns and ArrayList<Event>
    // object with the 5 day forecast data.
    public static ArrayList<Event> extractDataFromJsonForecast(String weatherJSON) {
        // If the JSON URL is empty, return null.
        if (TextUtils.isEmpty(weatherJSON)) {
            Log.v(TAG, "JSON string is empty or null");
            return null;
        }

        // Create an empty ArrayList of Events
        ArrayList<Event> events = new ArrayList<>();

        // Parse the JSON response for the 5-day forecast URL (different JSON format than
        // the current weather JSON response)
        try {
            // Create a JSON object to hold the data
            JSONObject object = new JSONObject(weatherJSON);
            // Access the "list" array
            JSONArray listArray = object.getJSONArray("list");

            // Create an ArrayList of Events created from the JSON response
            for (int i = 0; i < listArray.length() - 1; i++) {
                // Access first object in the list
                JSONObject firstObject = listArray.getJSONObject(i);
                // Get time and date info
                int timeAndDate = firstObject.getInt("dt");
                // Access the "main" object
                JSONObject mainObject = firstObject.getJSONObject("main");
                // Get temp info
                double temperature = mainObject.getDouble("temp");
                // Access the "weather" object
                JSONArray weather = firstObject.getJSONArray("weather");
                // Access the first object in the array
                JSONObject firstObjectII = weather.getJSONObject(0);
                // Get the weatherID
                String weatherId = firstObjectII.getString("id");
                // Get weather
                String weatherMain = firstObjectII.getString("main");

                // Add the Event to the ArrayList
                events.add(new Event(temperature, weatherMain, weatherId, timeAndDate));
            }

            // Print out to screen the list of weather Events to check the ArrayList has been
            // correctly formed
            for (int j = 0; j < events.size() - 1; j++) {
                System.out.println("Number: " + j + "; " + events.get(j).toString());
            }
            Log.e(TAG, "Success parsing JSON results");
            // Return the ArrayList of Events
            return events;
        } catch (JSONException e) {
            Log.e(TAG, "Problem parsing the weather JSON results", e);
        }
        return null;
    }

    //-----------------Getting the weather icon straight from the API -----------//

    // Get the Bitmap image from the URL - the icon ID is taken from the JSON first
    // position object above
            /*try {
                URL url = createUrlObject("http://openweathermap.org/img/w/" + weatherIcon + ".png");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream input = connection.getInputStream();
                Bitmap weatherIconImage = BitmapFactory.decodeStream(input);

                // Return the newly created Event with up to date information
                return new Event(temperature, weatherMain, weatherIconImage);
            } catch (IOException e) {
                e.printStackTrace();
            }*/

}
