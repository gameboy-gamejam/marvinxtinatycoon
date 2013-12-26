package com.gj.gb.model;

public class GBGameState {

	public enum GBDayState {
		
		MORNING,
		
		AFTERNOON,
		
		EVENING
	}
	
	protected GBDayState dayState;
	
	protected int currentDay;
}
