package com.example.schedulegenerator.UserRecycler;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.schedulegenerator.R;

public class UserViewHolder extends RecyclerView.ViewHolder {
    public TextView name;
    public ImageView userImg;
    private ConstraintLayout layout;
    public UserViewHolder(@NonNull View itemView) {
        super(itemView);
        name = itemView.findViewById(R.id.theUserName);
        userImg = itemView.findViewById(R.id.userImgView);
        layout = itemView.findViewById(R.id.user_viewLayout);
    }

    public ConstraintLayout getLayout() {
        return layout;
    }
}
