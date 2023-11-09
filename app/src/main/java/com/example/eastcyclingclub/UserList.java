package com.example.eastcyclingclub;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class UserList extends ArrayAdapter<HelperClass> {
    private Activity context;
    List<HelperClass> helperClasses;

    public UserList(Activity context, List<HelperClass> helperClasses) {
        super(context, R.layout.activity_user_list, helperClasses);
        this.context = context;
        this.helperClasses = helperClasses;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.activity_user_list, null, true);

        TextView textViewName =  (TextView) listViewItem.findViewById(R.id.textViewName);
        TextView textViewRole = (TextView) listViewItem.findViewById(R.id.textViewRole);
        TextView textViewEmail= (TextView) listViewItem.findViewById(R.id.textViewEmail);
        TextView textViewPassword = (TextView) listViewItem.findViewById(R.id.textViewPassword);

        HelperClass helperClass = helperClasses.get(position);
        textViewName.setText("Name: " + helperClass.getName());
        textViewRole.setText("Role: " + helperClass.getRole());
        textViewEmail.setText("Email: " + helperClass.getEmail());
        textViewPassword.setText("Password: " + helperClass.getPassword());
        return listViewItem;
    }
}