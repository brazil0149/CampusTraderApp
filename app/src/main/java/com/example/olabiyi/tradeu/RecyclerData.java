package com.example.olabiyi.tradeu;

import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

/**
 * setters and getters for the info collected and displayed on the cardview
 */
public class RecyclerData {
    String title;
    String description;
    String Location;
    String option;
    String email;
    String name;
    RecyclerView data;
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getDescription() {
        return description;
    }
    public void setLocation(String Location) {
       this.Location = Location;
    }
    public String getLocation() {
        return Location;
    }
    public void setoption(String option) {
        this.option = option;
    }
    public String getoption() {
        return option;
    }
    public void setemail(String email) {
        this.email = email;
    }
    public String getemail() {
        return email;
    }
    public void setname(String name) {
        this.name = name;
    }
    public String getname() {
        return name;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public void setCrossImage(ImageView crossImage){
        setCrossImage(crossImage);
    }
}

