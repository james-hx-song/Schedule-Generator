package com.example.schedulegenerator.Controllers;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.schedulegenerator.Model.Request;
import com.example.schedulegenerator.R;
import com.example.schedulegenerator.Utils.Constants;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class RequestProfileActivity extends AppCompatActivity {

    private TextView nameID, msgID;

    private FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_profile);

        nameID = findViewById(R.id.nameID);
        msgID = findViewById(R.id.msgID);

        firestore = FirebaseFirestore.getInstance();

        changeInformation();

    }
    private void changeInformation()
    {
        Intent i = getIntent();
        String ID = i.getStringExtra(Constants.ID);
        firestore.collection(Constants.RQ).document(ID).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful())
                {
                    Request currentRQ = task.getResult().toObject(Request.class);
                    String name = currentRQ.getRequesterName();
                    String msg = currentRQ.getRequestMsg();
                    nameID.setText(name);
                    msgID.setText(msg);
                }
            }
        });


    }
    public void approve(View v)
    {
        firestore.collection(Constants.RQ).document(getIntent().getStringExtra(Constants.ID)).get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful())
                        {
                            Request currentRQ = task.getResult().toObject(Request.class);
                            currentRQ.setApproved(true);
                            currentRQ.setApproved(true);
                            firestore.collection(Constants.RQ).document(currentRQ.getProjectID()).set(currentRQ);
                        }
                    }
                });
        Toast.makeText(this, "The request is approved", Toast.LENGTH_LONG).show();
    }

    public void disapprove(View v)
    {
        firestore.collection(Constants.RQ).document(getIntent().getStringExtra(Constants.ID)).get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful())
                        {
                            Request currentRQ = task.getResult().toObject(Request.class);
                            currentRQ.setApproved(true);
                            currentRQ.setChecked(true);
                            firestore.collection(Constants.RQ).document(currentRQ.getProjectID()).set(currentRQ);
                        }
                    }
                });
        Toast.makeText(this, "The request is approved", Toast.LENGTH_LONG).show();
    }



}