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

//        vh.txt_option.setOnClickListener(v->
//        {
//            PopupMenu popupMenu =new PopupMenu(context,vh.txt_option);
//            popupMenu.inflate(R.menu.option_menu);
//            popupMenu.setOnMenuItemClickListener(item->
//            {
//                switch (item.getItemId())
//                {
//                    case R.id.menu_edit:
//                        Intent intent=new Intent(context,databaseScreen.class);
//                        intent.putExtra("EDIT", (Parcelable) walks);
//                        context.startActivity(intent);
//                        break;
//                    case R.id.menu_remove:
//                        DAOWalker dao=new DAOWalker();
//                        dao.remove(walks.getKey()).addOnSuccessListener(suc->
//                        {
//                            Toast.makeText(context, "Record is removed", Toast.LENGTH_SHORT).show();
//                            notifyItemRemoved(position);
//                            list.remove(walks);
//                        }).addOnFailureListener(er->
//                        {
//                            Toast.makeText(context, ""+er.getMessage(), Toast.LENGTH_SHORT).show();
//                        });
//
//                        break;
//                }
//                return false;
//            });
//            popupMenu.show();
//        });
    }

    @Override
    public int getItemCount()
    {
        return list.size();
    }
}
