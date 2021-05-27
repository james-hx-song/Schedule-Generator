package com.example.schedulegenerator.Controllers;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.example.schedulegenerator.CollabRecycler.CollabAdapter;
import com.example.schedulegenerator.Model.Project;
import com.example.schedulegenerator.Model.User;
import com.example.schedulegenerator.R;
import com.example.schedulegenerator.Utils.Constants;
import com.example.schedulegenerator.projectRecycler.projectAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class CollabActivity extends AppCompatActivity {


    private RecyclerView collabRecycler;

    private ArrayList<User> mData;

    private FirebaseFirestore fireStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collab);

        collabRecycler = findViewById(R.id.collabRecycler);
         mData = new ArrayList<>();


        fireStore = FirebaseFirestore.getInstance();
        setUpRecycler();
    }

    private void setUpRecycler()
    {
        String projectID = getIntent().getStringExtra(Constants.ID);
        fireStore.collection(Constants.PROJECT).document(projectID).get().addOnCompleteListener(
                new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful())
                        {
                            ArrayList<String> users = task.getResult().toObject(Project.class).getCollaborators();
                            for (String eachID : users)
                            {
                                fireStore.collection(Constants.USER).document(eachID).get()
                                        .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                if (task.isSuccessful())
                                                {
                                                    mData.add(task.getResult().toObject(User.class));
                                                }
                                            }
                                        });
                            }
                            CollabAdapter adapter = new CollabAdapter(mData, getBaseContext(), projectID);
                            collabRecycler.setAdapter(adapter);
                            collabRecycler.setLayoutManager(new LinearLayoutManager(getBaseContext()));
                        }
                    }
                }
        );
    }
    public void refresh(View v)
    {
        String projectID = getIntent().getStringExtra(Constants.ID);
        CollabAdapter myAdapter = new CollabAdapter(mData, this, projectID);
        myAdapter.clear();
        collabRecycler.setAdapter(myAdapter);
        setUpRecycler();
    }
}