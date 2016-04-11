package com.example.test.entity;

import cn.bmob.v3.BmobObject;

/**
 * Created by 海飞 on 2016/4/11.
 */
public class HelpData extends BmobObject{
    private String nickName;
    private String loction;
    private DateTime startTime;
    private DateTime endTime;
    private String things;
    private Integer money;

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public void setLoction(String loction) {
        this.loction = loction;
    }

    public void setStartTime(DateTime startTime) {
        this.startTime = startTime;
    }

    public void setThings(String things) {
        this.things = things;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public String getNickName() {
        return nickName;
    }

    public String getLoction() {
        return loction;
    }

    public String getThings() {
        return things;
    }

    public DateTime getStartTime() {
        return startTime;
    }

    public int getMoney() {
        return money;
    }

    public DateTime getEndTime() {
        return endTime;
    }
    public void setEndTime(DateTime endTime) {
        this.endTime = endTime;
    }
}
