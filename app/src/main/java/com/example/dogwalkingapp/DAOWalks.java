package com.example.dogwalkingapp;


import android.util.Log;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.HashMap;

/**
 * DAOWalks class creates the database reference for the firebase database associated with the currently
 * logged in user
 */
public class DAOWalks
{
    public static final String TAG = "DAOWalks";
    private DatabaseReference databaseReference;
    public DAOWalks()
    {
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        databaseReference = db.getReference(Walks.class.getSimpleName());
    }

    //Adds a walks object to the database.
    public Task<Void> add(Walks walks)
    {
        return databaseReference.push().setValue(walks);
    }

//    public Task<Void> update(String key, HashMap<String ,Object> hashMap)
//    {
//        return databaseReference.child(key).updateChildren(hashMap);
//    }
//    public Task<Void> remove(String key)
//    {
//        return databaseReference.child(key).removeValue();
//    }

    //Returns the walks corresponding to the UID associated with the user currently logged in 8 at a time.
    public Query getWalksByUID(String uID){
        Log.i(TAG, "Getting Walk");
        return databaseReference.orderByChild("uID").equalTo(uID).limitToFirst(8);
    }



//    public Query get(String key)
//    {
//        if(key == null)
//        {
//            return databaseReference.orderByKey().limitToFirst(8);
//        }
//        return databaseReference.orderByKey().startAfter(key).limitToFirst(8);
//    }
//
//    public Query get()
//    {
//        return databaseReference;
//    }
}