package com.team16.um.mmquizmaster;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.team16.um.mmquizmaster.Pagers.Home;

public class LoginActivity extends AppCompatActivity implements FacebookCallback<LoginResult>{
    Button singin,singup;
    LoginButton btnlogin;
    CallbackManager manager;
    public Profile myprofile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);

        if(AccessToken.getCurrentAccessToken()!=null){
            startActivity(new Intent(this, HomeActivity.class));
            finish();
        }

        setContentView(R.layout.login_activity);

        singin=(Button)findViewById(R.id.singinbtn);
        singin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast toast=Toast.makeText(LoginActivity.this,"Please Login With Facebook..!",Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER,0,0);
                toast.show();

            }
        });
        singup=(Button)findViewById(R.id.btnsingup);
        singup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast toast=Toast.makeText(LoginActivity.this,"Please Login With Facebook..!",Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER,0,0);
                toast.show();

            }
        });
        manager = CallbackManager.Factory.create();
        // tv = (TextView) findViewById(R.id.tv);

        btnlogin = (LoginButton) findViewById(R.id.userloginbutton);
        btnlogin.registerCallback(manager,LoginActivity.this);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        manager.onActivityResult(requestCode,resultCode,data);
    }

    @Override
    public void onSuccess(LoginResult loginResult) {

        if (Profile.getCurrentProfile() == null){
            ProfileTracker tracker = new ProfileTracker() {
                @Override
                protected void onCurrentProfileChanged(Profile oldProfile, Profile currentProfile) {

                    Log.e("myprofileurl",currentProfile.getProfilePictureUri(300,300)+"");
                    Startactivity.addLogin(LoginActivity.this);
                    Startactivity.addUserData(LoginActivity.this,currentProfile.getName(),currentProfile.getId(),
                            currentProfile.getProfilePictureUri(300,300)+"");

                    startActivity(new Intent(LoginActivity.this,HomeActivity.class));

                    finish();

                }
            };
        }else{
            myprofile = Profile.getCurrentProfile();
            Startactivity.addLogin(LoginActivity.this);
            Startactivity.addUserData(LoginActivity.this,myprofile.getName(),myprofile.getId(),
                    myprofile.getProfilePictureUri(300,300)+"");

            startActivity(new Intent(LoginActivity.this,HomeActivity.class));
            finish();
        }

    }

    @Override
    public void onCancel() {
        Toast.makeText(this, "Cancel", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onError(FacebookException error) {
        Toast.makeText(this, "Please check your internet", Toast.LENGTH_SHORT).show();
    }
}
