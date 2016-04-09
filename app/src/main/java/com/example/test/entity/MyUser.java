package com.example.test.entity;

import cn.bmob.v3.BmobUser;

/**
 * Created by 海飞 on 2016/4/7.
 */
public class MyUser  extends BmobUser{

    private String nickName;
    private boolean sex;
    private String location;

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public void setSex(boolean sex) {
        this.sex = sex;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getNickName() {
        return nickName;
    }

    public boolean getSex() {
        return sex;
    }

    public String getLocation() {
        return location;
    }
}
