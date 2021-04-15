package com.example.schedulegenerator.Controllers;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
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

public class AddProjectActivity extends AppCompatActivity {

    private EditText projectName, projectStatus, projectCapacity;

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
        projectStatus = findViewById(R.id.projectStage);
        projectCapacity = findViewById(R.id.projectSize);
        collabs = new ArrayList<String>();
        collabs.add(mAuth.getUid());

    }

    public void addNewProjects(View v)
    {
        String projectID = UUID.randomUUID().toString();
        String name = projectName.getText().toString();
        String status = projectName.getText().toString();
        int size = Integer.parseInt(projectCapacity.getText().toString());

        Project newProject = new Project(collabs, true, projectID, size, status, name);

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
}