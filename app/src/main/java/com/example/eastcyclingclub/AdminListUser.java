package com.example.eastcyclingclub;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class AdminListUser extends ArrayAdapter<AdminHelperClassUserList> {
    private Activity context;
    List<AdminHelperClassUserList> adminHelperClassUserLists;

    public AdminListUser(Activity context, List<AdminHelperClassUserList> adminHelperClassUserLists) {
        super(context, R.layout.admin_list_user, adminHelperClassUserLists);
        this.context = context;
        this.adminHelperClassUserLists = adminHelperClassUserLists;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.admin_list_user, null, true);

        TextView textViewName =  (TextView) listViewItem.findViewById(R.id.textViewName);
        TextView textViewUsername = (TextView) listViewItem.findViewById(R.id.textViewUserName);
        TextView textViewRole = (TextView) listViewItem.findViewById(R.id.textViewRole);
        TextView textViewEmail= (TextView) listViewItem.findViewById(R.id.textViewEmail);
        TextView textViewPassword = (TextView) listViewItem.findViewById(R.id.textViewPassword);

        AdminHelperClassUserList adminHelperClassUserList = adminHelperClassUserLists.get(position);
        textViewName.setText(adminHelperClassUserList.getName());
        textViewUsername.setText("Username: " + adminHelperClassUserList.getUsername());
        textViewRole.setText("Role: " + adminHelperClassUserList.getRole());
        textViewEmail.setText("Email: " + adminHelperClassUserList.getEmail());
        textViewPassword.setText("Password: " + adminHelperClassUserList.getPassword());
        return listViewItem;
    }
}