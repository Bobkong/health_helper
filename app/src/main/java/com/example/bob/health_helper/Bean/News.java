package com.example.bob.health_helper.Bean;

import java.io.Serializable;
import java.util.Date;

public class News implements Serializable {
    public static final int TAG_FIND=0;//发现
    public static final int TAG_HYPERTENSION=1;//高血压
    public static final int TAG_DIABETES=2;//糖尿病
    public static final int TAG_HYPERLIPIDEMIA=3;//高血脂
    public static final int TAG_STROKE=4;//脑卒中
    public static final int TAG_CORONARY_HEART_DISEASE=5;//冠心病

    private int id;
    private String title;
    private String source;//资讯来源
    private String tag;//资讯标签
    private String img;
    private String url;
    private String date;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
