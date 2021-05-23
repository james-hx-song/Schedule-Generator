package com.example.schedulegenerator.Controllers;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.schedulegenerator.Model.Project;
import com.example.schedulegenerator.R;
import com.example.schedulegenerator.Utils.Constants;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class EditProjectActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

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
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.shared2, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        openSpinner.setAdapter(adapter);
        openSpinner.setOnItemSelectedListener(this);

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
        firestore.collection(Constants.PROJECT).document(ID).get().addOnCompleteListener
                (new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful())
                {
                    Project previousProject = task.getResult().toObject(Project.class);
                    int size = Integer.parseInt(capacity.getText().toString());
                    String stats = status.getText().toString();
                    String theName = name.getText().toString();
                    boolean open = openSpinner.getSelectedItem().toString().equals(Constants.PRIVATE)
                            ? false : true;
                    Project newProject = new Project(previousProject.getCollaborators(),
                            open, previousProject.getProjectID(), size,
                            stats, theName);
                    firestore.collection(Constants.PROJECT).document(ID).set(newProject);
                    Toast.makeText(getBaseContext(), "Update successful", Toast.LENGTH_LONG).show();
                }
            }
        });
        Intent goBack = new Intent(this, MainActivity.class);
        startActivity(goBack);
    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String text = adapterView.getItemAtPosition(i).toString();
        Toast.makeText(adapterView.getContext(), text, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}