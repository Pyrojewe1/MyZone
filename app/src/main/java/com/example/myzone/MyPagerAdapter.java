package com.example.myzone;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.PagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class MyPagerAdapter extends FragmentPagerAdapter {
    private ArrayList<Fragment> fragmentArrayList;
    private FragmentManager fm;

    public MyPagerAdapter(FragmentManager fm,ArrayList<Fragment> fragmentArrayList) {
        super(fm);
        this.fragmentArrayList=fragmentArrayList;
        this.fm = fm;
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentArrayList.get(position);
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }




    @Override
    public int getCount() {
        return fragmentArrayList.size();
    }

    /*@Override

    public Object instantiateItem(ViewGroup container, int position) {
        Fragment fragment = (Fragment) super.instantiateItem(container, position);
        if(position==1) {
            String fragmentTag = fragment.getTag();
            FragmentTransaction ft = fm.beginTransaction();
            ft.remove(fragment);
            fragment = StaticVar.fragmentArrayList.get(position);
            ft.add(container.getId(), fragment, fragmentTag);
            ft.attach(fragment);
            ft.commit();
        }
        return fragment;
    }

     */
}