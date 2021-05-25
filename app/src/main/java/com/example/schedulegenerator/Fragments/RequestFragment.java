package com.example.schedulegenerator.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.schedulegenerator.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

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
        return rootView;
    }

    private void setUpRecycler()
    {


    }
}
