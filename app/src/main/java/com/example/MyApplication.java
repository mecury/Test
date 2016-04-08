package com.example;

import android.app.Application;
import android.content.Context;

import cn.bmob.v3.Bmob;

/**
 * Created by 海飞 on 2016/4/7.
 */
public class MyApplication extends Application{
    private Context context;
    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        Bmob.initialize(context, "89839e9b84d5f728733ca20335726395");
    }
}
