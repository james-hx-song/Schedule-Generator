package com.example.schedulegenerator.CollabRecycler;

import android.content.Context;
import android.content.Intent;
import android.icu.text.Transliterator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.schedulegenerator.Controllers.ProjectProfileActivity;
import com.example.schedulegenerator.Model.Project;
import com.example.schedulegenerator.Model.User;
import com.example.schedulegenerator.R;
import com.example.schedulegenerator.Utils.Constants;
import com.example.schedulegenerator.Utils.GlideApp;
import com.example.schedulegenerator.projectRecycler.projectViewHolder;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class CollabAdapter extends RecyclerView.Adapter<CollabViewHolder>  {

    private ArrayList<User> mData;
    private Context context;
    private String projectID;

    public CollabAdapter(ArrayList data, Context context, String projectID)
    {
        this.mData = data;
        this.context = context;
        this.projectID = projectID;
    }

    public void clear()
    {
        int size = mData.size();
        mData.clear();
        notifyItemRangeRemoved(0, size);
    }
    @NonNull
    @Override
    public CollabViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View myView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.collab_row_layout, parent, false);
        CollabViewHolder holder = new CollabViewHolder(myView);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull CollabViewHolder holder, int position) {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        StorageReference storageReference = FirebaseStorage.getInstance().getReference();
        StorageReference ref = storageReference.child("images/" + mData.get(position).getImgID());
        holder.name.setText(mData.get(position).getName());
        GlideApp.with(context)
                .load(ref)
                .placeholder(R.drawable.profiledefault)
                .into(holder.collabImg);
        if (mData.get(position).getUid().equals(mAuth.getUid()))
        {
            holder.removeBtn.setVisibility(View.GONE);
        }
        holder.removeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseFirestore fireStore = FirebaseFirestore.getInstance();
                FirebaseAuth mAuth = FirebaseAuth.getInstance();
                fireStore.collection(Constants.PROJECT).document(projectID).get().addOnCompleteListener(
                        new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if(task.isSuccessful())
                                {
                                    Project eachProject = task.getResult().toObject(Project.class);
                                    ArrayList<String> totalList = eachProject.getCollaborators();
                                    totalList.remove(mData.get(position).getUid());
                                    eachProject.setCollaborators(totalList);
                                    fireStore.collection(Constants.PROJECT).document(projectID).set(eachProject);
                                }
                            }
                        }
                );
                fireStore.collection(Constants.USER).document(mAuth.getUid()).get().addOnCompleteListener(
                        new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if(task.isSuccessful())
                                {
                                    User eachUser = task.getResult().toObject(User.class);
                                    ArrayList<String> projectList = eachUser.getProjectList();
                                    projectList.remove(projectID);
                                    eachUser.setProjectList(projectList);
                                    fireStore.collection(Constants.USER).document(mAuth.getUid()).set(eachUser);
                                }
                            }
                        }
                );
                Toast.makeText(context, "successfully removed", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }






}
