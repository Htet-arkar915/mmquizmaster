package com.team16.um.mmquizmaster;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.FacebookSdk;

public class Startactivity extends AppCompatActivity {
    ViewPager vpager;
    LinearLayout dotlayout;
    Button btnnext, btnskip;
    TextView[] dottvarr;
    int[] layout;
    MyViewPagerAdapter myViewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(this);
        if (CheckLogin(Startactivity.this)) {
            Log.i("User : ",AccessToken.getCurrentAccessToken()+" User ");
            if (AccessToken.getCurrentAccessToken() == null)
                startActivity(new Intent(Startactivity.this, LoginActivity.class));
            else
                startActivity(new Intent(Startactivity.this, HomeActivity.class));
            finish();
        }
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_FULLSCREEN);
        }
        setContentView(R.layout.activity_startactivity);
        btnnext = (Button) findViewById(R.id.btn_next);
        btnskip = (Button) findViewById(R.id.btn_skip);
        vpager = (ViewPager) findViewById(R.id.view_pager);
        dotlayout = (LinearLayout) findViewById(R.id.layoutDots);
        layout = new int[]{
                R.layout.welcome_slide1,
                R.layout.welcome_slide2,
                R.layout.welcome_slide3,
                R.layout.welcomeslidefour
        };
        addBottomDot(0);

        changeStatusBarColor();

        myViewPagerAdapter = new MyViewPagerAdapter();
        vpager.setAdapter(myViewPagerAdapter);
        vpager.addOnPageChangeListener(viewPageChangeListener);
        btnskip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GoLoginPage();
            }
        });
        btnnext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentindex = getItem(+1);
                if (currentindex < layout.length) {
                    vpager.setCurrentItem(currentindex);
                } else {
                    startActivity(new Intent(Startactivity.this, LoginActivity.class));
                    finish();

                }
            }
        });
    }

    private void changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
            window.setStatusBarColor(Color.BLACK);
        }
    }

    private void addBottomDot(int current) {
        dottvarr = new TextView[layout.length];
        int[] coloractive = getResources().getIntArray(R.array.color_of_active);
        int[] colorinactive = getResources().getIntArray(R.array.color_of_inactive);
        dotlayout.removeAllViews();
        for (int i = 0; i < dottvarr.length; i++) {
            dottvarr[i] = new TextView(this);
            dottvarr[i].setText(Html.fromHtml("&#8226"));
            dottvarr[i].setTextSize(35);
            dottvarr[i].setTextColor(colorinactive[current]);
            dotlayout.addView(dottvarr[i]);
        }
        if (dottvarr.length > 0) {
            dottvarr[current].setTextColor(coloractive[current]);
        }

    }

    private int getItem(int i) {
        return vpager.getCurrentItem() + i;
    }

    private void GoLoginPage() {
        startActivity(new Intent(Startactivity.this, LoginActivity.class));
        finish();

    }

    ViewPager.OnPageChangeListener viewPageChangeListener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            addBottomDot(position);
            if (position == layout.length - 1) {
                Startactivity.removeUser(Startactivity.this);
                btnnext.setText("GOT IT");
                btnskip.setVisibility(View.GONE);
            } else {
                btnnext.setText("NEXT");
                btnskip.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    //to check Login
    public static boolean CheckLogin(Context context) {
        SharedPreferences sharepf = context.getSharedPreferences("Sdata", MODE_PRIVATE);
        return sharepf.getBoolean("ch", false);
    }

    public static void addlv(Context con) {
        SharedPreferences setting = con.getSharedPreferences("mylv", MODE_PRIVATE);
        SharedPreferences.Editor editor = setting.edit();
        editor.putBoolean("ch", true);
        editor.commit();
    }

    public static void addLogin(Context con) {
        SharedPreferences setting = con.getSharedPreferences("Sdata", MODE_PRIVATE);
        SharedPreferences.Editor editor = setting.edit();
        editor.putBoolean("ch", true);
        editor.commit();
    }

    //Store user data
    public static void addUserData(Context context, String username, String userid, String profileurl) {
        SharedPreferences sh = context.getSharedPreferences("userdata", MODE_PRIVATE);
        SharedPreferences.Editor editor = sh.edit();
        editor.putString("username", username);
        editor.putString("userid", userid);
        editor.putString("profile", profileurl);
        editor.commit();
    }

    public static void removeUser(Context context) {
        SharedPreferences setting = context.getSharedPreferences("Sdata", MODE_PRIVATE);
        SharedPreferences.Editor editor = setting.edit();
        editor.putBoolean("ch", true);
        editor.commit();
    }

    private class MyViewPagerAdapter extends PagerAdapter {
        private LayoutInflater layoutInflater;

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = layoutInflater.inflate(layout[position], container, false);
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            View view = (View) object;
            container.removeView(view);
        }

        @Override
        public int getCount() {
            return layout.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }


    }
}