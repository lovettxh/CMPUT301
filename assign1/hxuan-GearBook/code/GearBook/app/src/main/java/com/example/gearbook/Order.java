package com.example.gearbook;

import java.text.DecimalFormat;

// Order object data structure
public class Order {
    private String date;
    private String maker;
    private String description;
    private double price;
    private String comment;
    // Constructor
    public Order(String d, String m, String de, double p, String c){
        this.date = d;
        this.maker = m;
        this.description = de;
        this.price = p;
        this.comment = c;
    }
    // getter function
    public String getDate(){
        return this.date;
    }
    public String getMaker(){
        return this.maker;
    }
    public String getDescription(){
        return this.description;
    }
    public String getPrice(){
        DecimalFormat f = new DecimalFormat("##0.00");
        return f.format(this.price);
    }
    public String getComment(){
        return this.comment;
    }

}
