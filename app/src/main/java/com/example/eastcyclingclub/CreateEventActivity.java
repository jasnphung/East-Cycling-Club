package com.example.eastcyclingclub;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CreateEventActivity extends AppCompatActivity {

    Spinner eventType;
    FirebaseDatabase database;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);

        eventType = findViewById(R.id.eventtype_dropdown_menu);
        ArrayAdapter<CharSequence> eventAdapter = ArrayAdapter.createFromResource(this, R.array.EventOptions, R.layout.spnr_eventtype);
        eventAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        eventType.setPrompt("Select an Event Type");
        eventType.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) this);
        eventType.setAdapter(eventAdapter);

        ArrayAdapter<CharSequence> difficultyAdapter = ArrayAdapter.createFromResource(this, R.array.DifficultyLevelOptions, R.layout.spnr_difficultylevel);
        difficultyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

    }
}