package com.example.schedulegenerator.Controllers;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.schedulegenerator.Fragments.ProfileFragment;
import com.example.schedulegenerator.Fragments.ProjectFragment;
import com.example.schedulegenerator.Fragments.ProjectSearchFragment;
import com.example.schedulegenerator.Fragments.RequestFragment;
import com.example.schedulegenerator.Fragments.UserSearchFragment;
import com.example.schedulegenerator.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ProcessProjectActivity extends AppCompatActivity {

    private BottomNavigationView BottomNav;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_process_project);

        BottomNav = findViewById(R.id.bottomNavigationView);

        BottomNav.setOnNavigationItemSelectedListener(navListener);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;
                    switch (item.getItemId())
                    {
                        case R.id.projectData:
                            selectedFragment = new ProjectSearchFragment();
                            break;
                        case R.id.userData:
                            selectedFragment = new UserSearchFragment();
                            break;
                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.frag_container_2,
                            selectedFragment).commit();
                    return true;
                }
            };


}