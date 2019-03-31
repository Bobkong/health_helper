package com.example.bob.health_helper.Bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Bob on 2019/3/31.
 */

public class MeasureBean implements Serializable{
	@SerializedName("bodyScore")
	private double bodyScore;//身体得分
	@SerializedName("bmi")
	private double Bmi;//BMI指数
	@SerializedName("bodyAge")
	private int bodyAge;//身体年龄
	@SerializedName("visceralFat")
	private double visceralFat;//内脏脂肪
	@SerializedName("baselMetabolism")
	private double baselMetabolism;//基础代谢
	@SerializedName("bodyFatRatio")
	private double bodyFatRatio;//脂肪率
	@SerializedName("muscleMassRatio")
	private double muscleMassRatio;//肌肉率
	@SerializedName("protein")
	private double protein;//蛋白质
	@SerializedName("bodyWaterRatio")
	private double bodyWaterRatio;//水分率
	@SerializedName("bodyFatMass")
	private double bodyFatMass;//脂肪量
	@SerializedName("muscleMass")
	private double muscleMass;//肌肉量
	@SerializedName("skeletalMuscleMass")
	private double skeletalMuscleMass;//骨骼肌
	@SerializedName("boneMass")
	private double boneMass;//骨量
	@SerializedName("fatControl")
	private double fatControl;//脂肪控制
	@SerializedName("muscleControl")
	private double muscleControl;//肌肉控制
	@SerializedName("fatFreeMass")
	private double fatFreeMass;//去脂体重
	@SerializedName("date")
	private String time;
	@SerializedName("weight")
	private double weight;
	@SerializedName("uid")
	private String uid;

	public double getBodyScore() {
		return bodyScore;
	}

	public void setBodyScore(double bodyScore) {
		this.bodyScore = bodyScore;
	}

	public double getBmi() {
		return Bmi;
	}

	public void setBmi(double bmi) {
		Bmi = bmi;
	}

	public int getBodyAge() {
		return bodyAge;
	}

	public void setBodyAge(int bodyAge) {
		this.bodyAge = bodyAge;
	}

	public double getVisceralFat() {
		return visceralFat;
	}

	public void setVisceralFat(double visceralFat) {
		this.visceralFat = visceralFat;
	}

	public double getBaselMetabolism() {
		return baselMetabolism;
	}

	public void setBaselMetabolism(double baselMetabolism) {
		this.baselMetabolism = baselMetabolism;
	}

	public double getBodyFatRatio() {
		return bodyFatRatio;
	}

	public void setBodyFatRatio(double bodyFatRatio) {
		this.bodyFatRatio = bodyFatRatio;
	}

	public double getMuscleMassRatio() {
		return muscleMassRatio;
	}

	public void setMuscleMassRatio(double muscleMassRatio) {
		this.muscleMassRatio = muscleMassRatio;
	}

	public double getProtein() {
		return protein;
	}

	public void setProtein(double protein) {
		this.protein = protein;
	}

	public double getBodyWaterRatio() {
		return bodyWaterRatio;
	}

	public void setBodyWaterRatio(double bodyWaterRatio) {
		this.bodyWaterRatio = bodyWaterRatio;
	}

	public double getBodyFatMass() {
		return bodyFatMass;
	}

	public void setBodyFatMass(double bodyFatMass) {
		this.bodyFatMass = bodyFatMass;
	}

	public double getMuscleMass() {
		return muscleMass;
	}

	public void setMuscleMass(double muscleMass) {
		this.muscleMass = muscleMass;
	}

	public double getSkeletalMuscleMass() {
		return skeletalMuscleMass;
	}

	public void setSkeletalMuscleMass(double skeletalMuscleMass) {
		this.skeletalMuscleMass = skeletalMuscleMass;
	}

	public double getBoneMass() {
		return boneMass;
	}

	public void setBoneMass(double boneMass) {
		this.boneMass = boneMass;
	}

	public double getFatControl() {
		return fatControl;
	}

	public void setFatControl(double fatControl) {
		this.fatControl = fatControl;
	}

	public double getMuscleControl() {
		return muscleControl;
	}

	public void setMuscleControl(double muscleControl) {
		this.muscleControl = muscleControl;
	}

	public double getFatFreeMass() {
		return fatFreeMass;
	}

	public void setFatFreeMass(double fatFreeMass) {
		this.fatFreeMass = fatFreeMass;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}
}
