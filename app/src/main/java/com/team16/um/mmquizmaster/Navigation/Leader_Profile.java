package com.team16.um.mmquizmaster.Navigation;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.team16.um.mmquizmaster.Adapters.AcceptRecycler;
import com.team16.um.mmquizmaster.Adapters.LeaderRecycler;
import com.team16.um.mmquizmaster.ChFightActivity.Accept_profile;
import com.team16.um.mmquizmaster.Model.UserLevelfirebase;
import com.team16.um.mmquizmaster.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class Leader_Profile extends AppCompatActivity {
    CircleImageView profileimg;
    TextView profilename,profilelevel,xp;
    TextView userprofilecoin;
    int mylevel;
    ProgressBar progress;
    Firebase userlevel;
    String userid;
    Toolbar tb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user__profile);
        SharedPreferences sp = Leader_Profile.this.getSharedPreferences("userdata", MODE_PRIVATE);
        profileimg=(CircleImageView)findViewById(R.id.profileimg);
        progress=(ProgressBar)findViewById(R.id.progressBar);
        profilename=(TextView)findViewById(R.id.profilename);
        profilelevel=(TextView)findViewById(R.id.profilelevel);
        //backlayout=(ImageView)findViewById(R.id.backarrow);
        userprofilecoin=(TextView)findViewById(R.id.onlineusercoin);
//        userprofilecoin.setText("   "+ChallengeRecycler.circleimgclickuser.getUsercoin()+"  coins");
        xp=(TextView)findViewById(R.id.experience);
        tb=(Toolbar)findViewById(R.id.profiletoolbar);
        Glide.with(Leader_Profile.this).load(LeaderRecycler.leaderclickuser.getUserImgurl()).into(profileimg);
        profilename.setText(LeaderRecycler.leaderclickuser.getUsername());
        setSupportActionBar(tb);
        getSupportActionBar().setTitle("Profile");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // Toast.makeText(this, AcceptRecycler.acceptprofileid+"", Toast.LENGTH_SHORT).show();
        tb.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        Firebase.setAndroidContext(Leader_Profile.this);
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
            if (LeaderRecycler.leaderclickuser.getUserId().equals(dsh.getValue(UserLevelfirebase.class).getUserid())){
                progress.setMax(dsh.getValue(UserLevelfirebase.class).getRatenum());
                progress.setProgress(dsh.getValue(UserLevelfirebase.class).getExperience());
                profilelevel.setText("Level : "+dsh.getValue(UserLevelfirebase.class).getLevel());
                userprofilecoin.setText("  "+dsh.getValue(UserLevelfirebase.class).getCoin()+"coins");
                if (dsh.getValue(UserLevelfirebase.class).getRatenum()!=0) {
                    xp.setText(dsh.getValue(UserLevelfirebase.class).getExperience() + "/" + dsh.getValue(UserLevelfirebase.class).getRatenum() + "XP");
                }else
                    xp.setText(dsh.getValue(UserLevelfirebase.class).getExperience() + "/" +50+ "XP");
            }
        }
    }
}
