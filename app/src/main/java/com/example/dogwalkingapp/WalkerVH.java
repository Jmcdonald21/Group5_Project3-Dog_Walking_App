package com.example.dogwalkingapp;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * WalkerVH creates the textview/itemview information for use with the RVScreen to display user information
 */
public class WalkerVH extends RecyclerView.ViewHolder {

    public TextView txt_name,txt_uID, txt_option, txt_startDate, txt_endDate;

    public WalkerVH(@NonNull View itemView) {
        super(itemView);
        txt_name = itemView.findViewById(R.id.txt_name);
        txt_uID = itemView.findViewById(R.id.txt_uID);
        txt_startDate = itemView.findViewById(R.id.txt_startDate);
        txt_endDate = itemView.findViewById(R.id.txt_endDate);
        txt_option = itemView.findViewById(R.id.txt_option);
    }
}
