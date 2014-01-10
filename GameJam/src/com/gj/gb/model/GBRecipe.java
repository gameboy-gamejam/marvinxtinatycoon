package com.gj.gb.model;

import java.util.ArrayList;
import java.util.List;

public class GBRecipe {

	protected int id;

	protected String name;

	protected String description;

	protected List<Integer> ingredients;

	private int quantity;

	public GBRecipe(int id, String name, String description, int... ingredient) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.ingredients = new ArrayList<Integer>();
		int n = ingredient.length;
		for (int i = 0; i < n; i++) {
			this.ingredients.add(ingredient[i]);
		}
	}

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

	public List<Integer> getIngredients() {
		return ingredients;
	}

	public void setIngredients(List<Integer> ingredients) {
		this.ingredients = ingredients;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
}
