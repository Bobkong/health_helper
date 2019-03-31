package com.example.bob.health_helper.Bean;

import com.example.bob.health_helper.Util.SharedPreferenceUtil;
import com.google.gson.annotations.SerializedName;
import com.lifesense.ble.bean.WeightAppendData;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Bob on 2019/3/23.
 */

public class MeasureData implements Serializable {

	/*
		sex:0-female,1-male
		*/
	private double bodyScore;//身体得分
	private double Bmi;//BMI指数
	private int bodyAge;//身体年龄
	private double visceralFat;//内脏脂肪
	private double baselMetabolism;//基础代谢
	private double bodyFatRatio;//脂肪率
	private double muscleMassRatio;//肌肉率
	private double protein;//蛋白质
	private double bodyWaterRatio;//水分率
	private double bodyFatMass;//脂肪量
	private double muscleMass;//肌肉量
	private double skeletalMuscleMass;//骨骼肌
	private double boneMass;//骨量
	private double fatControl;//脂肪控制
	private double muscleControl;//肌肉控制
	private double fatFreeMass;//去脂体重
	private String time;
	private double weight;

	public MeasureData(double bodyScore, double bmi, int bodyAge, double visceralFat, double baselMetabolism, double bodyFatRatio, double muscleMassRatio, double protein, double bodyWaterRatio, double bodyFatMass, double muscleMass, double skeletalMuscleMass, double boneMass, double fatControl, double muscleControl, double fatFreeMass, String time, double weight) {
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

	public MeasureData(WeightAppendData weightAppendData, double height, double weight, int age, int sex) {
		this.weight = weight;

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
		this.bodyScore=dataFormat(calBodyScore(sex,this.bodyFatRatio,this.Bmi));
	}

	//数据格式
	private DecimalFormat df1=new DecimalFormat("#.#");
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
				if(bodyFatRatio<=28){
					if(Bmi>=19) result= 90-(bodyFatRatio-24)+(Bmi-19)*2;
					else result= 90-(bodyFatRatio-24)*0.5+(Bmi-19)*4;
				}
				else{
					if(Bmi>=21) result= 90-(bodyFatRatio-28)*2-(Bmi-21);
					else result= 90-(bodyFatRatio-28)*2-(21-Bmi);
				}
				break;
			case 1:
				if(bodyFatRatio<=18){
					if(Bmi>=21) result= 90-(bodyFatRatio-14)+(Bmi-21)*2;
					else result= 90-(bodyFatRatio-14)*0.5+(Bmi-21)*4;
				}
				else{
					if(Bmi>=23) result= 90-(bodyFatRatio-18)*2-(Bmi-23);
					else result= 90-(bodyFatRatio-18)*2-(23-Bmi);
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

	@Override
	public String toString() {
		return "MeasureData{" +
				"bodyScore=" + bodyScore +
				", Bmi=" + Bmi +
				", bodyAge=" + bodyAge +
				", visceralFat=" + visceralFat +
				", baselMetabolism=" + baselMetabolism +
				", bodyFatRatio=" + bodyFatRatio +
				", muscleMassRatio=" + muscleMassRatio +
				", protein=" + protein +
				", bodyWaterRatio=" + bodyWaterRatio +
				", bodyFatMass=" + bodyFatMass +
				", muscleMass=" + muscleMass +
				", skeletalMuscleMass=" + skeletalMuscleMass +
				", boneMass=" + boneMass +
				", fatControl=" + fatControl +
				", muscleControl=" + muscleControl +
				", fatFreeMass=" + fatFreeMass +
				", time=" + time +
				", weight=" + weight +
				", df1=" + df1 +
				'}';
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

	public MeasureBean getMeasureBean(){
		MeasureBean res = new MeasureBean();
		res.setBodyScore(bodyScore);
		res.setBmi(Bmi);
		res.setBodyAge(bodyAge);
		res.setVisceralFat(visceralFat);
		res.setBaselMetabolism(baselMetabolism);
		res.setBodyFatRatio(bodyFatRatio);
		res.setMuscleMassRatio(muscleMassRatio);
		res.setProtein(protein);
		res.setBodyWaterRatio(bodyWaterRatio);
		res.setBodyFatMass(bodyFatMass);
		res.setMuscleMass(muscleMass);
		res.setSkeletalMuscleMass(skeletalMuscleMass);
		res.setBoneMass(boneMass);
		res.setFatControl(fatControl);
		res.setMuscleControl(muscleControl);
		res.setFatFreeMass(fatFreeMass);
		res.setTime(time);
		res.setWeight(weight);
		res.setUid(SharedPreferenceUtil.getUser().getUid());
		return res;
	}
}
