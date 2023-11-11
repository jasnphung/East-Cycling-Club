package com.example.eastcyclingclub;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
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
        Spinner eventType = findViewById(R.id.editEventType);
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

        // Variables for making sure at least 1 option is selected
        difficultySC = findViewById(R.id.difficultySwitch);
        minimumAgeSC = findViewById(R.id.minimumAgeSwitch);
        paceSC = findViewById(R.id.paceSwitch);
        routeDetailsSC = findViewById(R.id.routeDetailsSwitch);
        createEventTypeBTN = findViewById(R.id.createEventTypeButtton);

        createEventTypeBTN.setOnClickListener(view -> {
            // Checks if at least one option is selected: if so, allows event creation, if not, outputs warning message
            if (difficultySC.isChecked() || minimumAgeSC.isChecked() || paceSC.isChecked() || routeDetailsSC.isChecked()) {
                // Grab event details from user input
                String selectedEventType = eventType.getSelectedItem().toString();

                // Create an instance of the EventCreateHelperClass with event details
                EventListHelperClass helper = new EventListHelperClass(selectedEventType, String.valueOf(difficultySC.isChecked()), String.valueOf(minimumAgeSC.isChecked()), String.valueOf(paceSC.isChecked()), String.valueOf(routeDetailsSC.isChecked()));

                // Store the event information in the Firebase Realtime Database under the "events" node with a unique key
                reference.child(selectedEventType).setValue(helper);
                Toast.makeText(CreateEventActivity.this, "Event created successfully!", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(getApplicationContext(), EventActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_left);
                finish();
            }
            else {
                AlertDialog.Builder builder = new AlertDialog.Builder(CreateEventActivity.this);
                builder.setTitle("Try again!");
                builder.setMessage("Please select at least 1 option.");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });
    }
}