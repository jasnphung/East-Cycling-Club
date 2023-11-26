package com.example.eastcyclingclub;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class ClubEventsActivity extends AppCompatActivity {

    // Variables for the expanding button
    FloatingActionButton expandFAB, offerFAB;

    TextView offerText, editText;

    Boolean areAllFABsVisible;

    ListView listViewEvents;

    List<AdminHelperClassEventList> adminHelperClassEventLists;

    DatabaseReference databaseEvents;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.club_activity_events);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigation);
        bottomNavigationView.setSelectedItemId(R.id.event);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id==R.id.event){
                return true;
            }if (id==R.id.profile){
                startActivity(new Intent(getApplicationContext(), ClubActivityProfile.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_left);
                finish();
            }
            return false;
        });

        // Floating action button for expanding to the offer event button and edit event button
        expandFAB = findViewById(R.id.expandMenuFAB);
        offerFAB = findViewById(R.id.offerEventFAB);

        offerText = findViewById(R.id.offerEventText);

        offerFAB.setVisibility(View.GONE);
        offerText.setVisibility(View.GONE);

        areAllFABsVisible = false;

        expandFAB.setOnClickListener(view -> {
            if (!areAllFABsVisible) {
                offerFAB.show();
                offerText.setVisibility(View.VISIBLE);

                areAllFABsVisible = true;
            }
            else {
                offerFAB.hide();
                offerText.setVisibility(View.GONE);

                areAllFABsVisible = false;
            }
        });

        // From current layout to creating event layout
        offerFAB.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), ClubActivityAssociateEvent.class);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_left);
            finish();
        });

        //TODO: alter from here to retrieve associated events from the specific CLUB OWNER ACCOUNT as the database reference
        databaseEvents = FirebaseDatabase.getInstance().getReference("events");
        listViewEvents = (ListView) findViewById(R.id.listViewEvents);

        adminHelperClassEventLists = new ArrayList<>();

        listViewEvents.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                AdminHelperClassEventList adminHelperClassEventList = adminHelperClassEventLists.get(position);
                showUpdateDeleteDialog(adminHelperClassEventList.getEventType(), adminHelperClassEventList.getDifficulty() , adminHelperClassEventList.getMinimumAge(), adminHelperClassEventList.getMaximumAge(), adminHelperClassEventList.getPace());
                return true;
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
    }


    //todo needs to be updated for cycling club owner fields
    private void showUpdateDeleteDialog(String eventType, String difficultyLevel, String minimumAge, String maximumAge, String pace) {
        Intent intent = new Intent(ClubEventsActivity.this, AdminActivityUpdateEvent.class);

        intent.putExtra("eventTypeKey", eventType);
        intent.putExtra("difficultyKey", difficultyLevel);
        intent.putExtra("minimumAgeKey", minimumAge);
        intent.putExtra("maximumAgeKey", maximumAge);
        intent.putExtra("paceKey", pace);

        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_left);
        finish();
    }
}