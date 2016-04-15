package com.example.test.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.test.R;
import com.example.test.entity.HelpData;

import java.util.ArrayList;
import java.util.Calendar;

import cn.bmob.v3.BmobUser;

/**
 * Created by 海飞 on 2016/4/12.
 */
public class FuiAdapter extends BaseAdapter {

    public ArrayList<HelpData> data;
    public Context context;

    public FuiAdapter(ArrayList<HelpData> data, Context context){
        this.data = data;
        this.context = context;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView==null){
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.listitem_fuilist,parent,false);
            viewHolder.tvNickName = (TextView) convertView.findViewById(R.id.tv_nickname);
            viewHolder.tvTime = (TextView) convertView.findViewById(R.id.tv_time);
            viewHolder.tvLocation = (TextView) convertView.findViewById(R.id.tv_location);
            viewHolder.tv_money = (TextView) convertView.findViewById(R.id.tv_money);
            viewHolder.tvPhoneNumber = (TextView) convertView.findViewById(R.id.tv_phoneNumber);
            viewHolder.tvThingText = (TextView) convertView.findViewById(R.id.tv_thingText);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        HelpData help = data.get(position);
        viewHolder.tvNickName.setText(help.getNickName()); //设置昵称
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        String startTime = year+"/"+help.getStartTime().getMonth()+"/"+help.getStartTime().getDay()+
                "  "+help.getStartTime().getHour()+":"+help.getEndTime().getMinute();
        String endTime = year+"/"+help.getEndTime().getMonth()+"/"+help.getEndTime().getDay()+
                "  "+help.getEndTime().getHour()+":"+help.getEndTime().getMinute();
        viewHolder.tvTime.setText(startTime+"————"+endTime);   //设置时间段
        viewHolder.tvLocation.setText(help.getLoction());      //设置城市
        if (help.getMoney() == 0){
            viewHolder.tv_money.setText("自主协商");
        }else{
            viewHolder.tv_money.setText(help.getMoney()+"");
        }
        BmobUser user = BmobUser.getCurrentUser(context);
        viewHolder.tvPhoneNumber.setText(user.getUsername());      //设置手机联系方式
        viewHolder.tvThingText.setText(help.getThings());
        return convertView;
    }

    static class ViewHolder{
        TextView tvNickName;
        TextView tvTime;
        TextView tvLocation;
        TextView tv_money;
        TextView tvPhoneNumber;
        TextView tvThingText;
    }
}



























