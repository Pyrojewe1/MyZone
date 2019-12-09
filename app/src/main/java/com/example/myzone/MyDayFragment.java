package com.example.myzone;

import android.app.ActionBar;
import android.app.DatePickerDialog;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class MyDayFragment extends Fragment {
    private PopupWindow popupWindow;
    Calendar ca = Calendar.getInstance();
    int  mYear = ca.get(Calendar.YEAR);
    int  mMonth = ca.get(Calendar.MONTH);
    int  mDay = ca.get(Calendar.DAY_OF_MONTH);
    String dateChoose;
    String nowdate =  mYear+"-"+(mMonth+1) + "-" + mDay;
    ListView listView1;

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        StaticVar.toDayMissions =null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.todo, container,false);
        ListView missionListView = view.findViewById(R.id.listview);
        StaticVar.slidingListView = missionListView;
        ArrayList<Mission> missionArrayList = StaticVar.loginDBHelper.queryMission(StaticVar.user);
        SlidingDrawer_Adapter slidingDrawer_adapter = new SlidingDrawer_Adapter(StaticVar.staticContext, missionArrayList);
        ArrayList<Mission> todayMissionList = StaticVar.loginDBHelper.queryTodayMission(StaticVar.user);
         listView1 = view.findViewById(R.id.listview1);
        StaticVar.toDayMissions =listView1;
        MissionAdapter missionAdapter = new MissionAdapter(StaticVar.staticContext, todayMissionList);
        missionListView.setAdapter(slidingDrawer_adapter);
        listView1.setAdapter(missionAdapter);
        TextView time =view.findViewById(R.id.tv_time1);
        Calendar calendar = Calendar.getInstance();
        int month = calendar.get(Calendar.MONTH)+1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        time.setText(month+"月"+day+"日");
        Button addmission = view.findViewById(R.id.add);
        addmission.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                showAnimation();
            }
        });
        /*Button button = view.findViewById(R.id.button1);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //showAnimation();
            }
        });*/
        return view;
    }

    private void showAnimation() {

        LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View vPopupWindow = inflater.inflate(R.layout.myday_add_mission, null, false);//引入弹窗布局
        popupWindow = new PopupWindow(vPopupWindow, ActionBar.LayoutParams.MATCH_PARENT,ActionBar.LayoutParams.WRAP_CONTENT , true);
        popupWindow.setAnimationStyle(R.style.popwindow_anim_style);
        final View parentView = LayoutInflater.from(getContext()).inflate(R.layout.myday_add_mission, null);
        popupWindow.showAtLocation(vPopupWindow, Gravity.BOTTOM, 0, 0);
        LinearLayout date = vPopupWindow.findViewById(R.id.date);
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                mYear = year;
                                mMonth = month;
                                mDay = dayOfMonth;
                                dateChoose =  year+"-"+(month+1) + "-" + dayOfMonth;
                                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                                try {
                                    if(simpleDateFormat.parse(simpleDateFormat.format(new Date())).compareTo(simpleDateFormat.parse(dateChoose))>0){
                                        dateChoose=nowdate;
                                        Toast.makeText(StaticVar.staticContext,"截止日期应为今日及以后的日期，截止日期以置为今天",Toast.LENGTH_LONG).show();
                                    }
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                                TextView textView =  vPopupWindow.findViewById(R.id.text);
                                textView.setText(dateChoose);
                            }
                        },
                        mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        Button addmission2 = vPopupWindow.findViewById(R.id.addMission) ;
        addmission2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText editText =vPopupWindow.findViewById(R.id.mission_dowhat);
                String dowhat = editText.getText().toString();
                Mission mission = new Mission();
                mission.setDoWhat(dowhat);
                mission.setToday(true);
                mission.setImportant(false);
                mission.setComplete(false);
                mission.setFinishTime(dateChoose);
                mission.setStartTime(nowdate);
                mission.setOwner(StaticVar.user);
                mission.setRemarks("未填写");
                StaticVar.loginDBHelper.insertMission(mission);
                dateChoose = nowdate;
                SlidingDrawer_Adapter slidingDrawer_adapter1 = new SlidingDrawer_Adapter(StaticVar.staticContext,StaticVar.loginDBHelper.queryMission(StaticVar.user));
                StaticVar.slidingListView.setAdapter(slidingDrawer_adapter1);
                MissionAdapter missionAdapter = new MissionAdapter(StaticVar.staticContext,StaticVar.loginDBHelper.queryTodayMission(StaticVar.user));
                listView1.setAdapter(missionAdapter);
                Toast.makeText(getContext(),"添加任务成功",Toast.LENGTH_LONG).show();
            }
        });
    }

    /*private void addBackground() {
        // 设置背景颜色变暗
        WindowManager.LayoutParams lp =getActivity().getWindow().getAttributes();
        lp.alpha = 0.7f;//调节透明度
        getActivity().getWindow().setAttributes(lp);
        //dismiss时恢复原样
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {

            @Override
            public void onDismiss() {
                WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
                lp.alpha = 1f;
                getActivity().getWindow().setAttributes(lp);
            }
        });
    }*/


    public String returnS(){
        return "Myday Fragment";
    }

}