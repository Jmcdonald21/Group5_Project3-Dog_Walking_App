package com.example.dogwalkingapp;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * StatScreen class creates the graphical statistical analysis screen of the user's walk tracking information
 * based on user defined sections of time
 */
public class StatScreen extends AppCompatActivity {
    // creates new instance of DAOWalks
    DAOWalks dao;
    // Creates new instance of startTime date
    Date startTime = new Date();
    // Creates new instance of endTime date
    Date endTime = new Date();
    // Creates new arraylist for storing walk from database
    ArrayList<Walks> walks = new ArrayList<>();
    // Creates new arraylist for generating graphical analysis
    ArrayList<BarEntry> walksGraph = new ArrayList<>();
    // creates new instance of current firebase user for id
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    // creates new instances of textstart and textend for displaying calendar
    EditText eTextStart, eTextEnd;
    // creates new instance of datepicker for displaying calendar
    DatePickerDialog picker;

    /**
     * Overrides the onCreate method of the StatScreen class
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stat_screen);
        // Create local instances during the creation of the screen for all UI
        eTextStart = (EditText) findViewById(R.id.textView);
        eTextEnd = (EditText) findViewById(R.id.textView2);
        eTextStart.setInputType(InputType.TYPE_NULL);
        eTextEnd.setInputType(InputType.TYPE_NULL);
        eTextStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //local instance of calendar for getting date
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                picker = new DatePickerDialog(StatScreen.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                eTextStart.setText((monthOfYear + 1) + "/" + dayOfMonth + "/" + year);
                                startTime.setDate(dayOfMonth);
                                startTime.setMonth(monthOfYear);
                                startTime.setYear(year - 1900);
                                startTime.setHours(0);
                                startTime.setMinutes(0);
                                startTime.setSeconds(0);
                            }
                        }, year, month, day);
                picker.show();
            }
        });
        eTextEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // local instance of calendar for getting date
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                picker = new DatePickerDialog(StatScreen.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                eTextEnd.setText((monthOfYear + 1) + "/" + dayOfMonth + "/" + year);
                                endTime.setDate(dayOfMonth);
                                endTime.setMonth(monthOfYear);
                                endTime.setYear(year - 1900);
                                endTime.setHours(23);
                                endTime.setMinutes(59);
                                endTime.setSeconds(59);
                            }
                        }, year, month, day);
                picker.show();
            }
        });
    }
    /**
     * The loadGraph method creates the barchart that is used to display the information stored about the user's walk tracking
     * based on the user inputted date/times
     *
     * @param view
     */
    public void loadGraph(View view) {
        // creates new barchart for displaying graph
        BarChart barChart = findViewById(R.id.barChart);
        dao = new DAOWalks();

        readData(new MyCallBack() {
            @Override
            public void onCallback(ArrayList<Walks> walks) {
                System.out.println("Number Walks: " + walks.size());
                walksGraph.clear();
                // iterate through the walks saved for the user and display them if the time is between the selected time
                for(int i = 0; i < walks.size(); i++){
                    if (walks.get(i).getStartDate().after(startTime) && walks.get(i).getEndDate().before(endTime)) {
                        walksGraph.add(new BarEntry(i + 1, (float) walks.get(i).getDistanceTraveled()));
                    }
                }
                // create new bardataset for displaying graph
                BarDataSet barDataSet = new BarDataSet(walksGraph, "Miles Walked");
                barDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
                barDataSet.setValueTextColor(Color.BLACK);
                barDataSet.setValueTextSize(16f);

                BarData barData = new BarData(barDataSet);

                barChart.setFitBars(true);
                barChart.setData(barData);
                barChart.getDescription().setText("Weekly Walks");
                barChart.animateY(2000);
            }
        });
        }

    /**
     * onHome creates the intent upon button press for going to the homescreen
     * @param view
     */
        public void onHome (View view){
            Intent intent = new Intent(this, HomeScreen.class);
            startActivity(intent);
    }

    public interface MyCallBack {
        void onCallback(ArrayList<Walks> walk);
    }

    /**
     * Read the data in from the database for use locally to be displayed
     * @param myCallback
     */
    public void readData(MyCallBack myCallback) {
        if (user != null) {
            dao.getWalksByUID(user.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                @SuppressLint("NotifyDataSetChanged")
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    walks.clear();
                    for (DataSnapshot data : snapshot.getChildren()) {
                        Walks userWalks = data.getValue(Walks.class);
                        walks.add(userWalks);
                    }
                    myCallback.onCallback(walks);
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }
}