package com.example.eastcyclingclub;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ParticipantActivityEvents extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    String userUsername, userName, userRole, userPassword, userAge, userPace, userExperienceLevel,selectedSearch;
    ListView listViewEventsP;
    DatabaseReference databaseEvents, databaseEvents1;
    List<ParticipantHelperClassEvent> participantHelperClassEvents;
    List<ClubHelperClassEvent> clubHelperClassEvents;
    Spinner spinner;
    SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.participant_activity_events);



        spinner = findViewById(R.id.searchoptions);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.SearchOptions, R.layout.participant_spinner_search);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        selectedSearch = spinner.getSelectedItem().toString();

        searchView = findViewById(R.id.searchView);


        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {
                userUsername = null;
                userName = null;
                userRole = null;
                userPassword = null;
                userAge = null;
                userPace = null;
                userExperienceLevel = null;
            } else {
                userUsername = extras.getString("username");
                userName = extras.getString("name");
                userRole = extras.getString("role");
                userPassword = extras.getString("password");
                userAge = extras.getString("age");;
                userPace = extras.getString("pace");
                userExperienceLevel = extras.getString("experienceLevel");
            }
        } else {
            userUsername = (String) savedInstanceState.getSerializable("username");
            userName = (String) savedInstanceState.getSerializable("name");
            userRole = (String) savedInstanceState.getSerializable("role");
            userPassword = (String) savedInstanceState.getSerializable("password");
            userAge = (String) savedInstanceState.getSerializable("age");;
            userPace = (String) savedInstanceState.getSerializable("pace");;
            userExperienceLevel = (String) savedInstanceState.getSerializable("experienceLevel");;
        }

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigation);
        bottomNavigationView.setSelectedItemId(R.id.event);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.event){
                return true;
            }if (id == R.id.profile){
                Intent intent = new Intent(getApplicationContext(), ParticipantActivityProfile.class);
                intent.putExtra("username", userUsername);
                intent.putExtra("name", userName);
                intent.putExtra("password", userPassword);
                intent.putExtra("age", userUsername);
                intent.putExtra("pace", userName);
                intent.putExtra("experienceLevel", userRole);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_left);
                finish();
            }
            return false;
        });


       databaseEvents = FirebaseDatabase.getInstance().getReference().child("users");
       listViewEventsP = (ListView) findViewById(R.id.ratingsListView);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchList(newText);
                return true;
            }
        });


    }


    public void searchList(String text){
        if (spinner.getSelectedItem().toString().equals("Search for events by: Event Name")){
            ArrayList <ClubHelperClassEvent> searchList = new ArrayList<>();
            databaseEvents.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    searchList.clear();

                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                        if (postSnapshot.hasChildren()) {
                            for (DataSnapshot postpostSnapshot : postSnapshot.getChildren()) {
                                if (postpostSnapshot.hasChildren()) {
                                    for (DataSnapshot eventSnapshot : postpostSnapshot.getChildren()) {
                                        ClubHelperClassEvent clubHelperClassEvent = eventSnapshot.getValue(ClubHelperClassEvent.class);
                                        if (clubHelperClassEvent.getEventType() != null) {
                                            if (clubHelperClassEvent.getEventName().toLowerCase().contains(text.toLowerCase())) {
                                                searchList.add(clubHelperClassEvent);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                    Log.d("TAG", "onDataChange: searchList size: " + searchList.size());

                    ClubListEvent eventAdapter = new ClubListEvent(ParticipantActivityEvents.this, searchList);
                    listViewEventsP.setAdapter(eventAdapter);

                    Log.d("TAG", "onDataChange: Adapter set");

                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        }
        else if (spinner.getSelectedItem().toString().equals("Search for events by: Event Type")){
            ArrayList <ClubHelperClassEvent> searchList = new ArrayList<>();
            databaseEvents.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    searchList.clear();

                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                        if (postSnapshot.hasChildren()) {
                            for (DataSnapshot postpostSnapshot : postSnapshot.getChildren()) {
                                if (postpostSnapshot.hasChildren()) {
                                    for (DataSnapshot eventSnapshot : postpostSnapshot.getChildren()) {
                                        ClubHelperClassEvent clubHelperClassEvent = eventSnapshot.getValue(ClubHelperClassEvent.class);
                                        if (clubHelperClassEvent.getEventType() != null) {
                                            if (clubHelperClassEvent.getEventType().toLowerCase().contains(text.toLowerCase())) {
                                                searchList.add(clubHelperClassEvent);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }

                    ClubListEvent eventAdapter = new ClubListEvent(ParticipantActivityEvents.this, searchList);
                    listViewEventsP.setAdapter(eventAdapter);


                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
        else if (spinner.getSelectedItem().toString().equals("Search for clubs")) {
            List<GeneralHelperClassUser> generalHelperClassUsers = new ArrayList<>();
            databaseEvents.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    generalHelperClassUsers.clear();

                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) { // getting users
                        GeneralHelperClassUser generalHelperClassUser = postSnapshot.getValue(GeneralHelperClassUser.class);

                        if (generalHelperClassUser.getRole().contains("Cycling Club Owner") && generalHelperClassUser.getName().toLowerCase().contains(text.toLowerCase())) {
                            generalHelperClassUsers.add(generalHelperClassUser);
                        }
                    }

                    ParticipantListClub userAdapter = new ParticipantListClub(ParticipantActivityEvents.this, generalHelperClassUsers);
                    listViewEventsP.setAdapter(userAdapter);

                    listViewEventsP.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            GeneralHelperClassUser generalHelperClassUser = generalHelperClassUsers.get(position);

                            Intent intent = new Intent(ParticipantActivityEvents.this, ParticipantActivityViewClub.class);

                            intent.putExtra("userUsernameKey", generalHelperClassUser.getUsername());

                            startActivity(intent);
                            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_left);
                            finish();
                        }
                    });
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
    }


    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String selectedItem = parent.getItemAtPosition(position).toString();

        // Depending on the selected item, perform actions accordingly
        if (selectedItem.equals("Search for events by: Event Type")) {
            Toast.makeText(this, "Event type selected", Toast.LENGTH_SHORT).show();
        }
        // Perform actions specific to "Participants"
        else if (selectedItem.equals("Search for events by: Event Name")) {
            Toast.makeText(this, "Event name selected", Toast.LENGTH_SHORT).show();
        }
        else if (selectedItem.equals("Search for clubs")) {
            Toast.makeText(this, "Clubs selected", Toast.LENGTH_SHORT).show();
        }
    }


    public void onNothingSelected(AdapterView<?> parent) {
        Toast.makeText(this, "Nothing selected", Toast.LENGTH_SHORT).show();

    }

    @Override
    protected void onStart() {
        super.onStart();
        databaseEvents.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                participantHelperClassEvents.clear();

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    ParticipantHelperClassEvent participantHelperClassEvent = postSnapshot.getValue(ParticipantHelperClassEvent.class);
//                    participantHelperClassEvents.add(participantHelperClassEvent);
                }

//                ParticipantListEvent eventAdapter = new ParticipantListEvent(ParticipantActivityEvents.this, participantHelperClassEvents);
//                listViewEvents.setAdapter(eventAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }
}