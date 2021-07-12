package com.example.schedulegenerator.CollabRecycler;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.schedulegenerator.R;

/**
 * The viewholder (cardview) for each cell on the recyclerview
 */
public class CollabViewHolder extends RecyclerView.ViewHolder {
    public TextView name;
    public ImageView collabImg;
    public Button removeBtn;
    private ConstraintLayout layout;
    public CollabViewHolder(@NonNull View itemView) {
        super(itemView);
        name = itemView.findViewById(R.id.CollabName);
        collabImg = itemView.findViewById(R.id.collabImgView);
        layout = itemView.findViewById(R.id.collab_viewLayout);
        removeBtn = itemView.findViewById(R.id.removeBtn);
    }

    public ConstraintLayout getLayout() {
        return layout;
    }
}
