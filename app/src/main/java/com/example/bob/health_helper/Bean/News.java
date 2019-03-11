package com.example.bob.health_helper.Bean;

import java.io.Serializable;

public class News implements Serializable {
    public static final int TAG_FIND=0;//发现
    public static final int TAG_HYPERTENSION=1;//高血压
    public static final int TAG_DIABETES=2;//糖尿病
    public static final int TAG_HYPERLIPIDEMIA=3;//高血脂
    public static final int TAG_STROKE=4;//脑卒中
    public static final int TAG_CORONARY_HEART_DISEASE=5;//冠心病

    private int id;
    private String title;
    private String source;
    private String tag;
    private String url;
}
