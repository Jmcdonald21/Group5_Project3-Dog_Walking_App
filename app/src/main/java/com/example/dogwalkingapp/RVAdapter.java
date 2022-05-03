package com.example.dogwalkingapp;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

/**
 * RVAdapter class creates the adapter to translate the walks object and insert it into
 * the firebase database
 */
public class RVAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private Context context;
    ArrayList<Walks> list = new ArrayList<>();
    public RVAdapter(Context ctx)
    {
        this.context = ctx;
    }
    public void setItems(ArrayList<Walks> walks)
    {
        list = walks;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_item,parent,false);
        return new WalkerVH(view);
    }
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position)
    {
        Walks w = null;
        this.onBindViewHolder(holder,position,w);
    }

    /**
     * onBindViewHolder sets up the necessary format for inserting data from the application
     * into the firebase database
     * @param holder
     * @param position
     * @param w
     */
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position, Walks w)
    {

        String pattern = "MM/dd/yyyy HH:mm a";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern, Locale.US);

        WalkerVH vh = (WalkerVH) holder;
        Walks walks = w==null? list.get(position):w;
        vh.txt_name.setText(walks.getName());
        vh.txt_uID.setText(walks.getuID());
        vh.txt_startDate.setText(simpleDateFormat.format(walks.getStartDate()));
        vh.txt_endDate.setText(simpleDateFormat.format(walks.getEndDate()));
    }

    @Override
    public int getItemCount()
    {
        return list.size();
    }
}
