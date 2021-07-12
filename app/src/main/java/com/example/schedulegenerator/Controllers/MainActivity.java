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
import android.widget.Button;
import android.widget.SearchView;

import com.example.schedulegenerator.Model.Project;
import com.example.schedulegenerator.Model.User;
import com.example.schedulegenerator.R;
import com.example.schedulegenerator.Utils.Constants;
import com.example.schedulegenerator.projectRecycler.projectAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.api.Distribution;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

/**
 * The display of all public projects here and the second activity the user enters
 */
public class MainActivity extends AppCompatActivity {

    private ArrayList<Project> allProjects;
    private RecyclerView projectRecycler;
    private projectAdapter adapter;
    private Button summaryButton;
    private User currentUser;

    private FirebaseAuth mAuth;
    private FirebaseFirestore mStore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        mStore = FirebaseFirestore.getInstance();

        currentUser = new User();

        summaryButton = findViewById(R.id.summaryBtn);

        allProjects = new ArrayList<Project>();

        projectRecycler = findViewById(R.id.ProjectRecycler);

        mStore.collection(Constants.USER).document(mAuth.getUid()).get().addOnCompleteListener
                (new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful())
                {
                    User currUser = task.getResult().toObject(User.class);
                    if (!currUser.getRole().equals(Constants.ADMIN))
                    {
                        summaryButton.setVisibility(View.GONE);
                    }
                    setUser(currUser);
                    populateData();
                }
            }
        });
    }

    /**
     * Get all the data from firebase on public projects and display them on recyclerview.
     */
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
                            if (currProject.isOpen() || currentUser.getRole().equals(Constants.ADMIN))
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


    /**
     * Redirect to userprofileactiviy class
     * @param v of userprofileactivity
     */
    public void user(View v)
    {
        Intent i = new Intent(this, UserProfileActivity.class);
        startActivity(i);
        finish();
    }

    /**
     * Refreshing the adapter for recyclerview
     * @param v of refresh button
     */
    public void refresh(View v)
    {
        projectAdapter myAdapter = new projectAdapter(allProjects, this);
        myAdapter.clear();
        projectRecycler.setAdapter(myAdapter);
        populateData();
    }

    /**
     * Project summary redirection
     * @param v of summary refresh btn
     */
    public void goToSummary(View v)
    {
        Intent i = new Intent(this, ProcessProjectActivity.class);
        startActivity(i);
        finish();
    }

    /**
     * Add project redirection
     * @param v of add project btn
     */
    public void goToAddProject(View v)
    {
        Intent i = new Intent(this, AddProjectActivity.class);
        startActivity(i);
    }

    /**
     * sign out button
     * @param v of signout btn
     */
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

    /**
     * helper method for async issue
     * @param user async user
     */
    private void setUser(User user)
    {
        currentUser = user;
    }

}