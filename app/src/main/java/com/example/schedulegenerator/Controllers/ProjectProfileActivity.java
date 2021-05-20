package com.example.schedulegenerator.Controllers;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.usage.ConfigurationStats;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.schedulegenerator.Model.Project;
import com.example.schedulegenerator.Model.Request;
import com.example.schedulegenerator.Model.User;
import com.example.schedulegenerator.R;
import com.example.schedulegenerator.RequestRecycler.RequestAdapter;
import com.example.schedulegenerator.Utils.Constants;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class ProjectProfileActivity extends AppCompatActivity {

    private TextView name, status, capacity, open, sendRq;

    private Button request, editInfo;

    private RecyclerView recycler;

    private FirebaseFirestore mStore;
    private FirebaseAuth mAuth;

    private ArrayList<Request> mData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_profile);
        name = findViewById(R.id.Nameans);
        status = findViewById(R.id.StatusAns);
        capacity = findViewById(R.id.CapacityAns);
        open = findViewById(R.id.OpenAns);
        request = findViewById(R.id.requestBtn);
        editInfo = findViewById(R.id.editBtn);
        sendRq = findViewById(R.id.textViewRq);
        recycler = findViewById(R.id.RequestRecycler);

        Intent i = getIntent();
        name.setText(i.getStringExtra(Constants.NAME));
        status.setText(i.getStringExtra(Constants.STATUS));
        capacity.setText(String.valueOf(i.getIntExtra(Constants.SIZE, 0)));
        open.setText(i.getStringExtra(Constants.OPEN));

        mStore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        mData = new ArrayList<Request>();

        setUpTheButtonsAndTexts();
        seeAllRequests();
    }

    private void setUpTheButtonsAndTexts()
    {
        mStore.collection(Constants.USER).document(mAuth.getUid()).get().addOnCompleteListener(
                new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful())
                        {
                            User currUser = task.getResult().toObject(User.class);
                            ArrayList<String> currList = currUser.getProjectList();
                            String currProjectID = getIntent().getStringExtra(Constants.ID);
                            if (!currList.contains(currProjectID))
                            {
                                editInfo.setVisibility(View.GONE);
                                recycler.setVisibility(View.GONE);
                            }
                            else
                            {
                                request.setVisibility(View.GONE);
                                sendRq.setVisibility(View.GONE);
                            }
                        }
                    }
                });

    }

    public void sendRequest(View v)
    {
        Intent i = getIntent();
        Intent request = new Intent(this, RequestProjectActivity.class);
        request.putExtra(Constants.ID, i.getStringExtra(Constants.ID));
        startActivity(request);
        finish();
    }

    public void seeAllRequests()
    {
        mStore.collection(Constants.RQ).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful())
                {
                    for (QueryDocumentSnapshot eachDoc : task.getResult())
                    {
                        Request eachRq = eachDoc.toObject(Request.class);
                        mData.add(eachRq);
                    }
                    RequestAdapter adapter = new RequestAdapter(mData, getBaseContext());
                    recycler.setAdapter(adapter);
                    recycler.setLayoutManager(new LinearLayoutManager(getBaseContext()));
                }
            }
        });
    }

    public void editTheInfo(View v)
    {
        Intent editProject = new Intent(this, EditProjectActivity.class);
        Intent i = getIntent();
        editProject.putExtra(Constants.NAME, i.getStringExtra(Constants.NAME));
        editProject.putExtra(Constants.ID, i.getStringExtra(Constants.ID));
        editProject.putExtra(Constants.SIZE, i.getStringExtra(Constants.SIZE));
        editProject.putExtra(Constants.STATUS, i.getStringExtra(Constants.STATUS));
        startActivity(editProject);
    }


}