package com.example.android.weather;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.text.DateFormatSymbols;
import java.util.ArrayList;

public class WeatherForecastAdapter extends ArrayAdapter<Event> {

    // Initialise the ArrayAdapter's initial storage for the context and the list of Events
    public WeatherForecastAdapter(Context context, ArrayList<Event> eventList) {
        super(context, 0, eventList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // Check if there is a spare view to reuse, and if not inflate a new one
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }

        // Get the Event object at the position in the list defined in the method parameter
        Event currentEvent = getItem(position);

        // Set the DAY and MONTH on the UI for the weather forecast of the current Event
        TextView dayTextView = (TextView) listItemView.findViewById(R.id.day);
        dayTextView.setText(returnDay(currentEvent.getDate()));

        // Set the TIME on the UI for the weather forecast of the current Event
        TextView timeTextView = (TextView) listItemView.findViewById(R.id.time);
        timeTextView.setText(returnTime(currentEvent.getDate()));

        // Set the temperature on the UI for the weather forecast of the current Event
        TextView tempTextView = (TextView) listItemView.findViewById(R.id.temp_predicted);
        tempTextView.setText(currentEvent.getTemperature() + "\u00b0C");

        // Update the weather icon ImageView
        ImageView weatherIconImageView = (ImageView)
                listItemView.findViewById(R.id.weather_icon_small);
        String weatherId = currentEvent.getWeatherID();
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

        return listItemView;
    }

    // String is returned from JSON in the "yyyy-MM-dd HH:mm:ss" format.
    // Extract the DAY and MONTH value only for addition to the UI
    public String returnDay(String date) {
        String day = Character.toString(date.charAt(8)) + Character.toString(date.charAt(9));
        String month = Character.toString(date.charAt(5)) + Character.toString(date.charAt(6));
        int numberMonth = Integer.parseInt(month);
        String vocabMonth = getMonth(numberMonth);

        return day + " " + vocabMonth;
    }

    // String is returned from JSON in the "yyyy-MM-dd HH:mm:ss" format.
    // Extract the TIME value only for addition to the UI
    public String returnTime(String date) {
        return Character.toString(date.charAt(11))+ Character.toString(date.charAt(12))
                + ":" + Character.toString(date.charAt(14))+ Character.toString(date.charAt(15));
    }

    // Converts numeral month into vocab month
    public String getMonth(int month) {
        return new DateFormatSymbols().getMonths()[month-1];
    }
}
