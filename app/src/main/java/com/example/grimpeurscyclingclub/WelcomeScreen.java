package com.example.grimpeurscyclingclub;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.FirebaseAuth;

public class WelcomeScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_screen);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        // text to change
        TextView text = (TextView)findViewById(R.id.WelcomeText);

        if (user != null){
            Intent intent = getIntent();
            // takes the extra data put in intent inside CreateAccount.java
            String name = intent.getStringExtra("name");
            String role = intent.getStringExtra("role");
            text.setText(getString(R.string.welcome_text, name, role));
        }
        else {
            text.setText("Welcome! \n You are signed in as admin");
        }
    }

}