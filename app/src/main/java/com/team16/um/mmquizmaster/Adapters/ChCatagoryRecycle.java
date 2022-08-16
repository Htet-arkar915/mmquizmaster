package com.team16.um.mmquizmaster.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.balysv.materialripple.MaterialRippleLayout;
import com.kyawhtut.FontUtil;
import com.team16.um.mmquizmaster.ChFightActivity.FightActionGroup;
import com.team16.um.mmquizmaster.ChFightActivity.FightAhtwehtwe;
import com.team16.um.mmquizmaster.ChFightActivity.FightBartharyae;
import com.team16.um.mmquizmaster.ChFightActivity.FightEducation;
import com.team16.um.mmquizmaster.ChFightActivity.FightHandmade;
import com.team16.um.mmquizmaster.ChFightActivity.FightHistory;
import com.team16.um.mmquizmaster.ChFightActivity.FightNation;
import com.team16.um.mmquizmaster.ChFightActivity.FightRiver_activity;
import com.team16.um.mmquizmaster.ChFightActivity.FightSakarpone;
import com.team16.um.mmquizmaster.ChFightActivity.FightSarSo;
import com.team16.um.mmquizmaster.HomeActivity;
import com.team16.um.mmquizmaster.Model.Catagoryinfo;
import com.team16.um.mmquizmaster.R;

import java.util.ArrayList;

/**
 * Created by Admin on 10/18/2017.
 */

class ChCatagoryRecycle extends RecyclerView.Adapter <ChCatagoryRecycle.MyHolder>{
    Context context;
    ArrayList<Catagoryinfo> catlist;
    String parent;
    FontUtil fontUtil=new FontUtil();
    public ChCatagoryRecycle(Context context, ArrayList<Catagoryinfo> catagorylist,String parent) {
        this.context=context;
        this.catlist=catagorylist;
        this.parent=parent;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.homecatagory,parent,false);
        MyHolder holder=new MyHolder(view);
        return holder;

    }

    @Override
    public void onBindViewHolder(MyHolder holder, final int position) {

        final Catagoryinfo cata= (Catagoryinfo) catlist.get(position);
        holder.chimg.setImageResource(((Catagoryinfo) catlist.get(position)).getImg());
        String titlename=((Catagoryinfo) catlist.get(position)).getCatagory();

        if (HomeActivity.CheckLanguage(context)){
            String unicode= (String) fontUtil.zawgyi2unicode(titlename);
            holder.chtv.setText(unicode);
        }else
            holder.chtv.setText(titlename);
        holder.rip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                switch (position){
                    case 0:
                        Intent i = new Intent(context,FightBartharyae.class);
                        i.putExtra("userprofile",ChallengeRecycler.clickuser);
                        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        context.startActivity(i);
                        ChallengeRecycler.chdialog.dismiss();
                        break;
                    case 1:
                        Intent a = new Intent(context,FightEducation.class);
                        a.putExtra("userprofile",ChallengeRecycler.clickuser);
                        a.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        context.startActivity(a);
                        ChallengeRecycler.chdialog.dismiss();
                        break;
                    case 2:
                        Intent b = new Intent(context,FightActionGroup.class);
                        b.putExtra("userprofile",ChallengeRecycler.clickuser);
                        b.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        context.startActivity(b);
                        ChallengeRecycler.chdialog.dismiss();
                        break;
                    case 3:
                        Intent c = new Intent(context,FightHistory.class);
                        c.putExtra("userprofile",ChallengeRecycler.clickuser);
                        c.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        context.startActivity(c);
                        ChallengeRecycler.chdialog.dismiss();
                        break;
                    case 4:
                        Intent d = new Intent(context,FightNation.class);
                        d.putExtra("userprofile",ChallengeRecycler.clickuser);
                        d.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        context.startActivity(d);
                        ChallengeRecycler.chdialog.dismiss();
                        break;
                    case 5:
                        Intent e = new Intent(context,FightRiver_activity.class);
                        e.putExtra("userprofile",ChallengeRecycler.clickuser);
                        e.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        context.startActivity(e);
                        ChallengeRecycler.chdialog.dismiss();
                        break;
                    case 6:
                        Intent f = new Intent(context,FightSakarpone.class);
                        f.putExtra("userprofile",ChallengeRecycler.clickuser);
                        f.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        context.startActivity(f);
                        ChallengeRecycler.chdialog.dismiss();
                        break;
                    case 7:
                        Intent g = new Intent(context,FightHandmade.class);
                        g.putExtra("userprofile",ChallengeRecycler.clickuser);
                        g.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        context.startActivity(g);
                        ChallengeRecycler.chdialog.dismiss();
                        break;
                    case 8:
                        Intent h = new Intent(context,FightAhtwehtwe.class);
                        h.putExtra("userprofile",ChallengeRecycler.clickuser);
                        h.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        context.startActivity(h);
                        ChallengeRecycler.chdialog.dismiss();
                        break;
                    case 9:
                        Intent j = new Intent(context,FightSarSo.class);
                        j.putExtra("userprofile",ChallengeRecycler.clickuser);
                        j.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        context.startActivity(j);
                        ChallengeRecycler.chdialog.dismiss();
                        break;
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return catlist.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {

        ImageView chimg;
        TextView chtv;
        MaterialRippleLayout rip;

        public MyHolder(View itemView) {

            super(itemView);

            chimg=(ImageView) itemView.findViewById(R.id.catagoryimg);
            chtv=(TextView)itemView.findViewById(R.id.catagoryname);
            rip=(MaterialRippleLayout)itemView.findViewById(R.id.ripple_effect);
        }
    }
}
