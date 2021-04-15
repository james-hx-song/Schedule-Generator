package com.example.schedulegenerator.Controllers;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.schedulegenerator.R;

public class MainActivity extends AppCompatActivity {

    private RecyclerView projectRecycler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        projectRecycler = findViewById(R.id.ProjectRecycler);
    }

    public void goToAddProject(View v)
    {
        Intent i = new Intent(this, AddProjectActivity.class);
        startActivity(i);
    }
}