package com.example.bob.health_helper.Util;


import com.example.bob.health_helper.Bean.MeasureBean;
import com.example.bob.health_helper.Bean.MeasureData;
import com.example.bob.health_helper.Bean.MeasureGridItem;
import com.example.bob.health_helper.R;


import java.util.ArrayList;

import static java.lang.Math.pow;

/**
 * Created by Bob on 2018/11/20.
 */

public class MeasureGridUtils {
	private static MeasureGridUtils instance;
	public static int INDEX_NUM = 15;
	public static ArrayList<MeasureGridItem> STANDARD = new ArrayList<>();
	public static void getInstance(MeasureBean data){
		if (instance == null){
			instance = new MeasureGridUtils();
		}
		STANDARD.clear();
		STANDARD.add(new MeasureGridItem(R.string.bmi,data.getBmi(),R.string.blank,getBmiState(data.getBmi())[0],getBmiState(data.getBmi())[1],getBmiState(data.getBmi())[2]));
		STANDARD.add(new MeasureGridItem(R.string.bodyAge,data.getBodyAge(),R.string.years_old,getAgeState(data.getBodyAge())[0],getAgeState(data.getBodyAge())[1],getAgeState(data.getBodyAge())[2]));
		STANDARD.add(new MeasureGridItem(R.string.gutLevel,data.getVisceralFat(),R.string.blank,getVisceralFatState(data.getVisceralFat())[0],getVisceralFatState(data.getVisceralFat())[1],getVisceralFatState(data.getVisceralFat())[2]));
		STANDARD.add(new MeasureGridItem(R.string.metabolize,data.getBaselMetabolism(),R.string.cal,getBaselMetabolismState(data.getBaselMetabolism())[0],getBaselMetabolismState(data.getBaselMetabolism())[1],getBaselMetabolismState(data.getBaselMetabolism())[2]));
		STANDARD.add(new MeasureGridItem(R.string.oilPercent,data.getBodyFatRatio(),R.string.percent,getFatRatioState(data.getBodyFatRatio())[0],getFatRatioState(data.getBodyFatRatio())[1],getFatRatioState(data.getBodyFatRatio())[2]));
		STANDARD.add(new MeasureGridItem(R.string.musclePercent,data.getMuscleMassRatio(),R.string.percent,getMuscleMassRatio(data.getMuscleMassRatio())[0],getMuscleMassRatio(data.getMuscleMassRatio())[1],getMuscleMassRatio(data.getMuscleMassRatio())[2]));
		STANDARD.add(new MeasureGridItem(R.string.protein,data.getProtein(),R.string.percent,getProteinState(data.getProtein())[0],getProteinState(data.getProtein())[1],getProteinState(data.getProtein())[2]));
		STANDARD.add(new MeasureGridItem(R.string.waterPercent,data.getBodyWaterRatio(),R.string.percent,getBodyWaterRatioState(data.getBodyWaterRatio())[0],getBodyWaterRatioState(data.getBodyWaterRatio())[1],getBodyWaterRatioState(data.getBodyWaterRatio())[2]));
		STANDARD.add(new MeasureGridItem(R.string.oilValue,data.getBodyFatMass(),R.string.kg,getFatRatioState(data.getBodyFatRatio())[0],getFatRatioState(data.getBodyFatRatio())[1],getFatRatioState(data.getBodyFatRatio())[2]));
		STANDARD.add(new MeasureGridItem(R.string.muscleValue,data.getMuscleMass(),R.string.kg,getMuscleMassState(data.getMuscleMass())[0],getMuscleMassState(data.getMuscleMass())[1],getMuscleMassState(data.getMuscleMass())[2]));
		STANDARD.add(new MeasureGridItem(R.string.boneMuscle,data.getSkeletalMuscleMass(),R.string.kg,getSkeletalMuscleState(data.getSkeletalMuscleMass())[0],getSkeletalMuscleState(data.getSkeletalMuscleMass())[1],getSkeletalMuscleState(data.getSkeletalMuscleMass())[2]));
		STANDARD.add(new MeasureGridItem(R.string.boneValue,data.getBoneMass(),R.string.kg,getBoneMassState(data.getBoneMass(),data.getWeight())[0],getBoneMassState(data.getBoneMass(),data.getWeight())[1],getBoneMassState(data.getBoneMass(),data.getWeight())[2]));
		STANDARD.add(new MeasureGridItem(R.string.weightWithoutOil,data.getFatFreeMass(),R.string.kg,getFatFreeMassState(data.getFatFreeMass(),data.getWeight())[0],getFatFreeMassState(data.getFatFreeMass(),data.getWeight())[1],getFatFreeMassState(data.getFatFreeMass(),data.getWeight())[2]));
		STANDARD.add(new MeasureGridItem(R.string.oilControl,data.getFatControl(),R.string.kg,getControlState(data.getFatControl())[0],getControlState(data.getFatControl())[1],0));
		STANDARD.add(new MeasureGridItem(R.string.muscleControl,data.getMuscleControl(),R.string.kg,getControlState(data.getMuscleControl())[0],getControlState(data.getMuscleControl())[1],0));

	}

