package com.example.dogwalkingapp;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class WalkerVH extends RecyclerView.ViewHolder {

    public TextView txt_name,txt_time, txt_option;

    public WalkerVH(@NonNull View itemView) {
        super(itemView);
        txt_name = itemView.findViewById(R.id.txt_name);
        txt_time = itemView.findViewById(R.id.txt_time);
        txt_option = itemView.findViewById(R.id.txt_option);
    }
}
