package com.team16.um.mmquizmaster.ChFightActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.team16.um.mmquizmaster.HomeActivity;
import com.team16.um.mmquizmaster.Model.CompletenotiFb;
import com.team16.um.mmquizmaster.Model.ResultFirebase;
import com.team16.um.mmquizmaster.Model.UserLevelfirebase;
import com.team16.um.mmquizmaster.R;

import de.hdodenhof.circleimageview.CircleImageView;


public class AcceptResult extends AppCompatActivity {
    RelativeLayout rv1,rv2,rv3;
    Button btn;
    Animation animation;
    String challengeimg,challengeid,myimg,myname,myfbid,challengename;
    int challengemark,mymark,challengelevel,mylevel;
    CircleImageView cimage,aimage;
    TextView resulttv,winloss,headertext,coin,exp;
    RelativeLayout acclayout;
    Firebase removefb,resultfb;
    ResultFirebase resultFirebase;
    CompletenotiFb completenotiFb;
    Firebase userlevel;
    int experience,level,ratenum,total,wincoin;
    UserLevelfirebase userlvfb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accept_result);
        //animation

        rv1= (RelativeLayout) findViewById(R.id.relative1);
        rv2= (RelativeLayout) findViewById(R.id.relative2);
        rv3= (RelativeLayout) findViewById(R.id.relative3);
        btn= (Button) findViewById(R.id.accfinish);
        animation= AnimationUtils.loadAnimation(AcceptResult.this,R.anim.bounce_acceptright);
        rv1.startAnimation(animation);
        animation= AnimationUtils.loadAnimation(AcceptResult.this,R.anim.bounce_acceptleft);
        rv2.startAnimation(animation);
        animation= AnimationUtils.loadAnimation(AcceptResult.this,R.anim.bounce_acceptdown);
        rv3.startAnimation(animation);
        animation= AnimationUtils.loadAnimation(AcceptResult.this,R.anim.bounce_acceptup);
        btn.startAnimation(animation);
        challengeimg=getIntent().getStringExtra("chimg");
        challengeid=getIntent().getStringExtra("chid");
        coin=(TextView)findViewById(R.id.getcoin);
        exp=(TextView)findViewById(R.id.getexperience);
        headertext=(TextView)findViewById(R.id.header);
        challengename=getIntent().getStringExtra("chname");
        acclayout=(RelativeLayout)findViewById(R.id.accresultlayout);
        Firebase.setAndroidContext(AcceptResult.this);

        userlevel=new Firebase("https://userinformation-1825d.firebaseio.com/");
        removefb=new Firebase("https://challenge-1d2ec.firebaseio.com/");
        resultfb=new Firebase("https://challenge-1d2ec.firebaseio.com/");

        challengemark=Integer.parseInt(String.valueOf(getIntent().getIntExtra("chmark",0)));
        mymark=Integer.parseInt(String.valueOf(getIntent().getIntExtra("mark",0)));
        challengelevel=Integer.parseInt(String.valueOf(getIntent().getIntExtra("chlevel",0)));
        mylevel=Integer.parseInt(String.valueOf(getIntent().getIntExtra("mylevel",0)));

        SharedPreferences myspf=AcceptResult.this.getSharedPreferences("userdata",MODE_PRIVATE);
        myimg=myspf.getString("profile","");
        myname=myspf.getString("username","");
        myfbid=myspf.getString("userid","");

        cimage=(CircleImageView)findViewById(R.id.challengerimg);
        aimage=(CircleImageView)findViewById(R.id.accpterimg);
        resulttv=(TextView)findViewById(R.id.markshow);
        completenotiFb=new CompletenotiFb();
        winloss=(TextView)findViewById(R.id.winer);

        Glide.with(AcceptResult.this).load(myimg).into(aimage);
        Glide.with(AcceptResult.this).load(challengeimg).into(cimage);
        resulttv.setText(mymark+"  :  "+challengemark);
        if (mymark<challengemark){

            winloss.setText("You Lose");
            headertext.setText("Sorry !");
            exp.setText("+0 XP");
            coin.setText("+0 Coins");
            putlevel(challengeid,challengeimg,challengename);
        }else if (mymark>challengemark){
            winloss.setText("You Win");
            putlevel(myfbid,myimg,myname);


        }else {
            //acclayout.setBackgroundResource(R.drawable.acceptyoudraw);
            exp.setText("+0 XP");
            coin.setText("+0 Coins");
            winloss.setText("Draw");

        }
        resultFirebase=new ResultFirebase();
        resultFirebase.setAcceptname(myname);
        resultFirebase.setChallengename(challengename);
        resultFirebase.setChallengecount(challengemark);
        resultFirebase.setChallengeid(challengeid);
        resultFirebase.setAcceptid(myfbid);
        resultFirebase.setAcceptcount(mymark);
        resultFirebase.setChallengelevel(challengelevel);
        resultFirebase.setAcceptlevel(mylevel);
        resultFirebase.setChallengeimg(challengeimg);
        resultFirebase.setAcceptimg(myimg);
        ///////
        completenotiFb.setAccName(myname);
        completenotiFb.setChname(challengename);
        completenotiFb.setChid(challengeid);
        completenotiFb.setAcceptfbid(myfbid);
        completenotiFb.setNoti(1);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivity(new Intent(AcceptResult.this, HomeActivity.class));
                Intent okintent=new Intent(AcceptResult.this, HomeActivity.class);
                okintent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                resultfb.child("ChallengeResult").push().setValue(resultFirebase);
                removefb.child("Challenge").child(challengeid+myfbid).removeValue();
                removefb.child("ChSecuration").child(challengeid+myfbid).child("securationnum").setValue(3);
                resultfb.child("Complete").child(challengeid+myfbid).setValue(completenotiFb);


                startActivity(okintent);
                finish();
            }
        });
        changeStatusBarColor();

    }

    private void putlevel(final String userid, final String userimg, final String username) {
        userlevel.child("Userlevel").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Producewin(dataSnapshot,userid,userimg,username);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });


    }

    private void changeStatusBarColor() {
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP){
            Window window=getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
            window.setStatusBarColor(getResources().getColor(R.color.color_challenge));
        }
    }
    //for level
    private void Producewin(DataSnapshot dataSnapshot,String userid,String userimg,String username) {
           userlvfb=new UserLevelfirebase();
       for (DataSnapshot dsh:dataSnapshot.getChildren()){
            if (userid.equals(dsh.getValue(UserLevelfirebase.class).getUserid())){
                level=dsh.getValue(UserLevelfirebase.class).getLevel();
                total=dsh.getValue(UserLevelfirebase.class).getTotalwin();
                experience=dsh.getValue(UserLevelfirebase.class).getExperience();
                ratenum=dsh.getValue(UserLevelfirebase.class).getRatenum();
                wincoin=dsh.getValue(UserLevelfirebase.class).getCoin();
            }

        }
         wincoin+=10;
         total+=1;
         experience+=50;
        if (ratenum==0){
            ratenum=50;
        }
         if (experience>=ratenum){
               level+=1;
               experience=0;
               ratenum*=2;
           }

        userlvfb.setUsername(username);
        userlvfb.setUserimg(userimg);
        userlvfb.setLevel(level);
        userlvfb.setUserid(userid);
        userlvfb.setExperience(experience);
        userlvfb.setRatenum(ratenum);
        userlvfb.setTotalwin(total);
        userlvfb.setCoin(wincoin);
        userlevel.child("Userlevel").child(userid).setValue(userlvfb);
    }
}
