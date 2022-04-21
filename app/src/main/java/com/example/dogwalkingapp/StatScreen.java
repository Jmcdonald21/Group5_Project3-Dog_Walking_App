package com.example.dogwalkingapp;

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


public class StatScreen extends AppCompatActivity {
    Date startDate, endDate;
    double distanceTraveled;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stat_screen);
    }

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
}
