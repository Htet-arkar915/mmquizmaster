package com.team16.um.mmquizmaster.Adapters;

import android.app.Activity;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.balysv.materialripple.MaterialRippleLayout;
import com.bumptech.glide.Glide;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.team16.um.mmquizmaster.ChFightActivity.AcceptFight;
import com.team16.um.mmquizmaster.ChFightActivity.Accept_profile;
import com.team16.um.mmquizmaster.HomeActivity;
import com.team16.um.mmquizmaster.Model.ChSecurationinfo;
import com.team16.um.mmquizmaster.Model.ChallengeFirebase;
import com.team16.um.mmquizmaster.R;

import java.util.ArrayList;
import java.util.Collections;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Admin on 10/23/2017.
 */

public class AcceptRecycler extends RecyclerView.Adapter <AcceptRecycler.MyHolder>{
    Firebase firebase;
    Context con;
    String parentacc;
    ArrayList<ChallengeFirebase> acceptlist;
    public static String acceptprofileid;
    public static String accnameprofile,acceprofileuserimg;
    public AcceptRecycler(Context context, ArrayList<ChallengeFirebase> acceptlist) {
        this.con=context;
        this.acceptlist=acceptlist;
        Collections.reverse(acceptlist);
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(con).inflate(R.layout.acceptcustomize,parent,false);
        MyHolder holder=new MyHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final MyHolder holder, final int position) {

        if (!HomeActivity.haveNetworkConnection(con)){
            holder.itemView.setVisibility(View.INVISIBLE);
        }
        Firebase.setAndroidContext(con);

        firebase=new Firebase("https://challenge-1d2ec.firebaseio.com/");
        final ChallengeFirebase chfbinfo=new ChallengeFirebase();
        Glide.with(con).load(acceptlist.get(position).getUserimg1()).into(holder.chuserimg);
        holder.tvname.setText(acceptlist.get(position).getUsername1());
        holder.acclevel.setText("Level : "+acceptlist.get(position).getLevel1());
        holder.rejectch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NotificationManager mymanager=(NotificationManager)con.getSystemService(Context.NOTIFICATION_SERVICE);
                mymanager.cancelAll();

                firebase.child("Challenge").child(acceptlist.get(position).getParent()).child("userid2").removeValue();
                firebase.child("ChSecuration").child(acceptlist.get(position).getParent()).child("securationnum").setValue(3);

            }
        });
        holder.acceptch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NotificationManager mymanager=(NotificationManager)con.getSystemService(Context.NOTIFICATION_SERVICE);
                mymanager.cancelAll();
                ConnectivityManager cm=(ConnectivityManager)con.getSystemService(con.CONNECTIVITY_SERVICE);
                if (cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState()==NetworkInfo.State.CONNECTED||
                        cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState()== NetworkInfo.State.CONNECTED){
                    firebase.child("ChSecuration").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            Clicktocheck(dataSnapshot);
                        }

                        @Override
                        public void onCancelled(FirebaseError firebaseError) {

                        }
                    });
                }else {
                    Toast.makeText(con, "Check Internet", Toast.LENGTH_SHORT).show();
                }
            }

            private void Clicktocheck(DataSnapshot dataSnapshot) {

                for (DataSnapshot dsh:dataSnapshot.getChildren()){
                    if (acceptlist.get(position).getParent().equals(dsh.getValue(ChSecurationinfo.class).getMainparent())
                            &&dsh.getValue(ChSecurationinfo.class).getSecurationnum()==3){
                        Toast.makeText(con, acceptlist.get(position).getUsername1()+"is cancel challenge.", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        parentacc=acceptlist.get(position).getParent();
                        Intent intent=new Intent(con, AcceptFight.class).putExtra("parent",parentacc);

                        firebase.child("ChSecuration").child(acceptlist.get(position).getParent()).child("securationnum").setValue(2);
                        con.startActivity(intent);
                    }
                }
            }
        });
        holder.myview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityOptionsCompat compat=ActivityOptionsCompat.makeSceneTransitionAnimation(
                        (Activity) con,holder.myview.findViewById(R.id.challenger),"myImage");
                acceptprofileid=acceptlist.get(position).getUserid1();
                acceprofileuserimg=acceptlist.get(position).getUserimg1();
                accnameprofile=acceptlist.get(position).getUsername1();
                con.startActivity(new Intent(con,Accept_profile.class),compat.toBundle());
                ((Activity) con).finish();
            }
        });

    }

//    private void animate(View itemView, int position) {
//        Animation animation= AnimationUtils.loadAnimation(con,R.anim.slideleft);
//        itemView.startAnimation(animation);
//    }

    @Override
    public int getItemCount() {
        return acceptlist.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {

        CircleImageView chuserimg;
        TextView acclevel;
        TextView tvname;
        ImageView rejectch,acceptch;
        MaterialRippleLayout myview;

        public MyHolder(View itemView) {

            super(itemView);

            chuserimg=(CircleImageView)itemView.findViewById(R.id.challenger);
            acclevel=(TextView)itemView.findViewById(R.id.lvacc);
            tvname=(TextView)itemView.findViewById(R.id.nameacc);
            myview=(MaterialRippleLayout)itemView.findViewById(R.id.acceptripple_effect);
            rejectch=(ImageView)itemView.findViewById(R.id.cancelacc);
            acceptch=(ImageView)itemView.findViewById(R.id.recieveacc);
        }
    }

}
