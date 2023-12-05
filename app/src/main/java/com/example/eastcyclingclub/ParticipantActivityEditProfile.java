package com.example.eastcyclingclub;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ParticipantActivityEditProfile extends AppCompatActivity {

    EditText editName, editEmail, editPassword;
    Button saveButton;
    String nameUser, usernameUser, passwordUser, roleUser;

    FirebaseDatabase database;
    DatabaseReference reference;

    public ParticipantActivityEditProfile(){}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.participant_activity_edit_profile);

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {
                usernameUser = null;
                nameUser = null;
                passwordUser = null;
            } else {
                usernameUser = extras.getString("username");
                nameUser = extras.getString("name");
                passwordUser = extras.getString("password");
            }
        } else {
            usernameUser = (String) savedInstanceState.getSerializable("username");
            nameUser = (String) savedInstanceState.getSerializable("name");
            passwordUser = (String) savedInstanceState.getSerializable("password");
        }

        reference = FirebaseDatabase.getInstance().getReference("users");

        editName = findViewById(R.id.editName);
        editEmail = findViewById(R.id.editEmail);
        editPassword = findViewById(R.id.editUsername);

        showUserData();

        saveButton = findViewById(R.id.saveButton);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name = editName.getText().toString();
                String email = editEmail.getText().toString();
                String password = editPassword.getText().toString();

                if ( allCredentialsAreValid(name, email, password) ) {
                    reference.child(usernameUser).child("name").setValue( name );
                    reference.child(usernameUser).child("email").setValue( email );
                    reference.child(usernameUser).child("password").setValue( password );
                    Toast.makeText(ParticipantActivityEditProfile.this, "Saved", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(ParticipantActivityEditProfile.this, ParticipantActivityProfile.class);

                    intent.putExtra("name", name );
                    intent.putExtra("username", usernameUser);
                    intent.putExtra("email", email );
                    intent.putExtra("password", password);
                    intent.putExtra("role", roleUser);

                    startActivity(intent);
                } else if ( name.isEmpty() ) {
                    editName.setError("No Name Specified");
                    editName.requestFocus();
                }
                else if ( email.isEmpty() ) {
                    editEmail.setError("No Email Specified");
                    editEmail.requestFocus();
                }
                else if ( password.isEmpty() ) {
                    editPassword.setError("No Password Specified");
                    editPassword.requestFocus();
                }
                else {
                    Toast.makeText(ParticipantActivityEditProfile.this, "Ensure All Fields Are Entered", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public boolean allCredentialsAreValid(String newUserName, String newEmail, String newPassword){

        String emailRegex = "^[a-zA-Z0-9][a-zA-Z0-9_]+@[a-zA-Z0-9]+(?:\\.[a-zA-Z0-9]+)";

        Pattern pattern = Pattern.compile(emailRegex, Pattern.UNICODE_CASE);
        Matcher matcher = pattern.matcher(newEmail);

        // makes sure the email is valid, username is non-empty, and password is non-empty
        return ( matcher.matches() && !( newUserName.isEmpty() ) && !( newPassword.isEmpty() ) );
    }



//    private boolean isNameChanged() {
//        if (!nameUser.equals(editName.getText().toString())) {
//            return true;
//        } else {
//            return false;
//        }
//    }
//
//    private boolean isEmailChanged() {
//        if (!emailUser.equals(editEmail.getText().toString())) {
//            return true;
//        } else {
//            return false;
//        }
//    }
//
//
//    private boolean isPasswordChanged() {
//        if (!passwordUser.equals(editPassword.getText().toString())) {
//
//            return true;
//        } else {
//            return false;
//        }
//    }

    public void showUserData() {
        if( usernameUser == null ){ return; }

        DatabaseReference specificUserReference = database.getInstance().getReference().child("users").child(usernameUser);

        final String[] nameFromDatabase = new String[1];
        final String[] emailFromDatabase = new String[1];
        final String[] passwordFromDatabase = new String[1];
        final String[] roleFromDatabase = new String[1];

        new Thread(new Runnable() {
            @Override
            public void run() {
                specificUserReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            nameFromDatabase[0] = dataSnapshot.child("name").getValue(String.class);
                            emailFromDatabase[0] = dataSnapshot.child("email").getValue(String.class);
                            passwordFromDatabase[0] = dataSnapshot.child("password").getValue(String.class);
                            roleFromDatabase[0] = dataSnapshot.child("role").getValue(String.class);

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    editName.setText(nameFromDatabase[0]);
                                    editEmail.setText(emailFromDatabase[0]);
                                    editPassword.setText(passwordFromDatabase[0]);
                                    roleUser = roleFromDatabase[0];
                                }
                            });

                            Log.d("TAG", "Profile values retrieved");
                        } else {
                            Log.d("TAG", "Profile values do not exist");
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Log.d("TAG", "Error retrieving profile values", databaseError.toException());
                    }
                });
            }
        }).start();
    }

}