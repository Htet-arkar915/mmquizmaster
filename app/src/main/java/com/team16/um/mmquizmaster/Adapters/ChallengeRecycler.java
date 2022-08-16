package com.team16.um.mmquizmaster.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.balysv.materialripple.MaterialRippleLayout;
import com.bumptech.glide.Glide;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.firebase.client.utilities.Pair;
import com.kyawhtut.FontUtil;
import com.team16.um.mmquizmaster.ChFightActivity.AcceptFight;
import com.team16.um.mmquizmaster.ChFightActivity.User_Profile;
import com.team16.um.mmquizmaster.HomeActivity;
import com.team16.um.mmquizmaster.Model.Catagoryinfo;
import com.team16.um.mmquizmaster.Model.ChSecurationinfo;
import com.team16.um.mmquizmaster.Model.ChallengeFirebase;
import com.team16.um.mmquizmaster.Model.UserfirebaseInfo;
import com.team16.um.mmquizmaster.Pagers.Home;
import com.team16.um.mmquizmaster.R;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Admin on 10/14/2017.
 */

public class ChallengeRecycler extends RecyclerView.Adapter<ChallengeRecycler.MyHolder> {
    Context con;
    private List<UserfirebaseInfo> sUsername;
    ArrayList<UserfirebaseInfo> userlist;
    ArrayList<String> challengstatus;
    ArrayList<Catagoryinfo> catagorylist;
    RecyclerView.Adapter ChRecycle;
    AlertDialog.Builder chdialogbuilder;
    RecyclerView rvchdialog;
    ImageView cancelimg;
    String parent,chstr="ျပိဳင္မယ္";
    public static AlertDialog chdialog;
    public static UserfirebaseInfo clickuser;
    public static UserfirebaseInfo circleimgclickuser;
    String string="ရလဒ္ေစာင့္ပါ";
    FontUtil fontUtil=new FontUtil();

    SharedPreferences shf;

    public ChallengeRecycler(Context context, ArrayList<UserfirebaseInfo> user, ArrayList<String> challengstatus) {
        this.con = context;
        this.userlist = user;
        this.challengstatus = challengstatus;

    }

