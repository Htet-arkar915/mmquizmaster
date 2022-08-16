package com.team16.um.mmquizmaster.Model;

/**
 * Created by Admin on 11/16/2017.
 */

public class ChSecurationinfo {
    String challengeid,acceptid,mainparent;
    int Securationnum;

    public String getMainparent() {
        return mainparent;
    }

    public void setMainparent(String mainparent) {
        this.mainparent = mainparent;
    }

    public String getChallengeid() {
        return challengeid;
    }

    public void setChallengeid(String challengeid) {
        this.challengeid = challengeid;
    }

    public String getAcceptid() {
        return acceptid;
    }

    public void setAcceptid(String acceptid) {
        this.acceptid = acceptid;
    }

    public int getSecurationnum() {
        return Securationnum;
    }

    public void setSecurationnum(int securationnum) {
        Securationnum = securationnum;
    }
}
