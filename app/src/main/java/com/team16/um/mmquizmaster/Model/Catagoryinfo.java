package com.team16.um.mmquizmaster.Model;

import android.content.Context;

/**
 * Created by Admin on 10/12/2017.
 */

public class Catagoryinfo {
    int img;
    String catagory;
    public Catagoryinfo(){

    }

    public Catagoryinfo(int img,String catagory){
        this.img=img;
        this.catagory=catagory;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public String getCatagory() {
        return catagory;
    }

    public void setCatagory(String catagory) {
        this.catagory = catagory;
    }
}
