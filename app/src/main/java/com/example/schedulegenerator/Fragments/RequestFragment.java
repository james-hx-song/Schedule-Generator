package com.example.schedulegenerator.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.schedulegenerator.Model.Request;
import com.example.schedulegenerator.R;
import com.example.schedulegenerator.RequestRecycler.RequestAdapter;
import com.example.schedulegenerator.Utils.Constants;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.api.Distribution;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class RequestFragment extends Fragment {
    private RecyclerView allRqRecycler;
    private FirebaseAuth user;
    private FirebaseFirestore firestore;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_request, container, false);
        allRqRecycler = rootView.findViewById(R.id.allRequestRecycler);

        user = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        setUpRecycler();
        return rootView;
    }

    private void setUpRecycler()
    {

        firestore.collection(Constants.RQ).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful())
                {
                    ArrayList<Request> mData = new ArrayList<>();
                    for (DocumentSnapshot eachDoc : task.getResult())
                    {
                        Request eachRq = eachDoc.toObject(Request.class);
                        if (eachRq.getRequesterID().equals(user.getUid()))
                        {
                            mData.add(eachRq);
                        }
                    }
                    RequestAdapter adapter = new RequestAdapter(mData, getContext());
                    allRqRecycler.setAdapter(adapter);
                    allRqRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
                }
            }
        });


    }
}
