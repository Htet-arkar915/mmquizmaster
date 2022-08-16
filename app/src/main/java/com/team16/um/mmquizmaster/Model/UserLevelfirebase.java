package com.team16.um.mmquizmaster.Model;

/**
 * Created by Admin on 10/26/2017.
 */

public class UserLevelfirebase {
    String username,userid,userimg;

    int totalwin,level,ratenum,experience,coin;
    public int getCoin() {return coin;}
    public void setCoin(int coin) {this.coin = coin;}
    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public int getTotalwin() {
        return totalwin;
    }

    public void setTotalwin(int totalwin) {
        this.totalwin = totalwin;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getRatenum() {
        return ratenum;
    }

    public void setRatenum(int ratenum) {
        this.ratenum = ratenum;
    }

    public String getUserimg() {
        return userimg;
    }

    public void setUserimg(String userimg) {
        this.userimg = userimg;
    }
}
