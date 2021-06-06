package com.example.schedulegenerator.RequestRecycler;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.schedulegenerator.Controllers.RequestProfileActivity;
import com.example.schedulegenerator.Model.Request;
import com.example.schedulegenerator.R;
import com.example.schedulegenerator.Utils.Constants;
import com.google.firebase.auth.FirebaseAuth;

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
        View myView = LayoutInflater.from(parent.getContext()).inflate(R.layout.request_row_layout
        , parent, false);
        RequestViewHolder newHolder = new RequestViewHolder(myView);
        return newHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RequestViewHolder holder, int position) {
        holder.requestName.setText(mData.get(position).getRequesterName());
        String status = mData.get(position).isApproved() ? "approved" : "not approved";
        holder.status.setText(status);
        String UID = FirebaseAuth.getInstance().getUid();
        if (!UID.equals(mData.get(position).getRequesterID()) && !mData.get(position).isChecked())
        {
            holder.getLayout().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Request requestNow = mData.get(position);
                    Intent i = new Intent(mContext, RequestProfileActivity.class);
                    i.putExtra(Constants.ID, requestNow.getProjectID());
                    i.putExtra(Constants.USER, requestNow.getRequesterID());
                    mContext.startActivity(i);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }
}
