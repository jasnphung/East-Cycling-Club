package com.example.eastcyclingclub;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;


public class ClubActivityProfile extends AppCompatActivity {
    FirebaseDatabase database;
    DatabaseReference reference;
    TextView profileName, profileRole, profileUsername, phoneNumber, mainContact, instagramUsername, twitterUsername, facebookLink;
    Button editProfile, logout;
    String userUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.club_activity_profile);

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

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigation);
        bottomNavigationView.setSelectedItemId(R.id.profile);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.event){
                Intent intent = new Intent(getApplicationContext(), ClubActivityEvents.class);
                intent.putExtra("userUsernameKey", userUsername);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_left);
                finish();
            }if (id == R.id.profile){
                return true;
            }
            return false;
        });

        // Initializing database
        database = FirebaseDatabase.getInstance();
        reference = database.getReference(userUsername);

        profileName = findViewById(R.id.profileName);
        profileRole = findViewById(R.id.profileRole);
        profileUsername = findViewById(R.id.profileUsername);
        phoneNumber = findViewById(R.id.phoneNumber);
        mainContact = findViewById(R.id.mainContact);
        instagramUsername = findViewById(R.id.instagramUsername);
        twitterUsername = findViewById(R.id.twitterUsername);
        facebookLink = findViewById(R.id.facebookLink);

        editProfile = findViewById(R.id.editButton);
        logout = findViewById(R.id.logoutButton);

        showUserData();

        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                passUserData();
            }
        });


        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                startActivity(new Intent(ClubActivityProfile.this, GeneralActivityLogin.class));
            }
        });

    }

    public void showUserData(){

        DatabaseReference specificUserReference = database.getInstance().getReference().child("users").child(userUsername);

        final String[] nameFromDatabase = new String[1];
        final String[] roleFromDatabase = new String[1];
        final String[] usernameFromDatabase = new String[1];
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
                    roleFromDatabase[0] = dataSnapshot.child("role").getValue(String.class);
                    usernameFromDatabase[0] = dataSnapshot.child("username").getValue(String.class);
                    phoneNumberFromDatabase[0] = dataSnapshot.child("phoneNumber").getValue(String.class);
                    mainContactFromDatabase[0] = dataSnapshot.child("mainContact").getValue(String.class);
                    instagramUsernameFromDatabase[0] = dataSnapshot.child("instagramUsername").getValue(String.class);
                    twitterUsernameFromDatabase[0] = dataSnapshot.child("twitterUsername").getValue(String.class);
                    facebookLinkFromDatabase[0] = dataSnapshot.child("facebookLink").getValue(String.class);

                    profileName.setText("Name: " + nameFromDatabase[0]);
                    profileRole.setText("Role: " + roleFromDatabase[0]);
                    profileUsername.setText("Username: " + usernameFromDatabase[0]);
                    phoneNumber.setText("Phone number: " + phoneNumberFromDatabase[0]);

                    if (mainContactFromDatabase[0].isEmpty()) {
                        mainContact.setText("Main contact: None");
                    }
                    else {
                        mainContact.setText("Main contact: " + mainContactFromDatabase[0]);
                    }

                    if (instagramUsernameFromDatabase[0].isEmpty()) {
                        instagramUsername.setText("Instagram username: None");
                    }
                    else {
                        instagramUsername.setText("Instagram username: " + instagramUsernameFromDatabase[0]);
                    }

                    if (twitterUsernameFromDatabase[0].isEmpty()) {
                        twitterUsername.setText("Twitter username: None");
                    }
                    else {
                        twitterUsername.setText("Twitter username: " + twitterUsernameFromDatabase[0]);
                    }

                    if (facebookLinkFromDatabase[0].isEmpty()) {
                        facebookLink.setText("Facebook username: None");
                    }
                    else {
                        facebookLink.setText("Facebook link: " + facebookLinkFromDatabase[0]);
                    }

                    Log.d("TAG", "Values retrieved");
                }
                else {
                    Log.d("TAG", "Values do not exist");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d("TAG", "Error retrieving values", databaseError.toException());
            }
        });
    }

    public void passUserData(){
        String userUsername = profileUsername.getText().toString().trim();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
        Query checkUserDatabase = reference.orderByChild("username").equalTo(userUsername);

        checkUserDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.exists()){

                    String nameFromDB = snapshot.child(userUsername).child("name").getValue(String.class);
                    String emailFromDB = snapshot.child(userUsername).child("email").getValue(String.class);
                    String usernameFromDB = snapshot.child(userUsername).child("username").getValue(String.class);
                    String passwordFromDB = snapshot.child(userUsername).child("password").getValue(String.class);

                    Intent intent = new Intent(ClubActivityProfile.this, GeneralActivityEditProfile.class);

                    intent.putExtra("name", nameFromDB);
                    intent.putExtra("email", emailFromDB);
                    intent.putExtra("username", usernameFromDB);
                    intent.putExtra("password", passwordFromDB);

                    startActivity(intent);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}