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
import com.example.schedulegenerator.Model.User;
import com.example.schedulegenerator.R;
import com.example.schedulegenerator.Utils.Constants;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.UUID;

public class AddProjectActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private EditText projectName, projectCapacity;
    private Spinner openSpinner, projectStatus;

    private FirebaseAuth mAuth;
    private FirebaseFirestore fireStore;

    private ArrayList<String> collabs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_project);

        mAuth = FirebaseAuth.getInstance();
        fireStore = FirebaseFirestore.getInstance();


        projectName = findViewById(R.id.ProjectName);
        projectCapacity = findViewById(R.id.projectSize);


        openSpinner = findViewById(R.id.openSpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.shared, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        openSpinner.setAdapter(adapter);
        openSpinner.setOnItemSelectedListener(this);

        projectStatus = findViewById(R.id.StatusSpinner);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,
                R.array.status, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        projectStatus.setAdapter(adapter2);
        projectStatus.setOnItemSelectedListener(this);

        collabs = new ArrayList<String>();
        collabs.add(mAuth.getUid());

    }

    public void addNewProjects(View v)
    {
        String projectID = UUID.randomUUID().toString();
        String name = projectName.getText().toString();
        String status = projectStatus.getSelectedItem().toString();
        String shared = openSpinner.getSelectedItem().toString();
        boolean open = shared.equals(Constants.PUBLIC) ? true : false;
        int size = Integer.parseInt(projectCapacity.getText().toString());

        Project newProject = new Project(collabs, open, projectID, size, status, name);

        fireStore.collection(Constants.PROJECT).document(newProject.getProjectID()).set(newProject);

        fireStore.collection(Constants.USER).document(mAuth.getUid()).get().addOnCompleteListener(
                new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful())
                        {
                            DocumentSnapshot ds = task.getResult();
                            User newUser = ds.toObject(User.class);
                            newUser.getProjectList().add(projectID);
                            fireStore.collection(Constants.USER).document(mAuth.getUid()).set(newUser);
                            Toast.makeText(getBaseContext(), "project successfully added",
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                }
        );
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
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