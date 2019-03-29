package com.example.bob.health_helper.Bean;

import android.content.Context;

import com.google.gson.annotations.SerializedName;
import com.lifesense.ble.bean.WeightAppendData;
import com.litesuits.orm.db.annotation.Column;
import com.litesuits.orm.db.annotation.PrimaryKey;
import com.litesuits.orm.db.assit.QueryBuilder;
import com.litesuits.orm.db.enums.AssignType;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

/**
 * Created by Bob on 2019/3/23.
 */

public class MeasureData implements Serializable {
	/*
    sex:0-female,1-male
    */
	@Column("bodyScore")
	private double bodyScore;//身体得分
	@Column("bmi")
	private double Bmi;//BMI指数
	@Column("bodyAge")
	private int bodyAge;//身体年龄
	@Column("visceralFat")
	private double visceralFat;//内脏脂肪
	@Column("baselMetabolism")
	private double baselMetabolism;//基础代谢
	@Column("bodyFatRatio")
	private double bodyFatRatio;//脂肪率
	@Column("muscleMassRatio")
	private double muscleMassRatio;//肌肉率
	@Column("protein")
	private double protein;//蛋白质
	@Column("bodyWaterRatio")
	private double bodyWaterRatio;//水分率
	@Column("bodyFatMass")
	private double bodyFatMass;//脂肪量
	@Column("muscleMass")
	private double muscleMass;//肌肉量
	@Column("skeletalMuscleMass")
	private double skeletalMuscleMass;//骨骼肌
	@Column("boneMass")
	private double boneMass;//骨量
	@Column("fatControl")
	private double fatControl;//脂肪控制
	@Column("muscleControl")
	private double muscleControl;//肌肉控制
	@Column("fatFreeMass")
	private double fatFreeMass;//去脂体重

	//private double boneDensity;//骨密度

	private static final String COL_TIME = "time";
	@PrimaryKey(AssignType.BY_MYSELF)
	@Column(COL_TIME)
	private long time;
	@Column("weight")
	private double weight;

	public MeasureData(double bodyScore, double bmi, int bodyAge, double visceralFat, double baselMetabolism, double bodyFatRatio, double muscleMassRatio, double protein, double bodyWaterRatio, double bodyFatMass, double muscleMass, double skeletalMuscleMass, double boneMass, double fatControl, double muscleControl, double fatFreeMass, long time, double weight) {
		this.bodyScore = bodyScore;
		Bmi = bmi;
		this.bodyAge = bodyAge;
		this.visceralFat = visceralFat;
		this.baselMetabolism = baselMetabolism;
		this.bodyFatRatio = bodyFatRatio;
		this.muscleMassRatio = muscleMassRatio;
		this.protein = protein;
		this.bodyWaterRatio = bodyWaterRatio;
		this.bodyFatMass = bodyFatMass;
		this.muscleMass = muscleMass;
		this.skeletalMuscleMass = skeletalMuscleMass;
		this.boneMass = boneMass;
		this.fatControl = fatControl;
		this.muscleControl = muscleControl;
		this.fatFreeMass = fatFreeMass;
		this.time = time;
		this.weight = weight;
	}

	public MeasureData(WeightAppendData weightAppendData, double height, double weight, int age, int sex, long time) {
		this.weight = weight;
		this.time = time;
		this.bodyScore=dataFormat(calBodyScore(sex,this.bodyFatRatio,this.Bmi));

		this.Bmi= dataFormat(weightAppendData.getBmi());
		this.bodyAge=calBodyAge(sex,this.bodyFatRatio,age);
		this.visceralFat= dataFormat(weightAppendData.getVisceralFat());
		this.baselMetabolism = dataFormat(weightAppendData.getBasalMetabolism());

		this.bodyFatRatio =  dataFormat(weightAppendData.getBodyFatRatio());
		this.muscleMassRatio= dataFormat(weightAppendData.getMuscleMassRatio());
		this.protein= dataFormat(weightAppendData.getProteinContent());
		this.bodyWaterRatio = dataFormat(weightAppendData.getBodyWaterRatio());

		this.bodyFatMass = dataFormat(weight * this.bodyFatRatio / 100);
		this.muscleMass=dataFormat(weight*this.muscleMassRatio / 100);
		this.skeletalMuscleMass=dataFormat(calSkeletalMuscleMass(sex,height));
		this.boneMass=dataFormat(calBoneMass(sex,this.muscleMass));

		this.muscleControl=dataFormat(calControl(sex,this.Bmi,this.bodyFatRatio,height,weight)[0]);
		this.fatControl=dataFormat(calControl(sex,this.Bmi,this.bodyFatRatio,height,weight)[1]);
		this.fatFreeMass = dataFormat(weight - this.bodyFatMass);
		//this.boneDensity=weightAppendData.getBoneDensity();
	}

