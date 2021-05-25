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

import com.example.schedulegenerator.Model.Project;
import com.example.schedulegenerator.R;
import com.example.schedulegenerator.Utils.Constants;
import com.example.schedulegenerator.projectRecycler.projectAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class ProjectFragment extends Fragment {
    private RecyclerView userProjectRecycler;
    private FirebaseFirestore firestore;
    private FirebaseAuth firebaseUser;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_project, container, false);
        userProjectRecycler = rootView.findViewById(R.id.userProjectRecycler);
        firestore = FirebaseFirestore.getInstance();
        firebaseUser = FirebaseAuth.getInstance();
        setUpRecyclerView();
        return rootView;
    }

    private void setUpRecyclerView()
    {
        ArrayList<Project> mData = new ArrayList<>();
        firestore.collection(Constants.PROJECT).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful())
                {
                    for (DocumentSnapshot eachDocument : task.getResult())
                    {
                        Project eachProject = eachDocument.toObject(Project.class);
                        if (eachProject.getCollaborators().contains(firebaseUser.getUid()))
                        {
                            mData.add(eachProject);
                        }
                    }
                    projectAdapter adapter = new projectAdapter(mData, getContext());
                    userProjectRecycler.setAdapter(adapter);
                    userProjectRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
                }
            }
        });
    }
}
