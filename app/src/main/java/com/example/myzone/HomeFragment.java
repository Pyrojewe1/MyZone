package com.example.myzone;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.util.ArrayList;

public class HomeFragment extends Fragment
{
    private Myday myday;

    public HomeFragment(Myday myday)
    {
        this.myday = myday;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.home1, container,false);

        Button button = view.findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StaticVar.viewPager.setCurrentItem(1);
                StaticVar.index =1;
            }
        });

        Button important = view.findViewById(R.id.button2);
        important.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StaticVar.index =1;
                StaticVar.viewPager.setCurrentItem(2);
                Log.d("asdfsadf", Integer.toString(StaticVar.fragmentArrayList.size()));
            }
        });
        Button schedule = view.findViewById(R.id.button3);
        schedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StaticVar.index =1;
                StaticVar.viewPager.setCurrentItem(3);
                Log.d("asdfsadf", Integer.toString(StaticVar.fragmentArrayList.size()));
            }
        });
        //StaticVar.fragmentArrayList.add(new MyDayFragment());
        //MyPagerAdapter myPagerAdapter = new MyPagerAdapter(getActivity().getSupportFragmentManager(),StaticVar.fragmentArrayList);
        //StaticVar.viewPager.setAdapter(myPagerAdapter);
        return view;
    }
}
