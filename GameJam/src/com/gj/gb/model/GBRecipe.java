package com.gj.gb.model;

import java.util.List;

public class GBRecipe {

	protected int id;
	
	protected String name;
	
	protected String description;
	
	protected int price;
	
	protected List<Integer> ingredients;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public List<Integer> getIngredients() {
		return ingredients;
	}

	public void setIngredients(List<Integer> ingredients) {
		this.ingredients = ingredients;
	}
}
