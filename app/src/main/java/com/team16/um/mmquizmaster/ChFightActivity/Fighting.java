package com.team16.um.mmquizmaster.ChFightActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Transaction;
import com.firebase.client.ValueEventListener;
import com.kyawhtut.FontUtil;
import com.team16.um.mmquizmaster.Activity.Sakarpone;
import com.team16.um.mmquizmaster.HomeActivity;
import com.team16.um.mmquizmaster.Model.ChallengeFirebase;
import com.team16.um.mmquizmaster.Model.Question_answerinfo;
import com.team16.um.mmquizmaster.Model.UserfirebaseInfo;
import com.team16.um.mmquizmaster.R;
import com.todddavies.components.progressbar.ProgressWheel;

import java.util.ArrayList;
import java.util.logging.Handler;


public class Fighting extends AppCompatActivity implements View.OnClickListener {
    TextView qforch,qnumber;
    Button chbtnans1,chbtnans2,chbtnans3,chbtnans4;
    Firebase fightingfb;
    String mychid;
    Intent intent;
    ProgressWheel progressWheel;
    private int progressStatus =0;
    RelativeLayout progresslay;
    ProgressDialog progressDialog;
    int i=0,count=0;
    int mark=0;
    String answer,accuserid;
    CountDownTimer cdt;
    ChallengeFirebase chfb;
    SharedPreferences sp;
    TextView tv;
    UserfirebaseInfo getuser;
    ArrayList<Question_answerinfo> questionlist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.chfight_activity);
        progressDialog=new ProgressDialog(Fighting.this);
        progressDialog.setMessage("Loading.....");
        progressDialog.setCancelable(false);
        chfb=new ChallengeFirebase();
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();
        qnumber=(TextView)findViewById(R.id.quesno);
        qforch=(TextView)findViewById(R.id.chtvqestion);
        chbtnans1=(Button)findViewById(R.id.chbtnans1);
        chbtnans2=(Button)findViewById(R.id.chbtnans2);
        chbtnans3=(Button)findViewById(R.id.chbtnans3);
        chbtnans4=(Button)findViewById(R.id.chbtnans4);
        chbtnans1.setOnClickListener(this);
        chbtnans2.setOnClickListener(this);
        chbtnans3.setOnClickListener(this);
        chbtnans4.setOnClickListener(this);

        getuser= (UserfirebaseInfo) getIntent().getSerializableExtra("userfbinfo");
        accuserid=getuser.getUserId();
        Firebase.setAndroidContext(Fighting.this);

        sp=Fighting.this.getSharedPreferences("userdata",MODE_PRIVATE);
        fightingfb=new Firebase("https://challenge-1d2ec.firebaseio.com/");
        fightingfb.child("Challenge").addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                produceFbdata(dataSnapshot,getuser);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        AttributeSet attrs=null;
        final ProgressWheel pw = new ProgressWheel(Fighting.this,attrs);
        tv = (TextView) findViewById(R.id.tv);
        progressWheel = (ProgressWheel) findViewById(R.id.pw_spinner);
        progresslay = (RelativeLayout) findViewById(R.id.progreelay);
        cdt = new CountDownTimer(31000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                long seconds=millisUntilFinished/1000;
                tv.setText(millisUntilFinished/1000+"");
                progressWheel.setProgress((int)(progressStatus+=12));
                tv.setText(String.format("%02d", seconds % 60)
                );

            }
            @Override
            public void onFinish() {
                tv.setText("finish");
                Log.i("COUNT ", "FINISH");
                if(count<5){
                    Log.i("FINISH 1", "WORK");
                    intent=new Intent(Fighting.this,Chresult.class).putExtra("mark",mark).putExtra("userid",accuserid);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                    cancel();
                    cdt.cancel();
                }
            }
        }.start();
    }

    private void produceFbdata(DataSnapshot dataSnapshot,UserfirebaseInfo user) {
        SharedPreferences spf=Fighting.this.getSharedPreferences("userdata",MODE_PRIVATE);
        Question_answerinfo qandans1=new Question_answerinfo();
        Question_answerinfo qandans2=new Question_answerinfo();
        Question_answerinfo qandans3=new Question_answerinfo();
        Question_answerinfo qandans4=new Question_answerinfo();
        Question_answerinfo qandans5=new Question_answerinfo();

        for(DataSnapshot dsh:dataSnapshot.getChildren()){

            if((spf.getString("userid","")+user.getUserId()).equals(dsh.getValue(ChallengeFirebase.class).getParent())){

                qandans1.setQuestion(dsh.getValue(ChallengeFirebase.class).getQuestion1());
                qandans1.setCorrect(dsh.getValue(ChallengeFirebase.class).getOnecorrect());
                qandans1.setAns1(dsh.getValue(ChallengeFirebase.class).getOnepossible1());
                qandans1.setAns2(dsh.getValue(ChallengeFirebase.class).getOnepossible2());
                qandans1.setAns3(dsh.getValue(ChallengeFirebase.class).getOnepossible3());
                qandans1.setAns4(dsh.getValue(ChallengeFirebase.class).getOnepossible4());
                /////
                qandans2.setQuestion(dsh.getValue(ChallengeFirebase.class).getQuestion2());
                qandans2.setCorrect(dsh.getValue(ChallengeFirebase.class).getTwocorrect());
                qandans2.setAns1(dsh.getValue(ChallengeFirebase.class).getTwopossible1());
                qandans2.setAns2(dsh.getValue(ChallengeFirebase.class).getTwopossible2());
                qandans2.setAns3(dsh.getValue(ChallengeFirebase.class).getTwopossible3());
                qandans2.setAns4(dsh.getValue(ChallengeFirebase.class).getTwopossible4());
                /////
                qandans3.setQuestion(dsh.getValue(ChallengeFirebase.class).getQuestion3());
                qandans3.setCorrect(dsh.getValue(ChallengeFirebase.class).getThreecorrect());
                qandans3.setAns1(dsh.getValue(ChallengeFirebase.class).getThreepossible1());
                qandans3.setAns2(dsh.getValue(ChallengeFirebase.class).getThreepossible2());
                qandans3.setAns3(dsh.getValue(ChallengeFirebase.class).getThreepossible3());
                qandans3.setAns4(dsh.getValue(ChallengeFirebase.class).getThreepossible4());
                /////
                qandans4.setQuestion(dsh.getValue(ChallengeFirebase.class).getQuestion4());
                qandans4.setCorrect(dsh.getValue(ChallengeFirebase.class).getFourcorrect());
                qandans4.setAns1(dsh.getValue(ChallengeFirebase.class).getFourpossible1());
                qandans4.setAns2(dsh.getValue(ChallengeFirebase.class).getFourpossible2());
                qandans4.setAns3(dsh.getValue(ChallengeFirebase.class).getFourpossible3());
                qandans4.setAns4(dsh.getValue(ChallengeFirebase.class).getFourpossible4());
                /////
                qandans5.setQuestion(dsh.getValue(ChallengeFirebase.class).getQuestion5());
                qandans5.setCorrect(dsh.getValue(ChallengeFirebase.class).getFivecorrect());
                qandans5.setAns1(dsh.getValue(ChallengeFirebase.class).getFivepossible1());
                qandans5.setAns2(dsh.getValue(ChallengeFirebase.class).getFivepossible2());
                qandans5.setAns3(dsh.getValue(ChallengeFirebase.class).getFivepossible3());
                qandans5.setAns4(dsh.getValue(ChallengeFirebase.class).getFivepossible4());

            }
            // Toast.makeText(Fighting.this,qandans5.getQuestion(),Toast.LENGTH_LONG).show();
            questionlist =new ArrayList<>();
            questionlist.add(qandans1);
            questionlist.add(qandans2);
            questionlist.add(qandans3);
            questionlist.add(qandans4);
            questionlist.add(qandans5);

        }
        FontUtil fontUtil=new FontUtil();
        if (HomeActivity.CheckLanguage(Fighting.this)) {
            qforch.setText(fontUtil.zawgyi2unicode(questionlist.get(i).getQuestion()));
            chbtnans1.setText(fontUtil.zawgyi2unicode(questionlist.get(i).getAns1()));
            chbtnans2.setText(fontUtil.zawgyi2unicode(questionlist.get(i).getAns2()));
            chbtnans3.setText(fontUtil.zawgyi2unicode(questionlist.get(i).getAns3()));
            chbtnans4.setText(fontUtil.zawgyi2unicode(questionlist.get(i).getAns4()));
        }else{
            qforch.setText(questionlist.get(i).getQuestion());
            chbtnans1.setText(questionlist.get(i).getAns1());
            chbtnans2.setText(questionlist.get(i).getAns2());
            chbtnans3.setText(questionlist.get(i).getAns3());
            chbtnans4.setText(questionlist.get(i).getAns4());
        }
        answer=questionlist.get(i).getCorrect();
        progressDialog.dismiss();

        changeStatusBarColor();

    }
    private void changeStatusBarColor() {
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP){
            Window window=getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
            window.setStatusBarColor(getResources().getColor(R.color.color_challenge));
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.chbtnans1:
                count+=1;
                if(answer.equals("ans1")){
                    mark+=1;
                    // Toast.makeText(Fighting.this,"True",Toast.LENGTH_LONG).show();
                }else {
                    // Toast.makeText(Fighting.this,"False",Toast.LENGTH_LONG).show();
                }
                if(count<5){
                    i+=1;
                    qnumber.setText((1+i)+"/5");
                    FontUtil fontUtil=new FontUtil();
                    if (HomeActivity.CheckLanguage(Fighting.this)) {
                        qforch.setText(fontUtil.zawgyi2unicode(questionlist.get(i).getQuestion()));
                        chbtnans1.setText(fontUtil.zawgyi2unicode(questionlist.get(i).getAns1()));
                        chbtnans2.setText(fontUtil.zawgyi2unicode(questionlist.get(i).getAns2()));
                        chbtnans3.setText(fontUtil.zawgyi2unicode(questionlist.get(i).getAns3()));
                        chbtnans4.setText(fontUtil.zawgyi2unicode(questionlist.get(i).getAns4()));
                    }else{
                        qforch.setText(questionlist.get(i).getQuestion());
                        chbtnans1.setText(questionlist.get(i).getAns1());
                        chbtnans2.setText(questionlist.get(i).getAns2());
                        chbtnans3.setText(questionlist.get(i).getAns3());
                        chbtnans4.setText(questionlist.get(i).getAns4());
                    }
                    answer=questionlist.get(i).getCorrect();
                }else {
                    qforch.setText("Finish");
                    Enable();
                    cdt.cancel();
                    fightingfb.child("Challenge").child(sp.getString("userid","")+getuser.getUserId()).child("countone").setValue(mark);
                    //cdt.cancel();
                    Log.i("FINISH 2", "WORK");
                    Intent i=new Intent(Fighting.this,Chresult.class).putExtra("mark",mark).putExtra("userid",accuserid);
                    i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                    finish();

                    break;
                }

                break;
            case R.id.chbtnans2:
                count+=1;
                if(answer.equals("ans2")){
                    mark+=1;
                    // Toast.makeText(Fighting.this,"True",Toast.LENGTH_LONG).show();
                }else {
                    // Toast.makeText(Fighting.this,"False",Toast.LENGTH_LONG).show();
                }
                if(count<5){
                    i+=1;
                    qnumber.setText((1+i)+"/5");
                    FontUtil fontUtil=new FontUtil();
                    if (HomeActivity.CheckLanguage(Fighting.this)) {
                        qforch.setText(fontUtil.zawgyi2unicode(questionlist.get(i).getQuestion()));
                        chbtnans1.setText(fontUtil.zawgyi2unicode(questionlist.get(i).getAns1()));
                        chbtnans2.setText(fontUtil.zawgyi2unicode(questionlist.get(i).getAns2()));
                        chbtnans3.setText(fontUtil.zawgyi2unicode(questionlist.get(i).getAns3()));
                        chbtnans4.setText(fontUtil.zawgyi2unicode(questionlist.get(i).getAns4()));
                    }else{
                        qforch.setText(questionlist.get(i).getQuestion());
                        chbtnans1.setText(questionlist.get(i).getAns1());
                        chbtnans2.setText(questionlist.get(i).getAns2());
                        chbtnans3.setText(questionlist.get(i).getAns3());
                        chbtnans4.setText(questionlist.get(i).getAns4());
                    }
                    answer=questionlist.get(i).getCorrect();
                }else {
                    qforch.setText("Finish");
                    Enable();
                    cdt.cancel();
                    fightingfb.child("Challenge").child(sp.getString("userid","")+getuser.getUserId()).child("countone").setValue(mark);
                    // cdt.cancel();
                    Log.i("FINISH 3", "WORK");
                    Intent i=new Intent(Fighting.this,Chresult.class).putExtra("mark",mark).putExtra("userid",accuserid);
                    i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                    finish();

                    break;
                }
                break;
            case R.id.chbtnans3:
                count+=1;
                if(answer.equals("ans3")){
                    mark+=1;
                    // Toast.makeText(Fighting.this,"True",Toast.LENGTH_LONG).show();
                }else {
                    // Toast.makeText(Fighting.this,"False",Toast.LENGTH_LONG).show();
                }
                if(count<5){
                    i+=1;
                    qnumber.setText((1+i)+"/5");
                    FontUtil fontUtil=new FontUtil();
                    if (HomeActivity.CheckLanguage(Fighting.this)) {
                        qforch.setText(fontUtil.zawgyi2unicode(questionlist.get(i).getQuestion()));
                        chbtnans1.setText(fontUtil.zawgyi2unicode(questionlist.get(i).getAns1()));
                        chbtnans2.setText(fontUtil.zawgyi2unicode(questionlist.get(i).getAns2()));
                        chbtnans3.setText(fontUtil.zawgyi2unicode(questionlist.get(i).getAns3()));
                        chbtnans4.setText(fontUtil.zawgyi2unicode(questionlist.get(i).getAns4()));
                    }else{
                        qforch.setText(questionlist.get(i).getQuestion());
                        chbtnans1.setText(questionlist.get(i).getAns1());
                        chbtnans2.setText(questionlist.get(i).getAns2());
                        chbtnans3.setText(questionlist.get(i).getAns3());
                        chbtnans4.setText(questionlist.get(i).getAns4());
                    }
                    answer=questionlist.get(i).getCorrect();
                }else {
                    qforch.setText("Finish");
                    Enable();
                    cdt.cancel();

                    fightingfb.child("Challenge").child(sp.getString("userid","")+getuser.getUserId()).child("countone").setValue(mark);
                    //cdt.cancel();
                    Log.i("FINISH 4", "WORK");
                    Intent i=new Intent(Fighting.this,Chresult.class).putExtra("mark",mark).putExtra("userid",accuserid);
                    i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                    finish();

                    break;
                }
                break;
            case R.id.chbtnans4:
                count+=1;
                if(answer.equals("ans4")){
                    mark+=1;
                    //Toast.makeText(Fighting.this,"True",Toast.LENGTH_LONG).show();
                }else {
                    // Toast.makeText(Fighting.this,"False",Toast.LENGTH_LONG).show();
                }
                if(count<5){
                    i+=1;
                    qnumber.setText((1+i)+"/5");
                    FontUtil fontUtil=new FontUtil();
                    if (HomeActivity.CheckLanguage(Fighting.this)) {
                        qforch.setText(fontUtil.zawgyi2unicode(questionlist.get(i).getQuestion()));
                        chbtnans1.setText(fontUtil.zawgyi2unicode(questionlist.get(i).getAns1()));
                        chbtnans2.setText(fontUtil.zawgyi2unicode(questionlist.get(i).getAns2()));
                        chbtnans3.setText(fontUtil.zawgyi2unicode(questionlist.get(i).getAns3()));
                        chbtnans4.setText(fontUtil.zawgyi2unicode(questionlist.get(i).getAns4()));
                    }else{
                        qforch.setText(questionlist.get(i).getQuestion());
                        chbtnans1.setText(questionlist.get(i).getAns1());
                        chbtnans2.setText(questionlist.get(i).getAns2());
                        chbtnans3.setText(questionlist.get(i).getAns3());
                        chbtnans4.setText(questionlist.get(i).getAns4());
                    }
                    answer=questionlist.get(i).getCorrect();
                }else {
                    qforch.setText("Finish");
                    Enable();
                    cdt.cancel();

                    fightingfb.child("Challenge").child(sp.getString("userid","")+getuser.getUserId()).child("countone").setValue(mark);

                    Log.i("FINISH 5", "WORK");
                    Intent i=new Intent(Fighting.this,Chresult.class).putExtra("mark",mark).putExtra("userid",accuserid);
                    i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                    finish();
                    break;
                }
                break;
        }
    }
    private void Enable(){
        chbtnans1.setEnabled(false);
        chbtnans2.setEnabled(false);
        chbtnans3.setEnabled(false);
        chbtnans4.setEnabled(false);

    }
    boolean doublepress=false;

    @Override
    public void onBackPressed() {
        if(doublepress){
            super.onBackPressed();
            return;
        }
        this.doublepress=true;
        final AlertDialog.Builder builder=new AlertDialog.Builder(Fighting.this);
        builder.setCancelable(false);
        builder.setMessage("Are you sure?"+"\n"+"\n"+"You will lose in your challenge !");
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Enable();
                cdt.cancel();

                fightingfb.child("Challenge").child(sp.getString("userid","")+getuser.getUserId()).child("countone").setValue(mark);
                // cdt.cancel();
                Log.i("FINISH 3", "WORK");
                Intent i=new Intent(Fighting.this,Chresult.class).putExtra("mark",mark).putExtra("userid",accuserid);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                finish();

            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        final AlertDialog dialog=builder.create();
        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface myarg) {
                dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(getColor(R.color.colorPrimary));
                dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getColor(R.color.colorPrimary));
            }
        });
        dialog.show();

        new android.os.Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doublepress=false;
            }
        },2000);

    }
}
