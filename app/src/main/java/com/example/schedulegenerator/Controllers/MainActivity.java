package com.example.schedulegenerator.Controllers;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.SearchView;

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
    private projectAdapter adapter;

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
                            String role = getIntent().getStringExtra(Constants.ROLE);
                            if ((currProject.isOpen() || role.equals(Constants.ADMIN)))
                            {
                                allProjects.add(currProject);
                            }

                        }
                    }
                    adapter = new projectAdapter(allProjects, getBaseContext());
                    projectRecycler.setAdapter(adapter);
                    projectRecycler.setLayoutManager(new LinearLayoutManager(getBaseContext()));
                }
            });
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

    }

    public void user(View v)
    {
        Intent i = new Intent(this, UserProfileActivity.class);
        startActivity(i);
        finish();
    }

    public void refresh(View v)
    {
        projectAdapter myAdapter = new projectAdapter(allProjects, this);
        myAdapter.clear();
        projectRecycler.setAdapter(myAdapter);
        populateData();
    }

    public void goToSummary(View v)
    {
        Intent i = new Intent(this, ProcessProjectActivity.class);
        startActivity(i);
        finish();
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_menu, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();

        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                adapter.getFilter().filter(s);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

}