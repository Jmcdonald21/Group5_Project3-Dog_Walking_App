package com.example.dogwalkingapp;

import java.util.Date;

/**
 * Goal class creates the goal object that is inserted into the user data
 */
public class Goal {
    // create all private variables needed for saving the goal object
    private String name;
    private double distance;
    private Date endDate;
    private String uID;

    public Goal(){}

    // method for creating instance of Goal object
    public Goal(double distance, Date endDate, String uID, String name){
        this.distance = distance;
        this.endDate = endDate;
        this.uID = uID;
        this.name = name;
    }

    // method for getting user id
    public String getuID() {
        return uID;
    }

    // method for setting user id
    public void setuID(String uID) {
        this.uID = uID;
    }


    // method for getting distance
    public double getDistance() {
        return distance;
    }

    // method for setting the distance
    public void setDistance(double distance) {
        this.distance = distance;
    }

    // method for getting the end date of a walk
    public Date getEndDate() {
        return endDate;
    }

    // method for setting the end date of a walk
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    // method for getting the name of a user
    public String getName() {
        return name;
    }

    // method for setting the name of a user
    public void setName(String name) {
        this.name = name;
    }



}
