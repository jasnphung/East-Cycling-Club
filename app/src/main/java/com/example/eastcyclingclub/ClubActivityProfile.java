package com.example.eastcyclingclub;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;


public class ClubActivityProfile extends AppCompatActivity {

    TextView profileName, profileRole,profileUsername;
    TextView titleName, titleUsername;
    Button editProfile, logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.club_activity_profile);

        profileName = findViewById(R.id.profileName);
        profileRole = findViewById(R.id.profileRole);
        profileUsername = findViewById(R.id.profileUsername);
        editProfile = findViewById(R.id.editButton);
        logout = findViewById(R.id.logoutButton);

        showAllUserData();

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

    public void showAllUserData(){

        Intent intent = getIntent();
        String nameUser = intent.getStringExtra("name");
        String roleUser = intent.getStringExtra("role");
        String usernameUser = intent.getStringExtra("username");

        profileName.setText(nameUser);
        profileRole.setText(roleUser);
        profileUsername.setText(usernameUser);

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