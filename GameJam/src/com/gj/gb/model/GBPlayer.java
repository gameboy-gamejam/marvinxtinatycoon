package com.gj.gb.model;

import java.util.List;

public class GBPlayer {

	protected int gold;
	
	protected List<GBIngredient> ingredient;

	protected int popularity;

	public int getGold() {
		return gold;
	}

	public void setGold(int gold) {
		this.gold = gold;
	}

	public List<GBIngredient> getIngredient() {
		return ingredient;
	}

	public void setIngredient(List<GBIngredient> ingredient) {
		this.ingredient = ingredient;
	}

	public int getPopularity() {
		return popularity;
	}

	public void setPopularity(int popularity) {
		this.popularity = popularity;
	}
	
	// the number of customers per day
	public int getRatings(int day) {
		return this.popularity / day;
	}
}
