package com.example.test.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.test.R;
import com.example.test.entity.MyUser;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by 海飞 on 2016/4/25.
 */
public class ResiveActivity extends Activity{


    @Bind(R.id.et_nickname_revise)
    EditText etNicknameRevise;
    @Bind(R.id.btn_Man_revise)
    RadioButton btnManRevise;
    @Bind(R.id.btn_Woman_revise)
    RadioButton btnWomanRevise;
    @Bind(R.id.radioGroup2)
    RadioGroup radioGroup2;
    @Bind(R.id.et_location_revise)
    EditText etLocationRevise;
    @Bind(R.id.btn_revise_sure)
    Button btnReviseSure;

    private boolean isMan = true;
    private String userPhone;
    private Context context;
    private String nickName;
    private String location;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_revise);
        ButterKnife.bind(this);

        context = ResiveActivity.this;

        Intent intent = getIntent();
        userPhone = intent.getStringExtra("userPhone");

        initdata();

        radioGroup2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.btn_Woman_revise:
                        isMan = false;
                        break;
                    case R.id.btn_Man_revise:
                        isMan = true;
                        break;
                }
            }
        });

        btnReviseSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nickName = etNicknameRevise.getText().toString();
                location = etLocationRevise.getText().toString();

                MyUser user = new MyUser();
                user = BmobUser.getCurrentUser(context, MyUser.class);
                user.setNickName(nickName);
                user.setLocation(location);
                user.setSex(isMan);
                user.update(context, new UpdateListener() {
                    @Override
                    public void onSuccess() {
                        Toast.makeText(ResiveActivity.this, "更新成功", Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onFailure(int i, String s) {

                    }
                });

            }
        });
    }

    //初始化数据
    public void initdata() {
        BmobQuery<MyUser> query = new BmobQuery<>();
        query.setCachePolicy(BmobQuery.CachePolicy.CACHE_ELSE_NETWORK);
        query.addWhereEqualTo("username", userPhone);
        query.findObjects(context, new FindListener<MyUser>() {
            @Override
            public void onSuccess(List<MyUser> list) {
                for (MyUser user : list){
                    etNicknameRevise.setText(user.getNickName());
                    etLocationRevise.setText(user.getLocation());
                    isMan = user.getSex();
                    if (isMan){
                    }else{
                        btnWomanRevise.isFocusable();
                    }
                }
            }

            @Override
            public void onError(int i, String s) {
                Log.e("Error","在resive中查询失败！");
            }
        });
        query.clearAllCachedResults(this);
    }
}























