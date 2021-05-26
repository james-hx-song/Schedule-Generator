package com.example.schedulegenerator.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
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

public class ProfileFragment extends Fragment {

    private TextView name, email, role;
    private ImageView getImg;
    private FirebaseFirestore firestore;
    private FirebaseAuth firebaseAuth;
    private StorageReference storageReference;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_profile, container, false);
        name = rootView.findViewById(R.id.user_name);
        email = rootView.findViewById(R.id.email);
        role = rootView.findViewById(R.id.role);
        getImg = rootView.findViewById(R.id.getProfilePic);


        firestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();

        setUpTextViews();
        return rootView ;
    }

    private void setUpTextViews()
    {
        firestore.collection(Constants.USER).document(firebaseAuth.getUid()).get().addOnCompleteListener(
                new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful())
                        {
                            User currentUser = task.getResult().toObject(User.class);
                            name.setText(currentUser.getName());
                            email.setText(currentUser.getEmail());
                            role.setText(currentUser.getRole());
                        }
                    }
                }
        );

        StorageReference ref = storageReference.child("images/" + firebaseAuth.getUid());
        if (ref != null)
        {
            GlideApp.with(getContext()).load(ref).placeholder(R.drawable.profiledefault).into(getImg);
        }
    }


}
