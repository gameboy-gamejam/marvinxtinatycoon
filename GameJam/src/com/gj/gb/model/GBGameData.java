package com.gj.gb.model;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import com.gj.gb.factory.GBIngredientsFactory;
import com.gj.gb.factory.GBRecipeFactory;
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
	
	protected List<GBRecipe> recipes = new ArrayList<GBRecipe>();
	
	protected int level;
	
	protected int experience;
	
	protected int nextLevel;
	
	private int stamina;
	
	private List<GBRecipe> cookedDish = new ArrayList<GBRecipe>();

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
	
	public void addCustomerCount(int customer) {
		this.totalCustomers += customer;
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
	
	public int getIngredientQty(int id) {
		int n = ingredients.size();
		for (int i=0; i<n; i++) {
			GBIngredient ingredient = ingredients.get(i);
			if (ingredient.getId() == id) {
				return ingredient.getQuantity();
			}
		}
		return 0;
	}
	
	public void removeIngredient(int id) {
		int n = this.ingredients.size();
		for (int i=0; i<n; i++) {
			if (ingredients.get(i).getId() == id) {
				ingredients.remove(i);
				updateRecipe();
				break;
			}
		}
	}
	
	private void updateRecipe() {
		if (recipes == null) recipes = new ArrayList<GBRecipe>();
		
		recipes.clear();
		recipes.addAll(GBRecipeFactory.getAllAvailableRecipe());
	}

	public boolean hasIngredient(List<Integer> ingredients) {
		int n = ingredients.size();
		int m = this.ingredients.size();
		
		int ok = 0;
		for (int i=0; i<n; i++) {
			int iid = ingredients.get(i);
			for (int j=0; j<m; j++) {
				int jid = this.ingredients.get(j).getId();
				if (iid == jid) {
					ok++;
					break;
				}
			}
		}
		
		return ok == n;
	}
	
	public List<GBRecipe> getRecipes() {
		if (recipes == null || recipes.size() == 0) {
			recipes = new ArrayList<GBRecipe>();
			recipes.clear();
			updateRecipe();
		}
		return recipes;
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
		updateRecipe();
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
	
	public void addDish(GBRecipe recipe, int qty) {
		for (int i=0; i<qty; i++) {
			cookedDish.add(recipe);
		}
	}
	
	public void removeDish(GBRecipe recipe) {
		cookedDish.remove(recipe);
	}
	
	public List<GBRecipe> getReadyDish() {
		return cookedDish;
	}
}
