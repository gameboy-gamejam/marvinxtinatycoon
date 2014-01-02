package com.gj.gb.model;

import java.util.ArrayList;
import java.util.List;

public class GBGameData {

	public enum GBDayState {
		
		MORNING,
		
		AFTERNOON,
		
		EVENING
	}
	
	protected GBDayState dayState;
	
	protected int currentDay;
	
	protected int currentMonth;
	
	protected int currentYear;
	
	protected int totalDay;

	protected int currentGold;
	
	protected int currentRating;
	
	protected int totalCustomers;

	protected List<GBIngredient> ingredients = new ArrayList<GBIngredient>();
	
	protected int level;
	
	protected int experience;
	
	protected int nextLevel;
	
	private int stamina;

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

	public int getCurrentGold() {
		return currentGold;
	}

	public void setCurrentGold(int currentGold) {
		this.currentGold = currentGold;
	}

	public int getCurrentRating() {
		// update rating value
		currentRating = totalCustomers / currentDay;
		return currentRating;
	}

	public void setCurrentRating(int currentRating) {
		this.currentRating = currentRating;
	}

	public int getTotalCustomers() {
		return totalCustomers;
	}

	public void setTotalCustomers(int totalCustomers) {
		this.totalCustomers = totalCustomers;
	}
	
	public boolean removeIngredient(GBIngredient ingredient) {
		return this.ingredients.remove(ingredient);
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getExperience() {
		return experience;
	}

	public void setExperience(int experience) {
		this.experience = experience;
	}

	public int getNextLevel() {
		return nextLevel;
	}

	public void setNextLevel(int nextLevel) {
		this.nextLevel = nextLevel;
	}

	public int getCurrentMonth() {
		return currentMonth;
	}

	public void setCurrentMonth(int currentMonth) {
		this.currentMonth = currentMonth;
	}

	public int getCurrentYear() {
		return currentYear;
	}

	public void setCurrentYear(int currentYear) {
		this.currentYear = currentYear;
	}

	public int getTotalDay() {
		return totalDay;
	}

	public void setTotalDay(int totalDay) {
		this.totalDay = totalDay;
	}

	public List<GBIngredient> getIngredients() {
		return ingredients;
	}

	public void setIngredients(List<GBIngredient> ingredients) {
		this.ingredients = ingredients;
	}

	public void addIngredient(GBIngredient ingredient) {
		this.ingredients.add(ingredient);
	}

	/**
	 * @return the stamina
	 */
	public int getStamina() {
		return stamina;
	}

	/**
	 * @param stamina the stamina to set
	 */
	public void setStamina(int stamina) {
		this.stamina = stamina;
	}
}
