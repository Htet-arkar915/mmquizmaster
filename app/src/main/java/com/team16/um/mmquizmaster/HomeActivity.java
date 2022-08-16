package com.team16.um.mmquizmaster;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;

import com.facebook.AccessToken;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.squareup.picasso.Picasso;
import com.team16.um.mmquizmaster.Model.ChallengeFirebase;
import com.team16.um.mmquizmaster.Model.CompletenotiFb;
import com.team16.um.mmquizmaster.Model.ResultFirebase;
import com.team16.um.mmquizmaster.Model.UserLevelfirebase;
import com.team16.um.mmquizmaster.Model.UserfirebaseInfo;
import com.team16.um.mmquizmaster.Navigation.Aboutapp;
import com.team16.um.mmquizmaster.Navigation.Contactus;
import com.team16.um.mmquizmaster.Navigation.Leaderboard;
import com.team16.um.mmquizmaster.Navigation.Myprofile;
import com.team16.um.mmquizmaster.Navigation.Navhelp;
import com.team16.um.mmquizmaster.Pagers.Accept;
import com.team16.um.mmquizmaster.Pagers.Challenge;
import com.team16.um.mmquizmaster.Pagers.MyPagerAdapter;
import com.team16.um.mmquizmaster.Pagers.Result;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


@JsonIgnoreProperties(ignoreUnknown = true)
public class HomeActivity extends AppCompatActivity {
    CollapsingToolbarLayout colaphome;
    AppBarLayout appbarhome;
    Toolbar tb;
    DrawerLayout drawer;
    NavigationView navgv;
    TabLayout tlayout;
    ViewPager viewpager;
    Firebase userinfofirebase, chfb;
    TextView nlevel;
    Switch langchack,songcheck;
    UserLevelfirebase userlvfb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(this);
        setContentView(R.layout.finishlogin_activity);
        colaphome = (CollapsingToolbarLayout) findViewById(R.id.collapsinghome);
        appbarhome = (AppBarLayout) findViewById(R.id.appbarhome);
        SharedPreferences sp = HomeActivity.this.getSharedPreferences("userdata", MODE_PRIVATE);
        tlayout = (TabLayout) findViewById(R.id.hometab);
        viewpager = (ViewPager) findViewById(R.id.homepager);

        Firebase.setAndroidContext(HomeActivity.this);
        userinfofirebase = new Firebase("https://userinformation-1825d.firebaseio.com/");
        userlvfb = new UserLevelfirebase();

        Startactivity.addlv(HomeActivity.this);


        userinfofirebase.child("Userlevel").addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ProduceLevel(dataSnapshot);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        UserfirebaseInfo userfirebase = new UserfirebaseInfo();
        userfirebase.setUsername(sp.getString("username", ""));
        userfirebase.setUserImgurl(sp.getString("profile", ""));
        userfirebase.setUserId(sp.getString("userid", ""));
        userinfofirebase.child("Userdata").child(sp.getString("userid", "")).setValue(userfirebase);

        ///////////////////////////////////
        chfb = new Firebase("https://challenge-1d2ec.firebaseio.com/");
        chfb.child("Challenge").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                produceData(dataSnapshot);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
        /////////
        chfb.child("Complete").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Produce(dataSnapshot);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
        initCollapsingToolbar();
        tb = (Toolbar) findViewById(R.id.hometoolbar);
        drawer = (DrawerLayout) findViewById(R.id.homedrawer);
        navgv = (NavigationView) findViewById(R.id.navhome);
        setSupportActionBar(tb);
        getSupportActionBar().setTitle("Mm Quizmaster");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        ActionBarDrawerToggle tg = new ActionBarDrawerToggle(HomeActivity.this, drawer, tb, R.string.open, R.string.close);
        drawer.setDrawerListener(tg);
        tg.syncState();
        final MyPagerAdapter padapter = new MyPagerAdapter(getSupportFragmentManager());
        viewpager.setAdapter(padapter);
        tlayout.setupWithViewPager(viewpager);

