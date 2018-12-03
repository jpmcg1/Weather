package com.example.android.weather;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Utils {

    private static final String TAG = "Utils";

    // Retrieves the weather data from the API and returns an Event
    public static Event fetchWeatherData(String urlRequest) {
        // Create URL object
        URL url = createUrlObject(urlRequest);

        // Perform a HTTP request to the URL and return a JSON response
        String jsonResponse = "";
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(TAG, "Problem making the HTTP request", e);
        }

        Event weather = extractDataFromJson(jsonResponse);

        return weather;
    }

    // Create a URL object from a URL String
    private static URL createUrlObject(String urlRequest) {
        URL url = null;

        try {
            url = new URL(urlRequest);
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

    // Access the appropriate data in the API and returns and Event object with the data.
    public static Event extractDataFromJson(String weatherJSON) {
        // If the JSON URL is empty, return null.
        if (TextUtils.isEmpty(weatherJSON)) {
            Log.v(TAG, "JSON string is empty or null");
            return null;
        }

        try {
            // Create a JSON object to hold the data
            JSONObject object = new JSONObject(weatherJSON);
            JSONArray weather = object.getJSONArray("weather");
            JSONObject main = object.getJSONObject("main");

            JSONObject firstPosition = weather.getJSONObject(0);
            String weatherDescription = firstPosition.getString("description");
            Double temperature = main.getDouble("temp");

            // Return the newly created Event with up to date information
            return new Event(temperature, weatherDescription);

        } catch (JSONException e) {
            Log.e(TAG, "Problem parsing the weather JSON results", e);
        }
        return null;
    }

}
