package com.team16.um.mmquizmaster.Navigation;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.team16.um.mmquizmaster.Adapters.ResultRecycler;
import com.team16.um.mmquizmaster.ChFightActivity.AcceptResult;
import com.team16.um.mmquizmaster.HomeActivity;
import com.team16.um.mmquizmaster.Model.UserLevelfirebase;
import com.team16.um.mmquizmaster.R;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.team16.um.mmquizmaster.Adapters.ResultRecycler.myendresut;


public class Myprofile extends AppCompatActivity implements View.OnClickListener {
    CircleImageView profileimg;
    TextView profilename,profilelevel,xp,mycoin;
   // int mylevel;
    ProgressBar progress;
    Firebase userlevel;
    String userid;
    Toolbar tb;
    CircleImageView onetimeprize,secondprize,thirtprize,fourthprize,fifthprize,sixthprize,seventhprize,eightprize;
    AlertDialog.Builder oneprize;
    AlertDialog oneprizedialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myprofile);

        SharedPreferences sp = Myprofile.this.getSharedPreferences("userdata", MODE_PRIVATE);

        profileimg=(CircleImageView)findViewById(R.id.profileimg);
        progress=(ProgressBar)findViewById(R.id.progressBar);
        profilename=(TextView)findViewById(R.id.profilename);
        profilelevel=(TextView)findViewById(R.id.profilelevel);
        onetimeprize=(CircleImageView)findViewById(R.id.onetimeprize);
        secondprize=(CircleImageView)findViewById(R.id.secondprize);
        thirtprize=(CircleImageView)findViewById(R.id.thirdprize);
        fourthprize=(CircleImageView)findViewById(R.id.fourthprize);
        fifthprize=(CircleImageView)findViewById(R.id.fifthprize);
        sixthprize=(CircleImageView)findViewById(R.id.sixthprize);
        seventhprize=(CircleImageView)findViewById(R.id.seventhprize);
        eightprize=(CircleImageView)findViewById(R.id.eightprize);
        secondprize.setOnClickListener(this);
        thirtprize.setOnClickListener(this);
        fourthprize.setOnClickListener(this);
        onetimeprize.setOnClickListener(this);
        fifthprize.setOnClickListener(this);
        sixthprize.setOnClickListener(this);
        seventhprize.setOnClickListener(this);
        eightprize.setOnClickListener(this);
        mycoin=(TextView)findViewById(R.id.mycoin);
        xp=(TextView)findViewById(R.id.experience);
        tb=(Toolbar)findViewById(R.id.profiletoolbar);
        setSupportActionBar(tb);
        getSupportActionBar().setTitle("My Profile");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        tb.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        Glide.with(Myprofile.this).load(sp.getString("profile","")).into(profileimg);
        profilename.setText(sp.getString("username",""));
        userid=sp.getString("userid","");
        Firebase.setAndroidContext(Myprofile.this);
        userlevel=new Firebase("https://userinformation-1825d.firebaseio.com/");

        userlevel.child("Userlevel").addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                produceMyprofiledata(dataSnapshot);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }
    private void produceMyprofiledata(DataSnapshot dataSnapshot) {

        for (DataSnapshot dsh:dataSnapshot.getChildren()){

            if (userid.equals(dsh.getValue(UserLevelfirebase.class).getUserid())){

                progress.setMax(dsh.getValue(UserLevelfirebase.class).getRatenum());
                progress.setProgress(dsh.getValue(UserLevelfirebase.class).getExperience());
                profilelevel.setText("Level : "+dsh.getValue(UserLevelfirebase.class).getLevel());
                mycoin.setText("  "+dsh.getValue(UserLevelfirebase.class).getCoin()+" coins");
                if (dsh.getValue(UserLevelfirebase.class).getRatenum()!=0) {
                    xp.setText(dsh.getValue(UserLevelfirebase.class).getExperience() + "/" + dsh.getValue(UserLevelfirebase.class).getRatenum() + "XP");
                }else
                    xp.setText(dsh.getValue(UserLevelfirebase.class).getExperience() + "/" +50+ "XP");
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.onetimeprize:
                String str1="Finished correct all answers in one challenge";
                ShowaboutDialogleft(str1,-120,20);
                break;
            case R.id.secondprize:
                String str2="Finished 3 consecutive win in challenge";
                ShowaboutDialogleft(str2,120,20);
                break;
            case R.id.thirdprize:
                String str3="Finished 5 consecutive win in challenge";
                ShowaboutDialogRight(str3,-120,20);
                break;
            case R.id.fourthprize:
                String str4="10 consecutive win in challenge";
                ShowaboutDialogRight(str4,120,20);
                break;
            case R.id.fifthprize:
                String str5="Total win is equal to 100";
                ShowaboutDialogleft(str5,-120,250);
                break;
            case R.id.sixthprize:
                String str6="Get first place in leaderboard";
                ShowaboutDialogleft(str6,120,250);
                break;
            case R.id.seventhprize:
                String str7="Top 10 in leaderboard";
                ShowaboutDialogRight(str7,-120,250);
                break;
            case R.id.eightprize:
                String str8="Win challenge in all catagories";
                ShowaboutDialogRight(str8,120,250);
                break;

        }
    }

    private void ShowaboutDialogRight(String str, int i, int i1) {

        View view= LayoutInflater.from(Myprofile.this).inflate(R.layout.prizetwodialog,null,false);
        oneprize=new AlertDialog.Builder(Myprofile.this);
        TextView aboutfirst=(TextView)view.findViewById(R.id.aboutprize);
        aboutfirst.setText(str);
        oneprize.setView(view);
        oneprize.setCancelable(true);
        oneprizedialog=oneprize.create();
        oneprizedialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        oneprizedialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        Window window=oneprizedialog.getWindow();
        window.setLayout(200,200);
        oneprizedialog.show();
        WindowManager.LayoutParams layoutp=new WindowManager.LayoutParams();
        layoutp.copyFrom(oneprizedialog.getWindow().getAttributes());
        layoutp.width=500;
        layoutp.height=400;
        layoutp.x=i;
        layoutp.y=i1;
        oneprizedialog.getWindow().setAttributes(layoutp);

    }

    private void ShowaboutDialogleft(String str, int i, int i1) {
        View view= LayoutInflater.from(Myprofile.this).inflate(R.layout.prizeonedialog,null,false);
        oneprize=new AlertDialog.Builder(Myprofile.this);
        TextView aboutfirst=(TextView)view.findViewById(R.id.aboutprize);
        aboutfirst.setText(str);
        oneprize.setView(view);
        oneprize.setCancelable(true);
        oneprizedialog=oneprize.create();
        oneprizedialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        oneprizedialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        Window window=oneprizedialog.getWindow();
        window.setLayout(200,200);
        oneprizedialog.show();
        WindowManager.LayoutParams layoutp=new WindowManager.LayoutParams();
        layoutp.copyFrom(oneprizedialog.getWindow().getAttributes());
        layoutp.width=500;
        layoutp.height=400;
        layoutp.x=i;
        layoutp.y=i1;
        oneprizedialog.getWindow().setAttributes(layoutp);
    }
}
