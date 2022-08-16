package com.team16.um.mmquizmaster.Pagers;

import android.app.NotificationManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
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

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.team16.um.mmquizmaster.Adapters.ResultRecycler;
import com.team16.um.mmquizmaster.Model.ChallengeFirebase;
import com.team16.um.mmquizmaster.Model.ResultFirebase;
import com.team16.um.mmquizmaster.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import static android.content.Context.MODE_PRIVATE;

public class Result extends Fragment {
    RecyclerView resultrv;
    RecyclerView.Adapter ResultRecycle;
    ArrayList<ResultFirebase> resultlist;
    Firebase resultfb;
    String userid;
    View view;
    Result act;
    ProgressBar progressBar;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.activity_result,container,false);
        return view;
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);
        NotificationManager mymanager=(NotificationManager)view.getContext().getSystemService(Context.NOTIFICATION_SERVICE);
        mymanager.cancelAll();
        Firebase.setAndroidContext(getContext());
        resultrv=(RecyclerView)view.findViewById(R.id.resultrv);
        resultrv.setLayoutManager(new LinearLayoutManager(view.getContext()));
        progressBar=(ProgressBar)view.findViewById(R.id.resultprogress);
        SharedPreferences sp = getContext().getSharedPreferences("userdata", MODE_PRIVATE);
        userid = sp.getString("userid","");
        Log.d("url",sp.getString("userid",""));
        resultfb = new Firebase("https://challenge-1d2ec.firebaseio.com/");
        resultlist = new ArrayList<ResultFirebase>();
        resultfb.child("ChallengeResult").addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                resultlist.clear();
                produceData(dataSnapshot);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }

    private void produceData(DataSnapshot dataSnapshot) {
        for (DataSnapshot dsh:dataSnapshot.getChildren()){
            ResultFirebase resultinfo=new ResultFirebase();
            if(userid.equals(dsh.getValue(ResultFirebase.class).getChallengeid())||
                    userid.equals(dsh.getValue(ResultFirebase.class).getAcceptid())){

                resultinfo.setAcceptimg(dsh.getValue(ResultFirebase.class).getAcceptimg());
                resultinfo.setChallengeimg(dsh.getValue(ResultFirebase.class).getChallengeimg());
                resultinfo.setAcceptname(dsh.getValue(ResultFirebase.class).getAcceptname());
                resultinfo.setChallengename(dsh.getValue(ResultFirebase.class).getChallengename());
                resultinfo.setAcceptcount(dsh.getValue(ResultFirebase.class).getAcceptcount());
                resultinfo.setChallengecount(dsh.getValue(ResultFirebase.class).getChallengecount());
                resultinfo.setChallengeid(dsh.getValue(ResultFirebase.class).getChallengeid());
                resultinfo.setAcceptid(dsh.getValue(ResultFirebase.class).getAcceptid());
                resultlist.add(resultinfo);
            }
        }
        ResultRecycle=new ResultRecycler(getContext(),resultlist);
        resultrv.setAdapter(ResultRecycle);
        progressBar.setVisibility(View.INVISIBLE);

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.findItem(R.id.search).setVisible(false);
    }
}
