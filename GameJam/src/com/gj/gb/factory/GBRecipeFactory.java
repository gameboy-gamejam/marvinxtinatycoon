package com.gj.gb.factory;

import com.gj.gb.model.GBRecipe;

public class GBRecipeFactory {

	public static final GBRecipe getRecipeById(int id) {
		
		switch (id) {
		case 0: return new GBRecipe(id, "ROAST CHICKEN", "", 150, new int[] { 2, 5, 6 });
		case 1: return new GBRecipe(id, "ROAST BEEF", "", 185, new int[] { 3, 6, 7 });
		case 2: return new GBRecipe(id, "PEPPERONI PIZZA", "", 100, new int[] { 8, 9, 10 });
		default: return null;
		}
	}
}
