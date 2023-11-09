package com.example.eastcyclingclub;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CreateEventActivity extends AppCompatActivity {
    FirebaseDatabase database;
    DatabaseReference reference;

    Button createEventTypeBTN, returnToEventsBTN;

    SwitchCompat difficultySC, minimumAgeSC, paceSC, routeDetailsSC;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);

        // Initialize Firebase
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("events");

        // Dropdown menu for the event type
        Spinner eventType = findViewById(R.id.eventtype_dropdown_menu);
        ArrayAdapter<CharSequence> eventAdapter  = ArrayAdapter.createFromResource(this, R.array.EventOptions, R.layout.spnr_eventtype);
        eventAdapter.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        eventType.setAdapter(eventAdapter);

        // Return to events button
        returnToEventsBTN = findViewById(R.id.returnToEventsButton);
        returnToEventsBTN.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), EventActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_left);
            finish();
        });

        // Makes sure at least 1 option is selected
        difficultySC = findViewById(R.id.difficultySwitch);
        minimumAgeSC = findViewById(R.id.minimumAgeSwitch);
        paceSC = findViewById(R.id.paceSwitch);
        routeDetailsSC = findViewById(R.id.routeDetailsSwitch);

        createEventTypeBTN = findViewById(R.id.createEventTypeButtton);

        createEventTypeBTN.setOnClickListener(view -> {
            if (difficultySC.isChecked() || minimumAgeSC.isChecked() || paceSC.isChecked() || routeDetailsSC.isChecked()) {
                // Allow creation

                // Grab event details from user input
                String eventName = eventType.getSelectedItem().toString();
                String selectedEventType = eventType.getSelectedItem().toString();

                // Create an instance of the EventCreateHelperClass with event details
                EventCreateHelperClass helper = new EventCreateHelperClass(eventName, String.valueOf(minimumAgeSC.isChecked()), String.valueOf(paceSC.isChecked()), selectedEventType, String.valueOf(difficultySC.isChecked()));

                // Generate a unique key for the event
                // String eventId = reference.push().getKey();
                String eventId = selectedEventType;

                // Store the event information in the Firebase Realtime Database under the "events" node with a unique key
                reference.child(eventId).setValue(helper);
                Toast.makeText(CreateEventActivity.this, "Event created successfully!", Toast.LENGTH_SHORT).show();
            }
            else {
                // Error message
            }
        });
    }
}