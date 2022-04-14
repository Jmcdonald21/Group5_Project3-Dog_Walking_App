package com.example.dogwalkingapp;

import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;


public class StatScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stat_screen);

        BarChart barChart = findViewById(R.id.barChart);

        ArrayList<BarEntry> walks = new ArrayList<>();
        walks.add(new BarEntry(1, .42F));
        walks.add(new BarEntry(2, 1.2F));
        walks.add(new BarEntry(3, 1.5F));

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
