package com.team16.um.mmquizmaster.Pagers;

import android.app.Activity;
import android.app.NotificationManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.kyawhtut.FontUtil;
import com.team16.um.mmquizmaster.Adapters.AcceptRecycler;
import com.team16.um.mmquizmaster.HomeActivity;
import com.team16.um.mmquizmaster.Model.ChallengeFirebase;
import com.team16.um.mmquizmaster.R;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class Accept extends android.support.v4.app.Fragment{
    RecyclerView accrv;
    RecyclerView.Adapter AcceptRecycle;
    ArrayList<ChallengeFirebase> acceptlist;
    Firebase accfb;
    String url;
    TextView texthave;
    View view;
    Accept act;
    String str="စိန္ေခၚမွဳမ်ား မရွိေသးပါ";
    ProgressBar progressBar;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
         view=inflater.inflate(R.layout.activity_accept,container,false);
        return view;
    }

   @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);
       Firebase.setAndroidContext(getContext());
       //HomeActivity.hometile.setVisibility(View.VISIBLE);
        accrv=(RecyclerView)view.findViewById(R.id.accrv);
       texthave=(TextView)view.findViewById(R.id.havetxt);
       if (HomeActivity.CheckLanguage(view.getContext())){

           FontUtil util=new FontUtil();
           texthave.setText(util.zawgyi2unicode(str));
       }else {

           texthave.setText(str);
       }
       //texthave.setVisibility(View.INVISIBLE);
        accrv.setLayoutManager(new LinearLayoutManager(view.getContext()));
        progressBar=(ProgressBar)view.findViewById(R.id.accprogress);
       if (!HomeActivity.haveNetworkConnection(view.getContext())){

           progressBar.setVisibility(View.VISIBLE);
           accrv.setVisibility(View.INVISIBLE);
       }else {
           progressBar.setVisibility(View.INVISIBLE);
           accrv.setVisibility(View.VISIBLE);
       }
        SharedPreferences sp = getContext().getSharedPreferences("userdata", MODE_PRIVATE);
        url = sp.getString("userid","");
        Log.d("url",sp.getString("userid",""));
        accfb = new Firebase("https://challenge-1d2ec.firebaseio.com/");
        acceptlist = new ArrayList<ChallengeFirebase>();
       act=Accept.this;
        accfb.child("Challenge").addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                acceptlist.clear();
                produceData(dataSnapshot);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }
    //Produce data from firebase
    private void produceData(DataSnapshot dataSnapshot) {

        for (DataSnapshot dsh:dataSnapshot.getChildren()){

            ChallengeFirebase acceptinfo=new ChallengeFirebase();
           // Log.d("Enter",dsh.getValue(ChallengeFirebase.class).getUserId());
            if(url.equals(dsh.getValue(ChallengeFirebase.class).getUserid2())){

                acceptinfo.setUserimg1(dsh.getValue(ChallengeFirebase.class).getUserimg1());
                acceptinfo.setUsername1(dsh.getValue(ChallengeFirebase.class).getUsername1());
                acceptinfo.setLevel1(dsh.getValue(ChallengeFirebase.class).getLevel1());
                acceptinfo.setParent(dsh.getValue(ChallengeFirebase.class).getParent());
                acceptinfo.setUserid1(dsh.getValue(ChallengeFirebase.class).getUserid1());
                acceptinfo.setUserid2(dsh.getValue(ChallengeFirebase.class).getUserid2());
                acceptlist.add(acceptinfo);
            }
        }
        Log.d("size",acceptlist.size()+"size");
        AcceptRecycle=new AcceptRecycler(getContext(),acceptlist);
        accrv.setAdapter(AcceptRecycle);
        progressBar.setVisibility(View.INVISIBLE);
        texthave.setVisibility(View.VISIBLE);
        if(acceptlist.size()!=0){
            texthave.setVisibility(View.INVISIBLE);
        }
        if (!HomeActivity.haveNetworkConnection(view.getContext())){
            progressBar.setVisibility(View.VISIBLE);
            accrv.setVisibility(View.INVISIBLE);
        }else {
            progressBar.setVisibility(View.INVISIBLE);
            accrv.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.findItem(R.id.search).setVisible(false);
    }
}