	//数据格式
	DecimalFormat df1=new DecimalFormat("#.#");
	public double dataFormat(double item){
		return Double.valueOf(df1.format(item));
	}
	//肌肉控制和脂肪控制
	public double[] calControl(int sex,double Bmi,double bodyFatRatio,double height,double weight){
		if(sex==0){//female
			if(Bmi>20&&bodyFatRatio<22)
				return new double[]{0,0};
			else
				return new double[]{20*height*height*0.78-weight*(1-bodyFatRatio/100),
						20*height*height*0.22-weight*bodyFatRatio/100};

		}
		else{//male
			if(Bmi>22&&bodyFatRatio<12)
				return new double[]{0,0};
			else
				return new double[]{22*height*height*0.88-weight*(1-bodyFatRatio/100),
						22*height*height*0.12-weight*bodyFatRatio/100};
		}
	}

	//骨骼肌计算
	public double calSkeletalMuscleMass(int sex,double height){
		return 0.8442*bodyWaterRatio*weight/100 - 2.9903;
	}

	//骨量计算
	public double calBoneMass(int sex,double muscleMass){
		return sex==0?(-1.22 + 0.0944 * this.muscleMass):(0.116 + 0.0525 * this.muscleMass);
	}

	//身体年龄计算
	private static ArrayList<double[]> preConOfBodyAge=new ArrayList<>(
			Arrays.asList(new double[]{24,28,32,35,38,42,46},//female
					new double[]{14,19,24,27,30,33,36}));//male

	public int calBodyAge(int sex,double bodyFatRatio,int age){
		for(int i=0;i<preConOfBodyAge.get(sex).length;i++){
			if(bodyFatRatio<preConOfBodyAge.get(sex)[i])
				return i<3?age-3+i:age-3+i+1;
		}
		return age+5;
	}

	//身体得分计算
	public double calBodyScore(int sex,double bodyFatRatio,double Bmi){
		double result=0;
		switch (sex){
			case 0:
				if(bodyFatRatio*100<=28){
					if(Bmi>=19) result= 90-(bodyFatRatio*100-24)+(Bmi-19)*2;
					else result= 90-(bodyFatRatio*100-24)*0.5+(Bmi-19)*4;
				}
				else{
					if(Bmi>=21) result= 90-(bodyFatRatio*100-28)*2-(Bmi-21);
					else result= 90-(bodyFatRatio*100-28)*2-(21-Bmi);
				}
				break;
			case 1:
				if(bodyFatRatio*100<=18){
					if(Bmi>=21) result= 90-(bodyFatRatio*100-14)+(Bmi-21)*2;
					else result= 90-(bodyFatRatio*100-14)*0.5+(Bmi-21)*4;
				}
				else{
					if(Bmi>=23) result= 90-(bodyFatRatio*100-18)*2-(Bmi-23);
					else result= 90-(bodyFatRatio*100-18)*2-(23-Bmi);
				}
				break;
			default:break;
		}
		//备注部分格式要求
		if(result<19) result =19;
		if(result>100) result=100;
		return result;
	}

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

	public DecimalFormat getDf1() {
		return df1;
	}

	public void setDf1(DecimalFormat df1) {
		this.df1 = df1;
	}

	public static ArrayList<double[]> getPreConOfBodyAge() {
		return preConOfBodyAge;
	}

	public static void setPreConOfBodyAge(ArrayList<double[]> bodyAge) {
		preConOfBodyAge = bodyAge;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	/**
	 * DB impl
	 *//*
	public static void save(Context context, MeasureData data) {
		if(context != null) {
			DBManager.getInstance(context).save(data);
		}
	}

	public static ArrayList<MeasureData> queryAllDesc(Context context) {
		if(context != null) {
			return DBManager.getInstance(context).query(
					new QueryBuilder<>(MeasureData.class).appendOrderDescBy(COL_TIME));
		}
		return new ArrayList<>();
	}*/
}
