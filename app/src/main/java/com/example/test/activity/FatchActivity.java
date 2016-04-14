package com.example.test.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.test.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.bmob.v3.BmobSMS;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.RequestSMSCodeListener;
import cn.bmob.v3.listener.ResetPasswordByCodeListener;

/**
 * Created by 海飞 on 2016/4/6.
 * 找回密码的Activity
 */
public class FatchActivity extends Activity {

    @Bind(R.id.et_phone)
    EditText etPhone;
    @Bind(R.id.et_verify_code)
    EditText etVerifyCode;
    @Bind(R.id.btn_send)
    Button btnSend;
    @Bind(R.id.et_pwd)
    EditText etPwd;
    @Bind(R.id.btn_reset)
    Button btnReset;

    MyCountTimer timer;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fatch);
        ButterKnife.bind(this);
        context = FatchActivity.this;
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestSMSCode();
            }
        });
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetPwd();
            }
        });
    }

    /*
     *发送验证码按钮的时间变化
     */
    class MyCountTimer extends CountDownTimer{
        //第一个参数代表计时总时间，第二个参数代表每次减少的时间间隔
        public MyCountTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            btnSend.setText((millisUntilFinished/1000)+"秒后重发");
        }

        @Override
        public void onFinish() {
            btnSend.setText("重新发送验证码");
        }
    }

    /*
     * 发送短信验证码
     */
    private void requestSMSCode(){
        String number = etPhone.getText().toString();
        if (!TextUtils.isEmpty(number)){
            timer = new MyCountTimer(60000,1000);
            timer.start();
            BmobSMS.requestSMSCode(context, number, "注册验证码", new RequestSMSCodeListener() {
                @Override
                public void done(Integer integer, BmobException e) {
                    if ( e == null ){ //验证码发送成功
                        Toast.makeText(FatchActivity.this, "验证码发送成功", Toast.LENGTH_SHORT).show();
                    }else{  //验证码发送失败，停止计时
                        timer.cancel();
                    }
                }
            });
        }else{
            Toast.makeText(FatchActivity.this, "输入手机号", Toast.LENGTH_SHORT).show();
        }
    }

    /*
     *重置密码，中间检验了验证码
     */
    private void resetPwd() {
        final String code = etVerifyCode.getText().toString();
        final String pwd = etPwd.getText().toString();
        if(TextUtils.isEmpty(code)){
            Toast.makeText(FatchActivity.this, "验证码不能为空", Toast.LENGTH_SHORT).show();
            return ;
        }
        if (TextUtils.isEmpty(pwd)){
            Toast.makeText(FatchActivity.this, "密码不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        final ProgressDialog progress = new ProgressDialog(FatchActivity.this);
        progress.setMessage("正在重置密码...");
        progress.setCanceledOnTouchOutside(false);
        progress.show();
        //Bmob提供了重置密码功能，只需要输入验证码和新密码即可
        BmobUser.resetPasswordBySMSCode(context, code, pwd, new ResetPasswordByCodeListener() {
            @Override
            public void done(BmobException e) {
                progress.dismiss();
                if (e == null){
                    Toast.makeText(FatchActivity.this, "重置密码成功", Toast.LENGTH_SHORT).show();
                    finish();
                }else{
                    Toast.makeText(FatchActivity.this, "密码重置失败：code"+e.getErrorCode()+"，错误描述："+e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}