	private static int getIndex(double value,double[] standard){
		for (int i = 0;i < standard.length;i++){
			if (value < standard[i]){
				return i;
			}
		}
		return standard.length - 1;
	}
	public static int[] getBmiState(double value){
		double[] standard = new double[]{18.5,24,28,35};
		int[] color = new int[]{R.color.low,R.color.standard,R.color.high,R.color.super_high};
		int[] text = new int[]{R.string.thin,R.string.perfect,R.string.fat,R.string.superFat};
		int res = getIndex(value,standard);
		return new int[]{color[res],text[res],R.string.bmi_suggestion};
	}

	private static final double[][] FatRatioStandard = new double[][]{
			{13,23,28},//男，小于39岁
			{13,24,29},//男，大于39岁
			{22,34,39},//女，小于39岁
			{23,35,40}//女，大于39岁
	};

	public static int[] getFatRatioState(double value){
		int std = 0;
		if (SharedPreferenceUtil.getUser().getGender().equals("女")){
			std += 2;
		}
		if (SharedPreferenceUtil.getUser().getAge() > 39){
			std += 1;
		}

		double[] standard = FatRatioStandard[std];
		int[] suggestion = new int[]{R.string.fat_ratio_low,R.string.fat_ratio_perfect,R.string.fat_ratio_high,R.string.fat_ratio_super_high};
		int[] color = new int[]{R.color.low,R.color.perfect,R.color.high,R.color.super_high};
		int[] text = new int[]{R.string.low,R.string.perfect,R.string.high,R.string.superHigh};
		int res = getIndex(value,standard);
		return new int[]{color[res],text[res],suggestion[res]};
	}

	public static int[] getAgeState(int value){
		if ((SharedPreferenceUtil.getUser().getAge() - value) > 0){
			return new int[]{R.color.standard,R.string.young,R.string.body_age_young};
		}else {
			return new int[]{R.color.high,R.string.old,R.string.body_age_old};
		}
	}

	public static int[] getVisceralFatState(double value){
		double[] standard = new double[]{10,15,25};
		int[] suggestion = new int[]{R.string.gutLevel_perfect,R.string.gutLevel_high,R.string.gutLevel_super_high};
		int[] color = new int[]{R.color.perfect,R.color.high,R.color.super_high};
		int[] text = new int[]{R.string.perfect,R.string.high,R.string.superHigh};
		int res = getIndex(value,standard);
		return new int[]{color[res],text[res],suggestion[res]};
	}

	private static final double[][] BaselMetabolismStandard = new double[][]{
			{1550},
			{1500},
			{1350},
			{1220},
			{1210},
			{1170},
			{1110},
			{1010}
	};

	public static int[] getBaselMetabolismState(double value){
		int age = SharedPreferenceUtil.getUser().getAge();
		int std = 0;
		if (SharedPreferenceUtil.getUser().getGender().equals("女")){
			std += 1;
		}
		if (age < 50 && age > 29){
			std += 1;
		}else if (age < 70){
			std += 2;
		}else if (age >= 70){
			std += 3;
		}
		double[] standard = BaselMetabolismStandard[std];
		int[] suggestion = new int[]{R.string.metabolize_low,R.string.metabolize_perfect};
		int[] color = new int[]{R.color.low,R.color.perfect};
		int[] text = new int[]{R.string.low,R.string.perfect};
		int res = getIndex(value,standard);
		return new int[]{color[res],text[res],suggestion[res]};
	}

	private static final double[][] MuscleMassRatioStandard = new double[][]{
			{72,89.1},
			{67,82.1}
	};

	public static int[] getMuscleMassRatio(double value){
		int std = 0;
		if (SharedPreferenceUtil.getUser().getGender().equals("女")){
			std += 1;
		}
		int[] suggestion = new int[]{R.string.muscle_ratio_low,R.string.muscle_ratio_standard,R.string.muscle_ratio_perfect};
		int[] color = new int[]{R.color.low,R.color.standard,R.color.perfect};
		int[] text = new int[]{R.string.low,R.string.standard,R.string.perfect};
		int res = getIndex(value,MuscleMassRatioStandard[std]);
		return new int[]{color[res],text[res],suggestion[res]};
	}

	public static int[] getProteinState(double value){
		double[] standard = new double[]{16,20,30};
		int[] suggestion = new int[]{R.string.protein_low,R.string.protein_standard,R.string.protein_perfect};
		int[] color = new int[]{R.color.low,R.color.standard,R.color.perfect};
		int[] text = new int[]{R.string.low,R.string.standard,R.string.perfect};
		int res = getIndex(value,standard);
		return new int[]{color[res],text[res],suggestion[res]};
	}

