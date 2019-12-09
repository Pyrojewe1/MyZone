package com.example.myzone;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class Resister extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.resister);
        final EditText username = findViewById(R.id.username);
        final EditText password = findViewById(R.id.password);
        final EditText verify = findViewById(R.id.verify);
        Button submit = findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!password.getText().toString().equals(verify.getText().toString())){
                    Toast.makeText(Resister.this,"两次输入密码不一致",Toast.LENGTH_LONG).show();
                }
                else if(password.getText().toString().length()<8){
                    Toast.makeText(Resister.this,"输入密码过短",Toast.LENGTH_LONG).show();
                }
                else {
                    if(StaticVar.loginDBHelper.query(username.getText().toString())){
                        Toast.makeText(Resister.this,"账户已存在，请更改用户名",Toast.LENGTH_LONG);
                    }
                    else{
                        User user = new User();
                        user.setUser(username.getText().toString());
                        user.setPassword(password.getText().toString());
                        StaticVar.loginDBHelper.insert(user);
                        Toast.makeText(Resister.this,"注册成功",Toast.LENGTH_LONG).show();
                        StaticVar.user=username.getText().toString();
                        Intent intent = new Intent(Resister.this,Myday.class);
                        startActivity(intent);
                    }

                }
            }
        });

    }
}
