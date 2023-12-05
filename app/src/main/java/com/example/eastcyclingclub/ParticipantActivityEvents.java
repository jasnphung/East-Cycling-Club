package com.example.eastcyclingclub;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ParticipantActivityEvents extends AppCompatActivity {

    String userUsername, userName, userRole, userPassword;
    ListView listViewEvents;
    DatabaseReference databaseEvents;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.participant_activity_events);

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {
                userUsername = null;
                userName = null;
                userRole = null;
                userPassword = null;
            } else {
                userUsername = extras.getString("username");
                userName = extras.getString("name");
                userRole = extras.getString("role");
                userPassword = extras.getString("password");
            }
        } else {
            userUsername = (String) savedInstanceState.getSerializable("username");
            userName = (String) savedInstanceState.getSerializable("name");
            userRole = (String) savedInstanceState.getSerializable("role");
            userPassword = (String) savedInstanceState.getSerializable("password");
        }

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigation);
        bottomNavigationView.setSelectedItemId(R.id.event);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.event){
                return true;
            }if (id == R.id.profile){
                Intent intent = new Intent(getApplicationContext(), ParticipantActivityProfile.class);
                intent.putExtra("username", userUsername);
                intent.putExtra("name", userName);
                intent.putExtra("role", userRole);
                intent.putExtra("password", userPassword);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_left);
                finish();
            }
            return false;
        });


//        databaseEvents = FirebaseDatabase.getInstance().getReference("users").child(userUsername).child("events");
//        listViewEvents = (ListView) findViewById(R.id.listViewEvents);

//        clubHelperClassEvents = new ArrayList<>();
    }

    @Override
    protected void onStart() {
        super.onStart();
        databaseEvents.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                clubHelperClassEvents.clear();

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    ClubHelperClassEvent clubHelperClassEvent = postSnapshot.getValue(ClubHelperClassEvent.class);
//                    clubHelperClassEvents.add(clubHelperClassEvent);
                }

//                ClubListEvent eventAdapter = new ClubListEvent(ParticipantActivityEvents.this, clubHelperClassEvents);
//                listViewEvents.setAdapter(eventAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}