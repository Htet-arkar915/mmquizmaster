package com.team16.um.mmquizmaster.Model;

/**
 * Created by Admin on 10/24/2017.
 */

public class ResultFirebase {
    String challengeid,acceptid,challengename,acceptname,challengeimg,acceptimg;
    int challengelevel,acceptlevel,challengecount,acceptcount;
    public String getChallengeimg() {
        return challengeimg;
    }

    public void setChallengeimg(String challengeimg) {
        this.challengeimg = challengeimg;
    }

    public String getAcceptimg() {
        return acceptimg;
    }

    public void setAcceptimg(String acceptimg) {
        this.acceptimg = acceptimg;
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

    public String getChallengename() {
        return challengename;
    }

    public void setChallengename(String challengename) {
        this.challengename = challengename;
    }

    public String getAcceptname() {
        return acceptname;
    }

    public void setAcceptname(String acceptname) {
        this.acceptname = acceptname;
    }

    public int getChallengelevel() {
        return challengelevel;
    }

    public void setChallengelevel(int challengelevel) {
        this.challengelevel = challengelevel;
    }

    public int getAcceptlevel() {
        return acceptlevel;
    }

    public void setAcceptlevel(int acceptlevel) {
        this.acceptlevel = acceptlevel;
    }

    public int getChallengecount() {
        return challengecount;
    }

    public void setChallengecount(int challengecount) {
        this.challengecount = challengecount;
    }

    public int getAcceptcount() {
        return acceptcount;
    }

    public void setAcceptcount(int acceptcount) {
        this.acceptcount = acceptcount;
    }
}
