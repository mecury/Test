package com.example.test.entity;

/**
 * Created by 海飞 on 2016/4/11.
 */
public class HelpData {
    private String nickName;
    private String loction;
    private DateTime startTime;
    private DateTime endTime;
    private String things;
    private String money;

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

    public void setMoney(String money) {
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

    public String getMoney() {
        return money;
    }
}
