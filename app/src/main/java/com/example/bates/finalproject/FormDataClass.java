package com.example.bates.finalproject;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class FormDataClass {
    public String username;
    public String date;
    public double sg;
    public int degrees;
    public int batch;

    public FormDataClass(){}

    public FormDataClass(String username, String date, double sg, int degrees, int batch){
        this.username = username;
        this.date = date;
        this.sg = sg;
        this.degrees = degrees;
        this.batch = batch;
    }
}
