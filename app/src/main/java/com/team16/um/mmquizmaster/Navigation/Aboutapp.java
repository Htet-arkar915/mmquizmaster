package com.team16.um.mmquizmaster.Navigation;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.kyawhtut.FontUtil;
import com.team16.um.mmquizmaster.HomeActivity;
import com.team16.um.mmquizmaster.R;

public class Aboutapp extends AppCompatActivity {
    Toolbar tb;
    TextView tv;
    String str="             ကြ်န္ေတာ္တို႕App ေလးကေတာ့ MM Quizmaser ျဖစ္ပါတယ္ ခင္ဗ်ာ။ကြ်န္ေတာ္တို႕ App ရဲ႕ ရည္ရြယ္ခ်က္ကေတာ့ ျမန္မာနိုင္ငံသား တစ္ဦး အေနနွင့္သိသင့္သိထိုက္ေသာ ဗဟုသုတမ်ားကို ေပ်ာ္ရႊင္စြာ  ေလ့လာနိုင္ရန္အတြက္ျဖစ္ပါတယ္။တစ္စံုတစ္ေယာက္က သိခ်င္တဲ့ ဗဟုသုတမ်ားကို သိခ်င္ေသာအခါ စာအုပ္ေတြ ဝယ္ဖတ္ရတယ္အခ်ိန္ေတြကုန္တယ္ သိခ်င္တဲ့ အေၾကာင္းအရာကလည္း ဘယ္စာအုပ္ထဲမွာ ရွိလဲေသခ်ာမသိဘူး။စာဖတ္ ဝါသနာပါတဲ့ သူေတာင္ စာအၾကာၾကီးဖတ္ရရင္ ပ်င္းလာနိုင္ပါတယ္။"
   +" ထိုသို့ ျပႆနာမ်ားကို MM Quizmaster ကေျဖရွင္းေပးနိုင္ပါတယ္။ကိုယ္သိခ်င္တဲ့ အေၾကာင္းအရာမ်ားကို categories "+"ေသခ်ာခြဲထားေသာေၾကာင့္ လြယ္ကူစြာ သိရွိနိုင္ပါတယ္။Game ဆန္ဆန္ျဖစ္သည့္ အတြက္ ပ်င္းစရာ မလိုပါဘူး။ စိန္ေခၚယွဥ္ျပိဳင္မႈမ်ား ပါသည့္အတြက္ လူငယ္ေတြတြက္ စိတ္အားတက္ၾကြေစပါတယ္။"+ "Level မ်ားသတ္မွတ္ေပးထားသည့္အတြက္ ေလ့လာရာတြင္ ပိုမို၍ စိတ္ဝင္စားစြာ ေလ့လာနိုင္ေသာ Appေလးျဖစ္ပါတယ္။MM Quizmaster ကိုသံုးျခင္းအားျဖင့္  ဗဟုသုတမ်ားတိုးပြားလာျပီး ျပည္ပမွ သိသင့္သိထိုက္ေသာ ဗဟုသုတမ်ားကို ေပ်ာ္ရႊင္စြာ သိရွိနိုင္ပါသည္။";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aboutapp);
        tb=(Toolbar)findViewById(R.id.abouttool);
        tv=(TextView)findViewById(R.id.textabout);
        if (HomeActivity.CheckLanguage(Aboutapp.this)){
            FontUtil fontUtil=new FontUtil();
            tv.setText(fontUtil.zawgyi2unicode(str));
        }else {
            tv.setText(str);
        }

        setSupportActionBar(tb);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        tb.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
