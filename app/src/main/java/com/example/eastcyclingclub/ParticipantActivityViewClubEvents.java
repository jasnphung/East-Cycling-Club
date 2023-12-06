package com.example.eastcyclingclub;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ParticipantActivityViewClubEvents extends AppCompatActivity {
    //FirebaseDatabase databaseEvents;
    DatabaseReference databaseEvents;
    TextView clubEventsTextView;
    ListView listView;
    Button returnToClubButton;
    String userUsername;
    List<ClubHelperClassEvent> clubHelperClassEvents;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.participant_activity_view_club_events);

        // Getting the current user's username
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {
                userUsername = null;
            } else {
                userUsername = extras.getString("userUsernameKey");
            }
        } else {
            userUsername = (String) savedInstanceState.getSerializable("userUsernameKey");
        }

        databaseEvents = FirebaseDatabase.getInstance().getReference("users").child(userUsername).child("events");

        clubEventsTextView = findViewById(R.id.rateClubTextView);

        listView = (ListView) findViewById(R.id.listView);

        returnToClubButton = findViewById(R.id.returnToClubButton);

        clubHelperClassEvents = new ArrayList<>();

        // TODO: Implement participant enrolling in event functionality

        returnToClubButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ParticipantActivityViewClub.class);
                intent.putExtra("userUsernameKey", userUsername);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_left);
                finish();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        databaseEvents.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                clubHelperClassEvents.clear();

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    ClubHelperClassEvent clubHelperClassEvent = postSnapshot.getValue(ClubHelperClassEvent.class);
                    clubHelperClassEvents.add(clubHelperClassEvent);
                }

                ClubListEvent eventAdapter = new ClubListEvent(ParticipantActivityViewClubEvents.this, clubHelperClassEvents);
                listView.setAdapter(eventAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