	private static final double[][] BodyWaterRatioStandard = new double[][]{
			{55,65},
			{45,60}
	};

	public static int[] getBodyWaterRatioState(double value){
		int std = 0;
		if (SharedPreferenceUtil.getUser().getGender().equals("女")){
			std += 1;
		}
		int[] suggestion = new int[]{R.string.water_ratio_low,R.string.water_ratio_standard,R.string.water_ratio_perfect};
		int[] color = new int[]{R.color.low,R.color.standard,R.color.perfect};
		int[] text = new int[]{R.string.low,R.string.standard,R.string.perfect};
		int res = getIndex(value,BodyWaterRatioStandard[std]);
		return new int[]{color[res],text[res],suggestion[res]};
	}

	private static final double[][] MuscleMassStandard = new double[][]{
			{38.5,46.6},
			{44.0,52.5},
			{49.4,59.5},
			{29.1,34.8},
			{32.9,37.6},
			{36.5,42.6}
	};

	public static int[] getMuscleMassState(double value){
		int std = 0;
		if (SharedPreferenceUtil.getUser().getGender().equals("女")){
			std += 3;
		}
		int[] suggestion = new int[]{R.string.muscle_mass_low,R.string.muscle_mass_standard,R.string.muscle_mass_perfect};
		int[] color = new int[]{R.color.low,R.color.standard,R.color.perfect};
		int[] text = new int[]{R.string.low,R.string.standard,R.string.perfect};
		int res = getIndex(value,MuscleMassStandard[std]);
		return new int[]{color[res],text[res],suggestion[res]};
	}

	public static int[] getSkeletalMuscleState(double value){
		int height = SharedPreferenceUtil.getUser().getHeight();
		double standardSkeletalMuscle = SharedPreferenceUtil.getUser().getGender().equals("女")
				? (21 * pow(height, 2) * 0.42)
				: (22 * pow(height, 2) * 0.47);
		int[] suggestion = new int[]{R.string.bone_muscle_low,R.string.bone_muscle_standard,R.string.bone_muscle_perfect};
		int[] color = new int[]{R.color.low,R.color.standard,R.color.perfect};
		int[] text = new int[]{R.string.low,R.string.standard,R.string.perfect};
		double[] SkeletalMuscleMassStandard = new double[]{
				standardSkeletalMuscle * 0.9,
				standardSkeletalMuscle * 1.101
		};
		int res = getIndex(value,SkeletalMuscleMassStandard);
		return new int[]{color[res],text[res],suggestion[res]};
	}

	private static final double[][] BoneMassStandard = new double[][]{
			{2.5},{2.9},{3.2},
			{1.8},{2.2},{2.5}
	};

	public static int[] getBoneMassState(double value,double weight){
		int std = 0;
		if (SharedPreferenceUtil.getUser().getGender().equals("女")){
			std += 3;
			if (weight > 60){
				std += 2;
			}else if(weight >= 45){
				std += 1;
			}
		}else{
			if (weight > 75){
				std += 2;
			}else if(weight >= 60){
				std += 1;
			}
		}
		int[] suggestion = new int[]{R.string.bone_low,R.string.bone_perfect};
		int[] color = new int[]{R.color.low,R.color.perfect};
		int[] text = new int[]{R.string.low,R.string.perfect};
		int res = getIndex(value,BoneMassStandard[std]);
		return new int[]{color[res],text[res],suggestion[res]};
	}

	public static int[] getControlState(double value){
		if (value == 0){
			return new int[]{R.color.perfect,R.string.stay};
		}else if (value > 0){
			return new int[]{R.color.low,R.string.need_add};
		}else{
			return new int[]{R.color.high,R.string.need_sub};
		}
	}

	private static final double[][] FatFreeMassStandard = new double[][]{
			{0.72,0.77,0.87},
			{0.71,0.76,0.87},
			{0.61,0.66,0.78},
			{0.60,0.65,0.77}
	};

	public static int[] getFatFreeMassState(double value,double weight){
		int std = 0;
		if (SharedPreferenceUtil.getUser().getGender().equals("女")){
			std += 2;
		}
		if (SharedPreferenceUtil.getUser().getAge() > 39){
			std += 1;
		}
		int[] suggestion = new int[]{R.string.fat_free_super_low,R.string.fat_free_low,R.string.fat_ratio_perfect,R.string.fat_free_high};
		int[] color = new int[]{R.color.super_low,R.color.low,R.color.perfect,R.color.high};
		int[] text = new int[]{R.string.superLow,R.string.low,R.string.perfect,R.string.high};
		int res = getIndex(value/weight,FatFreeMassStandard[std]);
		return new int[]{color[res],text[res],suggestion[res]};
	}


}
