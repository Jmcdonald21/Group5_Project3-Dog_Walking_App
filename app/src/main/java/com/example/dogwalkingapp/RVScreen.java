package com.example.dogwalkingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.LinearLayout;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * RVScreen creates the refreshing screen activity that displays all walks currently stored for the user in the
 * firebase database
 */
public class RVScreen extends AppCompatActivity {

    // declare all the variables needed to display the user's currently recorded information
    // as well as the UI necessary to display it
    ArrayList<Walks> walksArrayList = new ArrayList<>();
    SwipeRefreshLayout swipeRefreshLayout;
    RecyclerView recyclerView;
    RVAdapter adapter;
    DAOWalks dao;
    boolean isLoading=false;
    String key =null;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        // Create instances of all necessary variables for both the UI as well as the backend database and
        // user associated account information
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rv_screen);
        swipeRefreshLayout = findViewById(R.id.swipe);
        recyclerView = findViewById(R.id.rv);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        adapter= new RVAdapter(this);
        recyclerView.setAdapter(adapter);
        dao = new DAOWalks();
        loadData();
        // Creates the onScrollListener that allows for the updating of the UI on the screen
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener()
        {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy)
            {
                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                int totalItem = linearLayoutManager.getItemCount();
                int lastVisible = linearLayoutManager.findLastCompletelyVisibleItemPosition();
                if(totalItem< lastVisible+3)
                {
                    if(!isLoading)
                    {
                        isLoading=true;
                        loadData();
                    }
                }
            }
        });
    }

    /**
     * loadData method loads the data from the database associated with the user into an array that is used to
     * display all user data
     */
    private void loadData()
    {

        swipeRefreshLayout.setRefreshing(true);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user != null)
            dao.getWalksByUID(user.getUid()).addListenerForSingleValueEvent(new ValueEventListener()
            {
                @SuppressLint("NotifyDataSetChanged")
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot)
                {
                    walksArrayList.clear();
                    for (DataSnapshot data : snapshot.getChildren())
                    {
                        Walks walks = data.getValue(Walks.class);
//                        walks.setKey(data.getKey());
                        walksArrayList.add(walks);
//                        key = data.getKey();
                    }
                    adapter.setItems(walksArrayList);
                    adapter.notifyDataSetChanged();
                    isLoading =false;
                    swipeRefreshLayout.setRefreshing(false);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error)
                {
                    swipeRefreshLayout.setRefreshing(false);
                }
            });
    }
}