package com.example.android.weather;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Utils {

    private static final String TAG = "Utils";

    // Access the appropriate data in the API and returns and Event object with the data.
    public Event extractDataFromJson(String weatherJSON) {
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
