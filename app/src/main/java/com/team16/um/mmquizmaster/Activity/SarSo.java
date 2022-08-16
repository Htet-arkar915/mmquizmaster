package com.team16.um.mmquizmaster.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.kyawhtut.FontUtil;
import com.team16.um.mmquizmaster.HomeActivity;
import com.team16.um.mmquizmaster.Model.Question_answerinfo;
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

public class SarSo extends AppCompatActivity implements View.OnClickListener{

    TextView question;
    Button btnans1,btnans2,btnans3,btnans4;
    ImageView btnnext;
    String correct;
    ArrayList<Question_answerinfo> qalist=new ArrayList<>();
    Question_answerinfo quesandans;
    Activity mactivity;
    Toolbar tb;
    Button floatbtn;
    MediaPlayer wtrue,wfalse;
    boolean tt=true;
    int num=0;
    FontUtil fontUtil=new FontUtil();
    String titlename="ေရွးစာဆိုမ်ား";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.soloplay);
        floatbtn=(Button)findViewById(R.id.showansbtn);
        floatbtn.setOnClickListener(this);
        btnans1=(Button)findViewById(R.id.btnans1);
        tb=(Toolbar)findViewById(R.id.solotoolbar);
        btnans2=(Button)findViewById(R.id.btnans2);
        btnans3=(Button)findViewById(R.id.btnans3);
        btnans4=(Button)findViewById(R.id.btnans4);
        question=(TextView)findViewById(R.id.tvqestion);
        btnnext=(ImageView) findViewById(R.id.nextbtnimg);
        setSupportActionBar(tb);
        if (HomeActivity.CheckLanguage(SarSo.this)) {
            String unicode= (String) fontUtil.zawgyi2unicode(titlename);
            getSupportActionBar().setTitle(unicode);
        }
        else
            getSupportActionBar().setTitle(titlename);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        tb.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
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
                Collections.shuffle(qalist);
            }
        }catch (JSONException e){e.printStackTrace();}

        askQuestion();

        if(savedInstanceState!=null){
            quesandans=(Question_answerinfo) savedInstanceState.getSerializable("obj");
        }

        btnans2.setOnClickListener(this);
        btnans3.setOnClickListener(this);
        btnans4.setOnClickListener(this);
        btnans1.setOnClickListener(this);
        btnnext.setOnClickListener(this);

    }

    private void ButtonStatus(boolean status){
        btnans1.setEnabled(status);
        btnans2.setEnabled(status);
        btnans3.setEnabled(status);
        btnans4.setEnabled(status);
    }

    private void askQuestion(){

        btnans1.setBackgroundResource(R.drawable.default_btnback);
        btnans2.setBackgroundResource(R.drawable.default_btnback);
        btnans3.setBackgroundResource(R.drawable.default_btnback);
        btnans4.setBackgroundResource(R.drawable.default_btnback);
        btnans1.setTextColor(Color.BLACK);
        btnans2.setTextColor(Color.BLACK);
        btnans3.setTextColor(Color.BLACK);
        btnans4.setTextColor(Color.BLACK);

        ButtonStatus(true);
        FontUtil fontUtil=new FontUtil();
        String trueanswer="အေျဖမွန္";
        Button trueans=(Button)findViewById(R.id.showansbtn);
        if (HomeActivity.CheckLanguage(SarSo.this)){
            question.setText(fontUtil.zawgyi2unicode(qalist.get(num).getQuestion()));
            btnans1.setText(fontUtil.zawgyi2unicode(qalist.get(num).getAns1()));
            btnans2.setText(fontUtil.zawgyi2unicode(qalist.get(num).getAns2()));
            btnans3.setText(fontUtil.zawgyi2unicode(qalist.get(num).getAns3()));
            btnans4.setText(fontUtil.zawgyi2unicode(qalist.get(num).getAns4()));
            trueans.setText(fontUtil.zawgyi2unicode(trueanswer));
        }else{
            trueans.setText(trueanswer);
            question.setText(qalist.get(num).getQuestion());
            btnans1.setText(qalist.get(num).getAns1());
            btnans2.setText(qalist.get(num).getAns2());
            btnans3.setText(qalist.get(num).getAns3());
            btnans4.setText(qalist.get(num).getAns4());
        }
        correct=qalist.get(num).getCorrect();
        if (num<qalist.size())
            num++;

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (quesandans!=null){
            outState.putSerializable("obj",quesandans);
        }
    }

    private String LoadJSONFromAsset() {
        StringBuilder stringBuilder=new StringBuilder();
        try{
            InputStream inputstream=getAssets().open("sarso.json");
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

    @Override
    public void onClick(View v) {

        int delay=500;

        ButtonStatus(false);
        if (!HomeActivity.CheckSong(SarSo.this)){
            wtrue= MediaPlayer.create(getApplicationContext(),R.raw.correct);
            wfalse=MediaPlayer.create(getApplicationContext(),R.raw.fail);
        }else {
            wtrue= MediaPlayer.create(getApplicationContext(),R.raw.nonemusic);
            wfalse=MediaPlayer.create(getApplicationContext(),R.raw.nonemusic);
        }
        mactivity=SarSo.this;
        switch (v.getId()){
            case R.id.btnans1:

                if(correct.equals("ans1")){
                    try{
                        Thread.sleep(delay);
                        btnans1.setBackgroundResource(R.drawable.answertruebtnback);
                        btnans2.setBackgroundResource(R.drawable.default_btnback);
                        btnans3.setBackgroundResource(R.drawable.default_btnback);
                        btnans4.setBackgroundResource(R.drawable.default_btnback);
                        btnans1.setTextColor(Color.WHITE);
                        btnans2.setTextColor(Color.BLACK);
                        btnans3.setTextColor(Color.BLACK);
                        btnans4.setTextColor(Color.BLACK);
                        wtrue.start();
                    }catch (InterruptedException e){

                    }
                    wtrue.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mp) {
                            wtrue.stop();
                            wtrue.release();
                            showDialog(SarSo.this,num,qalist.size());
                            Animation animation;
                            animation= AnimationUtils.loadAnimation(SarSo.this,R.anim.zoom);
                            question.startAnimation(animation);
                            animation=AnimationUtils.loadAnimation(SarSo.this,R.anim.float_left);
                            floatbtn.startAnimation(animation);
                            floatbtn.setEnabled(true);
                        }
                    });
                }else {
                    try{
                        Thread.sleep(delay);
                        btnans1.setBackgroundResource(R.drawable.answerfalsebtnback);
                        btnans2.setBackgroundResource(R.drawable.default_btnback);
                        btnans3.setBackgroundResource(R.drawable.default_btnback);
                        btnans4.setBackgroundResource(R.drawable.default_btnback);
                        btnans1.setTextColor(Color.WHITE);
                        btnans2.setTextColor(Color.BLACK);
                        btnans3.setTextColor(Color.BLACK);
                        btnans4.setTextColor(Color.BLACK);
                        wfalse.start();
                    }catch (InterruptedException e){

                    }
                    wfalse.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mp) {
                            ButtonStatus(true);
                            wfalse.stop();
                            wfalse.release();
                        }
                    });
                }
                break;
            case R.id.btnans2:
                if(correct.equals("ans2")){
                    try{
                        Thread.sleep(delay);
                        btnans1.setBackgroundResource(R.drawable.default_btnback);
                        btnans2.setBackgroundResource(R.drawable.answertruebtnback);
                        btnans3.setBackgroundResource(R.drawable.default_btnback);
                        btnans4.setBackgroundResource(R.drawable.default_btnback);
                        btnans1.setTextColor(Color.BLACK);
                        btnans2.setTextColor(Color.WHITE);
                        btnans3.setTextColor(Color.BLACK);
                        btnans4.setTextColor(Color.BLACK);
                        wtrue.start();
                    }catch (InterruptedException e){

                    }
                    wtrue.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mp) {
                            ButtonStatus(true);
                            wtrue.stop();
                            wtrue.release();
                            showDialog(SarSo.this,num,qalist.size());
                            Animation animation;
                            animation= AnimationUtils.loadAnimation(SarSo.this,R.anim.zoom);
                            question.startAnimation(animation);
                            animation=AnimationUtils.loadAnimation(SarSo.this,R.anim.float_left);
                            floatbtn.startAnimation(animation);
                            floatbtn.setEnabled(true);
                        }
                    });

                }else {
                    try{
                        Thread.sleep(delay);
                        btnans1.setBackgroundResource(R.drawable.default_btnback);
                        btnans2.setBackgroundResource(R.drawable.answerfalsebtnback);
                        btnans3.setBackgroundResource(R.drawable.default_btnback);
                        btnans4.setBackgroundResource(R.drawable.default_btnback);
                        btnans1.setTextColor(Color.BLACK);
                        btnans2.setTextColor(Color.WHITE);
                        btnans3.setTextColor(Color.BLACK);
                        btnans4.setTextColor(Color.BLACK);
                        wfalse.start();
                    }catch (InterruptedException e){

                    }

                    wfalse.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mp) {
                            ButtonStatus(true);
                            wfalse.stop();
                            wfalse.release();
                        }
                    });

                }
                break;
            case R.id.btnans3:
                if(correct.equals("ans3")){
                    try{
                        Thread.sleep(delay);
                        btnans1.setBackgroundResource(R.drawable.default_btnback);
                        btnans2.setBackgroundResource(R.drawable.default_btnback);
                        btnans3.setBackgroundResource(R.drawable.answertruebtnback);
                        btnans4.setBackgroundResource(R.drawable.default_btnback);
                        btnans1.setTextColor(Color.BLACK);
                        btnans2.setTextColor(Color.BLACK);
                        btnans3.setTextColor(Color.WHITE);
                        btnans4.setTextColor(Color.BLACK);
                        wtrue.start();
                    }catch (InterruptedException e){

                    }

                    wtrue.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mp) {
                            ButtonStatus(true);
                            wtrue.stop();
                            wtrue.release();
                            showDialog(SarSo.this,num,qalist.size());
                            Animation animation;
                            animation= AnimationUtils.loadAnimation(SarSo.this,R.anim.zoom);
                            question.startAnimation(animation);
                            animation=AnimationUtils.loadAnimation(SarSo.this,R.anim.float_left);
                            floatbtn.startAnimation(animation);
                            floatbtn.setEnabled(true);
                        }
                    });

                }else {
                    try{
                        Thread.sleep(delay);
                        btnans1.setBackgroundResource(R.drawable.default_btnback);
                        btnans2.setBackgroundResource(R.drawable.default_btnback);
                        btnans3.setBackgroundResource(R.drawable.answerfalsebtnback);
                        btnans4.setBackgroundResource(R.drawable.default_btnback);
                        btnans1.setTextColor(Color.BLACK);
                        btnans2.setTextColor(Color.BLACK);
                        btnans3.setTextColor(Color.WHITE);
                        btnans4.setTextColor(Color.BLACK);
                        wfalse.start();
                    }catch (InterruptedException e){

                    }


                    wfalse.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mp) {
                            ButtonStatus(true);
                            wfalse.stop();
                            wfalse.release();
                        }
                    });
                }
                break;
            case R.id.btnans4:
                if(correct.equals("ans4")){
                    try{
                        Thread.sleep(delay);
                        btnans1.setBackgroundResource(R.drawable.default_btnback);
                        btnans2.setBackgroundResource(R.drawable.default_btnback);
                        btnans3.setBackgroundResource(R.drawable.default_btnback);
                        btnans4.setBackgroundResource(R.drawable.answertruebtnback);
                        btnans1.setTextColor(Color.BLACK);
                        btnans2.setTextColor(Color.BLACK);
                        btnans3.setTextColor(Color.BLACK);
                        btnans4.setTextColor(Color.WHITE);
                        wtrue.start();
                    }catch (InterruptedException e){

                    }
                    wtrue.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mp) {
                            ButtonStatus(true);
                            wtrue.stop();
                            wtrue.release();
                            showDialog(SarSo.this,num,qalist.size());
                            Animation animation;
                            animation= AnimationUtils.loadAnimation(SarSo.this,R.anim.zoom);
                            question.startAnimation(animation);
                            animation=AnimationUtils.loadAnimation(SarSo.this,R.anim.float_left);
                            floatbtn.startAnimation(animation);
                            floatbtn.setEnabled(true);
                        }
                    });

                }else {
                    try{
                        Thread.sleep(delay);
                        btnans1.setBackgroundResource(R.drawable.default_btnback);
                        btnans2.setBackgroundResource(R.drawable.default_btnback);
                        btnans3.setBackgroundResource(R.drawable.default_btnback);
                        btnans4.setBackgroundResource(R.drawable.answerfalsebtnback);
                        btnans1.setTextColor(Color.BLACK);
                        btnans2.setTextColor(Color.BLACK);
                        btnans3.setTextColor(Color.BLACK);
                        btnans4.setTextColor(Color.WHITE);
                        wfalse.start();
                    }catch (InterruptedException e){

                    }

                    wfalse.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mp) {
                            ButtonStatus(true);
                            wfalse.stop();
                            wfalse.release();
                        }
                    });

                }break;

            case R.id.showansbtn:
                switch (correct){
                    case "ans1":
                        btnans1.setBackgroundResource(R.drawable.answertruebtnback);
                        btnans1.setTextColor(Color.WHITE);
                        wtrue.start();
                        wtrue.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                            @Override
                            public void onCompletion(MediaPlayer mp) {
                                wtrue.stop();
                                wtrue.release();
                            }
                        });
                        break;
                    case "ans2":
                        btnans2.setBackgroundResource(R.drawable.answertruebtnback);
                        btnans2.setTextColor(Color.WHITE);
                        wtrue.start();
                        wtrue.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                            @Override
                            public void onCompletion(MediaPlayer mp) {
                                wtrue.stop();
                                wtrue.release();
                            }
                        });
                        break;
                    case "ans3":
                        btnans3.setBackgroundResource(R.drawable.answertruebtnback);
                        btnans3.setTextColor(Color.WHITE);
                        wtrue.start();
                        wtrue.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                            @Override
                            public void onCompletion(MediaPlayer mp) {
                                wtrue.stop();
                                wtrue.release();
                            }
                        });
                        break;
                    case "ans4":
                        btnans4.setBackgroundResource(R.drawable.answertruebtnback);
                        btnans4.setTextColor(Color.WHITE);
                        wtrue.start();
                        wtrue.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                            @Override
                            public void onCompletion(MediaPlayer mp) {
                                wtrue.stop();
                                wtrue.release();
                            }
                        });
                        break;
                }floatbtn.setEnabled(false);
                break;
            case R.id.nextbtnimg:
                floatbtn.setEnabled(true);
                showDialog(SarSo.this,num,qalist.size());
                Animation animation;
                animation= AnimationUtils.loadAnimation(SarSo.this,R.anim.zoom);
                question.startAnimation(animation);
                animation=AnimationUtils.loadAnimation(SarSo.this,R.anim.float_left);
                floatbtn.startAnimation(animation);
                break;
        }
    }
    private void showDialog(final Context context, int num, int size){
        if(num==size){
            final AlertDialog.Builder builder=new AlertDialog.Builder(context);
            builder.setCancelable(false);
            FontUtil fontUtil=new FontUtil();
            String dgTitle="ဂုဏ္ယူပါတယ္....!!";
            String message="ယခုလက္ရွိ ေမးခြန္းမ်ား ကုန္ဆံုးသြားပါျပီ။ \n" +"ေက်းဇူးျပဳ၍ ေမးခြန္း အသစ္မ်ားေစာင့္ဆိုင္းေပးပါ။";
            if(HomeActivity.CheckLanguage(context)){
                String unicode= (String) fontUtil.zawgyi2unicode(dgTitle);
                String unimessage= (String) fontUtil.zawgyi2unicode(message);
                builder.setTitle(unicode);
                builder.setMessage(unimessage);

            }
            else {
                builder.setTitle(dgTitle);
                builder.setMessage(message);
            }
            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent sure=new Intent(context,HomeActivity.class);
                    sure.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    context.startActivity(sure);
                    finish();
                    //finish();

                }
            });
            final AlertDialog dialog=builder.create();
            dialog.setOnShowListener(new DialogInterface.OnShowListener() {
                @Override
                public void onShow(DialogInterface myarg) {
                    dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getColor(R.color.colorPrimary));
                }
            });
            dialog.show();
            num--;
        }else
            askQuestion();
    }
}