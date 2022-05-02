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
    DAOWalks dao;
    Date startTime = new Date();
    Date endTime = new Date();
    ArrayList<Walks> walks = new ArrayList<>();
    ArrayList<BarEntry> walksGraph = new ArrayList<>();
    ArrayList<Walks> useWalks = new ArrayList<>();
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    TextView startDate, endDate;
    EditText eTextStart, eTextEnd;
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
        eTextStart = (EditText) findViewById(R.id.textView);
        eTextEnd = (EditText) findViewById(R.id.textView2);
        eTextStart.setInputType(InputType.TYPE_NULL);
        eTextEnd.setInputType(InputType.TYPE_NULL);
        eTextStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                            }
                        }, year, month, day);
                picker.show();
            }
        });
        eTextEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
        BarChart barChart = findViewById(R.id.barChart);
        dao = new DAOWalks();

        readData(new MyCallBack() {
            @Override
            public void onCallback(ArrayList<Walks> walks) {
                System.out.println("Number Walks: " + walks.size());
                walksGraph.clear();

                for(int i = 0; i < walks.size(); i++){
                    if (walks.get(i).getStartDate().after(startTime) && walks.get(i).getEndDate().before(endTime)) {
                        walksGraph.add(new BarEntry(i + 1, (float) walks.get(i).getDistanceTraveled()));
                    }
                }

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

        public void onHome (View view){
            Intent intent = new Intent(this, HomeScreen.class);
            startActivity(intent);
    }

    public interface MyCallBack {
        void onCallback(ArrayList<Walks> walk);
    }

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