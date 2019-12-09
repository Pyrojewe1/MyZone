package com.example.myzone;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.provider.ContactsContract;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.TouchDelegate;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicReference;

public class MissionAdapter extends BaseAdapter {
    PopupWindow popupWindow;
    private Context context;
    private ArrayList<Mission> missionArrayList;
    public MissionAdapter(Context context, ArrayList<Mission> missionArrayList) {
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
            view = View.inflate(context, R.layout.item, null);
        TextView dowhat = view.findViewById(R.id.dowhat);
        Log.i("iiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiii",String.valueOf(missionArrayList.size()));
        dowhat.setText(missionArrayList.get(i).getDoWhat());


        final ImageView finish = view.findViewById(R.id.finish);
        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(missionArrayList.get(i).isComplete()){
                    missionArrayList.get(i).setComplete(false);
                    Log.i("已更新", missionArrayList.get(i).getDoWhat()+String.valueOf( missionArrayList.get(i).isComplete()));
                    StaticVar.loginDBHelper.updateMission(missionArrayList.get(i));
                    finish.setImageResource(R.drawable.no_complete);
                }
                else {
                    missionArrayList.get(i).setComplete(true);
                    Log.i("已更新", missionArrayList.get(i).getDoWhat()+String.valueOf( missionArrayList.get(i).isComplete()));
                    StaticVar.loginDBHelper.updateMission(  missionArrayList.get(i));
                    finish.setImageResource(R.drawable.complete);
                }
            }
        });

        final ImageView important = view.findViewById(R.id.important);
        important.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(missionArrayList.get(i).isImportant()){
                    missionArrayList.get(i).setImportant(false);
                    StaticVar.loginDBHelper.updateMission(  missionArrayList.get(i));
                    important.setImageResource(R.drawable.not_important);
                }
                else{
                    missionArrayList.get(i).setImportant(true);
                    StaticVar.loginDBHelper.updateMission(  missionArrayList.get(i));
                    important.setImageResource(R.drawable.important);
                }
            }
        });
        Log.i(missionArrayList.get(i).getDoWhat(),String.valueOf(missionArrayList.get(i).isComplete()));

        if( missionArrayList.get(i).isComplete()) {
            finish.setImageResource(R.drawable.complete);
        }


        if(missionArrayList.get(i).isImportant()){
            important.setImageResource(R.drawable.important);
        }
        else
            important.setImageResource(R.drawable.not_important);


        dowhat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater inflater = (LayoutInflater)StaticVar.staticContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                final View vPopupWindow = inflater.inflate(R.layout.mission_datails, null, false);//引入弹窗布局
                final ImageView important1 = vPopupWindow.findViewById(R.id.important);
                ImageView delete = vPopupWindow.findViewById(R.id.delete);
                delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        AlertDialog alertDialog = new AlertDialog.Builder(StaticVar.staticContext)
                                .setTitle("删除任务")
                                .setMessage("确认删除任务")
                                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int ii) {
                                        StaticVar.loginDBHelper.deleteMission(missionArrayList.get(i).getId());
                                        SlidingDrawer_Adapter slidingDrawer_adapter1 = new SlidingDrawer_Adapter(StaticVar.staticContext,StaticVar.loginDBHelper.queryMission(StaticVar.user));
                                        StaticVar.slidingListView.setAdapter(slidingDrawer_adapter1);
                                        Toast.makeText(StaticVar.staticContext,"删除成功",Toast.LENGTH_LONG).show();
                                    }
                                })
                                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int ii) {
                                        return;
                                    }
                                }).create();
                        alertDialog.show();

                    }
                });
                TextView start = vPopupWindow.findViewById(R.id.start);
                TextView end = vPopupWindow.findViewById(R.id.end);
                start.setText("开始时间"+missionArrayList.get(i).getStartTime());
                end.setText("截止时间"+missionArrayList.get(i).getFinishTime());


                TextView textView = vPopupWindow.findViewById(R.id.dowhat);
                textView.setText(missionArrayList.get(i).getDoWhat());
                if(missionArrayList.get(i).isImportant()) {
                    important1.setImageResource(R.drawable.important);
                }
                else {
                    important1.setImageResource(R.drawable.not_important);
                }
                final ImageView complete = vPopupWindow.findViewById(R.id.complete);
                if(missionArrayList.get(i).isComplete()) {
                    complete.setImageResource(R.drawable.complete);
                }
                else{
                    complete.setImageResource(R.drawable.no_complete);
                }

                final View parentView = LayoutInflater.from(StaticVar.staticContext).inflate(R.layout.mission_datails, null);
                final EditText remarks  =vPopupWindow.findViewById(R.id.remarks);
                remarks.setText(missionArrayList.get(i).getRemarks());
                remarks.setOnFocusChangeListener(new View.OnFocusChangeListener()
                {

                    public void onFocusChange(View v,boolean hasFocus)
                    {
                        if(!hasFocus)
                        {
                            missionArrayList.get(i).setRemarks(remarks.getText().toString());
                            StaticVar.loginDBHelper.updateMission(missionArrayList.get(i));
                        }
                        else
                        {

                        }
                    }
                });

                complete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(missionArrayList.get(i).isComplete()){
                            missionArrayList.get(i).setComplete(false);
                            Log.i("已更新", missionArrayList.get(i).getDoWhat()+String.valueOf( missionArrayList.get(i).isComplete()));
                            StaticVar.loginDBHelper.updateMission(missionArrayList.get(i));
                            complete.setImageResource(R.drawable.no_complete);
                        }
                        else {
                            missionArrayList.get(i).setComplete(true);
                            Log.i("已更新", missionArrayList.get(i).getDoWhat()+String.valueOf( missionArrayList.get(i).isComplete()));
                            StaticVar.loginDBHelper.updateMission(  missionArrayList.get(i));
                            complete.setImageResource(R.drawable.complete);
                        }
                    }
                });

                important1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(missionArrayList.get(i).isImportant()){
                            missionArrayList.get(i).setImportant(false);
                            StaticVar.loginDBHelper.updateMission(  missionArrayList.get(i));
                            important1.setImageResource(R.drawable.not_important);
                        }
                        else{
                            missionArrayList.get(i).setImportant(true);
                            StaticVar.loginDBHelper.updateMission(  missionArrayList.get(i));
                            important1.setImageResource(R.drawable.important);
                        }
                    }
                });

                popupWindow = new PopupWindow(vPopupWindow, ActionBar.LayoutParams.MATCH_PARENT,ActionBar.LayoutParams.MATCH_PARENT , true);
                popupWindow.setAnimationStyle(R.style.popwindow_anim_style);
                popupWindow.showAtLocation(vPopupWindow, Gravity.CENTER, 0, 0);
                popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {
                        if(StaticVar.importantList != null)
                        {
                            MissionAdapter missionAdapter = new MissionAdapter(StaticVar.staticContext,StaticVar.loginDBHelper.queryImportantMission(StaticVar.user));
                            StaticVar.importantList.setAdapter(missionAdapter);
                        }
                        if (StaticVar.toDayMissions != null)
                        {
                            MissionAdapter missionAdapter = new MissionAdapter(StaticVar.staticContext,StaticVar.loginDBHelper.queryTodayMission(StaticVar.user));
                            StaticVar.toDayMissions.setAdapter(missionAdapter);
                        }
                        if (StaticVar.scheduleList != null) {
                            MissionAdapter missionAdapter = new MissionAdapter(StaticVar.staticContext, StaticVar.loginDBHelper.queryMission(StaticVar.user));
                            StaticVar.scheduleList.setAdapter(missionAdapter);
                        }

                    }
                });
            }
        });

        return view;
    }


}
