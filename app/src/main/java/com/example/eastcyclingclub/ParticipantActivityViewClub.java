package com.example.eastcyclingclub;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ParticipantActivityViewClub extends AppCompatActivity {
    FirebaseDatabase database;
    DatabaseReference reference;
    TextView nameTextView, phoneNumberTextView, mainContactTextView, instagramUsernameTextView, twitterUsernameTextView, facebookLinkTextView;
    Button viewEventsButton, viewRatingsButton, addRatingButton, returnToSearchButton;
    String userUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.participant_activity_view_club);

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

        database = FirebaseDatabase.getInstance();
        reference = database.getReference(userUsername);

        nameTextView = findViewById(R.id.nameTextView);
        phoneNumberTextView = findViewById(R.id.phoneNumberTextView);
        mainContactTextView = findViewById(R.id.mainContactTextView);
        instagramUsernameTextView = findViewById(R.id.instagramUsernameTextView);
        twitterUsernameTextView = findViewById(R.id.twitterUsernameTextView);
        facebookLinkTextView = findViewById(R.id.facebookLinkTextView);

        viewEventsButton = findViewById(R.id.viewEventsButton);
        viewRatingsButton = findViewById(R.id.viewRatingsButton);
        addRatingButton = findViewById(R.id.addRatingButton);
        returnToSearchButton = findViewById(R.id.returnToSearchButton);

        showUserData();

        viewEventsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ParticipantActivityViewClubEvents.class);
                intent.putExtra("userUsernameKey", userUsername);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_left);
                finish();
            }
        });

        viewRatingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ParticipantActivityViewClubRatings.class);
                intent.putExtra("userUsernameKey", userUsername);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_left);
                finish();
            }
        });

        addRatingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ParticipantActivityAddRating.class);
                intent.putExtra("userUsernameKey", userUsername);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_left);
                finish();
            }
        });

        returnToSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ParticipantActivityEvents.class);
                intent.putExtra("userUsernameKey", userUsername);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_left);
                finish();
            }
        });
    }

    public void showUserData() {
        DatabaseReference specificUserReference = database.getInstance().getReference().child("users").child(userUsername);

        final String[] nameFromDatabase = new String[1];
        final String[] phoneNumberFromDatabase = new String[1];
        final String[] mainContactFromDatabase = new String[1];
        final String[] instagramUsernameFromDatabase = new String[1];
        final String[] twitterUsernameFromDatabase = new String[1];
        final String[] facebookLinkFromDatabase = new String[1];

        specificUserReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    nameFromDatabase[0] = dataSnapshot.child("name").getValue(String.class);
                    phoneNumberFromDatabase[0] = dataSnapshot.child("phoneNumber").getValue(String.class);
                    mainContactFromDatabase[0] = dataSnapshot.child("mainContact").getValue(String.class);
                    instagramUsernameFromDatabase[0] = dataSnapshot.child("instagramUsername").getValue(String.class);
                    twitterUsernameFromDatabase[0] = dataSnapshot.child("twitterUsername").getValue(String.class);
                    facebookLinkFromDatabase[0] = dataSnapshot.child("facebookLink").getValue(String.class);

                    nameTextView.setText(nameFromDatabase[0]);
                    phoneNumberTextView.setText("Phone number: " + phoneNumberFromDatabase[0]);

                    if (mainContactFromDatabase[0].isEmpty()) {
                        mainContactTextView.setText("Main contact: None");
                    }
                    else {
                        mainContactTextView.setText("Main contact: " + mainContactFromDatabase[0]);
                    }

                    if (instagramUsernameFromDatabase[0].isEmpty()) {
                        instagramUsernameTextView.setText("Instagram username: None");
                    }
                    else {
                        instagramUsernameTextView.setText("Instagram username: " + instagramUsernameFromDatabase[0]);
                    }

                    if (twitterUsernameFromDatabase[0].isEmpty()) {
                        twitterUsernameTextView.setText("Twitter username: None");
                    }
                    else {
                        twitterUsernameTextView.setText("Twitter username: " + twitterUsernameFromDatabase[0]);
                    }

                    if (facebookLinkFromDatabase[0].isEmpty()) {
                        facebookLinkTextView.setText("Facebook username: None");
                    }
                    else {
                        facebookLinkTextView.setText("Facebook link: " + facebookLinkFromDatabase[0]);
                    }

                    Log.d("TAG", "Profile values retrieved");
                }
                else {
                    Log.d("TAG", "Profile values do not exist");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d("TAG", "Error retrieving profile values", databaseError.toException());
            }
        });
    }
}
