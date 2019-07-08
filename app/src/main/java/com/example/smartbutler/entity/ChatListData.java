package com.example.smartbutler.entity;

/**
 * 项目名：    SmartButler
 * 包名：      com.example.smartbutler.entity
 * 文件名：    ChatListData
 * 作者：      钟士宜
 * 创建时间    2019/7/8 17:36
 * 描述：      对话列表的实体
 */
public class ChatListData {

    //type——用于区分左边还是右边的对话框
    private int type;
    //文本
    private String text;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
