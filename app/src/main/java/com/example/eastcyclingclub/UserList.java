package com.example.eastcyclingclub;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class UserList extends ArrayAdapter<UserListHelperClass> {
    private Activity context;
    List<UserListHelperClass> userListHelperClasses;

    public UserList(Activity context, List<UserListHelperClass> userListHelperClasses) {
        super(context, R.layout.activity_user_list, userListHelperClasses);
        this.context = context;
        this.userListHelperClasses = userListHelperClasses;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.activity_user_list, null, true);

        TextView textViewName =  (TextView) listViewItem.findViewById(R.id.textViewName);
        TextView textViewRole = (TextView) listViewItem.findViewById(R.id.textViewRole);
        TextView textViewEmail= (TextView) listViewItem.findViewById(R.id.textViewEmail);
        TextView textViewPassword = (TextView) listViewItem.findViewById(R.id.textViewPassword);

        UserListHelperClass userListHelperClass = userListHelperClasses.get(position);
        textViewName.setText("Name: " + userListHelperClass.getName());
        textViewRole.setText("Role: " + userListHelperClass.getRole());
        textViewEmail.setText("Email: " + userListHelperClass.getEmail());
        textViewPassword.setText("Password: " + userListHelperClass.getPassword());
        return listViewItem;
    }
}