        viewpager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(final int i, final float v, final int i2) {
            }
            @Override
            public void onPageSelected(final int i) {
                if(i == 1) {
                    Challenge fragment = (Challenge) padapter.instantiateItem(viewpager, i);;
                    if (fragment != null) {
                        fragment.refreshUserList();
                    }
                }
            }
            @Override
            public void onPageScrollStateChanged(final int i){
            }
        });

        setTabicon();
        tlayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tlayout.getSelectedTabPosition()) {
                    case 0:
                        tlayout.getTabAt(0).setIcon(R.drawable.ic_fsstudy);
                        tlayout.getTabAt(2).setIcon(R.drawable.ic_notification);
                        tlayout.getTabAt(1).setIcon(R.drawable.ic_champ);
                        tlayout.getTabAt(3).setIcon(R.drawable.ic_resultw);
                        break;
                    case 1:
                        tlayout.getTabAt(1).setIcon(R.drawable.ic_fschamp);
                        tlayout.getTabAt(0).setIcon(R.drawable.ic_study);
                        tlayout.getTabAt(2).setIcon(R.drawable.ic_notification);
                        tlayout.getTabAt(3).setIcon(R.drawable.ic_resultw);
                        break;
                    case 2:
                        tlayout.getTabAt(2).setIcon(R.drawable.ic_fsnotification);
                        tlayout.getTabAt(1).setIcon(R.drawable.ic_champ);
                        tlayout.getTabAt(0).setIcon(R.drawable.ic_study);
                        tlayout.getTabAt(3).setIcon(R.drawable.ic_resultw);
                        break;
                    case 3:
                        tlayout.getTabAt(3).setIcon(R.drawable.ic_result);
                        tlayout.getTabAt(2).setIcon(R.drawable.ic_notification);
                        tlayout.getTabAt(1).setIcon(R.drawable.ic_champ);
                        tlayout.getTabAt(0).setIcon(R.drawable.ic_study);
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        //To set data to the nav header
        final View nav = navgv.inflateHeaderView(R.layout.hngvheader);
        final CircleImageView navimg = (CircleImageView) nav.findViewById(R.id.navcircleimg);
        nlevel = (TextView) nav.findViewById(R.id.navuserlevel);
        TextView username = (TextView) nav.findViewById(R.id.navusername);

        Glide.with(nav.getContext()).load(sp.getString("profile", "")).into(navimg);
        navimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityOptionsCompat compat=ActivityOptionsCompat.makeSceneTransitionAnimation((Activity) nav.getContext(),navimg.findViewById(R.id.navcircleimg),"myProfile");
                startActivity(new Intent(HomeActivity.this,Myprofile.class),compat.toBundle());
            }
        });
        username.setText(sp.getString("username", ""));
        Menu menu=navgv.getMenu();
        langchack=(Switch) menu.findItem(R.id.navlanguage).getActionView().findViewById(R.id.checkswitch);
        if (CheckLanguage(HomeActivity.this)){
            langchack.setChecked(true);
        }
        langchack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (langchack.isChecked()){
                    DefineLanguage(HomeActivity.this);
                    onRestart();
                    recreate();
                }else {
                    RemoveLanguage(HomeActivity.this);
                    onRestart();
                    recreate();
                }
            }
        });
        songcheck=(Switch) menu.findItem(R.id.navsound).getActionView().findViewById(R.id.checksong);
        if(CheckSong(HomeActivity.this)){
            songcheck.setChecked(false);
        }
        songcheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!songcheck.isChecked()){
                    DefineSong(HomeActivity.this);

                }else {
                    RemoveSong(HomeActivity.this);

                }

            }
        });

        navgv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.myprofile:
                        item.setChecked(true);
                        startActivity(new Intent(HomeActivity.this, Myprofile.class));
                        drawer.closeDrawer(GravityCompat.START);
                        break;
                    case R.id.leaderboard:
                        item.setChecked(true);
                        Toast.makeText(HomeActivity.this, "Updating this section", Toast.LENGTH_SHORT).show();
                        //startActivity(new Intent(HomeActivity.this, Leaderboard.class));
                        //drawer.closeDrawer(GravityCompat.START);
                        break;
                    case R.id.navlanguage:
                        item.setChecked(true);
                        if (langchack.isChecked()){
                            langchack.setChecked(false);
                            RemoveLanguage(HomeActivity.this);
                            onRestart();
                            recreate();
                        }else {
                            langchack.setChecked(true);
                            DefineLanguage(HomeActivity.this);
                            onRestart();
                            recreate();
                        }
                        break;
                    case R.id.navsound:
                        item.setChecked(true);
                        if (!songcheck.isChecked()){
                            songcheck.setChecked(true);
                            RemoveSong(HomeActivity.this);
                        }else {
                            songcheck.setChecked(false);
                            DefineSong(HomeActivity.this);
                        }
                        break;
                    case R.id.navcontact:
                        item.setChecked(true);
                        startActivity(new Intent(HomeActivity.this, Contactus.class));
                        drawer.closeDrawer(GravityCompat.START);

                        break;
                    case R.id.navshare:
                        item.setChecked(true);
                        drawer.closeDrawer(GravityCompat.START);
                        shareIt();
                        break;
                    case R.id.navhelp:
                        item.setChecked(true);
                        startActivity(new Intent(HomeActivity.this, Navhelp.class));
                        drawer.closeDrawer(GravityCompat.START);
                        break;
                    case R.id.navabout:
                        item.setChecked(true);
                        startActivity(new Intent(HomeActivity.this, Aboutapp.class));
                        drawer.closeDrawer(GravityCompat.START);
                        break;
                    case R.id.logout:
                        item.setChecked(true);
                        drawer.closeDrawer(GravityCompat.START);
                        final AlertDialog.Builder builder=new AlertDialog.Builder(HomeActivity.this);
                        builder.setCancelable(false);
                        builder.setTitle("Log Out");
                        builder.setMessage("Are you sure?");
                        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if(AccessToken.getCurrentAccessToken()!=null){
                                    LoginManager.getInstance().logOut();
                                }
                                finish();
                            }
                        });
                        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                builder.create().dismiss();
                            }
                        });
                        final AlertDialog dialog=builder.create();
                        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
                            @Override
                            public void onShow(DialogInterface myarg) {
                                dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getColor(R.color.colorPrimary));
                                dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(getColor(R.color.colorPrimary));
                            }
                        });
                        dialog.show();

                        break;



                }

                return true;
            }
        });

        if (getIntent().getExtras() != null) {

            viewpager.setCurrentItem(getIntent().getIntExtra("item", 0));
        }

        changeStatusBarColor();

    }

    private void changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }
    }

    private void ProduceLevel(DataSnapshot dataSnapshot) {
        SharedPreferences spf = HomeActivity.this.getSharedPreferences("userdata", MODE_PRIVATE);
        for (DataSnapshot dsh : dataSnapshot.getChildren()) {
            if (dsh.getValue(UserLevelfirebase.class).getUserid().equals(spf.getString("userid", ""))) {
                int usercoin=dsh.getValue(UserLevelfirebase.class).getCoin();
                int level = dsh.getValue(UserLevelfirebase.class).getLevel();
                int xp=dsh.getValue(UserLevelfirebase.class).getExperience();
                userinfofirebase.child("Userdata").child(spf.getString("userid", "")).child("userlevel").setValue(level);
                userinfofirebase.child("Userdata").child(spf.getString("userid", "")).child("usercoin").setValue(usercoin);
                nlevel.setText("Level : " + level);
            } else if (!dsh.getValue(UserLevelfirebase.class).getUserid().equals(spf.getString("userid", ""))) {

            }
        }
    }

    private void Produce(DataSnapshot dataSnapshot) {
        for (DataSnapshot dsh : dataSnapshot.getChildren()) {
            SharedPreferences spf = HomeActivity.this.getSharedPreferences("userdata", MODE_PRIVATE);
            if (spf.getString("userid", "").equals(dsh.getValue(CompletenotiFb.class).getChid())
                    && (dsh.getValue(CompletenotiFb.class).getNoti() == 1)) {
                Completenoti(dsh.getValue(CompletenotiFb.class).getAccName());
                chfb.child("Complete").child(dsh.getValue(CompletenotiFb.class).getChid() +
                        dsh.getValue(CompletenotiFb.class).getAcceptfbid()).child("noti").setValue(0);

            }
        }
    }


    private void Completenoti(String acceptname) {

        final MediaPlayer mymusicnoti = MediaPlayer.create(getApplicationContext(), R.raw.correct);
        mymusicnoti.start();
        NotificationCompat.Builder mynoti = new NotificationCompat.Builder(this);
        mynoti.setSmallIcon(R.drawable.logo);
        mynoti.setAutoCancel(true);
        mynoti.setContentTitle("Challenge Notification");
        mynoti.setContentText(acceptname + " Comlpleted your Challenge.");
        NotificationManager myotimanager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mymusicnoti.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mymusicnoti.stop();
                mymusicnoti.release();
            }
        });
        Intent i = new Intent(this, HomeActivity.class);
        i.putExtra("item", 3);
        mynoti.setContentIntent(PendingIntent.getActivity(this, 0, i
                , PendingIntent.FLAG_UPDATE_CURRENT));
        myotimanager.notify(0, mynoti.build());

    }


    private void produceData(DataSnapshot dataSnapshot) {

        for (DataSnapshot dsh : dataSnapshot.getChildren()) {

            SharedPreferences spf = HomeActivity.this.getSharedPreferences("userdata", MODE_PRIVATE);
            if (spf.getString("userid", "").equals(dsh.getValue(ChallengeFirebase.class).getUserid2()) &&
                    dsh.getValue(ChallengeFirebase.class).getChnoti() == 1) {
                if (dsh.getValue(ChallengeFirebase.class).getUsername1()!=null){
                chfb.child("Challenge").child(dsh.getValue(ChallengeFirebase.class).getParent()).child("chnoti").setValue(0);
                chNoti(dsh.getValue(ChallengeFirebase.class).getUsername1());
                }

            }
        }
    }

    //Tabbar default icon
    private void setTabicon() {
        tlayout.getTabAt(0).setIcon(R.drawable.ic_fsstudy);
        tlayout.getTabAt(1).setIcon(R.drawable.ic_champ);
        tlayout.getTabAt(2).setIcon(R.drawable.ic_notification);
        tlayout.getTabAt(3).setIcon(R.drawable.ic_resultw);

    }

    //trace collapsing condition
    private void initCollapsingToolbar() {
        colaphome.setTitle(" ");
        appbarhome.setExpanded(true);
        appbarhome.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appbarhome.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    colaphome.setTitle("MM Quizmaster");
                    colaphome.setCollapsedTitleTextAppearance(R.style.Collaptitle);
                    isShow = true;
                } else if (isShow) {
                    colaphome.setTitle(" ");
                    isShow = false;
                }

            }
        });

    }

    boolean doublepress = false;

    //User click backkey of phone
    @Override
    public void onBackPressed() {
        if (doublepress) {
            super.onBackPressed();
            System.exit(1);
            System.setOut(System.out);
            finish();
            return;
        }
        this.doublepress = true;
        Toast.makeText(HomeActivity.this, "Please click Again", Toast.LENGTH_SHORT).show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doublepress = false;
            }
        }, 2000);


    }

    //Create menu at tool bar called option menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.homeoptionmenu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.homeexit:
                System.exit(1);
                finish();
                System.setOut(System.out);
                break;
            case R.id.homefeedback:
                Intent feedback = new Intent(Intent.ACTION_SEND);
                feedback.setData(Uri.parse("email"));
                String[] str = {"samsunginstitute@gmail.com"};
                feedback.putExtra(Intent.EXTRA_EMAIL, str);
                feedback.setType("message/rfc822");
                Intent chooser = Intent.createChooser(feedback, "Launch Email");
                startActivity(chooser);
                break;
        }
        return true;
    }

    ///////////////////future for song condition
    public static boolean CheckLanguage(Context con) {
        SharedPreferences langsharep = con.getSharedPreferences("Language", MODE_PRIVATE);
        return langsharep.getBoolean("lcheck", false);
    }

    public static void DefineLanguage(Context con) {
        SharedPreferences lspf = con.getSharedPreferences("Language", MODE_PRIVATE);
        SharedPreferences.Editor editor = lspf.edit();
        editor.putBoolean("lcheck", true);
        editor.commit();
    }
    public static void RemoveLanguage(Context con) {
        SharedPreferences lspf = con.getSharedPreferences("Language", MODE_PRIVATE);
        SharedPreferences.Editor editor = lspf.edit();
        editor.putBoolean("lcheck", false);
        editor.commit();
    }
    public static boolean CheckSong(Context con) {
        SharedPreferences Songsharep = con.getSharedPreferences("song", MODE_PRIVATE);
        return Songsharep.getBoolean("scheck", false);
    }

    public static void DefineSong(Context con) {
        SharedPreferences sspf = con.getSharedPreferences("song", MODE_PRIVATE);
        SharedPreferences.Editor editor = sspf.edit();
        editor.putBoolean("scheck", true);
        editor.commit();
    }
    public static void RemoveSong(Context con) {
        SharedPreferences sspf = con.getSharedPreferences("song", MODE_PRIVATE);
        SharedPreferences.Editor editor = sspf.edit();
        editor.putBoolean("scheck", false);
        editor.commit();
    }

    private void shareIt() {
//sharing implementation here
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "AndroidSolved");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT,
                "https://1drv.ms/u/s!AtiTGCXBhRmab1JzZx8wLbYJjXg");
        startActivity(Intent.createChooser(sharingIntent, "Share via"));
    }

    private void chNoti(String name) {
        final MediaPlayer mymusicnoti = MediaPlayer.create(getApplicationContext(), R.raw.correct);
        mymusicnoti.start();
        NotificationCompat.Builder mynoti = new NotificationCompat.Builder(this);
        mynoti.setSmallIcon(R.drawable.logo);
        mynoti.setAutoCancel(true);
        mynoti.setContentTitle("Challenge Notification");
        mynoti.setContentText(name + " Challenge you.");
        NotificationManager myotimanager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        myotimanager.notify(0, mynoti.build());
        mymusicnoti.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mymusicnoti.stop();
                mymusicnoti.release();
            }
        });
        Intent i = new Intent(this, HomeActivity.class);
        i.putExtra("item", 2);
        mynoti.setContentIntent(PendingIntent.getActivity(this, 0, i
                , PendingIntent.FLAG_UPDATE_CURRENT));
        myotimanager.notify(0, mynoti.build());

    }
    public static boolean haveNetworkConnection(Context context) {
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] netInfo = cm.getAllNetworkInfo();
        for (NetworkInfo ni : netInfo) {
            if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                if (ni.isConnected())
                    haveConnectedWifi = true;
            if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                if (ni.isConnected())
                    haveConnectedMobile = true;
        }
        return haveConnectedWifi || haveConnectedMobile;
    }


}
