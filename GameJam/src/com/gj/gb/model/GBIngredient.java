package com.gj.gb.model;

import com.gj.gb.factory.GBIngredientsFactory;

public class GBIngredient {

	public enum IngredientCategory {
		
		CROPS, // vegetables and other ingredients that can be harvested from land
		
		FISH, // fish products
		
		MEAT, // meat products
		
		FRUIT, // products that can be harvested from tree
		
		OTHERS
	}
	
	protected GBIngredientsFactory ingredient;
	
	private int quantity;

	public GBIngredient(GBIngredientsFactory ingredient) {
		this.ingredient = ingredient;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	public int getId(){
	    return ingredient.getId();
	}
	
	public String getName(){
	    return ingredient.getName();
	}
	
	public int getPrice(){
	    return ingredient.getPrice();
	}
	
	public int getRarity(){
	    return ingredient.getRarityLevel();
	}
}
