package com.gj.gb.model;

import java.util.List;

public class GBPlayer {

	protected int gold;
	
	protected List<Integer> ingredient;
	
	protected List<Integer> recipe;
	
	protected int popularity;

	public int getGold() {
		return gold;
	}

	public void setGold(int gold) {
		this.gold = gold;
	}

	public List<Integer> getIngredient() {
		return ingredient;
	}

	public void setIngredient(List<Integer> ingredient) {
		this.ingredient = ingredient;
	}

	public List<Integer> getRecipe() {
		return recipe;
	}

	public void setRecipe(List<Integer> recipe) {
		this.recipe = recipe;
	}

	public int getPopularity() {
		return popularity;
	}

	public void setPopularity(int popularity) {
		this.popularity = popularity;
	}
	
}
