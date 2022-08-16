package com.team16.um.mmquizmaster.Navigation;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.team16.um.mmquizmaster.Model.UserfirebaseInfo;
import com.team16.um.mmquizmaster.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class Contactus extends AppCompatActivity implements View.OnClickListener {
    ImageView btn1,btn2,btn3;
    TextView tphone1,tphone2,tphone3;
    ImageView back,profile1,profile2,profile3;
    String str;
    Toolbar tb;
    Firebase userfb;
    String profile1id="520399054978029",profile2id="334833473645807",profile3id="486083375105333";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contactus);
        btn1= (ImageView) findViewById(R.id.ph1);
        btn2= (ImageView) findViewById(R.id.ph2);
        btn3= (ImageView) findViewById(R.id.ph3);
        profile1= (CircleImageView) findViewById(R.id.logo1);
        profile2= (CircleImageView) findViewById(R.id.logo2);
        profile3= (CircleImageView) findViewById(R.id.logo3);

        // back=(ImageView)findViewById(R.id.cbackarrow);
        tb=(Toolbar)findViewById(R.id.contacttool);
        tphone1= (TextView) findViewById(R.id.phone1);
        tphone2= (TextView) findViewById(R.id.phone2);
        tphone3= (TextView) findViewById(R.id.phone3);
        btn2.setOnClickListener(this);
        btn1.setOnClickListener(this);
        btn3.setOnClickListener(this);
        profile1.setOnClickListener(this);
        profile2.setOnClickListener(this);
        profile3.setOnClickListener(this);

        setSupportActionBar(tb);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        tb.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        Firebase.setAndroidContext(Contactus.this);
        userfb=new Firebase("https://userinformation-1825d.firebaseio.com/");
        userfb.child("Userdata").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                produceDeveloperprofile(dataSnapshot);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });


    }

    private void produceDeveloperprofile(DataSnapshot dataSnapshot) {
        for (DataSnapshot dsh:dataSnapshot.getChildren()){
            if (profile1id.equals(dsh.getValue(UserfirebaseInfo.class).getUserId())){
                Glide.with(Contactus.this).load(dsh.getValue(UserfirebaseInfo.class).getUserImgurl()).into(profile1);
            }else if (profile2id.equals(dsh.getValue(UserfirebaseInfo.class).getUserId())){
                Glide.with(Contactus.this).load(dsh.getValue(UserfirebaseInfo.class).getUserImgurl()).into(profile2);
            }else if (profile3id.equals(dsh.getValue(UserfirebaseInfo.class).getUserId())){
                Glide.with(Contactus.this).load(dsh.getValue(UserfirebaseInfo.class).getUserImgurl()).into(profile3);
            }

        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ph1:
                Intent i=new Intent(Intent.ACTION_DIAL);
                str=tphone1.getText().toString();
                if(str.trim().isEmpty()){
                    i.setData(Uri.parse("tel:09424959694"));

                }
                else {
                    i.setData(Uri.parse("tel:09424959694"));
                }
                startActivity(i);
                break;
            case R.id.ph2:
                Intent intent=new Intent(Intent.ACTION_DIAL);
                str=tphone2.getText().toString();
                if(str.trim().isEmpty()){
                    intent.setData(Uri.parse("tel:0979028207"));

                }
                else {

                    intent.setData(Uri.parse("tel:0979028207"));
                }
                startActivity(intent);
                break;
            case R.id.ph3:
                Intent myintent=new Intent(Intent.ACTION_DIAL);
                str=tphone3.getText().toString();
                if(str.trim().isEmpty()){
                    myintent.setData(Uri.parse("tel:09793556427"));

                }
                else {
                    myintent.setData(Uri.parse("tel:09793556427"));
                }
                startActivity(myintent);
                break;
            case R.id.logo1:
                Intent facebookIntent_1=getFBIntent(getApplicationContext(),"100010238169376");
                startActivity(facebookIntent_1);
                break;
            case R.id.logo2:
                Intent facebookIntent_2=getFBIntent(getApplicationContext(),"100013574703296");
                startActivity(facebookIntent_2);
                break;
            case R.id.logo3:
                Intent facebookIntent_3=getFBIntent(getApplicationContext(),"100011110245541");
                startActivity(facebookIntent_3);
                break;



        }



    }

    private Intent getFBIntent(Context context, String facebookId) {
        try {
            context.getPackageManager().getPackageInfo("com.facebook.katana",0);
            String facebookScheme="fb://profile/"+facebookId;
            return new Intent(Intent.ACTION_VIEW,Uri.EMPTY.parse(facebookScheme));

        } catch (PackageManager.NameNotFoundException e) {
            String facebookProfileUri="https://www.facebook.com/"+facebookId;
            return new Intent(Intent.ACTION_VIEW,Uri.parse(facebookProfileUri));
        }

    }

}
