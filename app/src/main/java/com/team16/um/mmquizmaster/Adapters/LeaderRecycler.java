package com.team16.um.mmquizmaster.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.facebook.internal.CollectionMapper;
import com.team16.um.mmquizmaster.Model.UserfirebaseInfo;
import com.team16.um.mmquizmaster.Navigation.Leader_Profile;
import com.team16.um.mmquizmaster.R;

import java.security.AccessControlContext;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Admin on 11/9/2017.
 */

public class LeaderRecycler extends RecyclerView.Adapter <LeaderRecycler.MyHolder> {

    Context con;
    ArrayList<UserfirebaseInfo> leaderlist;
    public static UserfirebaseInfo leaderclickuser;

    public LeaderRecycler(Context context, ArrayList<UserfirebaseInfo> userlist) {

        this.con=context;
        this.leaderlist=userlist;
        Collections.sort(userlist, new Comparator<UserfirebaseInfo>(){
            @Override
            public int compare(UserfirebaseInfo o1, UserfirebaseInfo o2) {
                if (o1.getUserlevel()>o2.getUserlevel()){
                    return -1;
                }else if (o1.getUserlevel()==o2.getUserlevel()&&o1.getUserxp()<o2.getUserxp()) {
                    return 1;
                }else {
                    return 1;
                }
            }
        });

    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(con).inflate(R.layout.leadercustomize,parent,false);
        MyHolder holder=new MyHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final MyHolder holder, final int position) {

        SharedPreferences sp=con.getSharedPreferences("userdata",Context.MODE_PRIVATE);
        holder.level.setText(leaderlist.get(position).getUserlevel()+"");
        holder.name.setText(leaderlist.get(position).getUsername());
        holder.rank.setText(1+position+"");
        if (position!=0){
            holder.firstleader.setVisibility(View.INVISIBLE);
        }else {
            holder.firstleader.setVisibility(View.VISIBLE);
        }
        Glide.with(con).load(leaderlist.get(position).getUserImgurl()).into(holder.leaderimg);
        if (leaderlist.get(position).getUserId().equals(sp.getString("userid",""))){
            holder.myback.setBackgroundResource(R.drawable.leadermyback);
        }else {
            holder.myback.setBackgroundColor(Color.WHITE);
        }
        holder.myback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ActivityOptionsCompat compat=ActivityOptionsCompat.makeSceneTransitionAnimation((Activity) con,holder.myback.findViewById(R.id.mybackinleader),"myImage");
                leaderclickuser=leaderlist.get(position);
                con.startActivity(new Intent(con, Leader_Profile.class),compat.toBundle());
            }
        });

    }

    @Override
    public int getItemCount() {
        return leaderlist.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {

        TextView rank,name,level;
        CircleImageView leaderimg;
        ImageView firstleader;
        LinearLayout myback;

        public MyHolder(View itemView) {

            super(itemView);
            rank=(TextView)itemView.findViewById(R.id.tvrank);
            firstleader=(ImageView) itemView.findViewById(R.id.firstleader);
            name=(TextView)itemView.findViewById(R.id.leadername);
            level=(TextView)itemView.findViewById(R.id.leaderlevel);
            myback=(LinearLayout)itemView.findViewById(R.id.mybackinleader);
            leaderimg=(CircleImageView)itemView.findViewById(R.id.leaderimg);
        }
    }
}
