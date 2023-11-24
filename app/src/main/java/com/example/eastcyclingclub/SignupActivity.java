package com.example.eastcyclingclub;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignupActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    EditText signupName, signupUsername, signupEmail, signupPassword;
    TextView loginRedirectText;
    Button signupButton;
    Spinner spinner;
    FirebaseDatabase database;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        signupName = findViewById(R.id.signup_name);
        signupEmail = findViewById(R.id.signup_email);
        signupUsername = findViewById(R.id.signup_username);
        signupPassword = findViewById(R.id.signup_password);
        loginRedirectText = findViewById(R.id.loginRedirectText);
        signupButton = findViewById(R.id.signup_button);
        spinner = findViewById(R.id.dropdown_menu);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.AccountOptions, R.layout.spnr_accounttype);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) this);

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                database = FirebaseDatabase.getInstance();
                reference = database.getReference("users");

                String name = signupName.getText().toString();
                String email = signupEmail.getText().toString();
                String username = signupUsername.getText().toString();
                String password = signupPassword.getText().toString();
                String role = spinner.getSelectedItem().toString();

                if( verifyElementsAreNonEmpty(name, email, password, username, role) ){
                    UserListHelperClass userListHelperClass = new UserListHelperClass(name, email, username, password, role);
                    reference.child(username).setValue(userListHelperClass);

                    Toast.makeText(SignupActivity.this, "Signup successful!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(SignupActivity.this, "ERROR: missing information", Toast.LENGTH_LONG).show();
                }
            }
        });

        loginRedirectText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                startActivity(intent);
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


    private boolean verifyElementsAreNonEmpty(String name, String email, String pwd, String userName, String role){

        boolean allCredentialsAreValid = true;

        if( name.isEmpty() ){
            signupName.setError("Invalid credentials");
            signupName.requestFocus();
            allCredentialsAreValid = false;
        }
        if( email.isEmpty() ){
            signupEmail.setError("Invalid credentials");
            signupEmail.requestFocus();
            allCredentialsAreValid = false;
        }
        if( userName.isEmpty() ){
            signupUsername.setError("Invalid credentials");
            signupUsername.requestFocus();
            allCredentialsAreValid = false;
        }
        if( pwd.isEmpty() ){
            signupPassword.setError("Invalid credentials");
            signupPassword.requestFocus();
            allCredentialsAreValid = false;
        }
        if (role.equals("Select Account Type")) {
            spinner.requestFocus();
            allCredentialsAreValid = false;
        }

        return allCredentialsAreValid;
    }
}