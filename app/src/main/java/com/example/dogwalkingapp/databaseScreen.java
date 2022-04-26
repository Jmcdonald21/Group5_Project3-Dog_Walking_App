package com.example.dogwalkingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;

public class databaseScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_database_screen);
        final EditText edit_name = findViewById(R.id.edit_name);
        final EditText edit_time = findViewById(R.id.edit_time);
        Button btn = findViewById(R.id.btn_submit);
        Button btn_open = findViewById(R.id.btn_open);
        btn_open.setOnClickListener(v->
        {
            Intent intent =new Intent(databaseScreen.this, RVScreen.class);
            startActivity(intent);
        });
        DAOWalker dao =new DAOWalker();
        Walker walker_edit = (Walker)getIntent().getSerializableExtra("EDIT");
        if(walker_edit !=null)
        {
            btn.setText("UPDATE");
            edit_name.setText(walker_edit.getName());
            edit_time.setText(walker_edit.getTime());
            btn_open.setVisibility(View.GONE);
        }
        else
        {
            btn.setText("SUBMIT");
            btn_open.setVisibility(View.VISIBLE);
        }
        btn.setOnClickListener(v->
        {
            Walker walker = new Walker(edit_name.getText().toString(), edit_time.getText().toString());
            if(walker_edit==null)
            {
                dao.add(walker).addOnSuccessListener(suc ->
                {
                    Toast.makeText(this, "Record is inserted", Toast.LENGTH_SHORT).show();
                }).addOnFailureListener(er ->
                {
                    Toast.makeText(this, "" + er.getMessage(), Toast.LENGTH_SHORT).show();
                });
            }
            else
            {
                HashMap<String, Object> hashMap = new HashMap<>();
                hashMap.put("name", edit_name.getText().toString());
                hashMap.put("position", edit_time.getText().toString());
                dao.update(walker_edit.getKey(), hashMap).addOnSuccessListener(suc ->
                {
                    Toast.makeText(this, "Record is updated", Toast.LENGTH_SHORT).show();
                    finish();
                }).addOnFailureListener(er ->
                {
                    Toast.makeText(this, "" + er.getMessage(), Toast.LENGTH_SHORT).show();
                });
            }
        });


    }
}

