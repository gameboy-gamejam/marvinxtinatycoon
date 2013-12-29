package com.gj.gb.model;

public class GBGameState {

	public enum GBDayState {
		
		MORNING,
		
		AFTERNOON,
		
		EVENING
	}
	
	protected GBDayState dayState;
	
	protected int currentDay;

	public GBDayState getDayState() {
		return dayState;
	}

	public void setDayState(GBDayState dayState) {
		this.dayState = dayState;
	}

	public int getCurrentDay() {
		return currentDay;
	}

	public void setCurrentDay(int currentDay) {
		this.currentDay = currentDay;
	}
}
