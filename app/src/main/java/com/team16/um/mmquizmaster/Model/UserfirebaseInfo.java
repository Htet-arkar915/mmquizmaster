package com.team16.um.mmquizmaster.Model;


import java.io.Serializable;

/**
 * Created by Admin on 10/14/2017.
 */

public class UserfirebaseInfo implements Serializable{
    String username,userImgurl,UserId;
    int userlevel;
    int usercoin;

    public int getUserxp() {
        return userxp;
    }

    public void setUserxp(int userxp) {
        this.userxp = userxp;
    }

    int userxp;

    public int getUsercoin() {
        return usercoin;
    }

    public void setUsercoin(int usercoin) {
        this.usercoin = usercoin;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserImgurl() {
        return userImgurl;
    }

    public void setUserImgurl(String userImgurl) {
        this.userImgurl = userImgurl;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public int getUserlevel() {
        return userlevel;
    }

    public void setUserlevel(int userlevel) {
        this.userlevel = userlevel;
    }
}
