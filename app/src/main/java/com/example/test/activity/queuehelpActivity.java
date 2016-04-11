package com.example.test.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.test.R;
import com.example.test.entity.DateTime;
import com.example.test.entity.HelpData;
import com.example.test.fragment.GetEndTimeFragment;
import com.example.test.fragment.GetStartTimeFragment;

import java.util.Calendar;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by 海飞 on 2016/4/10.
 */
public class queuehelpActivity extends Activity implements GetStartTimeFragment.DataCallback, GetEndTimeFragment.DataCallback {

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
    @Bind(R.id.et_queue_thing)
    EditText etQueueThing;
    @Bind(R.id.et_queue_location)
    EditText etQueueLocation;

    private Boolean isUserMoney = false;

    private String nickName;       //昵称
    private String location;    //地点
    private DateTime startTime; //开始时间
    private DateTime endTime;   //结束时间
    private String things;  //事件
    private int money;      //金额
    private HelpData helpData;
    private Context context;

    private Calendar calendar = Calendar.getInstance();  //日历对象
    private int Year = calendar.get(Calendar.YEAR);        //动态得到改变的日期，年
    private int Month = calendar.get(Calendar.MONTH) + 1;      //月
    private int Day = calendar.get(Calendar.DAY_OF_MONTH);     //日
    private int Hour = calendar.get(Calendar.HOUR_OF_DAY);     //小时
    private int Minute = calendar.get(Calendar.MINUTE);    //分钟

    private int mMonth = Month;
    private int mDay = Day;
    private int mHour = Hour;
    private int mMinute = Minute;

    private int nMonth = Month;
    private int nDay = Day;
    private int nHour = Hour;
    private int nMinute = Minute;
    //回调的GetStartTimeFragment.DataCallback中的方法
    @Override
    public void getStartTimeData(DateTime dateTime) {
        etTimeStart.setText(dateTime.getYear() + "/" + dateTime.getMonth() + "/" + dateTime.getDay() + "    " + dateTime.getHour() + ":" + dateTime.getMinute());
        mMonth = dateTime.getMonth();
        mDay = dateTime.getDay();
        nHour = dateTime.getHour();
        nMinute = dateTime.getMinute();
    }

    @Override
    public void getEndTimeData(DateTime dateTime) {
        etTimeEnd.setText(dateTime.getYear() + "/" + dateTime.getMonth() + "/" + dateTime.getDay() + "    " + dateTime.getHour() + ":" + dateTime.getMinute());
        nMonth = dateTime.getMonth();
        nDay = dateTime.getDay();
        nHour = dateTime.getHour();
        nMinute = dateTime.getMinute();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_queuehelp);
        ButterKnife.bind(this);
        context = queuehelpActivity.this;

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
                getStartTimeFragment.show(getFragmentManager(), "getStartTimeFragment");
            }
        });

        //获得结束时间的点击事件
        btnTimeEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //重用了获得开始时间的方法
                GetEndTimeFragment getEndTimeFragment = new GetEndTimeFragment();
                getEndTimeFragment.show(getFragmentManager(), "getEndTimeFragment");
            }
        });

        helpData = new HelpData();
        startTime = new DateTime();
        endTime = new DateTime();

        //确定按钮的点击事件
        btQueueSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                loadTimeData(startTime,mMonth,mDay,mHour,mMinute);      //为startTime赋值
                loadTimeData(endTime, nMonth, nDay, nHour, nMinute);        //为endTime赋值
                boolean isTrue = initData();
                setData();
                Log.e("HelpData", helpData.getNickName()+helpData.getLoction()+helpData.getThings()
                        +helpData.getStartTime().getMonth()+"/"+helpData.getStartTime().getDay()+"  "+
                        helpData.getStartTime().getHour()+":"+helpData.getStartTime().getMinute()+"endTime:"
                        +helpData.getEndTime().getMonth()+"/"+helpData.getEndTime().getDay()+"  "
                        +helpData.getEndTime().getHour()+":"+helpData.getEndTime().getMinute());
                if (isTrue){
                    //Bmob上传数据
                    helpData.save(context, new SaveListener() {
                        @Override
                        public void onSuccess() {
                            Toast.makeText(queuehelpActivity.this, "发布成功", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFailure(int i, String s) {
                            Toast.makeText(queuehelpActivity.this, "发布失败", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }

    //加载数据
    public boolean initData() {
        nickName = etQueueNickname.getText().toString();
        if (nickName .equals("")){
            Toast.makeText(queuehelpActivity.this, "昵称不能为空", Toast.LENGTH_SHORT).show();
            return false;
        }
        location = etQueueLocation.getText().toString();
        if (location.equals("")){
            Toast.makeText(queuehelpActivity.this, "地点不能为空", Toast.LENGTH_SHORT).show();
            return false;
        }
        things = etQueueThing.getText().toString();
        if (things.equals("")){
            Toast.makeText(queuehelpActivity.this, "事件不能为空", Toast.LENGTH_SHORT).show();
            return false;
        }
        try {
            if (queueMoney.getText().toString().equals("")) {
                money = 0;
            } else {
                money = Integer.parseInt(queueMoney.getText().toString());
            }
        } catch (Exception e) {
            Toast.makeText(queuehelpActivity.this, "金额必须为数字", Toast.LENGTH_SHORT).show();
            return false;
        }
            return true;
    }

    //将数据放入HelpData中
    public void setData() {
        helpData.setNickName(nickName);
        helpData.setLoction(location);
        helpData.setStartTime(startTime);
        helpData.setEndTime(endTime);
        helpData.setThings(things);
        helpData.setMoney(money);
    }

    //用于加载回调方法中的时间数据，保存到startTime和endTime
    public void loadTimeData(DateTime time, int month, int day, int hour, int minute){
        time.setMonth(month);
        time.setDay(day);
        time.setHour(hour);
        time.setMinute(minute);
    }
}











