package com.example.bob.health_helper.Data.Bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class User implements Serializable {
    @SerializedName("uid")
    private String uid;
    @SerializedName("name")
    private String name;
    @SerializedName("gender")
    private String gender;
    @SerializedName("iconUrl")
    private String iconurl;
    @SerializedName("height")
    private int height;//cm
    @SerializedName("weight")
    private float weight;//kg
    @SerializedName("age")
    private int age;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getIconurl() {
        return iconurl;
    }

    public void setIconurl(String iconurl) {
        this.iconurl = iconurl;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "User{" +
                "uid='" + uid + '\'' +
                ", name='" + name + '\'' +
                ", gender='" + gender + '\'' +
                ", iconurl='" + iconurl + '\'' +
                ", height=" + height +
                ", weight=" + weight +
                ", age=" + age +
                '}';
    }
}
