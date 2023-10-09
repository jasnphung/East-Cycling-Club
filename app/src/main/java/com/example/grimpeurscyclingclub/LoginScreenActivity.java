package com.example.grimpeurscyclingclub;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;
import android.widget.EditText;
import android.widget.Button;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginScreenActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    EditText email;
    EditText password;
    Button loginButton;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_screen);
        mAuth = FirebaseAuth.getInstance();
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        loginButton = findViewById(R.id.loginButton);

        loginButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {

                if (TextUtils.isEmpty(email.getText().toString())) {
                    Toast.makeText(LoginScreenActivity.this, "Enter Email", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password.getText().toString())) {
                    Toast.makeText(LoginScreenActivity.this, "Enter Password", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (email.getText().toString().equals("admin") && password.getText().toString().equals("admin")) {
                    Toast.makeText(LoginScreenActivity.this, "Login Successful!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LoginScreenActivity.this, WelcomeScreen.class);
                    startActivity(intent);
                    finish();
                }

                signIn(email.getText().toString(), password.getText().toString());
            }
        });
    }

    private void signIn(String email, String password) {
        // [START sign_in_with_email]
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(LoginScreenActivity.this, "Login Successful!", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(LoginScreenActivity.this, WelcomeScreen.class);
                            startActivity(intent);

                        } else {
                            Toast.makeText(LoginScreenActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        // [END sign_in_with_email]
    }

    public void onClickSignUpText(View view) {
        Intent intent = new Intent(LoginScreenActivity.this, CreateAccount.class);
        startActivity(intent);
    }
}