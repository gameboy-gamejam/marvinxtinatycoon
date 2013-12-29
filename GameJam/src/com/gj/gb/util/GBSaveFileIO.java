package com.gj.gb.util;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.gj.gb.factory.GBIngredientsFactory;
import com.gj.gb.model.GBGameState;
import com.gj.gb.model.GBIngredient;
import com.gj.gb.model.GBPlayer;
import com.gj.gb.model.GBGameState.GBDayState;

public class GBSaveFileIO {

	public static void saveGameState(Context context, GBGameState state) {
		SharedPreferences prefs = context.getSharedPreferences("gb-prefs", Context.MODE_PRIVATE);
		
		Editor edit = prefs.edit();
		
		edit.putString("daystate", state.getDayState().toString());
		edit.putInt("currentday", state.getCurrentDay());
		
		edit.commit();
	}
	
	public static void savePlayerData(Context context, GBPlayer player) {
		SharedPreferences prefs = context.getSharedPreferences("gb-prefs", Context.MODE_PRIVATE);
		
		Editor edit = prefs.edit();
		
		edit.putInt("gold", player.getGold());
		edit.putInt("rating", player.getPopularity());
		
		String ingredients = parseIngredientDataFromList(player.getIngredient());
		edit.putString("ingredients", ingredients);
		
		edit.commit();
	}

	private static String parseIngredientDataFromList(List<GBIngredient> ingredientList) {
		String ingredients = "";
		
		int n = ingredientList.size();
		
		for (int i=0; i<n; i++) {
			GBIngredient ingredient = ingredientList.get(i);
			
			if (i > 0) {
				ingredients += ":";
			} else {
				ingredients += ingredient.getId() + "x" + ingredient.getQuantity();
			}
		}
		
		return ingredients;
	}
	
	public static GBGameState loadGameState(Context context) {
		SharedPreferences prefs = context.getSharedPreferences("gb-prefs", Context.MODE_PRIVATE);
		GBGameState state = new GBGameState();
		
		state.setCurrentDay(prefs.getInt("currentday", 1));
		state.setDayState(GBDayState.valueOf(prefs.getString("daystate", GBDayState.MORNING.toString())));
		
		return state;
	}
	
	public static GBPlayer loadPlayerData(Context context) {
		SharedPreferences prefs = context.getSharedPreferences("gb-prefs", Context.MODE_PRIVATE);
		GBPlayer player = new GBPlayer();
		
		player.setGold(prefs.getInt("gold", 500));
		player.setPopularity(prefs.getInt("rating", 0));
		
		List<GBIngredient> ingredients = parseIngredientDataFromString(prefs.getString("ingredients", ""));
		player.setIngredient(ingredients);
		
		return player;
	}

	private static List<GBIngredient> parseIngredientDataFromString(
			String ingredientString) {
		List<GBIngredient> ingredients = new ArrayList<GBIngredient>();
		
		String[] data = ingredientString.split(":");
		int n = data.length;
		
		for (int i=0; i<n; i++) {
			String ingredient = data[i];
			String[] ingredientData = ingredient.split("x");
			
			GBIngredient item = GBIngredientsFactory.getIngredientById(Integer.valueOf(ingredientData[0]));
			if (item != null) {
				item.setQuantity(Integer.valueOf(ingredientData[1]));
				ingredients.add(item);
			}
		}
		
		return ingredients;
	}
}
