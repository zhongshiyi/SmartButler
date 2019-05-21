package com.example.smartbutler.entity;

import cn.bmob.v3.BmobUser;

/**
 * 项目名：    SmartButler
 * 包名：      com.example.smartbutler.entity
 * 文件名：    MyUser
 * 作者：      钟士宜
 * 创建时间    2019/5/21 19:46
 * 描述：      用户属性
 */
public class MyUser extends BmobUser {
    private int age;
    private String desc;
    private boolean sex;

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public boolean isSex() {
        return sex;
    }

    public void setSex(boolean sex) {
        this.sex = sex;
    }
}
