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

        // Set the time on the UI for the weather forecast of the current Event
        TextView timeTextView = (TextView) listItemView.findViewById(R.id.time);
        timeTextView.setText(Integer.toString(currentEvent.getTime()));

        // Set the temperature on the UI for the weather forecast of the current Event
        TextView tempTextView = (TextView) listItemView.findViewById(R.id.temp_predicted);
        tempTextView.setText(Double.toString(currentEvent.getTemperature()));

        // TODO add in images to Drawable file and set them to UI here.
        // Check Main Activity for details

        return listItemView;
    }
}
