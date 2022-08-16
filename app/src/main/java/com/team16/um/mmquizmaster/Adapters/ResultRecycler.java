package com.team16.um.mmquizmaster.Adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.team16.um.mmquizmaster.Model.ResultFirebase;
import com.team16.um.mmquizmaster.Model.UserLevelfirebase;
import com.team16.um.mmquizmaster.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Admin on 10/24/2017.
 */

public class ResultRecycler extends RecyclerView.Adapter <ResultRecycler.MyHolder>{

    Context con;
    ArrayList<ResultFirebase> resultlist;
    String myid;
    Firebase userlevel;
    UserLevelfirebase ufb;
    public static ResultFirebase myendresut;

    public ResultRecycler(Context context, ArrayList<ResultFirebase> resultlist) {

        this.con=context;
        this.resultlist=resultlist;
        Collections.reverse(resultlist);
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(con).inflate(R.layout.resultcustomize,parent,false);
        MyHolder holder=new MyHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyHolder holder, int position) {


        Firebase.setAndroidContext(con);
        ufb=new UserLevelfirebase();
        userlevel=new Firebase("https://userinformation-1825d.firebaseio.com/");
        SharedPreferences sp=con.getSharedPreferences("userdata",con.MODE_PRIVATE);
        myid=sp.getString("userid","");

        if (myid.equals(resultlist.get(position).getChallengeid())){

            myendresut=new ResultFirebase();
            myendresut=resultlist.get(0);
            Glide.with(con).load(resultlist.get(position).getChallengeimg()).into(holder.myphoto);
            Glide.with(con).load(resultlist.get(position).getAcceptimg()).into(holder.chphoto);
            holder.myname.setText(resultlist.get(position).getChallengename());
            holder.chname.setText(resultlist.get(position).getAcceptname());
            holder.showscore.setText(resultlist.get(position).getChallengecount()+"    :    "+
                    resultlist.get(position).getAcceptcount());
            if (resultlist.get(position).getChallengecount()<resultlist.get(position).getAcceptcount()){
                holder.resultlayout.setBackgroundResource(R.drawable.resultloseback);
            }else
            if (resultlist.get(position).getChallengecount()==resultlist.get(position).getAcceptcount())
            {
                holder.resultlayout.setBackgroundResource(R.drawable.drawback);
            }else {
                holder.resultlayout.setBackgroundResource(R.drawable.resultwinback);
            }
        }else if (myid.equals(resultlist.get(position).getAcceptid())){
            Glide.with(con).load(resultlist.get(position).getChallengeimg()).into(holder.chphoto);
            Glide.with(con).load(resultlist.get(position).getAcceptimg()).into(holder.myphoto);
            holder.chname.setText(resultlist.get(position).getChallengename());
            holder.myname.setText(resultlist.get(position).getAcceptname());
            holder.showscore.setText(resultlist.get(position).getAcceptcount()+"    :    "+
                    resultlist.get(position).getChallengecount());
            if (resultlist.get(position).getChallengecount()>resultlist.get(position).getAcceptcount()){
                holder.resultlayout.setBackgroundResource(R.drawable.resultloseback);
            }else
            if (resultlist.get(position).getChallengecount()==resultlist.get(position).getAcceptcount())
            {
                holder.resultlayout.setBackgroundResource(R.drawable.drawback);
            }else {
                holder.resultlayout.setBackgroundResource(R.drawable.resultwinback);

            }
        }
    }

//    private void animate(View itemView, int position) {
//        Animation animation= AnimationUtils.loadAnimation(con,R.anim.slideleft);
//        itemView.startAnimation(animation);
//    }


    @Override
    public int getItemCount() {
        return resultlist.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {

        CircleImageView myphoto,chphoto;
        TextView myname,chname,showscore;
        RelativeLayout resultlayout;

        public MyHolder(View itemView) {
            super(itemView);

            myphoto=(CircleImageView)itemView.findViewById(R.id.myphoto);
            chphoto=(CircleImageView)itemView.findViewById(R.id.chphoto);
            myname=(TextView) itemView.findViewById(R.id.myresultchname);
            chname=(TextView)itemView.findViewById(R.id.resultchname);
            showscore=(TextView)itemView.findViewById(R.id.resultshowmark);
            resultlayout=(RelativeLayout)itemView.findViewById(R.id.resultcardback);
        }
    }
}
