package com.example.schedulegenerator.UserRecycler;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.schedulegenerator.CollabRecycler.CollabViewHolder;
import com.example.schedulegenerator.Model.Project;
import com.example.schedulegenerator.Model.User;
import com.example.schedulegenerator.R;
import com.example.schedulegenerator.Utils.Constants;
import com.example.schedulegenerator.Utils.GlideApp;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class UserAdapter extends RecyclerView.Adapter<UserViewHolder>  {

    private ArrayList<User> mData;
    private Context context;

    public UserAdapter(ArrayList data, Context context)
    {
        this.mData = data;
        this.context = context;
    }

    public void clear()
    {
        int size = mData.size();
        mData.clear();
        notifyItemRangeRemoved(0, size);
    }
    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View myView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.user_row_layout, parent, false);
        UserViewHolder holder = new UserViewHolder(myView);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        StorageReference storageReference = FirebaseStorage.getInstance().getReference();
        StorageReference ref = storageReference.child("images/" + mData.get(position).getImgID());
        holder.name.setText(mData.get(position).getName());
        GlideApp.with(context)
                .load(ref)
                .placeholder(R.drawable.profiledefault)
                .into(holder.userImg);



    }

    @Override
    public int getItemCount() {
        return mData.size();
    }


}
