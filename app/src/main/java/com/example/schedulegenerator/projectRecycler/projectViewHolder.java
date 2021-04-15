package com.example.schedulegenerator.projectRecycler;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.schedulegenerator.R;

public class projectViewHolder extends RecyclerView.ViewHolder {
    public TextView projectName;
    public TextView projectStatus;

    public projectViewHolder(@NonNull View itemView) {
        super(itemView);
        projectName = itemView.findViewById(R.id.projectName);
        projectStatus = itemView.findViewById(R.id.statusText);
    }
}
