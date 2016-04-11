package com.example.test.fragment;

import android.app.DialogFragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.test.R;
import com.example.test.entity.DateTime;

import java.util.Calendar;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by 海飞 on 2016/4/10.
 */
public class GetStartTimeFragment extends DialogFragment{



    public interface DataCallback{
        void getStartTimeData(DateTime dateTime);
    }

    @Bind(R.id.dp_StartTime)
    DatePicker dpStartTime;
    @Bind(R.id.tp_StartTime)
    TimePicker tpStartTime;
    @Bind(R.id.btn_get_start_sure)
    Button btnGetStartSure;

    private int mYear;      //当前时间的年份
    private int mMonth;  //当前时间的月份
    private int mDay;    //当前时间的日期
    private int mMinute;    //当前的分钟
    private int mHour;      //当前的小时
    private Calendar calendar = Calendar.getInstance();  //日历对象

    private int nYear = calendar.get(Calendar.YEAR);        //动态得到改变的日期，年
    private int nMonth = calendar.get(Calendar.MONTH) + 1;      //月
    private int nDay = calendar.get(Calendar.DAY_OF_MONTH);     //日
    private int nHour = calendar.get(Calendar.HOUR_OF_DAY);     //小时
    private int nMinute = calendar.get(Calendar.MINUTE);    //分钟

    private DateTime myDate; //存储的日期和时间

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        View view = inflater.inflate(R.layout.fragment_getstarttime, container);
        ButterKnife.bind(this, view);

        mYear = calendar.get(Calendar.YEAR);
        mMonth = calendar.get(Calendar.MONTH) + 1;
        mDay = calendar.get(Calendar.DAY_OF_MONTH);
        mHour = calendar.get(Calendar.HOUR_OF_DAY);
        mMinute = calendar.get(Calendar.MINUTE);

        //日期改变的监听
        dpStartTime.getCalendarView().setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                Log.e("time",nYear + "," + nMonth + "," + nDay);
                boolean isTrue = dateChecked(year, month, dayOfMonth);
                if (isTrue) {
                    nYear = year;
                    nMonth = month+1;
                    nDay = dayOfMonth;
                } else {
                    dismiss();
                }
            }
        });

        //时间改变的监听
        tpStartTime.setIs24HourView(true);
        tpStartTime.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                Log.e("time",hourOfDay+":"+minute);
                nHour = hourOfDay;
                nMinute = minute;
                Toast.makeText(getActivity(),  hourOfDay+":"+minute, Toast.LENGTH_SHORT).show();
            }
        });

        //确定按钮的点击
        btnGetStartSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDate = new DateTime();
                myDate.setYear(nYear);
                myDate.setMonth(nMonth);
                myDate.setDay(nDay);
                myDate.setHour(nHour);
                myDate.setMinute(nMinute);

                DataCallback dataCallback = (DataCallback) getActivity();
                dataCallback.getStartTimeData(myDate);
                dismiss();
            }
        });
        return view;
    }

    /*
     *检查日期的变化，必须符合以下条件
     */
    public boolean dateChecked(int year, int monthOfYear, int dayOfMonth) {
        if (year != mYear ) {
            Toast.makeText(getActivity(), "年份必须是当前年份", Toast.LENGTH_SHORT).show();
            return false;
        } else if (monthOfYear < mMonth || mMonth + 2 < monthOfYear) {
            Toast.makeText(getActivity(), "月份最多超出当前月份2个月", Toast.LENGTH_SHORT).show();
            return false;
        } else if (year == mYear && monthOfYear == mMonth && dayOfMonth < mDay) {
            Toast.makeText(getActivity(), "日期不能小于当前日期", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}

