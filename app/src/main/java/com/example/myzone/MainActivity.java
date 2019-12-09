package com.example.myzone;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final LoginDBHelper loginDBHelper = new LoginDBHelper(this,"login",null,10);
        StaticVar.loginDBHelper = loginDBHelper;
        Button login  =  findViewById(R.id.login);
        Button zhuce = findViewById(R.id.zhuce);
        zhuce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Intent intent = new Intent(MainActivity.this,Resister.class);
               startActivity(intent);
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText user = findViewById(R.id.user);
                EditText password = findViewById(R.id.password);


                if(loginDBHelper.query(user.getText().toString(),password.getText().toString())){
                    StaticVar.user=user.getText().toString();
                    Intent intent = new Intent(MainActivity.this,Myday.class);
                    startActivity(intent);
                    Toast.makeText(MainActivity.this,"success",Toast.LENGTH_SHORT).show();
                }
                else Toast.makeText(MainActivity.this,"faild",Toast.LENGTH_SHORT).show();

            }
        });


    }


}
