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

        TextView textViewEventType = (TextView) listViewItem.findViewById(R.id.textViewEventType);
        TextView textViewDifficulty = (TextView) listViewItem.findViewById(R.id.textViewDifficulty);
        TextView textViewMinimumAge = (TextView) listViewItem.findViewById(R.id.textViewMinimumAge);
        TextView textViewMaximumAge = (TextView) listViewItem.findViewById(R.id.textViewMaximumAge);
        TextView textViewPace = (TextView) listViewItem.findViewById(R.id.textViewPace);

        EventListHelperClass eventListHelperClass = eventListHelperClasses.get(position);
        textViewEventType.setText("Event Type: " + eventListHelperClass.getEventType());
        textViewDifficulty.setText("Difficulty: " + eventListHelperClass.getDifficulty());
        textViewMinimumAge.setText("Minimum Age: " + eventListHelperClass.getMinimumAge());
        textViewMaximumAge.setText("Maximum Age: " + eventListHelperClass.getMaximumAge());
        textViewPace.setText("Pace Needed: " + eventListHelperClass.getPace());

        return listViewItem;
    }
}
