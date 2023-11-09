package com.example.eastcyclingclub;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class EventActivity extends AppCompatActivity {

    // Variables for the expanding button
    FloatingActionButton expandFAB, offerFAB, editFAB;

    TextView offerText, editText;

    Boolean areAllFABsVisible;

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

        // 
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

        offerFAB.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), CreateEventActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_left);
            finish();
        });

        editFAB.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), EditEventTypesActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_left);
            finish();
        });
    }

    public void goToCreateEvent(View view) {
        Intent intent = new Intent(getApplicationContext(), CreateEventActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_left);
        finish();
    }
}