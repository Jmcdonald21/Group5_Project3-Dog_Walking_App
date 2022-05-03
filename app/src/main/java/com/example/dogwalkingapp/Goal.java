package com.example.dogwalkingapp;

import java.util.Date;

public class Goal {
    private String name;
    private double distance;
    private Date endDate;
    private String uID;

    public Goal(){}

    public Goal(double distance, Date endDate, String uID, String name){
        this.distance = distance;
        this.endDate = endDate;
        this.uID = uID;
        this.name = name;
    }

    public String getuID() {
        return uID;
    }

    public void setuID(String uID) {
        this.uID = uID;
    }


    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



}
