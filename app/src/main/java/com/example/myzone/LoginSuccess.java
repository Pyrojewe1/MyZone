package com.example.myzone;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

public class LoginSuccess extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.success);
            String a = "2019.1.1";
            String b ="2019.10.2";
        Log.i("TAGTAG",String.valueOf(a.compareTo(b)));
        Log.i("TAGTAG",String.valueOf(b.compareTo(a)));
        String c = "100.2.3";
        String d = "50.3.2";
        Log.i("TAGTAG",String.valueOf(c.compareTo(d)));
        Log.i("TAGTAG",String.valueOf(d.compareTo(c)));
        }
    }

