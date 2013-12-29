package com.gj.gb.model;

public class GBIngredient {

	public enum IngredientCategory {
		
		CROPS, // vegetables and other ingredients that can be harvested from land
		
		FISH, // fish products
		
		MEAT, // meat products
		
		FRUIT, // products that can be harvested from tree
		
		OTHERS
	}
	
	protected int id;
	
	protected String name;

	protected int price;
	
	protected IngredientCategory category;
	
	/* value from 1-5 */
	protected int rarity;

	public GBIngredient(int id, String name, IngredientCategory category, int price, int rarity) {
		super();
		this.id = id;
		this.name = name;
		this.category = category;
		this.price = price;
		this.rarity = rarity;
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

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public int getRarity() {
		return rarity;
	}

	public void setRarity(int rarity) {
		this.rarity = rarity;
	}

	public IngredientCategory getCategory() {
		return category;
	}

	public void setCategory(IngredientCategory category) {
		this.category = category;
	}
}
