package com.example.schedulegenerator.projectRecycler;

import android.content.Context;
import android.content.Intent;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.schedulegenerator.Controllers.ProjectProfileActivity;
import com.example.schedulegenerator.Model.Project;
import com.example.schedulegenerator.R;
import com.example.schedulegenerator.Utils.Constants;

import java.util.ArrayList;
import java.util.List;

public class projectAdapter extends RecyclerView.Adapter<projectViewHolder> implements Filterable {

    private ArrayList<Project> mData;
    private ArrayList<Project> mDataFull;
    private Context context;
    public projectAdapter(ArrayList data, Context context)
    {
        this.mData = data;
        this.context = context;
        mDataFull = new ArrayList<>(mData);
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
        holder.getLayout().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Project projectNow = mData.get(position);
                Intent i = new Intent(context, ProjectProfileActivity.class);
                i.putExtra(Constants.ID, projectNow.getProjectID());
                i.putExtra(Constants.NAME, projectNow.getName());
                i.putExtra(Constants.OPEN, (projectNow.isOpen()));
                i.putExtra(Constants.STATUS, projectNow.getStatus());
                i.putExtra(Constants.SIZE, String.valueOf(projectNow.getCapacity()));
                i.putExtra(Constants.ID, projectNow.getProjectID());
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    @Override
    public Filter getFilter() {
        return mFilter;
    }

    private Filter mFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            ArrayList<Project> filteredList = new ArrayList<>();

            if (charSequence == null || charSequence.length() == 0)
            {
                filteredList.addAll(mDataFull);
            }
            else
            {
                String filterPattern = charSequence.toString().toLowerCase().trim();

                for (Project eachProject : mDataFull)
                {
                    if (eachProject.getName().toLowerCase().contains(filterPattern))
                    {
                        filteredList.add(eachProject);
                    }
                }
            }
            FilterResults result = new FilterResults();
            result.values = filteredList;

            return result;
        }


        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            mData.clear();
            mData.addAll((List) filterResults.values);
            notifyDataSetChanged();
        }
    };
}
