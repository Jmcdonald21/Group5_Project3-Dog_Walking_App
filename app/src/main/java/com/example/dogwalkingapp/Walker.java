package com.example.dogwalkingapp;

public class Walker {


    private String Name;
    private String Time;
    private String key;
    public Walker()
    {
    }
    public Walker(String name, String time) {
        Name = name;
        Time = time;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }
    public String getKey()
    {
        return key;
    }

    public void setKey(String key)
    {
        this.key = key;
    }





}
