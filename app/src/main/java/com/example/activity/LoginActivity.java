package com.example.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.entity.MyUser;
import com.example.test.MainActivity;
import com.example.test.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by 海飞 on 2016/4/6.
 * 登陆的Activity
 */
public class LoginActivity extends Activity {


    @Bind(R.id.et_user)
    EditText etUser;
    @Bind(R.id.btn_clear)
    Button btnClear;
    @Bind(R.id.et_password)
    EditText etPassword;
    @Bind(R.id.tv_fetchPass)
    TextView tvFetchPass;
    @Bind(R.id.tv_newUser)
    TextView tvNewUser;
    @Bind(R.id.bt_login)
    Button btLogin;

    private String userphone;   //用户注册手机号
    private String password;   //用户密码
    private MyUser user;
    private Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        context = LoginActivity.this;
        ButterKnife.bind(this);

        //登陆
        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userphone = etUser.getText().toString();
                password = etPassword.getText().toString();
                if (checkUser()){
                    Bmoblogin();
                }
            }
        });
        //注册用户
        tvNewUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, NewuserActivity.class);
                startActivity(intent);
            }
        });
    }
    //检查密码和用户名
    private boolean checkUser(){
        if (userphone.length() != 11){
            Toast.makeText(LoginActivity.this, "请输入11位注册的手机号", Toast.LENGTH_SHORT).show();
            return false;
        }
        try{
            Integer.parseInt(password);
        }catch(Exception e){
            Toast.makeText(LoginActivity.this, "密码必须为数字", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (password == "") {
            Toast.makeText(LoginActivity.this, "密码不能为空", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
       //Bmob登陆步骤
    private void Bmoblogin(){
        user = new MyUser();
        user.setUsername(userphone);
        user.setPassword(password);
        user.login(context, new SaveListener() {
            @Override
            public void onSuccess() {
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                LoginActivity.this.finish();    //将当前activity销毁
            }

            @Override
            public void onFailure(int i, String s) {
                Toast.makeText(LoginActivity.this, "登陆失败", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
