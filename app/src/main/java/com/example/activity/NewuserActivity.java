package com.example.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.entity.MyUser;
import com.example.test.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by 海飞 on 2016/4/6.
 */
public class NewuserActivity extends Activity {

    @Bind(R.id.et_username)
    EditText et_username;
    @Bind(R.id.btn_register_phone)
    Button btn_register_phone;
    @Bind(R.id.et_nickname)
    EditText et_Nickname;
    @Bind(R.id.btn_Man)
    RadioButton btn_Man;
    @Bind(R.id.btn_Woman)
    RadioButton btn_Woman;
    @Bind(R.id.radioGroup)
    RadioGroup radio_Group;
    @Bind(R.id.et_location)
    EditText et_Location;
    @Bind(R.id.et_newpassword)
    EditText et_Newpassword;
    @Bind(R.id.et_mewpasssword_two)
    EditText et_Mewpasssword_Two;

    private String userPhone;   //手机号
    private String nickName;    //昵称
    private Boolean isMan = true;  //性别
    private String location;    //城市
    private String indentifyNumber; //验证码
    private String pwd; //密码
    private String pwd_two; //确认密码
    private MyUser user;

    private Context context;
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newuser);
        ButterKnife.bind(this);
        context = NewuserActivity.this;
//        //获取验证码的点击事件
//        btn_getpwd_phone.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                userPhone = et_username.getText().toString();
//                indentifyNumber = et_newpassword_phone.getText().toString();
//                Log.d("userPhone======》", userPhone);
//                getIdentify();  //已经通过Bmob发送了验证码
//            }
//        });

        //注册按钮的点击事件
        btn_register_phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userPhone = et_username.getText().toString();
                nickName = et_Nickname.getText().toString();
                location = et_Location.getText().toString();
                pwd = et_Newpassword.getText().toString();
                pwd_two = et_Mewpasssword_Two.getText().toString();
                Log.e("msg", "注册按钮");
                if (checkListener()){//检测成功
                    user = new MyUser();
                    user.setUsername(userPhone);
                    user.setPassword(pwd);
                    user.setNickName(nickName);
                    user.setLocation(location);
                    user.setSex(isMan);

                    user.signUp(context, new SaveListener() {
                        @Override
                        public void onSuccess() {
                            Toast.makeText(NewuserActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFailure(int i, String s) {
                            Toast.makeText(NewuserActivity.this, "注册失败", Toast.LENGTH_SHORT).show();
                        }
                    });
                }else{
                    Toast.makeText(NewuserActivity.this, "未知的错误", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public boolean checkListener() {
        //手机号的检测监听
        if (userPhone.length() != 11) {
            Toast.makeText(NewuserActivity.this, "请正确输入手机号码", Toast.LENGTH_SHORT).show();
            return false;
        }
        //昵称的检测
        if (nickName == "") {
            Toast.makeText(NewuserActivity.this, "昵称不能为空", Toast.LENGTH_SHORT).show();
            return false;
        }
        //性别的单选框，点击事件
        radio_Group.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.btn_Man:
                        isMan = true;
                        break;
                    case R.id.btn_Woman:
                        isMan = false;
                        break;
                }
            }
        });
        //城市的检测
        if (location == "") {
            Toast.makeText(NewuserActivity.this, "请输入你所在的城市", Toast.LENGTH_SHORT).show();
            return false;
        }
        //密码和确认密码的检测
        try {
            Integer.parseInt(pwd);
            Integer.parseInt(pwd_two);
        }catch (Exception e){
            Toast.makeText(NewuserActivity.this, "密码必须为数字", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (pwd == "" || pwd_two == "" || pwd.equals(pwd_two) == false){
            Toast.makeText(NewuserActivity.this, "请确保两次输入的密码相等", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

//    /*
//     * 或取验证码
//     */
//    private void getIdentify() {
//        if (userPhone.length() != 11) {
//            Toast.makeText(NewuserActivity.this, "请正确输入手机号码", Toast.LENGTH_SHORT).show();
//        } else {
//            Log.e("Message", "2");
//            BmobSMS.requestSMSCode(this, userPhone, "短信模版", new RequestSMSCodeListener() {
//                @Override
//                public void done(Integer integer, BmobException e) {
//                    if (e == null) {
//                        //发送成功时，将获取验证码按钮不可点击，且为灰色
//                        btn_getpwd_phone.setClickable(false);
//                        btn_getpwd_phone.setBackgroundColor(Color.GRAY);
//                        Toast.makeText(NewuserActivity.this, "验证码发送成功", Toast.LENGTH_SHORT).show();
//                        new CountDownTimer(60000, 1000) {
//                            @Override
//                            public void onFinish() {
//                                btn_getpwd_phone.setClickable(true);
//                                btn_getpwd_phone.setBackgroundResource(R.drawable.btn_login_n);
//                                btn_getpwd_phone.setText("重新发送");
//                            }
//
//                            @Override
//                            public void onTick(long millisUntilFinished) {
//                                btn_getpwd_phone.setBackgroundResource(R.drawable.btn_login_p);
//                                btn_getpwd_phone.setText(millisUntilFinished / 1000 + "秒");
//                            }
//                        }.start();
//                    } else {
//                        Toast.makeText(NewuserActivity.this, "验证码发送失败", Toast.LENGTH_SHORT).show();
//                    }
//                }
//            });
//        }
//    }
}










