package com.example.eastcyclingclub;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.Firebase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UpdateEventActivity extends AppCompatActivity {
    FirebaseDatabase database;
    DatabaseReference reference;
    TextView eventName;
    Button editEventBTN, deleteEventBTN, returnToEventsBTN;
    SwitchCompat difficultySC, minimumAgeSC, paceSC, routeDetailsSC;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_event);

        Intent intent = getIntent();
        String nameValue = intent.getStringExtra("nameKey");
        String difficultyValue = intent.getStringExtra("difficultyKey");
        String minimumAgeValue = intent.getStringExtra("minimumAgeKey");
        String paceValue = intent.getStringExtra("paceKey");
        String routeDetailsValue = intent.getStringExtra("routeDetailsKey");

        eventName = findViewById(R.id.eventName);
        difficultySC = findViewById(R.id.difficultySwitch);
        minimumAgeSC = findViewById(R.id.minimumAgeSwitch);
        paceSC = findViewById(R.id.paceSwitch);
        routeDetailsSC = findViewById(R.id.routeDetailsSwitch);
        editEventBTN = findViewById(R.id.editEventButton);
        deleteEventBTN = findViewById(R.id.deleteEventButton);

        eventName.setText(nameValue);
        difficultySC.setChecked(Boolean.parseBoolean(difficultyValue));
        minimumAgeSC.setChecked(Boolean.parseBoolean(minimumAgeValue));
        paceSC.setChecked(Boolean.parseBoolean(paceValue));
        routeDetailsSC.setChecked(Boolean.parseBoolean(routeDetailsValue));

        editEventBTN.setOnClickListener(view -> {
            // Checks if at least one option is selected: if so, allows event creation, if not, outputs warning message
            if (difficultySC.isChecked() || minimumAgeSC.isChecked() || paceSC.isChecked() || routeDetailsSC.isChecked()) {
                updateProduct(nameValue, difficultySC.isChecked(), minimumAgeSC.isChecked(), paceSC.isChecked(), routeDetailsSC.isChecked());
                Toast.makeText(UpdateEventActivity.this, "Event updated", Toast.LENGTH_SHORT).show();

                Intent intent2 = new Intent(getApplicationContext(), EventActivity.class);
                startActivity(intent2);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_left);
                finish();
            } else {
                AlertDialog.Builder builder = new AlertDialog.Builder(UpdateEventActivity.this);
                builder.setTitle("Try again!");
                builder.setMessage("Please select at least 1 option.");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });

        deleteEventBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteProduct(nameValue);
                Toast.makeText(UpdateEventActivity.this, "Event delete", Toast.LENGTH_LONG).show();
                Intent intent3 = new Intent(getApplicationContext(), EventActivity.class);
                startActivity(intent3);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_left);
                finish();
            }
        });

        // Return to events button
        returnToEventsBTN = findViewById(R.id.returnToEventsButton);
        returnToEventsBTN.setOnClickListener(view -> {
            Intent intent1 = new Intent(getApplicationContext(), EventActivity.class);
            startActivity(intent1);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_left);
            finish();
        });
    }

    private void updateProduct(String eventNameOnOff, Boolean difficultyOnOff, Boolean minimumAgeOnOff, Boolean paceOnOff, Boolean routeDetailsOnOff) {
        DatabaseReference dR = FirebaseDatabase.getInstance().getReference("events").child(eventNameOnOff);

        EventListHelperClass eventListHelperClass = new EventListHelperClass(eventNameOnOff, String.valueOf(difficultyOnOff), String.valueOf(minimumAgeOnOff), String.valueOf(paceOnOff), String.valueOf(routeDetailsOnOff));
        dR.setValue(eventListHelperClass);
        Toast.makeText(getApplicationContext(), "Event Type Updated", Toast.LENGTH_LONG).show();
    }

    private void deleteProduct(String eventNameOnOff) {
        DatabaseReference dR = FirebaseDatabase.getInstance().getReference("events").child(eventNameOnOff);
        dR.removeValue();
        Toast.makeText(getApplicationContext(), "Event type deleted", Toast.LENGTH_LONG).show();
    }
}
