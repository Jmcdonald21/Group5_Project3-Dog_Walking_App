package com.example.dogwalkingapp;

import java.util.Date;

public class Walks {


    private String name;
    private double distanceTraveled;
    private Date startDate;
    private Date endDate;
    private String uID;

    public Walks() {
    }

    public Walks(double distanceTraveled, Date startDate, Date endDate, String uID, String name) {
        this.distanceTraveled = distanceTraveled;
        this.startDate = startDate;
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


    public double getDistanceTraveled() {
        return distanceTraveled;
    }

    public void setDistanceTraveled(double distanceTraveled) {
        this.distanceTraveled = distanceTraveled;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
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
