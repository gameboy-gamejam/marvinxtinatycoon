package com.gj.gb.factory;

import com.gj.gb.model.GBRecipe;

public class GBRecipeFactory {

	public static final GBRecipe getRecipeById(int id) {
		
		switch (id) {
		case 0: return new GBRecipe(id, "Roast Chicken", "Raw chicken that has been cooked in an oven or on a spit or barbecue, or possibly pot-roasted in the pan on a stove-top.", 150, new int[] { 2, 5, 6 });
		case 1: return new GBRecipe(id, "Roast Beef", " A dish of beef which is roasted in an oven. Essentially prepared as a main meal, the leftovers can be and are often served within sandwiches.", 185, new int[] { 3, 6, 7 });
		case 2: return new GBRecipe(id, "Pepperoni Pizza", "Tomato and cheese pizza with pepperoni.", 100, new int[] { 8, 9, 10 });
		default: return null;
		}
	}
}
