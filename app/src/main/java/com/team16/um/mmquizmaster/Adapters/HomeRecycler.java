package com.team16.um.mmquizmaster.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.balysv.materialripple.MaterialRippleLayout;
import com.kyawhtut.FontUtil;
import com.team16.um.mmquizmaster.Activity.ActionGroup;
import com.team16.um.mmquizmaster.Activity.Ahtwehtwe;
import com.team16.um.mmquizmaster.Activity.BartharYae;
import com.team16.um.mmquizmaster.Activity.EducationEx;
import com.team16.um.mmquizmaster.Activity.SarSo;
import com.team16.um.mmquizmaster.Activity.Nation_activity;
import com.team16.um.mmquizmaster.Activity.River_activity;
import com.team16.um.mmquizmaster.Activity.HistoryEx;
import com.team16.um.mmquizmaster.Activity.Sakarpone;
import com.team16.um.mmquizmaster.Activity.Handmade_activity;
import com.team16.um.mmquizmaster.HomeActivity;
import com.team16.um.mmquizmaster.Model.Catagoryinfo;
import com.team16.um.mmquizmaster.R;

import java.util.ArrayList;

/**
 * Created by Admin on 10/10/2017.
 */

public class HomeRecycler extends RecyclerView.Adapter <HomeRecycler.MyHolder> {
    Context con;
    ArrayList catagorylist;
    int lastPosition=-1;
    public HomeRecycler(Context context, ArrayList<Catagoryinfo> catagorymain) {
        this.catagorylist=catagorymain;
        this.con=context;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(con).inflate(R.layout.homecatagory,parent,false);
        MyHolder holder=new MyHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyHolder holder, final int position) {

        animte(holder.itemView,position);

        final Catagoryinfo cata= (Catagoryinfo) catagorylist.get(position);
        holder.img.setImageResource(((Catagoryinfo) catagorylist.get(position)).getImg());
        FontUtil fontUtil=new FontUtil();
        String titlename=((Catagoryinfo) catagorylist.get(position)).getCatagory();

        if (HomeActivity.CheckLanguage(con)){
            String unicode= (String) fontUtil.zawgyi2unicode(titlename);
            holder.tv.setText(unicode);
        }else
            holder.tv.setText(titlename);
        holder.rip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                switch (position){
                    case 0:
                        Intent gintent=new Intent(v.getContext(), BartharYae.class);
                        v.getContext().startActivity(gintent);
                        break;
                    case 1:
                        Intent nintent=new Intent(v.getContext(), EducationEx.class);
                        v.getContext().startActivity(nintent);
                        break;
                    case 2:
                        Intent hintent=new Intent(v.getContext(), ActionGroup.class);
                        v.getContext().startActivity(hintent);
                        break;
                    case 3:
                        Intent eintent=new Intent(v.getContext(), HistoryEx.class);
                        v.getContext().startActivity(eintent);
                        break;
                    case 4:
                        Intent sintent=new Intent(v.getContext(), Nation_activity.class);
                        v.getContext().startActivity(sintent);
                        break;
                    case 5:
                        Intent bintent=new Intent(v.getContext(), River_activity.class);
                        v.getContext().startActivity(bintent);
                        break;
                    case 6:
                        Intent aintent=new Intent(v.getContext(), Sakarpone.class);
                        v.getContext().startActivity(aintent);
                        break;
                    case 7:
                        Intent nyanintent=new Intent(v.getContext(), Handmade_activity.class);
                        v.getContext().startActivity(nyanintent);
                        break;
                    case 8:
                        v.getContext().startActivity(new Intent(v.getContext(),Ahtwehtwe.class));
                        break;
                    case 9:
                        v.getContext().startActivity(new Intent(v.getContext(), SarSo.class));
                        break;
                }
            }
        });

    }

    private void animte(View itemView, int position) {

        Animation animation= AnimationUtils.loadAnimation(con,R.anim.slideleft);
        itemView.startAnimation(animation);
        lastPosition = position;
    }

    @Override
    public int getItemCount() {
        return catagorylist.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {

        ImageView img;
        TextView tv;
        MaterialRippleLayout rip;
        public MyHolder(View itemView) {

            super(itemView);

            img=(ImageView) itemView.findViewById(R.id.catagoryimg);
            tv=(TextView)itemView.findViewById(R.id.catagoryname);
            rip=(MaterialRippleLayout)itemView.findViewById(R.id.ripple_effect);

        }
    }
}
