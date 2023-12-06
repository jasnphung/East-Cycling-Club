package com.example.eastcyclingclub;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ParticipantActivityAddRating extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    FirebaseDatabase database;
    DatabaseReference reference;
    Spinner ratingOptions;
    EditText commentEditText;
    Button submitRatingButton, returnToClubButton;
    String userUsername, selectedRating;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.participant_activity_add_rating);

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

        ratingOptions = findViewById(R.id.ratingOptions);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.RatingOptions, R.layout.participant_spinner_ratings);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ratingOptions.setAdapter(adapter);
        ratingOptions.setOnItemSelectedListener(this);

        selectedRating = ratingOptions.getSelectedItem().toString();

        commentEditText = findViewById(R.id.commentEditText);

        submitRatingButton = findViewById(R.id.submitRatingButton);

        returnToClubButton = findViewById(R.id.returnToClubButton);

        submitRatingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ratingNumber = selectedRating;
                String ratingComment = commentEditText.getText().toString();

                if (ratingComment.isEmpty()) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(ParticipantActivityAddRating.this);
                    builder.setTitle("Try again!");
                    builder.setMessage("Please enter a comment");
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }
                else {
                    DatabaseReference specificUserReferenceNumbers = FirebaseDatabase.getInstance().getReference().child("users").child(userUsername).child("ratings");

                    ParticipantHelperClassRating helper = new ParticipantHelperClassRating(userUsername, ratingNumber, ratingComment);

                    specificUserReferenceNumbers.child(userUsername + "Rating").setValue(helper);

                    Toast.makeText(ParticipantActivityAddRating.this, "Rating submitted!", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(getApplicationContext(), ParticipantActivityEvents.class);
                    intent.putExtra("userUsernameKey", userUsername);
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_left);
                    finish();
                }
            }
        });

        returnToClubButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ParticipantActivityViewClub.class);
                intent.putExtra("userUsernameKey", userUsername);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_left);
                finish();
            }
        });
    }

    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        selectedRating = (String) parent.getItemAtPosition(position);
    }

    public void onNothingSelected(AdapterView<?> parent) {

    }
}
