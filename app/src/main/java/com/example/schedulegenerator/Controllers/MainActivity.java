package com.example.schedulegenerator.Controllers;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.schedulegenerator.Model.Project;
import com.example.schedulegenerator.R;
import com.example.schedulegenerator.Utils.Constants;
import com.example.schedulegenerator.projectRecycler.projectAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.api.Distribution;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Project> allProjects;
    private RecyclerView projectRecycler;

    private FirebaseAuth mAuth;
    private FirebaseFirestore mStore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        mStore = FirebaseFirestore.getInstance();

        allProjects = new ArrayList<Project>();

        projectRecycler = findViewById(R.id.ProjectRecycler);

        populateData();
    }

    private void populateData()
    {
        try
        {
            mStore.collection(Constants.PROJECT).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful())
                    {
                        for (QueryDocumentSnapshot document : task.getResult())
                        {
                            Project currProject = document.toObject(Project.class);
                            allProjects.add(currProject);
                        }
                    }
                }
            });
            projectAdapter mAdapter = new projectAdapter(allProjects, getBaseContext());
            projectRecycler.setAdapter(mAdapter);
            projectRecycler.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

    }

    public void refresh(View v)
    {
        projectAdapter myAdapter = new projectAdapter(allProjects, this);
        myAdapter.clear();
        projectRecycler.setAdapter(myAdapter);
        populateData();
    }

    public void goToAddProject(View v)
    {
        Intent i = new Intent(this, AddProjectActivity.class);
        startActivity(i);
    }

    public void signOut(View v)
    {
        mAuth.signOut();
        Intent goBacktoAuth = new Intent(this, AuthActivity.class);
        startActivity(goBacktoAuth);
    }
}