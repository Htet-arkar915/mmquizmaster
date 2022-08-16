package com.team16.um.mmquizmaster.ChFightActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.team16.um.mmquizmaster.Adapters.ChallengeRecycler;
import com.team16.um.mmquizmaster.Model.AcceptFirebase;
import com.team16.um.mmquizmaster.Model.ChallengeFirebase;
import com.team16.um.mmquizmaster.Model.Question_answerinfo;
import com.team16.um.mmquizmaster.Model.UserfirebaseInfo;
import com.team16.um.mmquizmaster.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;

import de.hdodenhof.circleimageview.CircleImageView;

public class FightEducation extends AppCompatActivity {
    RelativeLayout rv1,rv2,rv3,rv4;
    Button btn;
    Animation animation;
    CircleImageView chimg,acceptimg;
    TextView chname,acceptname,chlevel,acceptlevel;
    Button startbtn;
    Firebase myfirbase;
    String myid;
    Toolbar tb;
    Firebase inputfirebase;
    ArrayList<Question_answerinfo> qalist,giveqalist;
    Question_answerinfo quesandans;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_before_fight);
        //animation
        rv1= (RelativeLayout) findViewById(R.id.relative1);
        rv2= (RelativeLayout) findViewById(R.id.relative2);
        rv3= (RelativeLayout) findViewById(R.id.bg_relatve1);
        rv4= (RelativeLayout) findViewById(R.id.bg_relative2);
        btn= (Button) findViewById(R.id.startbtn);
        animation= AnimationUtils.loadAnimation(FightEducation.this,R.anim.bounce_left);
        rv1.startAnimation(animation);
        animation= AnimationUtils.loadAnimation(FightEducation.this,R.anim.bounce_right);
        rv2.startAnimation(animation);
        animation= AnimationUtils.loadAnimation(FightEducation.this,R.anim.bounce_down);
        rv3.startAnimation(animation);
        animation= AnimationUtils.loadAnimation(FightEducation.this,R.anim.bounce_up);
        rv4.startAnimation(animation);
        animation=AnimationUtils.loadAnimation(FightEducation.this,R.anim.fade_out);
        btn.startAnimation(animation);

        tb=(Toolbar)findViewById(R.id.chtoolbar);
        setSupportActionBar(tb);
        getSupportActionBar().setTitle("Challenge");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        tb.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        qalist=new ArrayList<Question_answerinfo>();
        giveqalist=new ArrayList<Question_answerinfo>();
        final UserfirebaseInfo parm = (UserfirebaseInfo) getIntent().getSerializableExtra("userprofile");
        chimg=(CircleImageView)findViewById(R.id.chimg);
        acceptimg=(CircleImageView)findViewById(R.id.acceptimg);
        chname=(TextView)findViewById(R.id.chname);
        acceptname=(TextView)findViewById(R.id.acceptname);
        chlevel=(TextView)findViewById(R.id.chlevel);
        acceptlevel=(TextView)findViewById(R.id.acceptlevel);
        startbtn=(Button)findViewById(R.id.startbtn);
        //parent=getIntent().getStringExtra("parent");
        Firebase.setAndroidContext(FightEducation.this);
        final SharedPreferences sp = FightEducation.this.getSharedPreferences("userdata", MODE_PRIVATE);
        Glide.with(FightEducation.this).load(sp.getString("profile","")).into(chimg);
        Glide.with(FightEducation.this).load(parm.getUserImgurl()).into(acceptimg);
        chname.setText(sp.getString("username",""));
        acceptname.setText(parm.getUsername());
        acceptlevel.setText("Level :   "+parm.getUserlevel());
        myid=sp.getString("userid","");
        myfirbase=new Firebase("https://userinformation-1825d.firebaseio.com/");

       /* chfirbase=new Firebase("https://challenge-1d2ec.firebaseio.com/");*/
        myfirbase.child("Userdata").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                produceData(dataSnapshot);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        startbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputfirebase=new Firebase("https://challenge-1d2ec.firebaseio.com/");
                ChallengeFirebase mychfirebase=new ChallengeFirebase();
                AcceptFirebase acceptfirebase=new AcceptFirebase();
                //ChallengeFirebase questionFirebase=new ChallengeFirebase();
                mychfirebase.setUsername1(sp.getString("username",""));
                mychfirebase.setUserimg1(sp.getString("profile",""));
                mychfirebase.setLevel1(Integer.parseInt(chlevel.getText().toString()));
                mychfirebase.setUserid1(sp.getString("userid",""));
                //
                acceptfirebase.setChlevel(Integer.parseInt(chlevel.getText().toString()));
                acceptfirebase.setChname(sp.getString("username",""));
                acceptfirebase.setChUserid(sp.getString("userid",""));
                acceptfirebase.setAcceptid(parm.getUserId());
                inputfirebase.child("Accept").child(parm.getUserId()).child(sp.getString("userid",""))
                        .setValue(acceptfirebase);
                mychfirebase.setUsername2(parm.getUsername());
                //mychfirebase.setUserid2(parm.getUserId());
                mychfirebase.setUserimg2(parm.getUserImgurl());
                mychfirebase.setLeveltwo(parm.getUserlevel());
               /* inputfirebase.child("Challenge").child(sp.getString("userid","")+parm.getUserId())
                        .child("2").setValue(acceptfirebase);*/
                try {
                    JSONArray jsonArray=new JSONArray(LoadJSONFromAsset());
                    for (int i=0;i<jsonArray.length();i++){
                        JSONObject jobject=jsonArray.getJSONObject(i);
                        Question_answerinfo qanda=new Question_answerinfo();
                        qanda.setQuestion(jobject.getString("question"));
                        qanda.setAns1(jobject.getString("ans1"));
                        qanda.setAns2(jobject.getString("ans2"));
                        qanda.setAns3(jobject.getString("ans3"));
                        qanda.setAns4(jobject.getString("ans4"));
                        qanda.setCorrect(jobject.getString("correct"));
                        qalist.add(qanda);
                    }

                    Collections.shuffle(qalist);
                    giveqalist.add(qalist.get(0));
                    giveqalist.add(qalist.get(1));
                    giveqalist.add(qalist.get(2));
                    giveqalist.add(qalist.get(3));
                    giveqalist.add(qalist.get(4));
                    ////0
                    mychfirebase.setQuestion1(giveqalist.get(0).getQuestion());
                    mychfirebase.setOnepossible1(giveqalist.get(0).getAns1());
                    mychfirebase.setOnepossible2(giveqalist.get(0).getAns2());
                    mychfirebase.setOnepossible3(giveqalist.get(0).getAns3());
                    mychfirebase.setOnepossible4(giveqalist.get(0).getAns4());
                    mychfirebase.setOnecorrect(giveqalist.get(0).getCorrect());
                    /////1
                    mychfirebase.setQuestion2(giveqalist.get(1).getQuestion());
                    mychfirebase.setTwopossible1(giveqalist.get(1).getAns1());
                    mychfirebase.setTwopossible2(giveqalist.get(1).getAns2());
                    mychfirebase.setTwopossible3(giveqalist.get(1).getAns3());
                    mychfirebase.setTwopossible4(giveqalist.get(1).getAns4());
                    mychfirebase.setTwocorrect(giveqalist.get(1).getCorrect());
                    ///2
                    mychfirebase.setQuestion3(giveqalist.get(2).getQuestion());
                    mychfirebase.setThreepossible1(giveqalist.get(2).getAns1());
                    mychfirebase.setThreepossible2(giveqalist.get(2).getAns2());
                    mychfirebase.setThreepossible3(giveqalist.get(2).getAns3());
                    mychfirebase.setThreepossible4(giveqalist.get(2).getAns4());
                    mychfirebase.setThreecorrect(giveqalist.get(2).getCorrect());
                    //3
                    mychfirebase.setQuestion4(giveqalist.get(3).getQuestion());
                    mychfirebase.setFourpossible1(giveqalist.get(3).getAns1());
                    mychfirebase.setFourpossible2(giveqalist.get(3).getAns2());
                    mychfirebase.setFourpossible3(giveqalist.get(3).getAns3());
                    mychfirebase.setFourpossible4(giveqalist.get(3).getAns4());
                    mychfirebase.setFourcorrect(giveqalist.get(3).getCorrect());
                    //4
                    mychfirebase.setQuestion5(giveqalist.get(4).getQuestion());
                    mychfirebase.setFivepossible1(giveqalist.get(4).getAns1());
                    mychfirebase.setFivepossible2(giveqalist.get(4).getAns2());
                    mychfirebase.setFivepossible3(giveqalist.get(4).getAns3());
                    mychfirebase.setFivepossible4(giveqalist.get(4).getAns4());
                    mychfirebase.setFivecorrect(giveqalist.get(4).getCorrect());
                    mychfirebase.setParent(sp.getString("userid","")+parm.getUserId());
                    inputfirebase.child("Challenge").child(sp.getString("userid","")+parm.getUserId())
                            .setValue(mychfirebase);

                }catch (JSONException e){e.printStackTrace();}
                Intent intent=new Intent(FightEducation.this,Fighting.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent.putExtra("userfbinfo", ChallengeRecycler.clickuser));
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

    private String LoadJSONFromAsset() {
        StringBuilder stringBuilder=new StringBuilder();
        try{
            InputStream inputstream=getAssets().open("education.json");
            BufferedReader br=new BufferedReader(new InputStreamReader(inputstream));

            String str;
            while ((str = br.readLine())!=null){
                stringBuilder.append(str);
            }
            return stringBuilder.toString();

        }catch (IOException e){
            e.printStackTrace();
        }

        return null;
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private void produceData(DataSnapshot dataSnapshot) {
        for (DataSnapshot dsh:dataSnapshot.getChildren()){
            // Toast.makeText(FightEducation.this,dsh.getValue(UserfirebaseInfo.class).getUserId(),Toast.LENGTH_LONG).show();
            if(myid.equals(dsh.getValue(UserfirebaseInfo.class).getUserId())){
                chlevel.setText(dsh.getValue(UserfirebaseInfo.class).getUserlevel()+"");

            }
        }
    }
}
