package com.gj.gb.factory;

import java.util.ArrayList;
import java.util.List;

import com.gj.gb.model.GBGameData;
import com.gj.gb.model.GBRecipe;
import com.gj.gb.util.GBDataManager;

public class GBRecipeFactory {

	public static final GBRecipe getRecipeById(int id) {

		switch (id) {
		case 0:
			return new GBRecipe(id, "72OZ STEAK", "Sample", 6, 6, 6);
		case 1:
			return new GBRecipe(id, "APPLE TARTIN", "Sample", 0, 19);
		case 2:
			return new GBRecipe(id, "BANANA SPLIT", "Sample", 2, 19);
		case 3:
			return new GBRecipe(id, "BEANS AND MUSHROOM NOODLES", "Sample", 5,
					26, 27);
		case 4:
			return new GBRecipe(id, "BEEF ENCHILADA", "Sample", 13, 11, 6);
		case 5:
			return new GBRecipe(id, "BISCOTTI", "Sample", 16, 14);
		case 6:
			return new GBRecipe(id, "BLEU VAMPIRE STEAK", "Sample", 6, 33, 44);
		case 7:
			return new GBRecipe(id, "BRIGADERIOS", "Sample", 14, 8, 25);
		case 8:
			return new GBRecipe(id, "BRUSCHETTA WITH TOMATO AND BASIL",
					"Sample", 7, 44, 3);
		case 9:
			return new GBRecipe(id, "BURGER AND FRIES", "Sample", 7, 6, 33);
		case 10:
			return new GBRecipe(id, "CABBAGE ROLLS", "Sample", 36, 6, 9);
		case 11:
			return new GBRecipe(id, "CAESAR SALAD", "Sample", 38, 7, 16);
		case 12:
			return new GBRecipe(id, "CAMEMBERT BAKED IN A BOX", "Sample", 11,
					11, 7);
		case 13:
			return new GBRecipe(id, "CARIBBEAN CHICKEN SALAD", "Sample", 12, 5,
					38);
		case 14:
			return new GBRecipe(id, "CHEESE BOARD", "Sample", 11, 11, 11);
		case 15:
			return new GBRecipe(id, "CHEESE BURGER", "Sample", 7, 6, 11);
		case 16:
			return new GBRecipe(id, "CHICKEN ADOBO", "Sample", 12, 17, 4);
		case 17:
			return new GBRecipe(id, "CHICKEN AND LEEK SOUP", "Sample", 12, 21,
					21);
		case 18:
			return new GBRecipe(id, "CHICKEN BUCKET", "Sample", 12, 12, 12);
		case 19:
			return new GBRecipe(id, "CHICKEN TACOS", "Sample", 42, 12, 38);
		case 20:
			return new GBRecipe(id, "CHICKEN TIKKA MASALA", "Sample", 12, 36, 4);
		case 21:
			return new GBRecipe(id, "CHILI CON CARNE", "Sample", 13, 5, 36);
		case 22:
			return new GBRecipe(id, "CHILI CRABS", "Sample", 15, 15, 13);
		case 23:
			return new GBRecipe(id, "CHILI DOG", "Sample", 32, 13);
		case 24:
			return new GBRecipe(id, "CHOCOLATE CAKE WITH ICECREAM", "Sample",
					14, 19);
		case 25:
			return new GBRecipe(id, "CORN ON COB", "Sample", 42, 42, 8);
		case 26:
			return new GBRecipe(id, "CORNDOGS", "Sample", 42, 8, 6);
		case 27:
			return new GBRecipe(id, "CRAB-STUFFED MUSHROOM", "Sample", 15, 26,
					28);
		case 28:
			return new GBRecipe(id, "CREAM CHEESE BAGEL", "Sample", 11, 7, 38);
		case 29:
			return new GBRecipe(id, "CREAM OF MUSHROOM SOUP", "Sample", 26, 25,
					4);
		case 30:
			return new GBRecipe(id, "DIMSUM", "Sample", 34, 32);
		case 31:
			return new GBRecipe(id, "DONER KEBAB", "Sample", 20, 28, 38);
		case 32:
			return new GBRecipe(id, "FALAFEL", "Sample", 5, 28, 17);
		case 33:
			return new GBRecipe(id, "FISH PIE", "Sample", 45, 30, 33);
		case 34:
			return new GBRecipe(id, "FONDUE SET", "Sample", 11, 0, 7);
		case 35:
			return new GBRecipe(id, "FRENCH FRIES", "Sample", 33, 33, 11);
		case 36:
			return new GBRecipe(id, "FRUIT SELECTION", "Sample", 41, 0);
		case 37:
			return new GBRecipe(id, "GARDEN SALAD", "Sample", 38, 44, 16);
		case 38:
			return new GBRecipe(id, "GARLIC AND BUTTER PRAWNS", "Sample", 17,
					34, 8);
		case 39:
			return new GBRecipe(id, "GINGERBREAD HOUSE", "Sample", 18, 16, 7);
		case 40:
			return new GBRecipe(id, "GNOCCHI", "Sample", 29, 44, 3);
		case 41:
			return new GBRecipe(id, "GRILLED RUMP STEAK", "Sample", 6, 33, 38);
		case 42:
			return new GBRecipe(id, "HAM AND CHEESE SANDWICH", "Sample", 7, 32,
					11);
		case 43:
			return new GBRecipe(id, "HAMBURGER", "Sample", 7, 6);
		case 44:
			return new GBRecipe(id, "LAMB CASEROLE", "Sample", 20, 26);
		case 45:
			return new GBRecipe(id, "LAMB SAMOSAS", "Sample", 20, 4);
		case 46:
			return new GBRecipe(id, "LAMB SKEWERS", "Sample", 20, 17, 22);
		case 47:
			return new GBRecipe(id, "LASAGNA", "Sample", 6, 11, 29);
		case 48:
			return new GBRecipe(id, "LEMON FIZZ", "Sample", 23, 22);
		case 49:
			return new GBRecipe(id, "LOBSTER", "Sample", 24, 8, 38);
		case 50:
			return new GBRecipe(id, "LOBSTER SOUP", "Sample", 24, 8, 22);
		case 51:
			return new GBRecipe(id, "LOBSTER THERMIDOR", "Sample", 24, 24, 16);
		case 52:
			return new GBRecipe(id, "MACARONI AND CHEESE", "Sample", 29, 11);
		case 53:
			return new GBRecipe(id, "MAPO TOFU", "Sample", 43, 36, 13);
		case 54:
			return new GBRecipe(id, "MEAT PLATTER", "Sample", 31, 32, 7);
		case 55:
			return new GBRecipe(id, "MEGA NACHOS", "Sample", 42, 13, 11);
		case 56:
			return new GBRecipe(id, "MISO SOUP", "Sample", 40, 5, 43);
		case 57:
			return new GBRecipe(id, "MONSTER BURGER", "Sample", 6, 6, 11);
		case 58:
			return new GBRecipe(id, "MUSHROOM RISOTTO", "Sample", 36, 26);
		case 59:
			return new GBRecipe(id, "ONION SOUP", "Sample", 28, 17, 4);
		case 60:
			return new GBRecipe(id, "PAD THAI", "Sample", 27, 34, 23);
		case 61:
			return new GBRecipe(id, "PASTEL", "Sample", 12, 11);
		case 62:
			return new GBRecipe(id, "PASTRAMI SANDWICH", "Sample", 6, 17, 7);
		case 63:
			return new GBRecipe(id, "PEA AND BACON SOUP", "Sample", 30, 30, 1);
		case 64:
			return new GBRecipe(id, "PHO SOUP", "Sample", 6, 27, 13);
		case 65:
			return new GBRecipe(id, "PIZZA CALZONE", "Sample", 26, 31, 21);
		case 66:
			return new GBRecipe(id, "PIZZA FLORENTINE", "Sample", 11, 38, 16);
		case 67:
			return new GBRecipe(id, "PORK ADOBO", "Sample", 32, 17, 4);
		case 68:
			return new GBRecipe(id, "PORK AND APPLE CHOPS", "Sample", 32, 0, 28);
		case 69:
			return new GBRecipe(id, "PRAWN SUSHI", "Sample", 34, 40);
		case 70:
			return new GBRecipe(id, "PUMPKIN SOUP", "Sample", 35, 13);
		case 71:
			return new GBRecipe(id, "RAINBOW SUSHI", "Sample", 45, 45, 36);
		case 72:
			return new GBRecipe(id, "RIB 'N' WINGS", "Sample", 32, 12, 42);
		case 73:
			return new GBRecipe(id, "ROAST BEEF", "Sample", 6, 10, 38);
		case 74:
			return new GBRecipe(id, "ROAST CHICKEN", "Sample", 12, 33, 38);
		case 75:
			return new GBRecipe(id, "SAKURA MOCHI", "Sample", 5, 36);
		case 76:
			return new GBRecipe(id, "SALMON HANDROLLS", "Sample", 39, 36, 40);
		case 77:
			return new GBRecipe(id, "SALMON KEBABS WITH CHILI LIME GLAZE",
					"Sample", 39, 23, 13);
		case 78:
			return new GBRecipe(id, "SALMON SASHIMI", "Sample", 39, 39, 46);
		case 79:
			return new GBRecipe(id, "SALMON SUSHI", "Sample", 36, 39, 46);
		case 80:
			return new GBRecipe(id, "SCHINTZEL", "Sample", 32, 7, 44);
		case 81:
			return new GBRecipe(id, "SCRAMBLED EGGS", "Sample", 16, 8);
		case 82:
			return new GBRecipe(id, "SEAFOOD CHOWDER", "Sample", 39, 15, 25);
		case 83:
			return new GBRecipe(id, "SEAFOOD PAELLA", "Sample", 34, 36, 37);
		case 84:
			return new GBRecipe(id, "SEAFOOD PASTA", "Sample", 29, 34, 45);
		case 85:
			return new GBRecipe(id, "SEAFOOD PLATTER", "Sample", 39, 15, 24);
		case 86:
			return new GBRecipe(id, "SEAWEED SALAD", "Sample", 40, 18, 13);
		case 87:
			return new GBRecipe(id, "SHRIMP ON THE BARBIE", "Sample", 34, 22,
					17);
		case 88:
			return new GBRecipe(id, "SMOKED SALMON PLATTER", "Sample", 39, 39,
					22);
		case 89:
			return new GBRecipe(id, "SOUR STRAWBERRY SWIRLIES", "Sample", 23,
					41, 22);
		case 90:
			return new GBRecipe(id, "SPAGHETTI BOLOGNESE", "Sample", 29, 44, 6);
		case 91:
			return new GBRecipe(id, "SPAGHETTI CARBONARA", "Sample", 29, 6, 16);
		case 92:
			return new GBRecipe(id, "SPICY BUFFALO WINGS", "Sample", 12, 8, 13);
		case 93:
			return new GBRecipe(id, "STEAK AND CHIPS", "Sample", 6, 6, 33);
		case 94:
			return new GBRecipe(id, "STRAWBERRY CAKE", "Sample", 8, 41);
		case 95:
			return new GBRecipe(id, "STRAWBERRY CHEESECAKE", "Sample", 11, 41);
		case 96:
			return new GBRecipe(id, "STRAWBERRY SUNDAE", "Sample", 19, 41, 41);
		case 97:
			return new GBRecipe(id, "SUNDAE", "Sample", 14, 19);
		case 98:
			return new GBRecipe(id, "SWEET RICE CAKE", "Sample", 36, 36);
		case 99:
			return new GBRecipe(id, "SWEETCORN SOUP", "Sample", 42, 16);
		case 100:
			return new GBRecipe(id, "THE CHICKENATOR", "Sample", 12, 12, 16);
		case 101:
			return new GBRecipe(id, "TIGER PRAWN PLATTER", "Sample", 34, 17, 23);
		case 102:
			return new GBRecipe(id, "TOMATO AND BASIL SOUP", "Sample", 44, 3);
		case 103:
			return new GBRecipe(id, "TOMATO AND ONION TART", "Sample", 44, 28);
		case 104:
			return new GBRecipe(id, "TUNA AND SWEETCORN PANINI", "Sample", 42,
					45, 7);
		case 105:
			return new GBRecipe(id, "TUNA SALAD", "Sample", 45, 29);
		case 106:
			return new GBRecipe(id, "TUNA STEAK", "Sample", 45, 33, 10);
		case 107:
			return new GBRecipe(id, "TUNA SUSHI", "Sample", 36, 45, 46);
		case 108:
			return new GBRecipe(id, "VEGAN TOFU DELIGHT", "Sample", 43, 5, 38);
		case 109:
			return new GBRecipe(id, "VEGETABLE CHOWDER", "Sample", 25, 33, 30);
		case 110:
			return new GBRecipe(id, "VEGETARIAN FRIED RICE", "Sample", 36, 21,
					26);
		case 111:
			return new GBRecipe(id, "VEGETARIAN PLATTER", "Sample", 44, 38, 42);
		case 112:
			return new GBRecipe(id, "VEGETARIAN SOUP NOODLE", "Sample", 27, 26,
					43);
		case 113:
			return new GBRecipe(id, "YAKI GYOZA", "Sample", 32, 18, 17);
		case 114:
			return new GBRecipe(id, "YAKI UDON", "Sample", 27, 12);
		case 115:
			return new GBRecipe(id, "YAKIBUTA RAMEN", "Sample", 27, 32, 28);

		default:
			return null;
		}
	}
	
	public static List<GBRecipe> getAllAvailableRecipe() {
		GBGameData data = GBDataManager.getGameData();
		List<GBRecipe> list = new ArrayList<GBRecipe>();
		
		for (int i=0; i<116; i++) {
			GBRecipe recipe = getRecipeById(i);
			if (data.hasIngredient(recipe.getIngredients())) {
				list.add(recipe);
			}
		}
		
		return list;
	}
}
