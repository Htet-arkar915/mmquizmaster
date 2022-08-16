package com.team16.um.mmquizmaster.Pagers;

import android.support.annotation.Nullable;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kyawhtut.FontUtil;
import com.team16.um.mmquizmaster.Adapters.HomeRecycler;
import com.team16.um.mmquizmaster.HomeActivity;
import com.team16.um.mmquizmaster.Model.Catagoryinfo;
import com.team16.um.mmquizmaster.R;

import java.util.ArrayList;

public class Home extends android.support.v4.app.Fragment{
    RecyclerView.Adapter Myrecycle;
    RecyclerView rv;
    ArrayList<Catagoryinfo> catagorylist;
    ArrayList<Catagoryinfo> newcatagorylist;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_home, container, false);
        rv = (RecyclerView) view.findViewById(R.id.homerecycle);
        catagorylist = new ArrayList<>();
        newcatagorylist = new ArrayList<>();
            Myrecycle = new HomeRecycler(getContext(), catagorylist);
        rv.setLayoutManager(new GridLayoutManager(getContext(),2));
        rv.setItemAnimator(new DefaultItemAnimator());
        rv.setAdapter(Myrecycle);
        prepareCatagory();
        return view;
    }

    private void prepareCatagory() {
        int img[]={R.drawable.bartaryae,R.drawable.educationimg,
                R.drawable.actiongroupimg,R.drawable.historyimg,
                R.drawable.village, R.drawable.river,R.drawable.sakarpone,
                R.drawable.culture,R.drawable.ahtwe,R.drawable.sarso};
        Catagoryinfo catagory=new Catagoryinfo(img[0],"ဗုဒၶ");
        catagorylist.add(catagory);
        catagory=new Catagoryinfo(img[1],"ပညာေရး");
        catagorylist.add(catagory);
        catagory=new Catagoryinfo(img[2],"နိုင္ငံေရး");
        catagorylist.add(catagory);
        catagory=new Catagoryinfo(img[3],"သမိုင္းေရးရာ");
        catagorylist.add(catagory);
        catagory=new Catagoryinfo(img[4],"ျပည္နယ္နွင့္ျမိဳ့မ်ား");
        catagorylist.add(catagory);
        catagory=new Catagoryinfo(img[5],"ျမစ္ေခ်ာင္းမ်ား");
        catagorylist.add(catagory);
        catagory=new Catagoryinfo(img[6],"စကားပံု");
        catagorylist.add(catagory);
        catagory=new Catagoryinfo(img[7],"လက္မွုပညာ");
        catagorylist.add(catagory);
        catagory=new Catagoryinfo(img[8],"အေထြေထြ");
        catagorylist.add(catagory);
        catagory=new Catagoryinfo(img[9],"ေရွးစာဆိုမ်ား");
        catagorylist.add(catagory);
        Myrecycle.notifyDataSetChanged();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.findItem(R.id.search).setVisible(false);
    }
}
