package com.gj.gb.model;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import android.util.Log;

import com.gj.gb.factory.GBIngredientsFactory;
import com.gj.gb.logic.GBEconomics;

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
	
	public void removeIngredient(int id) {
		int n = this.ingredients.size();
		for (int i=0; i<n; i++) {
			if (ingredients.get(i).getId() == id) {
				ingredients.remove(i);
				break;
			}
		}
	}

	public void updateIngredient(int id, int quantity) {
		int n = this.ingredients.size();
		for (int i=0; i<n; i++) {
			GBIngredient ingredient = ingredients.get(i);
			if (ingredient.getId() == id) {
				ingredient.setQuantity(ingredient.getQuantity() + quantity);
				return; // quick return
			}
		}
		
		GBIngredient ingredient = GBIngredientsFactory.getIngredientById(id);
		ingredient.setQuantity(quantity);
		ingredients.add(ingredient);
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

	public void update() {
		updateDayState();
	}

	private void updateDayState() {
		if (dayState == GBDayState.MORNING) {
			dayState = GBDayState.AFTERNOON;
		} else if (dayState == GBDayState.AFTERNOON) {
			dayState = GBDayState.EVENING;
		} else if (dayState == GBDayState.EVENING) {
			dayState = GBDayState.MORNING;
			updateDay();
			GBEconomics.update();
		}
	}

	private void updateDay() {
		totalDay++;
		Log.w("called", "Day: " + totalDay);
		
		currentDay++;
		
		if (currentMonth == 2) {
			boolean isLeapYear = new GregorianCalendar().isLeapYear(currentYear);
			if ((isLeapYear && currentDay > 29) || (!isLeapYear && currentDay > 28)) {
				currentDay = 1;
				currentMonth++;
			}
		} else if (currentDay > 31 && is31()) {
			currentDay = 1;
			currentMonth++;
		} else if (currentDay > 30) {
			currentDay = 1;
			currentMonth++;
		}
		
		if (currentMonth > 12) {
			currentMonth = 1;
			currentYear++;
		}
	}

	private boolean is31() {
		return currentMonth == 1 || currentMonth == 3 || currentMonth == 5
				|| currentMonth == 7 || currentMonth == 8 || currentMonth == 10
				|| currentMonth == 12;
	}
}
