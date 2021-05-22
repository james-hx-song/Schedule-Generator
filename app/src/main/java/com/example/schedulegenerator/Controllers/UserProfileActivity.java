package com.example.schedulegenerator.Controllers;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.MenuItem;

import com.example.schedulegenerator.Fragments.ProfileFragment;
import com.example.schedulegenerator.Fragments.ProjectFragment;
import com.example.schedulegenerator.Fragments.RequestFragment;
import com.example.schedulegenerator.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class UserProfileActivity extends AppCompatActivity {

    private BottomNavigationView BottomNav;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        BottomNav = findViewById(R.id.bottom_navigation);
        BottomNav.setOnNavigationItemSelectedListener(navListener);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                new ProfileFragment()).commit();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;
                    switch (item.getItemId())
                    {
                        case R.id.nav_home:
                            selectedFragment = new ProfileFragment();
                            break;
                        case R.id.nav_project:
                            selectedFragment = new ProjectFragment();
                            break;
                        case R.id.nav_request:
                            selectedFragment = new RequestFragment();
                            break;
                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            selectedFragment).commit();
                    return true;
                }
            };
}