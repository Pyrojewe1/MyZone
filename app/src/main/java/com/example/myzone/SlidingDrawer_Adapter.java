package com.example.myzone;

import android.content.Context;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicReference;

public class SlidingDrawer_Adapter extends BaseAdapter {
    private Context context;
    private ArrayList<Mission> missionArrayList;
    public SlidingDrawer_Adapter(Context context, ArrayList<Mission> missionArrayList) {
        this.context = context;
        this.missionArrayList =missionArrayList;

    }

    @Override
    public int getCount() {
        return missionArrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(final int i, View convertView, ViewGroup viewGroup) {
        View view = null;
        if (convertView != null) {
            view = convertView;
        } else
            view = View.inflate(context, R.layout.item2, null);
        TextView dowhat = view.findViewById(R.id.dowhat);
        Log.i("iiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiii",String.valueOf(missionArrayList.size()));
        dowhat.setText(missionArrayList.get(i).getDoWhat());
        TextView finishDate = view.findViewById(R.id.finishDate);
        finishDate.setText(missionArrayList.get(i).getFinishTime());
        ImageView addToMyday = view.findViewById(R.id.addToMyday);
        addToMyday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                missionArrayList.get(i).setToday(true);
                StaticVar.loginDBHelper.updateMission(missionArrayList.get(i));
                MissionAdapter missionAdapter =new MissionAdapter(StaticVar.staticContext,StaticVar.loginDBHelper.queryTodayMission(StaticVar.user));
                StaticVar.toDayMissions.setAdapter(missionAdapter);
                Toast.makeText(StaticVar.staticContext,"添加成功",Toast.LENGTH_LONG).show();
            }
        });

        return view;
    }
}
