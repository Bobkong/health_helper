package com.example.bob.health_helper.Bean;

/**
 * Created by Bob on 2019/3/26.
 */

public class MeasureGridItem {
	private int name;
	private int unit;
	private double value;
	private int color;
	private int state;
	private int suggestion;

	public MeasureGridItem(int name, double value, int unit, int color,int state,int suggestion) {
		this.name = name;
		this.unit = unit;
		this.value = value;
		this.color = color;
		this.state = state;
		this.suggestion = suggestion;
	}

	public int getName() {
		return name;
	}

	public void setName(int name) {
		this.name = name;
	}

	public int getUnit() {
		return unit;
	}

	public void setUnit(int unit) {
		this.unit = unit;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

	public int getColor() {
		return color;
	}

	public void setColor(int color) {
		this.color = color;
	}

	public int getSuggestion() {
		return suggestion;
	}

	public void setSuggestion(int suggestion) {
		this.suggestion = suggestion;
	}
}
