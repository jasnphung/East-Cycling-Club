package com.example.eastcyclingclub;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class AdminListEvent extends ArrayAdapter<AdminHelperClassEventList>{
    private Activity context;
    List<AdminHelperClassEventList> adminHelperClassEventLists;

    public AdminListEvent(Activity context, List<AdminHelperClassEventList> adminHelperClassEventLists) {
        super(context, R.layout.admin_list_event, adminHelperClassEventLists);
        this.context = context;
        this.adminHelperClassEventLists = adminHelperClassEventLists;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.admin_list_event, null, true);

        TextView textViewEventType = (TextView) listViewItem.findViewById(R.id.textViewEventType);
        TextView textViewDifficulty = (TextView) listViewItem.findViewById(R.id.textViewDifficulty);
        TextView textViewMinimumAge = (TextView) listViewItem.findViewById(R.id.textViewMinimumAge);
        TextView textViewMaximumAge = (TextView) listViewItem.findViewById(R.id.textViewMaximumAge);
        TextView textViewPace = (TextView) listViewItem.findViewById(R.id.textViewPace);

        AdminHelperClassEventList adminHelperClassEventList = adminHelperClassEventLists.get(position);
        textViewEventType.setText("Event Type: " + adminHelperClassEventList.getEventType());
        textViewDifficulty.setText("Difficulty: " + adminHelperClassEventList.getDifficulty());
        textViewMinimumAge.setText("Minimum Age: " + adminHelperClassEventList.getMinimumAge());
        textViewMaximumAge.setText("Maximum Age: " + adminHelperClassEventList.getMaximumAge());
        textViewPace.setText("Pace Needed: " + adminHelperClassEventList.getPace());

        return listViewItem;
    }
}
