package com.example.schedulegenerator.Controllers;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.schedulegenerator.R;
import com.example.schedulegenerator.Utils.Constants;
import com.google.firebase.firestore.FirebaseFirestore;

public class EditProjectActivity extends AppCompatActivity {

    private EditText name, status, capacity;

    private FirebaseFirestore firestore;

    private Spinner openSpinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_project);

        name = findViewById(R.id.EditName);
        status = findViewById(R.id.EditStatus);
        capacity = findViewById(R.id.EditCapacity);
        openSpinner = findViewById(R.id.openSpinner1);

        firestore = FirebaseFirestore.getInstance();
        setUpTexts();
    }

    private void setUpTexts()
    {
        Intent i = getIntent();
        name.setText(i.getStringExtra(Constants.NAME));
        status.setText(i.getStringExtra(Constants.STATUS));
        capacity.setText(i.getStringExtra(Constants.SIZE));
    }

    public void update(View v)
    {
        Intent i = getIntent();
        String ID = i.getStringExtra(Constants.ID);
        firestore.collection()

    }
}