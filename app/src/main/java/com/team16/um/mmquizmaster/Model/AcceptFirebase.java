package com.team16.um.mmquizmaster.Model;

/**
 * Created by Admin on 10/20/2017.
 */

public class AcceptFirebase {
    String Chname;
    String ChUserId;
    String acceptid;
    int Chlevel;

    public String getAcceptid() {
        return acceptid;
    }

    public void setAcceptid(String acceptid) {
        this.acceptid = acceptid;
    }


    public int getChlevel() {
        return Chlevel;
    }

    public void setChlevel(int chlevel) {
        Chlevel = chlevel;
    }

    public String getChname() {
        return Chname;
    }

    public void setChname(String chname) {
        Chname = chname;
    }

    public String getChUserid() {
        return ChUserId;
    }

    public void setChUserid(String chUser) {
        ChUserId = chUser;
    }


}
