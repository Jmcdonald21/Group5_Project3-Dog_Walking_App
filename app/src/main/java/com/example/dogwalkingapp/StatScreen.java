package com.example.dogwalkingapp;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;

import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.Date;

/**
 * StatScreen class creates the graphical statistical analysis screen of the user's walk tracking information
 * based on user defined sections of time
 */
public class StatScreen extends AppCompatActivity {
    Date startDate, endDate;
    double distanceTraveled;

    /**
     * Overrides the onCreate method of the StatScreen class
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stat_screen);
    }

    /**
     * The loadGraph method creates the barchart that is used to display the information stored about the user's walk tracking
     * based on the user inputted date/times
     * @param view
     */
    public void loadGraph(View view) {
        BarChart barChart = findViewById(R.id.barChart);
        ArrayList<BarEntry> walks = new ArrayList<>();

        DatePicker startDatePicker = (DatePicker)findViewById(R.id.startDateSelector);
        DatePicker endDatePicker = (DatePicker)findViewById(R.id.endDateSelector);

        for (int i = startDatePicker.getDayOfMonth(); i < endDatePicker.getDayOfMonth(); i++) {
                walks.add(new BarEntry(i, .53F));
        }

        BarDataSet barDataSet = new BarDataSet(walks, "Hours Walked");
        barDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        barDataSet.setValueTextColor(Color.BLACK);
        barDataSet.setValueTextSize(16f);

        BarData barData = new BarData(barDataSet);

        barChart.setFitBars(true);
        barChart.setData(barData);
        barChart.getDescription().setText("Weekly Walks");
        barChart.animateY(2000);
    }

    public void onHome(View view) {
        Intent intent = new Intent(this, HomeScreen.class);
        startActivity(intent);
    }
}
