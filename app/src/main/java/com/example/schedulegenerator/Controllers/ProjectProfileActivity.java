package com.example.schedulegenerator.Controllers;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.schedulegenerator.R;
import com.example.schedulegenerator.Utils.Constants;

public class ProjectProfileActivity extends AppCompatActivity {

    private TextView name, status, capacity, open;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_profile);
        name = findViewById(R.id.Nameans);
        status = findViewById(R.id.StatusAns);
        capacity = findViewById(R.id.CapacityAns);
        open = findViewById(R.id.OpenAns);

        Intent i = getIntent();
        name.setText(i.getStringExtra(Constants.NAME));
        status.setText(i.getStringExtra(Constants.STATUS));
        capacity.setText(String.valueOf(i.getIntExtra(Constants.SIZE, 0)));
        open.setText(i.getStringExtra(Constants.OPEN));
    }
}