package com.example.grimpeurscyclingclub;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import android.text.TextUtils;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;





public class CreateAccount extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private FirebaseAuth mAuth;
    EditText editTextName;
    EditText editTextEmail;
    EditText editTextPassword;
    Button createAccountButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createaccount_screen);
        mAuth = FirebaseAuth.getInstance();
        editTextName = findViewById(R.id.name);
        editTextEmail = findViewById(R.id.email);
        editTextPassword = findViewById(R.id.password);
        Spinner spinner = findViewById(R.id.dropdown_menu);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.Options, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setPrompt("Select an Option");
        spinner.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) this);
        createAccountButton = findViewById(R.id.createAccountButton);

        createAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email, password, name;
                email = String.valueOf(editTextEmail.getText());
                password = String.valueOf(editTextPassword.getText());
                name = String.valueOf(editTextName.getText());

                if (TextUtils.isEmpty(name)) {
                    Toast.makeText(CreateAccount.this, "Enter Name", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(CreateAccount.this, "Enter Username", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(CreateAccount.this, "Enter Password", Toast.LENGTH_SHORT).show();
                    return;
                }

                createAccount(email, password);
            }
        });

    }

    public void createAccount(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            //upon successful account creation, application immediately logs you in instead of taking you back to login page
                            Toast.makeText(CreateAccount.this, "Account Created!", Toast.LENGTH_SHORT).show();
                            // !TODO when going into welcome screen, find a way to bring name
                            //  !TODO into welcome screen since Firebase doesnt store that information
                            Intent intent = new Intent(getApplicationContext(), WelcomeScreen.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(CreateAccount.this, "Cannot Create Account", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String selectedItem = parent.getItemAtPosition(position).toString();

        // Depending on the selected item, perform actions accordingly
        if (selectedItem.equals("Join a Club")) {
            // Perform actions specific to "Join a Club"
            Toast.makeText(this, "Join a Club selected", Toast.LENGTH_SHORT).show();
        } else // Perform actions specific to "Participants"
            if (selectedItem.equals("Participants"))
                Toast.makeText(this, "Participants selected", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        Toast.makeText(this, "Nothing selected", Toast.LENGTH_SHORT).show();
    }





}