    public void swapList(ArrayList<UserfirebaseInfo> user){
        this.userlist = user;
        notifyDataSetChanged();
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(con).inflate(R.layout.challengecustomize, parent, false);
        Log.d("Hello", "Hello");
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyHolder holder, final int position) {

        shf = con.getSharedPreferences("userdata", MODE_PRIVATE);

        if(HomeActivity.CheckLanguage(con)) {
            FontUtil fontUtil = new FontUtil();
            String unicode = (String) fontUtil.zawgyi2unicode(chstr);
            holder.chbtn.setText(unicode);
        }else {
            holder.chbtn.setText(chstr);
            holder.chbtn.setBackgroundResource(R.drawable.shadaw_effect);
        }

        if (HomeActivity.CheckLanguage(con)) {
            FontUtil fontUtil = new FontUtil();
            String unicode = (String) fontUtil.zawgyi2unicode(chstr);
            holder.chbtn.setText(unicode);
        } else{
            holder.chbtn.setText(chstr);
        }

        Log.i("USER LIST ::", userlist.size()+"");
        Log.i("USER POSITION ::", ""+position);
        if(challengstatus.contains(shf.getString("userid","")+userlist.get(position).getUserId() + "_1")) {
            holder.chbtn.setEnabled(true);
            holder.chbtn.setBackgroundResource(R.drawable.ch_bg_answer);

            if (HomeActivity.CheckLanguage(con)) {
                holder.chbtn.setText(fontUtil.zawgyi2unicode(string));
            } else {
                holder.chbtn.setText(string);
            }
            holder.chbtn.setEnabled(false);
        }
        else if(challengstatus.contains(shf.getString("userid","")+userlist.get(position).getUserId() + "_2")){
            holder.chbtn.setEnabled(true);
            holder.chbtn.setBackgroundResource(R.drawable.ch_bg_answer);

            if (HomeActivity.CheckLanguage(con)){
                holder.chbtn.setText(fontUtil.zawgyi2unicode(string));
            }else {
                holder.chbtn.setText(string);
            }
            holder.chbtn.setEnabled(false);
        }
        else if (challengstatus.contains(shf.getString("userid","")+userlist.get(position).getUserId() + "_3")) {

            holder.chbtn.setEnabled(true);

            if(HomeActivity.CheckLanguage(con)) {
                FontUtil fontUtil = new FontUtil();
                String unicode = (String) fontUtil.zawgyi2unicode(chstr);
                holder.chbtn.setText(unicode);
            }else {
                holder.chbtn.setText(chstr);
                holder.chbtn.setBackgroundResource(R.drawable.shadaw_effect);
            }
        }

        if (HomeActivity.CheckLanguage(con)) {
            holder.username.setText(userlist.get(position).getUsername());
        }else{
            FontUtil fontUtil=new FontUtil();
            holder.username.setText(fontUtil.zawgyi2unicode(userlist.get(position).getUsername()));
        }
        holder.userlevel.setText("Level : " + userlist.get(position).getUserlevel());
        Glide.with(con).load(userlist.get(position).getUserImgurl()).into(holder.circleImageView);

        holder.chbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                clickuser = userlist.get(position);
                View view = LayoutInflater.from(v.getContext()).inflate(R.layout.challengedialogview, null, false);
                String title="ေခါင္းစဥ္ေရြးရန္";
                TextView dialogtitle=(TextView)view.findViewById(R.id.chdialogtitle);
                if (HomeActivity.CheckLanguage(con)){
                    FontUtil util=new FontUtil();
                    dialogtitle.setText(util.zawgyi2unicode(title));
                }else {
                    dialogtitle.setText(title);
                }
                chdialogbuilder = new AlertDialog.Builder(v.getContext());
                chdialogbuilder.setView(view);
                chdialogbuilder.setCancelable(false);
                chdialog = chdialogbuilder.create();
                chdialog.show();
                cancelimg = (ImageView) view.findViewById(R.id.cancelimg);
                rvchdialog = (RecyclerView) view.findViewById(R.id.rvchallengedialog);
                catagorylist = new ArrayList<>();
                rvchdialog.setLayoutManager(new GridLayoutManager(v.getContext(), 2));
                ChRecycle = new ChCatagoryRecycle(con, catagorylist, parent);
                rvchdialog.setAdapter(ChRecycle);
                cancelimg.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        chdialog.dismiss();
                    }
                });
                prepareCatagory();

            }

        });

        holder.myview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityOptionsCompat compact=ActivityOptionsCompat.makeSceneTransitionAnimation(
                        (Activity) con,holder.myview.findViewById(R.id.chcircleimg),"myImage");
                circleimgclickuser=userlist.get(position);

                Intent inten=new Intent(con, User_Profile.class);

                con.startActivity(inten,compact.toBundle());
            }
        });
    }

    //
    private void animate(View itemView, int position) {
        Animation animation = AnimationUtils.loadAnimation(con, R.anim.slideleft);
        itemView.startAnimation(animation);
    }

    @Override
    public int getItemCount() {
        return userlist.size();
    }

    public void Filter(List<UserfirebaseInfo> username) {
        sUsername = new ArrayList<>();
        sUsername.addAll(username);
        notifyDataSetChanged();


    }

    public class MyHolder extends RecyclerView.ViewHolder {
        CircleImageView circleImageView;
        TextView username, userlevel;
        Button chbtn;
        MaterialRippleLayout myview;

        public MyHolder(View itemView) {
            super(itemView);
            circleImageView = (CircleImageView) itemView.findViewById(R.id.chcircleimg);
            username = (TextView) itemView.findViewById(R.id.chusername);
            userlevel = (TextView) itemView.findViewById(R.id.chtvlevel);
            chbtn = (Button) itemView.findViewById(R.id.chbtn);
            myview=(MaterialRippleLayout)itemView.findViewById(R.id.chripple_effect);
        }

        public void bind(UserfirebaseInfo model) {
            username.setText(model.getUsername());
        }
    }

    private void prepareCatagory() {
        int img[] = {R.drawable.bartaryae, R.drawable.educationimg,
                R.drawable.actiongroupimg, R.drawable.historyimg,
                R.drawable.village, R.drawable.river, R.drawable.sakarpone,
                R.drawable.culture, R.drawable.ahtwe, R.drawable.sarso};
        Catagoryinfo catagory = new Catagoryinfo(img[0], "ဗုဒၶ");
        catagorylist.add(catagory);
        catagory = new Catagoryinfo(img[1], "ပညာေရး");
        catagorylist.add(catagory);
        catagory = new Catagoryinfo(img[2], "နိုင္ငံေရး");
        catagorylist.add(catagory);
        catagory = new Catagoryinfo(img[3], "သမိုင္းေရးရာ");
        catagorylist.add(catagory);
        catagory = new Catagoryinfo(img[4], "ျပည္နယ္နွင့္ျမိဳ့မ်ား");
        catagorylist.add(catagory);
        catagory = new Catagoryinfo(img[5], "ျမစ္ေခ်ာင္းမ်ား");
        catagorylist.add(catagory);
        catagory = new Catagoryinfo(img[6], "စကားပံု");
        catagorylist.add(catagory);
        catagory = new Catagoryinfo(img[7], "လက္မွုပညာ");
        catagorylist.add(catagory);
        catagory = new Catagoryinfo(img[8], "အေထြေထြ");
        catagorylist.add(catagory);
        catagory = new Catagoryinfo(img[9], "ေရွးစာဆိုမ်ား");
        catagorylist.add(catagory);
        ChRecycle.notifyDataSetChanged();
    }
}
