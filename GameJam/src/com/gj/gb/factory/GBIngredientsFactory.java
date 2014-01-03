package com.gj.gb.factory;

import com.gj.gb.model.GBIngredient;
import com.gj.gb.model.GBIngredient.IngredientCategory;

public class GBIngredientsFactory {

	public static final GBIngredient getIngredientById(int id) {
		switch (id) {
		case 0: return new GBIngredient(id, "APPLE", IngredientCategory.FRUIT, 15, 1);
		case 1: return new GBIngredient(id, "BACON", IngredientCategory.MEAT, 50, 2);
		case 2: return new GBIngredient(id, "BANANA", IngredientCategory.FRUIT, 10, 1);
		case 3: return new GBIngredient(id, "BASIL", IngredientCategory.OTHERS, 25, 3);
		case 4: return new GBIngredient(id, "BAYLEAF", IngredientCategory.OTHERS, 5, 1);
		case 5: return new GBIngredient(id, "BEANS", IngredientCategory.CROPS, 12, 1);
		case 6: return new GBIngredient(id, "BEEF", IngredientCategory.MEAT, 70, 2);
		case 7: return new GBIngredient(id, "BREAD", IngredientCategory.OTHERS, 8, 1);
		case 8: return new GBIngredient(id, "BUTTER", IngredientCategory.OTHERS, 20, 1);
		case 9: return new GBIngredient(id, "CABBAGE", IngredientCategory.CROPS, 12, 1);
		case 10: return new GBIngredient(id, "CARROT", IngredientCategory.CROPS, 8, 1);
		case 11: return new GBIngredient(id, "CHEESE", IngredientCategory.OTHERS, 20, 1);
		case 12: return new GBIngredient(id, "CHICKEN", IngredientCategory.MEAT, 50, 2);
		case 13: return new GBIngredient(id, "CHILI", IngredientCategory.CROPS, 5, 1);
		case 14: return new GBIngredient(id, "CHOCOLATE", IngredientCategory.OTHERS, 30, 3);
		case 15: return new GBIngredient(id, "CRAB", IngredientCategory.FISH, 40, 3);
		case 16: return new GBIngredient(id, "EGG", IngredientCategory.OTHERS, 5, 1);
		case 17: return new GBIngredient(id, "GARLIC", IngredientCategory.CROPS, 5, 1);
		case 18: return new GBIngredient(id, "GINGER", IngredientCategory.CROPS, 8, 1);
		case 19: return new GBIngredient(id, "ICE CREAM", IngredientCategory.OTHERS, 25, 2);
		case 20: return new GBIngredient(id, "LAMB", IngredientCategory.MEAT, 85, 4);
		case 21: return new GBIngredient(id, "LEEK", IngredientCategory.OTHERS, 4, 1);
		case 22: return new GBIngredient(id, "LEMON", IngredientCategory.FRUIT, 20, 2);
		case 23: return new GBIngredient(id, "LIME", IngredientCategory.FRUIT, 20, 2);
		case 24: return new GBIngredient(id, "LOBSTER", IngredientCategory.FISH, 90, 4);
		case 25: return new GBIngredient(id, "MILK", IngredientCategory.OTHERS, 15, 1);
		case 26: return new GBIngredient(id, "MUSHROOM", IngredientCategory.CROPS, 18, 2);
		case 27: return new GBIngredient(id, "NOODLES", IngredientCategory.OTHERS, 20, 2);
		case 28: return new GBIngredient(id, "ONION", IngredientCategory.CROPS, 5, 1);
		case 29: return new GBIngredient(id, "PASTA", IngredientCategory.OTHERS, 35, 2);
		case 30: return new GBIngredient(id, "PEAS", IngredientCategory.OTHERS, 5, 1);
		case 31: return new GBIngredient(id, "PEPPERONI", IngredientCategory.OTHERS, 55, 3);
		case 32: return new GBIngredient(id, "PORK", IngredientCategory.MEAT, 50, 2);
		case 33: return new GBIngredient(id, "POTATO", IngredientCategory.CROPS, 5, 1);
		case 34: return new GBIngredient(id, "PRAWN", IngredientCategory.FISH, 20, 1);
		case 35: return new GBIngredient(id, "PUMPKIN", IngredientCategory.CROPS, 30, 3);
		case 36: return new GBIngredient(id, "RICE", IngredientCategory.OTHERS, 20, 1);
		case 37: return new GBIngredient(id, "SAFFRON", IngredientCategory.CROPS, 50, 4);
		case 38: return new GBIngredient(id, "SALAD", IngredientCategory.OTHERS, 15, 1);
		case 39: return new GBIngredient(id, "SALMON", IngredientCategory.FISH, 100, 4);
		case 40: return new GBIngredient(id, "SEAWEED", IngredientCategory.FISH, 10, 1);
		case 41: return new GBIngredient(id, "STRAWBERRY", IngredientCategory.FRUIT, 20, 3);
		case 42: return new GBIngredient(id, "SWEETCORN", IngredientCategory.OTHERS, 18, 2);
		case 43: return new GBIngredient(id, "TOFU", IngredientCategory.OTHERS, 10, 1);
		case 44: return new GBIngredient(id, "TOMATO", IngredientCategory.CROPS, 5, 1);
		case 45: return new GBIngredient(id, "TUNA", IngredientCategory.FISH, 115, 4);
		case 46: return new GBIngredient(id, "WASABI", IngredientCategory.OTHERS, 30, 3);
		default: return null;
		}
	}
}
