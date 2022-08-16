package com.team16.um.mmquizmaster.ChFightActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.firebase.client.Firebase;
import com.team16.um.mmquizmaster.HomeActivity;
import com.team16.um.mmquizmaster.Model.ChSecurationinfo;
import com.team16.um.mmquizmaster.Model.ChallengeFirebase;
import com.team16.um.mmquizmaster.R;


public class Chresult extends AppCompatActivity {
    TextView yourmark;
    Button okbtn;
    Firebase fb;
    ChSecurationinfo challengeinfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chresult);
        Firebase.setAndroidContext(Chresult.this);
        fb=new Firebase("https://challenge-1d2ec.firebaseio.com/");
        challengeinfo=new ChSecurationinfo();
        ChallengeFirebase myfb=new ChallengeFirebase();

        int mark=Integer.parseInt(String.valueOf(getIntent().getIntExtra("mark",0)));

        SharedPreferences spf=Chresult.this.getSharedPreferences("userdata",MODE_PRIVATE);
        final String myid=spf.getString("userid","");
        final String userid=getIntent().getStringExtra("userid");

        yourmark=(TextView)findViewById(R.id.yourmark);
        okbtn=(Button)findViewById(R.id.chfinishok);
        yourmark.setText("Correct  : "+mark);
        challengeinfo.setChallengeid(myid);
        challengeinfo.setAcceptid(userid);
        challengeinfo.setSecurationnum(1);
        challengeinfo.setMainparent(myid+userid);
        okbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent okintent=new Intent(Chresult.this, HomeActivity.class);
                okintent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(okintent);
                fb.child("Challenge").child(myid+userid).child("userid2").setValue(userid);
                fb.child("Challenge").child(myid+userid).child("chnoti").setValue(1);
                fb.child("ChSecuration").child(myid+userid).setValue(challengeinfo);
                finish();

            }
        });
        changeStatusBarColor();
    }
    private void changeStatusBarColor() {

        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP){
            Window window=getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
            window.setStatusBarColor(getResources().getColor(R.color.color_challenge));
        }
    }
}
