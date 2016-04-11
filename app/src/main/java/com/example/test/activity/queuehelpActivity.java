package com.example.test.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;

import com.example.test.R;
import com.example.test.entity.DateTime;
import com.example.test.fragment.GetEndTimeFragment;
import com.example.test.fragment.GetStartTimeFragment;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by 海飞 on 2016/4/10.
 */
public class queuehelpActivity extends Activity implements GetStartTimeFragment.DataCallback,GetEndTimeFragment.DataCallback{

    @Bind(R.id.et_queue_nickname)
    EditText etQueueNickname;
    @Bind(R.id.btn_timeStart)
    Button btnTimeStart;
    @Bind(R.id.et_time_start)
    EditText etTimeStart;
    @Bind(R.id.btn_timeEnd)
    Button btnTimeEnd;
    @Bind(R.id.et_time_end)
    EditText etTimeEnd;
    @Bind(R.id.queue_money)
    EditText queueMoney;
    @Bind(R.id.rbtn_money)
    RadioButton rbtnMoney;
    @Bind(R.id.Lin_money)
    LinearLayout LinMoney;
    @Bind(R.id.rbtn_self)
    RadioButton rbtnSelf;
    @Bind(R.id.bt_queue_sure)
    Button btQueueSure;

    private Boolean isUserMoney = false;

    //回调的GetStartTimeFragment.DataCallback中的方法
    @Override
    public void getStartTimeData(DateTime dateTime) {
        etTimeStart.setText(dateTime.getYear()+"/"+dateTime.getMonth()+"/"+dateTime.getDay()+"    "+ dateTime.getHour()+":"+dateTime.getMinute());
    }

    @Override
    public void getEndTimeData(DateTime dateTime) {
        etTimeEnd.setText(dateTime.getYear()+"/"+dateTime.getMonth()+"/"+dateTime.getDay()+"    "+ dateTime.getHour()+":"+dateTime.getMinute());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_queuehelp);
        ButterKnife.bind(this);


        //选择报酬的方式:自由协商
        rbtnMoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isUserMoney = false;
                LinMoney.setVisibility(View.INVISIBLE);
            }
        });
        //选择报酬的方式:限定金额
        rbtnSelf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isUserMoney = true;
                LinMoney.setVisibility(View.VISIBLE);
            }
        });
        //获得开始时间的点击事件
        btnTimeStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GetStartTimeFragment getStartTimeFragment = new GetStartTimeFragment();
                getStartTimeFragment.show(getFragmentManager(),"getStartTimeFragment");
            }
        });
        //获得结束时间的点击事件
        btnTimeEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //重用了获得开始时间的方法
                GetEndTimeFragment getEndTimeFragment = new GetEndTimeFragment();
                getEndTimeFragment.show(getFragmentManager(),"getEndTimeFragment");
            }
        });
        //确定按钮的点击事件
        btQueueSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    //加载数据
    public void data(){

    }
}











