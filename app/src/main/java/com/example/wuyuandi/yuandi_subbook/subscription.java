
/*
 *  Copyright (c) $2018.CMPUT301 Wi18,university of Alberta -All Rights Reserved.
 *  You may use,distribute or modify this code under terms and condition of code of student Behavior
 *  at University of Alberta.
 *  You can find a copy of the license in this project. Otherwise, please contace yuandi@ualberta.ca
 * /
 */

package com.example.wuyuandi.yuandi_subbook;

import java.util.Date;

public class subscription {
    private String name;
    private String date;
    private float charge;
    private String comment;

    public subscription(String name, String date, float charge, String comment){
        this.name = name;
        this.date = date;
        this.charge = charge;
        this.comment = comment;
    }
    public String getName(){
        return name;
    }
    public String getDate(){
        return date;
    }
    public float getCharge(){
        return charge;
    }
    public String getComment(){
        return comment;
    }


    public void setName(String name){
        this.name = name;
    }
    public void setDate(String date){
        this.date = date;
    }
    public void setCharge(float charge){
        this.charge = charge;
    }
    public void setComment(String comment){
        this.comment = comment;
    }

    public String toString(){
        return "Subscription:"+ name +"\n" + "Date:"+ date + "     Price:"+charge;
    }
    /*public void changeSub(String name, String date, double charge, String comment) {
        this.name = name;
        this.date = date;
        this.charge = charge;
        this.comment = comment;
    }*/

}
