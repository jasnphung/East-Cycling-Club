package com.example.eastcyclingclub;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class EventList extends ArrayAdapter<EventListHelperClass>{
    private Activity context;
    List<EventListHelperClass> eventListHelperClasses;

    public EventList(Activity context, List<EventListHelperClass> eventListHelperClasses) {
        super(context, R.layout.activity_event_list, eventListHelperClasses);
        this.context = context;
        this.eventListHelperClasses = eventListHelperClasses;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.activity_event_list, null, true);

        TextView textViewEventName = (TextView) listViewItem.findViewById(R.id.textViewEventName);
        TextView textViewDifficulty = (TextView) listViewItem.findViewById(R.id.textViewDifficulty);
        TextView textViewMinimumAge = (TextView) listViewItem.findViewById(R.id.textViewMinimumAge);
        TextView textViewPace = (TextView) listViewItem.findViewById(R.id.textViewPace);
        TextView textViewRouteDetails = (TextView) listViewItem.findViewById(R.id.textViewRouteDetails);

        EventListHelperClass eventListHelperClass = eventListHelperClasses.get(position);
        textViewEventName.setText("Event name: " + eventListHelperClass.getName());
        textViewDifficulty.setText("Difficulty setting: " + eventListHelperClass.getDifficulty());
        textViewMinimumAge.setText("Minimum age setting: " + eventListHelperClass.getMinimumAge());
        textViewPace.setText("Pace setting: " + eventListHelperClass.getPace());
        textViewRouteDetails.setText("Route details setting: " + eventListHelperClass.getRouteDetails());

        return listViewItem;
    }
}
