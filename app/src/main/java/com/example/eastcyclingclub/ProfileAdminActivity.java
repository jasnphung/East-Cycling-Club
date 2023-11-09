package com.example.eastcyclingclub;


import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ProfileAdminActivity extends AppCompatActivity {

    Button logout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_admin);

        logout = findViewById(R.id.logout_button);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ProfileAdminActivity.this, "Logged Out", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(ProfileAdminActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigation);
        bottomNavigationView.setSelectedItemId(R.id.profile);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id==R.id.event){
                startActivity(new Intent(getApplicationContext(), EventActivity.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_left);
                finish();
            }if (id==R.id.profile){
                return true;
            }if (id==R.id.account){
                startActivity(new Intent(getApplicationContext(), AccountActivity.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_left);
                finish();
            }
            return false;
        });
    }

}