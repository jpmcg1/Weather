package com.example.android.weather;

import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.text.DateFormatSymbols;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

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

        // Set the time on the UI for the weather forecast of the current Event
        TextView timeTextView = (TextView) listItemView.findViewById(R.id.time);
        timeTextView.setText(returnDate(currentEvent.getDate()));

        // Set the temperature on the UI for the weather forecast of the current Event
        TextView tempTextView = (TextView) listItemView.findViewById(R.id.temp_predicted);
        tempTextView.setText(currentEvent.getTemperature());

        // TODO add in images to Drawable file and set them to UI here.
        // Check Main Activity for details

        return listItemView;
    }

    public String returnDate(String date) {
        // String is returned from JSON in the "yyyy-MM-dd HH:mm:ss" format.
        // Extract the Date value only for addition to the UI
        String day = Character.toString(date.charAt(8)) + Character.toString(date.charAt(9));
        String month = Character.toString(date.charAt(5)) + Character.toString(date.charAt(6));
        int numberMonth = Integer.parseInt(month);
        String vocabMonth = getMonth(numberMonth);

        return day + " " + vocabMonth;
    }

    // Converts numeral month into vocab month
    public String getMonth(int month) {
        return new DateFormatSymbols().getMonths()[month-1];
    }
}
