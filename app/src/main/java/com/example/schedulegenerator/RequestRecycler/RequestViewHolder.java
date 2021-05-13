package com.example.schedulegenerator.RequestRecycler;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.solver.state.State;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.schedulegenerator.R;

public class RequestViewHolder extends RecyclerView.ViewHolder {
    private final ConstraintLayout layout;
    public TextView requestName;
    public TextView status;
    public RequestViewHolder(@NonNull View itemView) {
        super(itemView);
        requestName = itemView.findViewById(R.id.RqName);
        layout = itemView.findViewById(R.id.RequestRowLayout);
        status = itemView.findViewById(R.id.approveStatus);
    }

    public ConstraintLayout getLayout() {
        return layout;
    }
}
