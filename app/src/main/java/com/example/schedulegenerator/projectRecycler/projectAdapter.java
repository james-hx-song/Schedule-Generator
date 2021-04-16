package com.example.schedulegenerator.projectRecycler;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.schedulegenerator.Model.Project;
import com.example.schedulegenerator.R;

import java.util.ArrayList;

public class projectAdapter extends RecyclerView.Adapter<projectViewHolder>{

    ArrayList<Project> mData;
    private Context context;
    public projectAdapter(ArrayList data, Context context)
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
    public projectViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View myView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.project_row_layout, parent, false);
        projectViewHolder holder = new projectViewHolder(myView);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull projectViewHolder holder, int position) {
        holder.projectName.setText(mData.get(position).getName());
        holder.projectStatus.setText(mData.get(position).getStatus());

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }
}
