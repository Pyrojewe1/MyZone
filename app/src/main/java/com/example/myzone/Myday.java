package com.example.myzone;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Myday extends AppCompatActivity {
    private long firstTime = 0;
    private String ACTION_NAME = "alarmService";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide(); //隐藏标题栏
        }



        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String sDate = simpleDateFormat.format(new Date());
        Date date = null;
        try {
            date = simpleDateFormat.parse(sDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }


        if(StaticVar.loginDBHelper.queryUpdateTime(StaticVar.user).equals("0")){
            StaticVar.loginDBHelper.update_updateTime(StaticVar.user,simpleDateFormat.format(new Date()));
        }
        else {
            try {
                if(simpleDateFormat.parse(simpleDateFormat.format(new Date())).compareTo(simpleDateFormat.parse(StaticVar.loginDBHelper.queryUpdateTime(StaticVar.user)))>0){
                    try {
                        StaticVar.loginDBHelper.changeMission();
                        Log.e("数据库已被更新","" );
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                   StaticVar.loginDBHelper.update_updateTime(StaticVar.user,simpleDateFormat.format(new Date()));
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }

        }
        Log.e("今天是",simpleDateFormat.format(new Date()) );
        Log.e("date 是",StaticVar.loginDBHelper.queryUpdateTime(StaticVar.user) );

        StaticVar.staticContext = Myday.this;
        StaticVar.fragmentArrayList.add(new HomeFragment(this));
        StaticVar.fragmentArrayList.add(new MyDayFragment());
        StaticVar.fragmentArrayList.add(new Important_Fragment());
        StaticVar.fragmentArrayList.add(new Schedule_Fragment());
        final NoScrollViewPager viewPager = findViewById(R.id.in_viewpager);
        StaticVar.viewPager =viewPager;
        MyPagerAdapter myPagerAdapter = new MyPagerAdapter(getSupportFragmentManager(),StaticVar.fragmentArrayList);
        viewPager.setAdapter(myPagerAdapter);
        StaticVar.viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {/*
                Log.d("onPageselected", "triggered");
               if(position == 0) {

                  StaticVar.fragmentArrayList.clear();
                  StaticVar.fragmentArrayList.add(new HomeFragment(Myday.this));
                  Log.i("size is ",String.valueOf(StaticVar.fragmentArrayList.size()));
                  FragmentManager fragmentManager =getSupportFragmentManager();
                   MyPagerAdapter myPagerAdapter = new MyPagerAdapter(fragmentManager,StaticVar.fragmentArrayList);
                   StaticVar.viewPager.setAdapter(myPagerAdapter);

               }
               */
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    @Override
    public void onBackPressed() {
    if(StaticVar.index != 0 ) {
        StaticVar.index =0;
        StaticVar.viewPager.setCurrentItem(0);
    }
    else {
        long secondTime = System.currentTimeMillis();

        if (secondTime - firstTime < 2000) {
            System.exit(0);
        } else {
            Toast.makeText(getApplicationContext(), "再按一次返回键退出", Toast.LENGTH_SHORT).show();
            firstTime = System.currentTimeMillis();

        }
    }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        StaticVar.fragmentArrayList.clear();
    }











}

