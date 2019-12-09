package com.example.myzone;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.security.PublicKey;
import java.text.DateFormat;
import java.text.FieldPosition;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

public class LoginDBHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "logindb";
    private static final int DB_VERSION = 1;

    private static final String CREATE_INFO = "create table user("
            + "id integer primary key autoincrement,"
            + "user varchar(20) ,"
            + "password varchar(20),"
            + "updatetime varchar(20))";
    private SQLiteDatabase db;
    private Context context;
    private static final String CREATE_INFO2 = "create table if not exists mission("
            + "id integer primary key autoincrement,"
            + "dowhat varchar(20),"
            + "finishtime varchar(20),"
            + "starttime varchar(20),"
            + "important boolean,"
            + "owner varchar(20),"
            + "today boolean,"
            + "complete boolean,"
            + "remarks varchar(50))";

    public LoginDBHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        this.db = sqLiteDatabase;
        db.execSQL(CREATE_INFO);
        db.execSQL(CREATE_INFO2);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("drop table if exists user");
        sqLiteDatabase.execSQL("drop table if exists mission");
        onCreate(sqLiteDatabase);
    }

    public void update_updateTime(String user,String date){
     String sql = "update user set updatetime = '"+date+"' where user = '"+user+"'";
     getWritableDatabase().execSQL(sql);
    }

    public String queryUpdateTime(String user){
        Cursor cursor =getReadableDatabase().query("user",null, String.format("user = '%s'", user),null,null,null,null);
        if(cursor.moveToFirst()){
            return cursor.getString(cursor.getColumnIndex("updatetime"));
        }
        else return null;
    }


    public boolean query(String user, String password) {
        Cursor cursor = getReadableDatabase().query("user", null,
                String.format("user = '%s' and password = '%s'", user, password),
                null, null, null, null);
        if (cursor.moveToFirst()) {
            return true;
        } else return false;
    }

    public boolean query(String user) {
        Cursor cursor = getReadableDatabase().query("user", null,
                String.format("user = '%s'", user),
                null, null, null, null);
        if (cursor.moveToFirst()) {
            return true;
        } else return false;
    }

    public void insert(User user) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("user", user.getUser());
        values.put("password", user.getPassword());
        values.put("updatetime","0");
        db.insert("user", null, values);
    }

    public void insertMission(Mission mission) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("dowhat", mission.getDoWhat());
        values.put("finishtime", mission.getFinishTime());
        values.put("important", mission.isImportant());
        values.put("owner", mission.getOwner());
        values.put("starttime", mission.getStartTime());
        values.put("today", mission.isToday());
        values.put("remarks", mission.getRemarks());
        db.insert("mission", null, values);
    }

    public ArrayList<Mission> queryMission(String owner) {
        ArrayList<Mission> missionArrayList = new ArrayList<Mission>();
        Cursor cursor = getReadableDatabase().query("mission", null, String.format("owner = '%s'", owner), null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                Mission mission = new Mission();
                mission.setId(cursor.getInt(cursor.getColumnIndex("id")));
                mission.setDoWhat(cursor.getString(cursor.getColumnIndex("dowhat")));
                mission.setFinishTime(cursor.getString(cursor.getColumnIndex("finishtime")));
                mission.setImportant(cursor.getInt(cursor.getColumnIndex("important")) > 0);
                mission.setOwner(cursor.getString(cursor.getColumnIndex("owner")));
                mission.setStartTime(cursor.getString(cursor.getColumnIndex("starttime")));
                mission.setToday(cursor.getInt(cursor.getColumnIndex("today")) > 0);
                mission.setRemarks(cursor.getString(cursor.getColumnIndex("remarks")));
                mission.setComplete(cursor.getInt(cursor.getColumnIndex("complete")) > 0);
                missionArrayList.add(mission);
            }
            while (cursor.moveToNext());
        }
        return missionArrayList;
    }

    public ArrayList<Mission> queryImportantMission(String owner) {
        ArrayList<Mission> missionArrayList = new ArrayList<Mission>();
        Cursor cursor = getReadableDatabase().query("mission", null, String.format("owner = '%s' and important = 1 ", owner), null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                Mission mission = new Mission();
                mission.setId(cursor.getInt(cursor.getColumnIndex("id")));
                mission.setDoWhat(cursor.getString(cursor.getColumnIndex("dowhat")));
                mission.setFinishTime(cursor.getString(cursor.getColumnIndex("finishtime")));
                mission.setImportant(cursor.getInt(cursor.getColumnIndex("important")) > 0);
                mission.setOwner(cursor.getString(cursor.getColumnIndex("owner")));
                mission.setStartTime(cursor.getString(cursor.getColumnIndex("starttime")));
                mission.setToday(cursor.getInt(cursor.getColumnIndex("today")) > 0);
                mission.setRemarks(cursor.getString(cursor.getColumnIndex("remarks")));
                mission.setComplete(cursor.getInt(cursor.getColumnIndex("complete")) > 0);
                missionArrayList.add(mission);
            }
            while (cursor.moveToNext());
        }
        return missionArrayList;
    }
    public ArrayList<Mission> queryTodayMission(String owner) {
        ArrayList<Mission> missionArrayList = new ArrayList<Mission>();
        Cursor cursor = getReadableDatabase().query("mission", null, String.format("owner = '%s' and today = 1 ", owner), null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                Mission mission = new Mission();
                mission.setId(cursor.getInt(cursor.getColumnIndex("id")));
                mission.setDoWhat(cursor.getString(cursor.getColumnIndex("dowhat")));
                mission.setFinishTime(cursor.getString(cursor.getColumnIndex("finishtime")));
                mission.setImportant(cursor.getInt(cursor.getColumnIndex("important")) > 0);
                mission.setOwner(cursor.getString(cursor.getColumnIndex("owner")));
                mission.setStartTime(cursor.getString(cursor.getColumnIndex("starttime")));
                mission.setToday(cursor.getInt(cursor.getColumnIndex("today")) > 0);
                mission.setRemarks(cursor.getString(cursor.getColumnIndex("remarks")));
                mission.setComplete(cursor.getInt(cursor.getColumnIndex("complete")) > 0);
                missionArrayList.add(mission);
            }
            while (cursor.moveToNext());
        }
       Collections.sort(missionArrayList, new Comparator<Mission>() {
            @Override
            public int compare(Mission mission, Mission t1) {
                if(mission.isComplete())
                return 1;
                else return -1;
            }
        });
        return missionArrayList;
    }
    public void deleteMission(int id){
        SQLiteDatabase db = getWritableDatabase();
        String sql = "DELETE FROM mission WHERE ID = "+id;
        db.execSQL(sql);
    }

    public void updateMission (Mission mission){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("dowhat", mission.getDoWhat());
        values.put("finishtime", mission.getFinishTime());
        values.put("important", mission.isImportant());
        values.put("owner", mission.getOwner());
        values.put("starttime", mission.getStartTime());
        values.put("today", mission.isToday());
        values.put("remarks", mission.getRemarks());
        values.put("complete",mission.isComplete());
        String id = String.valueOf(mission.getId());
        db.update("mission", values, "id = ?", new String[] {id});
        Log.i("已更新了id为"+mission.getId()+"的信息"," ");
    }

    public void changeMission() throws ParseException {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

        Cursor cursor = getReadableDatabase().query("mission", null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                Mission mission = new Mission();
                mission.setId(cursor.getInt(cursor.getColumnIndex("id")));
                mission.setDoWhat(cursor.getString(cursor.getColumnIndex("dowhat")));
                mission.setFinishTime(cursor.getString(cursor.getColumnIndex("finishtime")));
               //Date date1 =df.parse(cursor.getString(cursor.getColumnIndex("finishtime")));
                //Date date2 = df.parse(StaticVar.loginDBHelper.queryUpdateTime(StaticVar.user));
                Log.e("执行到这里执行到这里执行到这里执行到这里1", "");
                if(cursor.getString(cursor.getColumnIndex("finishtime"))!=null) {
                    Log.e("执行到这里执行到这里执行到这里执行到这里", "");
                    if (df.parse(cursor.getString(cursor.getColumnIndex("finishtime"))).compareTo(df.parse(StaticVar.loginDBHelper.queryUpdateTime(StaticVar.user))) < 0) {
                        Log.e("任务id是",String.valueOf(mission.getId()));
                        Log.e("任务结束时间是",mission.getFinishTime());
                        deleteMission(mission.getId());
                        continue;
                    }
                }
                mission.setImportant(cursor.getInt(cursor.getColumnIndex("important")) > 0);
                mission.setOwner(cursor.getString(cursor.getColumnIndex("owner")));
                mission.setStartTime(cursor.getString(cursor.getColumnIndex("starttime")));
                mission.setToday(false);
                mission.setRemarks(cursor.getString(cursor.getColumnIndex("remarks")));
                mission.setComplete(cursor.getInt(cursor.getColumnIndex("complete")) > 0);
                updateMission(mission);
            }
            while (cursor.moveToNext());
        }
    }
}

