package com.example.eastcyclingclub;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;

public class EventActivity extends AppCompatActivity {

    // Variables for the expanding button
    FloatingActionButton expandFAB, offerFAB, editFAB;

    TextView offerText, editText;

    Boolean areAllFABsVisible;

    ListView listViewEvents;

    List<EventListHelperClass> eventListHelperClasses;

    DatabaseReference databaseEvents;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigation);
        bottomNavigationView.setSelectedItemId(R.id.event);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id==R.id.event){
                return true;
            }if (id==R.id.profile){

                startActivity(new Intent(getApplicationContext(), ProfileAdminActivity.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_left);
                finish();
            }if (id==R.id.account){
                startActivity(new Intent(getApplicationContext(), AccountActivity.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_left);
                finish();
            }
            return false;
        });

        // Floating action button for expanding to the offer event button and edit event button
        expandFAB = findViewById(R.id.expandMenuFAB);
        offerFAB = findViewById(R.id.offerEventFAB);
        editFAB = findViewById(R.id.editEventTypeFAB);

        offerText = findViewById(R.id.offerEventText);
        editText = findViewById(R.id.editEventTypeText);

        offerFAB.setVisibility(View.GONE);
        editFAB.setVisibility(View.GONE);
        offerText.setVisibility(View.GONE);
        editText.setVisibility(View.GONE);

        areAllFABsVisible = false;

        expandFAB.setOnClickListener(view -> {
            if (!areAllFABsVisible) {
                offerFAB.show();
                editFAB.show();
                offerText.setVisibility(View.VISIBLE);
                editText.setVisibility(View.VISIBLE);

                areAllFABsVisible = true;
            }
            else {
                offerFAB.hide();
                editFAB.hide();
                offerText.setVisibility(View.GONE);
                editText.setVisibility(View.GONE);

                areAllFABsVisible = false;
            }
        });

        // From current layout to creating event layout
        offerFAB.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), CreateEventActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_left);
            finish();
        });

        // From current layout to edit event layout
        editFAB.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), EditEventTypesActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_left);
            finish();
        });

        databaseEvents = FirebaseDatabase.getInstance().getReference("events");
        listViewEvents = (ListView) findViewById(R.id.listViewEvents);

        eventListHelperClasses = new ArrayList<>();

        listViewEvents.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                EventListHelperClass eventListHelperClass = eventListHelperClasses.get(position);
                showUpdateDeleteDialog(eventListHelperClass.getName(), eventListHelperClass.getDifficulty(), eventListHelperClass.getMinimumAge(), eventListHelperClass.getPace(), eventListHelperClass.getRouteDetails());
                return true;
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        databaseEvents.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                eventListHelperClasses.clear();

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    EventListHelperClass eventListHelperClass = postSnapshot.getValue(EventListHelperClass.class);
                    eventListHelperClasses.add(eventListHelperClass);
                }

                EventList eventAdapter = new EventList(EventActivity.this, eventListHelperClasses);
                listViewEvents.setAdapter(eventAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void showUpdateDeleteDialog(String eventName, String difficultyOnOff, String minimumAgeOnOff, String paceOnOff, String routeDetailsOnOff) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.update_event, null);
        dialogBuilder.setView(dialogView);

        final EditText editTextDifficulty = (EditText) dialogView.findViewById(R.id.editTextDifficulty);
        final EditText editTextMinimumAge = (EditText) dialogView.findViewById(R.id.editTextMinimumAge);
        final EditText editTextPace = (EditText) dialogView.findViewById(R.id.editTextPace);
        final EditText editTextRouteDetails = (EditText) dialogView.findViewById(R.id.editTextRouteDetails);
        final Button buttonUpdate = (Button) dialogView.findViewById(R.id.buttonUpdateEvent);
        final Button buttonDelete = (Button) dialogView.findViewById(R.id.buttonDeleteEvent);

        dialogBuilder.setTitle(eventName);
        final AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();

        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String difficultyOnOff = editTextDifficulty.getText().toString().trim();
                String minimumAgeOnOff = editTextMinimumAge.getText().toString().trim();
                String paceOnOff = editTextPace.getText().toString().trim();
                String routeDetailsOnOff = editTextRouteDetails.getText().toString().trim();
                if (difficultyOnOff.matches("") || minimumAgeOnOff.matches("") || paceOnOff.matches("") || routeDetailsOnOff.matches("")) {
                    Toast.makeText(getApplicationContext(), "Cannot Update with empty fields", Toast.LENGTH_LONG).show();
                }
                else {
                    updateProduct(eventName, difficultyOnOff, minimumAgeOnOff, paceOnOff, routeDetailsOnOff);
                    alertDialog.dismiss();
                }
            }
        });

        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteProduct(eventName);
                alertDialog.dismiss();
            }
        });
    }

    private void updateProduct(String eventNameOnOff, String difficultyOnOff, String minimumAgeOnOff, String paceOnOff, String routeDetailsOnOff) {
        DatabaseReference dR = FirebaseDatabase.getInstance().getReference("events").child(eventNameOnOff);

        EventListHelperClass eventListHelperClass = new EventListHelperClass(eventNameOnOff, difficultyOnOff, minimumAgeOnOff, paceOnOff, routeDetailsOnOff);
        dR.setValue(eventListHelperClass);
        Toast.makeText(getApplicationContext(), "Event Type Updated", Toast.LENGTH_LONG).show();
    }

    private void deleteProduct(String eventNameOnOff) {
        DatabaseReference dR = FirebaseDatabase.getInstance().getReference("events").child(eventNameOnOff);
        dR.removeValue();
        Toast.makeText(getApplicationContext(), "Event type deleted", Toast.LENGTH_LONG).show();
    }
}