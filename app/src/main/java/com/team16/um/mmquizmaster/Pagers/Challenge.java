package com.team16.um.mmquizmaster.Pagers;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
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
import com.kyawhtut.FontUtil;
import com.team16.um.mmquizmaster.Adapters.ChallengeRecycler;
import com.team16.um.mmquizmaster.HomeActivity;
import com.team16.um.mmquizmaster.Model.ChSecurationinfo;
import com.team16.um.mmquizmaster.Model.UserfirebaseInfo;
import com.team16.um.mmquizmaster.R;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class Challenge extends Fragment {
    ChallengeRecycler Myrecycle;
    RecyclerView rv;
    ArrayList<UserfirebaseInfo> userlist;
    ArrayList<String> challengstatus = new ArrayList<>();
    Firebase fb;
    String url;
    View view;
    ProgressBar progressBar;

    SearchView searchView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {


        view = inflater.inflate(R.layout.activity_challenge, container, false);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);
        Firebase.setAndroidContext(getContext());
        rv = (RecyclerView) view.findViewById(R.id.challengerecycler);
        rv.setLayoutManager(new LinearLayoutManager(view.getContext()));
        progressBar = (ProgressBar) view.findViewById(R.id.chprogress);
        SharedPreferences sp = getContext().getSharedPreferences("userdata", MODE_PRIVATE);
        url = sp.getString("userid", "");
        Log.d("url", sp.getString("userid", ""));
        fb = new Firebase("https://userinformation-1825d.firebaseio.com/");
        userlist = new ArrayList<UserfirebaseInfo>();
        fb.child("Userdata").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                userlist.clear();
                produceData(dataSnapshot);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }

    //Produce data from firebase
    private void produceData(DataSnapshot dataSnapshot) {

        for (DataSnapshot dsh : dataSnapshot.getChildren()) {

            UserfirebaseInfo ufbinfo = new UserfirebaseInfo();
            Log.d("Enter", dsh.getValue(UserfirebaseInfo.class).getUserId());
            if (!url.equals(dsh.getValue(UserfirebaseInfo.class).getUserId())) {

                ufbinfo.setUsername(dsh.getValue(UserfirebaseInfo.class).getUsername());
                ufbinfo.setUserId(dsh.getValue(UserfirebaseInfo.class).getUserId());
                ufbinfo.setUserImgurl(dsh.getValue(UserfirebaseInfo.class).getUserImgurl());
                ufbinfo.setUserlevel(dsh.getValue(UserfirebaseInfo.class).getUserlevel());
                ufbinfo.setUsercoin(dsh.getValue(UserfirebaseInfo.class).getUsercoin());
                Log.d("url", dsh.getValue(UserfirebaseInfo.class).getUserImgurl());
                userlist.add(ufbinfo);

            }
        }

        Firebase checkchallenge = new Firebase("https://challenge-1d2ec.firebaseio.com/");
        checkchallenge.child("ChSecuration").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                CheckSecuration(dataSnapshot);
            }

            private void CheckSecuration(DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists()) {
                    challengstatus.clear();
                    for (DataSnapshot dsh : dataSnapshot.getChildren()) {
                        challengstatus.add(dsh.getValue(ChSecurationinfo.class).getMainparent() + "_" + dsh.getValue(ChSecurationinfo.class).getSecurationnum());
                    }
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        Log.d("USER size", userlist.size() + "size");
        Myrecycle = new ChallengeRecycler(getActivity(), userlist, challengstatus);
        rv.setAdapter(Myrecycle);
        progressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        menu.findItem(R.id.search).setVisible(true);
        Log.i("Size : ", menu.findItem(R.id.search) + "");
        searchView = (SearchView) menu.findItem(R.id.search).getActionView();

        if (userlist.size()==0){

        menu.findItem(R.id.search).setVisible(false);
        }
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;

            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(!newText.equals("")) {

                    final ArrayList<UserfirebaseInfo> userN = (ArrayList<UserfirebaseInfo>) filter(userlist, newText);
                    Myrecycle.swapList(userN);

                }else{
                    Log.d("USER size", userlist.size() + "size");
                    Myrecycle.swapList(userlist);
                }
                return false;
            }
            
        });

        super.onCreateOptionsMenu(menu, inflater);

    }

    public void refreshUserList(){
        if(userlist != null) {
            if (userlist.size() > 0)
                Myrecycle.swapList(userlist);
        }
    }

    private List<UserfirebaseInfo> filter(ArrayList<UserfirebaseInfo> userlist, String query) {
        query = query.toLowerCase();
        final List<UserfirebaseInfo> filterModeList = new ArrayList<>();
        for (UserfirebaseInfo model : userlist) {
            final String text = model.getUsername().toLowerCase();
            if (text.contains(query.toLowerCase())) {
                filterModeList.add(model);
            }
        }
        Log.i("Query : ",query+" "+filterModeList.size());
        return filterModeList;
    }

    @Override
    public void onResume() {
        super.onResume();

        Log.d("RESUME USER size", userlist.size() + "size");
        if(userlist.size() > 0)
            Myrecycle.swapList(userlist);
    }
}

