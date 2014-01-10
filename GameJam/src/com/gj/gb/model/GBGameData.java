package com.gj.gb.model;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import android.util.Log;

import com.gj.gb.factory.GBIngredientsFactory;
import com.gj.gb.factory.GBRecipeFactory;
import com.gj.gb.logic.GBStatsHelper;
import com.gj.gb.util.Utils;

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

	protected int gourmetPoints;

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
		return currentRating;
	}

	public void setCurrentRating(int currentRating) {
		this.currentRating = currentRating;
		if (this.currentRating < 0)
			this.currentRating = 0;
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
		return gourmetPoints;
	}

	public void setExperience(int experience) {
		this.gourmetPoints = experience;
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
		for (int i = 0; i < n; i++) {
			GBIngredient ingredient = ingredients.get(i);
			if (ingredient.getId() == id) {
				return ingredient.getQuantity();
			}
		}
		return 0;
	}

	public void removeIngredient(int id) {
		int n = this.ingredients.size();
		for (int i = 0; i < n; i++) {
			if (ingredients.get(i).getId() == id) {
				ingredients.remove(i);
				updateRecipe();
				break;
			}
		}
	}

	private void updateRecipe() {
		if (recipes == null)
			recipes = new ArrayList<GBRecipe>();

		recipes.clear();
		recipes.addAll(GBRecipeFactory.getAllAvailableRecipe());
	}

	public boolean hasIngredient(List<Integer> ingredients) {
		int n = ingredients.size();
		int m = this.ingredients.size();

		int ok = 0;
		for (int i = 0; i < n; i++) {
			int iid = ingredients.get(i);
			for (int j = 0; j < m; j++) {
				int jid = this.ingredients.get(j).getId();
				if (iid == jid) {
					ok++;
					break;
				}
			}
		}

		return ok == n;
	}

	public List<GBRecipe> getAvailableRecipes() {
		if (recipes == null || recipes.size() == 0) {
			recipes = new ArrayList<GBRecipe>();
			recipes.clear();
			updateRecipe();
		}
		return recipes;
	}

	public void updateIngredient(int id, int quantity) {
		int n = this.ingredients.size();
		for (int i = 0; i < n; i++) {
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
	 * @param stamina
	 *            the stamina to set
	 */
	public void setStamina(int stamina) {
		this.stamina = stamina;
	}

	public void useStamina() {
		stamina--;
	}

	public boolean update() {
		return updateDayState();
	}

	private boolean updateDayState() {
		if (dayState == GBDayState.MORNING) {
			dayState = GBDayState.AFTERNOON;
		} else if (dayState == GBDayState.AFTERNOON) {
			dayState = GBDayState.EVENING;
		} else if (dayState == GBDayState.EVENING) {
			return true;
		}
		return false;
	}

	public void updateDay() {
		dayState = GBDayState.MORNING;
		totalDay++;

		currentDay++;

		if (currentMonth == 2) {
			boolean isLeapYear = new GregorianCalendar()
					.isLeapYear(currentYear);
			if ((isLeapYear && currentDay > 29)
					|| (!isLeapYear && currentDay > 28)) {
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

		dayTotalCustomer = 0;
		dayTotalGold = 0;
		dayTotalRatings = 0;
		dayTotalExperience = 0;
	}

	private boolean is31() {
		return currentMonth == 1 || currentMonth == 3 || currentMonth == 5
				|| currentMonth == 7 || currentMonth == 8 || currentMonth == 10
				|| currentMonth == 12;
	}

	public void addDish(GBRecipe recipe, int qty) {
		for (int i = 0; i < qty; i++) {
			cookedDish.add(recipe);
		}
	}

	public void removeDish(int id) {
		int n = cookedDish.size();
		for (int i = 0; i < n; i++) {
			GBRecipe temp = cookedDish.get(i);
			if (temp.getId() == id) {
				cookedDish.remove(temp);
				break;
			}
		}
	}

	public List<GBRecipe> getReadyDish() {
		return cookedDish;
	}

	public boolean hasDish(int id) {
		int n = cookedDish.size();
		for (int i = 0; i < n; i++) {
			if (cookedDish.get(i).getId() == id) {
				return true;
			}
		}
		return false;
	}

	public void clearReadyDish() {
		this.cookedDish.clear();
	}

	public boolean hasLevel() {
		if (gourmetPoints >= nextLevel) {
			nextLevel = GBStatsHelper.calculateNextLevel(level, nextLevel);
			level++;
			return true;
		}
		return false;
	}

	public void refreshIngredients() {
		List<GBIngredient> toBeRemoved = new ArrayList<GBIngredient>();
		int n = this.ingredients.size();
		for (int i = 0; i < n; i++) {
			GBIngredient gbi = ingredients.get(i);
			if (gbi.getQuantity() == 0) {
				toBeRemoved.add(gbi);
			}
		}

		if (toBeRemoved.size() > 0) {
			int len = toBeRemoved.size();
			for (int i = 0; i < len; i++) {
				Log.w("test", "Removed!");
				this.ingredients.remove(toBeRemoved.get(i));
			}
		}
	}

	public void recoverStamina(int i) {
		stamina += i;
		if (stamina > 10) {
			stamina = 10;
		}
	}

	protected int dayTotalCustomer = 0;
	protected int dayTotalGold = 0;
	protected int dayTotalRatings = 0;
	private int dayTotalExperience = 0;

	public int getDayTotalCustomer() {
		return dayTotalCustomer;
	}

	public void setDayTotalCustomer(int dayTotalCustomer) {
		this.dayTotalCustomer = dayTotalCustomer;
	}

	public int getDayTotalGold() {
		return dayTotalGold;
	}

	public void setDayTotalGold(int dayTotalGold) {
		this.dayTotalGold = dayTotalGold;
	}

	public int getDayTotalRatings() {
		return dayTotalRatings;
	}

	public void setDayTotalRatings(int dayTotalRatings) {
		this.dayTotalRatings = dayTotalRatings;
	}

	public int getDayTotalExperience() {
		return dayTotalExperience;
	}

	public void setDayTotalExperience(int dayTotalExperience) {
		this.dayTotalExperience = dayTotalExperience;
	}

	List<GBRecipe> available;

	public List<GBRecipe> getMenu() {
		if (available == null) {
			available = new ArrayList<GBRecipe>();
			List<GBRecipe> recipes = getAvailableRecipes();
			int n = recipes.size();
			if (n < 4) {
				for (int i = 0; i < n; i++) {
					available.add(recipes.get(i));
				}
			} else {
				while (available.size() < 4) {
					GBRecipe random = recipes.get(Utils.RANDOM.nextInt(n));
					if (!available.contains(random)) {
						available.add(random);
					}
				}
			}
		}
		return available;
	}

	public void clearMenu() {
		if (available != null) {
			available.clear();
			available = null;
		}
	}
}
