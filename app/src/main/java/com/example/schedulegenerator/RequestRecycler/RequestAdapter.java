package com.example.schedulegenerator.RequestRecycler;

import android.content.Context;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.schedulegenerator.Model.Request;

import java.util.ArrayList;

public class RequestAdapter extends RecyclerView.Adapter<RequestViewHolder> {
    private ArrayList<Request> mData;
    private Context mContext;


    public RequestAdapter(ArrayList data, Context context)
    {
        this.mData = data;
        this.mContext = context;

    }
    @NonNull
    @Override
    public RequestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RequestViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }
}
