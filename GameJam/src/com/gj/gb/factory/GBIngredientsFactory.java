package com.gj.gb.factory;

import com.gj.gb.model.GBIngredient;
import com.gj.gb.model.GBIngredient.IngredientCategory;

public class GBIngredientsFactory {

	public static final GBIngredient getIngredientById(int id) {
		switch (id) {
		case 0: return new GBIngredient(id, "EGG", IngredientCategory.OTHERS, 5, 1);
		case 1: return new GBIngredient(id, "MILK", IngredientCategory.OTHERS, 15, 1);
		case 2: return new GBIngredient(id, "CHICKEN", IngredientCategory.MEAT, 50, 2);
		case 3: return new GBIngredient(id, "BEEF", IngredientCategory.MEAT, 85, 2);
		case 4: return new GBIngredient(id, "PORK", IngredientCategory.MEAT, 60, 2);
		case 5: return new GBIngredient(id, "POTATO", IngredientCategory.CROPS, 8, 1);
		case 6: return new GBIngredient(id, "SALAD", IngredientCategory.CROPS, 12, 2);
		case 7: return new GBIngredient(id, "CARROT", IngredientCategory.CROPS, 8, 1);
		case 8: return new GBIngredient(id, "PEPPERONI", IngredientCategory.MEAT, 100, 2);
		case 9: return new GBIngredient(id, "CHEESE", IngredientCategory.OTHERS, 30, 2);
		case 10: return new GBIngredient(id, "TOMATO", IngredientCategory.CROPS, 8, 1);
		default: return null;
		}
	}
}
