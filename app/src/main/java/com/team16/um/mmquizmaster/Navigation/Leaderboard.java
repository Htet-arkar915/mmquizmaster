package com.team16.um.mmquizmaster.Navigation;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.team16.um.mmquizmaster.Adapters.ChallengeRecycler;
import com.team16.um.mmquizmaster.Adapters.LeaderRecycler;
import com.team16.um.mmquizmaster.Model.UserfirebaseInfo;
import com.team16.um.mmquizmaster.R;

import java.util.ArrayList;

import static java.security.AccessController.getContext;

public class Leaderboard extends AppCompatActivity {
    Toolbar tb;
    RecyclerView recycle;
    String url;
    Firebase fb;
    RecyclerView.Adapter Myrecycle;
    ArrayList<UserfirebaseInfo> userlist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);
        tb=(Toolbar)findViewById(R.id.leaderboard_toolbar);
        recycle=(RecyclerView)findViewById(R.id.leaderboardrecycle);
        setSupportActionBar(tb);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Leaderboard");
        tb.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        recycle.setLayoutManager(new LinearLayoutManager(Leaderboard.this));
        SharedPreferences sp =Leaderboard.this.getSharedPreferences("userdata", MODE_PRIVATE);
        url = sp.getString("userid", "");
        Log.d("url", sp.getString("userid", ""));
        fb = new Firebase("https://userinformation-1825d.firebaseio.com/");
        userlist = new ArrayList<UserfirebaseInfo>();
        fb.child("Userdata").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                userlist.clear();
                produceData(dataSnapshot);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }

    private void produceData(DataSnapshot dataSnapshot) {
        for (DataSnapshot dsh : dataSnapshot.getChildren()) {
            UserfirebaseInfo ufbinfo = new UserfirebaseInfo();
            Log.d("Enter", dsh.getValue(UserfirebaseInfo.class).getUserId());
                ufbinfo.setUsername(dsh.getValue(UserfirebaseInfo.class).getUsername());
                ufbinfo.setUserId(dsh.getValue(UserfirebaseInfo.class).getUserId());
                ufbinfo.setUserImgurl(dsh.getValue(UserfirebaseInfo.class).getUserImgurl());
                ufbinfo.setUserlevel(dsh.getValue(UserfirebaseInfo.class).getUserlevel());
                Log.d("url", dsh.getValue(UserfirebaseInfo.class).getUserImgurl());
                userlist.add(ufbinfo);
        }
        Log.d("size", userlist.size() + "size");
        Myrecycle = new LeaderRecycler(Leaderboard.this, userlist);
        recycle.setAdapter(Myrecycle);
       // progressBar.setVisibility(View.INVISIBLE);
    }
}
