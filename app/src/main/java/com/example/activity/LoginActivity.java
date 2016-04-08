package com.example.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.entity.MyUser;
import com.example.test.MainActivity;
import com.example.test.R;

/**
 * Created by 海飞 on 2016/4/6.
 * 登陆的Activity
 */
public class LoginActivity extends Activity {

    private Button bt_login;
    private TextView tv_fetchPass;
    private TextView tv_newUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        bt_login = (Button) findViewById(R.id.bt_log);
        tv_fetchPass = (TextView) findViewById(R.id.tv_fetchPass);
        tv_newUser = (TextView) findViewById(R.id.tv_newUser);

        //登陆
        bt_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyUser user = new MyUser();
                
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                LoginActivity.this.finish();
            }
        });
        //注册用户
        tv_newUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, NewuserActivity.class);
                startActivity(intent);
            }
        });
    }
}
