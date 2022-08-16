package com.team16.um.mmquizmaster.Pagers;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by Admin on 10/9/2017.
 */

public class MyPagerAdapter extends FragmentStatePagerAdapter{
    public MyPagerAdapter(FragmentManager supportFragmentManager) {
        super(supportFragmentManager);

    }

    @Override
    public Fragment getItem(int position) {
        Fragment frag=null;
        switch (position){
            case 0:
                frag= new Home();
                break;
            case 1:
                frag=new Challenge();
                break;
            case 2:
                frag=new Accept();
                break;
            case 3:
                frag=new Result();
                break;
        }
        return frag;
    }

    @Override
    public int getCount() {
        return 4;
    }

}
