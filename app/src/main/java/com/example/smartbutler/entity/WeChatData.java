package com.example.smartbutler.entity;

/**
 * 项目名：    SmartButler
 * 包名：      com.example.smartbutler.entity
 * 文件名：    WeChatData
 * 作者：      钟士宜
 * 创建时间    2019/7/9 10:01
 * 描述：      微信精选实体类
 */
public class WeChatData {

    //标题
    private String title;
    //来源
    private String source;
    //图片地址
    private String Imgurl;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getImgurl() {
        return Imgurl;
    }

    public void setImgurl(String imgurl) {
        Imgurl = imgurl;
    }

}
