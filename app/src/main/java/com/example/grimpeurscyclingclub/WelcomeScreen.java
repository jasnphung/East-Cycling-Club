package com.example.grimpeurscyclingclub;

import androidx.appcompat.app.AppCompatActivity;

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

        TextView text = (TextView)findViewById(R.id.WelcomeText);

        if(user != null){
            // TODO: Needs to be getDisplayName() eventually
            String name = user.getEmail();

            text.setText(getString(R.string.welcome_text, name));
        }else{
            text.setText(getString(R.string.unable_to_login));
        }
    }
}