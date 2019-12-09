package com.example.myzone;

import android.content.Context;
import android.widget.ListView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;
import java.util.Date;

public class StaticVar {
    static String user;
    static Context staticContext;
    static String date;
    static LoginDBHelper loginDBHelper;
    static NoScrollViewPager viewPager;
    static ArrayList<Fragment> fragmentArrayList = new ArrayList<>();
    static ListView toDayMissions;
    static ListView slidingListView;
    static ListView importantList;
    static ListView scheduleList;
    static int index = 0 ;
    static Date dt;
    static int a;
}
