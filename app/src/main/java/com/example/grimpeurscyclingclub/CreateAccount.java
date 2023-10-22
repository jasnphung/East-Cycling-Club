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
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;





public class CreateAccount extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private FirebaseAuth mAuth;
    EditText editTextName;
    EditText editTextEmail;
    EditText editTextPassword;
    Button createAccountButton;
    Spinner spinner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createaccount_screen);
        mAuth = FirebaseAuth.getInstance();
        editTextName = findViewById(R.id.name);
        editTextEmail = findViewById(R.id.email);
        editTextPassword = findViewById(R.id.password);
        spinner = findViewById(R.id.dropdown_menu);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.Options, R.layout.spnr_accounttype);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setPrompt("Select an Account Type");
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

                String role = spinner.getSelectedItem().toString();

                createAccount(email, password, name, role);
            }
        });

    }

    public void createAccount(String email, String password, String name, String role) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // success notification
                            Toast.makeText(CreateAccount.this, "Account Created!", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(CreateAccount.this, WelcomeScreen.class);
                            // 'brings over' extra name and role to welcome page
                            intent.putExtra("name", name);
                            intent.putExtra("role", role);
                            startActivity(intent);
                            finish();

                        } else {
                            String errorMessage = task.getException().getMessage();
                            Toast.makeText(CreateAccount.this, errorMessage, Toast.LENGTH_SHORT).show();
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