package com.example.dogwalkingapp;

import java.util.Date;

/**
 * Walks class creates the walk objects that are stored into the user database using firebase and google login
 * services
 */
public class Walks {


    //declare all private variables associated with the Walk object
    private String name;
    private double distanceTraveled;
    private Date startDate;
    private Date endDate;
    private String uID;

    public Walks() {
    }

    //method for creating an instance of the Walks object
    public Walks(double distanceTraveled, Date startDate, Date endDate, String uID, String name) {
        this.distanceTraveled = distanceTraveled;
        this.startDate = startDate;
        this.endDate = endDate;
        this.uID = uID;
        this.name = name;
    }

    //method for getting the user id
    public String getuID() {
        return uID;
    }

    //method for setting the user id
    public void setuID(String uID) {
        this.uID = uID;
    }


    //method for getting the distancetraveled
    public double getDistanceTraveled() {
        return distanceTraveled;
    }

    //method for setting the distance traveled
    public void setDistanceTraveled(double distanceTraveled) {
        this.distanceTraveled = distanceTraveled;
    }

    //method for getting the startdate
    public Date getStartDate() {
        return startDate;
    }

    //method for setting the startdate
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    //method for getting the enddate
    public Date getEndDate() {
        return endDate;
    }

    //method for setting the enddate
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    //method for getting the name of user
    public String getName() {
        return name;
    }

    //method for setting the name of user
    public void setName(String name) {
        this.name = name;
    }


}
