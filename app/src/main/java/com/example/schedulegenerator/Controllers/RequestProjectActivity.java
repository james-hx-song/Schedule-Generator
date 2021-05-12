package com.example.schedulegenerator.Controllers;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.schedulegenerator.Model.Request;
import com.example.schedulegenerator.Model.User;
import com.example.schedulegenerator.R;
import com.example.schedulegenerator.Utils.Constants;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class RequestProjectActivity extends AppCompatActivity {


    private FirebaseAuth mAuth;
    private FirebaseFirestore mStore;

    private EditText reqMsg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_project);

        reqMsg = findViewById(R.id.rqMsg);

        mAuth = FirebaseAuth.getInstance();
        mStore = FirebaseFirestore.getInstance();
    }

    public void sendRequest(View v)
    {
        mStore.collection(Constants.USER).document(mAuth.getUid()).get().addOnCompleteListener(
                new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful())
                        {
                            String userName = task.getResult().toObject(User.class).getName();
                            String projectID = getIntent().getStringExtra(Constants.ID);
                            Request newRq = new Request(mAuth.getUid(), projectID
                                    , reqMsg.getText().toString(), userName);
                            mStore.collection(Constants.RQ).document(projectID).set(newRq);
                        }
                    }
                }
        );

    }


}