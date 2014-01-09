package com.gj.gb.util;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.gj.gb.factory.GBIngredientsFactory;
import com.gj.gb.logic.GBEconomics;
import com.gj.gb.model.GBGameData;
import com.gj.gb.model.GBGameData.GBDayState;
import com.gj.gb.model.GBIngredient;

public class GBDataManager {

	protected static Context CONTEXT_REF;

	protected static SharedPreferences PREFS;

	protected static GBGameData GAME_DATA;

	public static void init(Context context) {
		GBDataManager.CONTEXT_REF = context;
		initPrefs();
	}

	public static void cleanup() {
		GBDataManager.CONTEXT_REF = null;
		GBDataManager.PREFS = null;
		GBDataManager.GAME_DATA = null;
	}

	public static boolean hasSaveData() {
		if (PREFS == null) {
			initPrefs();
		}
		return PREFS.getAll().size() > 0;
	}

	public static void clearGameData() {
		GAME_DATA = null;
	}

	public static void clearGamePrefs() {
		PREFS.edit().clear().commit();
	}
	
	public static void createData() {
		// temporary fix
		if (GAME_DATA == null) {
			GAME_DATA = new GBGameData();

			GAME_DATA.setDayTotalCustomer(0);
			GAME_DATA.setDayTotalGold(0);
			GAME_DATA.setDayTotalRatings(0);
			GAME_DATA.setDayTotalExperience(0);
			GAME_DATA.setCurrentDay(1);
			GAME_DATA.setCurrentMonth(1);
			GAME_DATA.setCurrentYear(2014);
			GAME_DATA.setTotalDay(0);
			GAME_DATA.setCurrentGold(500);
			GAME_DATA.setDayState(GBDayState.MORNING);
			GAME_DATA.setLevel(1);
			GAME_DATA.setNextLevel(2);
			GAME_DATA.setExperience(0);
			GAME_DATA.setTotalCustomers(0);
			GAME_DATA.setStamina(10); // pano logic nito? haha

			/* assign starting ingredients */
			List<GBIngredient> ingredients = new ArrayList<GBIngredient>();

			// EGG
			GBIngredient egg = GBIngredientsFactory.getIngredientById(16);
			egg.setQuantity(20);
			ingredients.add(egg);

			// BUTTER
			GBIngredient butter = GBIngredientsFactory.getIngredientById(8);
			butter.setQuantity(10);
			ingredients.add(butter);

			GAME_DATA.setIngredients(ingredients);

			GBEconomics.initMarket();
		}
	}

	public static GBGameData getGameData() {
		return GAME_DATA;
	}

	public static void saveData() {
		if (PREFS == null)
			initPrefs();
		Editor edit = PREFS.edit();

		edit.putInt("currentDay", GAME_DATA.getCurrentDay());
		edit.putInt("currentMonth", GAME_DATA.getCurrentMonth());
		edit.putInt("currentYear", GAME_DATA.getCurrentYear());
		edit.putInt("totalDay", GAME_DATA.getTotalDay());
		edit.putInt("currentGold", GAME_DATA.getCurrentGold());
		edit.putString("dayState", GAME_DATA.getDayState().toString());
		edit.putInt("level", GAME_DATA.getLevel());
		edit.putInt("nextLevel", GAME_DATA.getNextLevel());
		edit.putInt("experience", GAME_DATA.getExperience());
		edit.putInt("totalCustomer", GAME_DATA.getTotalCustomers());
		edit.putInt("stamina", GAME_DATA.getStamina());
		edit.putInt("day_customer", GAME_DATA.getDayTotalCustomer());
		edit.putInt("day_gold", GAME_DATA.getDayTotalGold());
		edit.putInt("day_rating", GAME_DATA.getDayTotalRatings());
		edit.putInt("day_experience", GAME_DATA.getDayTotalExperience());

		String converted = convertIngredientList(GAME_DATA.getIngredients());
		edit.putString("ingredients", converted);
		
		GBEconomics.saveData(edit);
		
		edit.commit();
	}

	private static String convertIngredientList(List<GBIngredient> ingredients) {
		int n = ingredients.size();
		String retVal = "";

		for (int i = 0; i < n; i++) {
			GBIngredient ingredient = ingredients.get(i);

			int id = ingredient.getId();
			int qty = ingredient.getQuantity();

			if (i > 0) {
				retVal += ":";
			}

			retVal += id + "x" + qty;
		}

		return retVal;
	}

	private static void initPrefs() {
		GBDataManager.PREFS = CONTEXT_REF.getSharedPreferences("gb-prefs",
				Context.MODE_PRIVATE);
	}

	public static void loadData() {
		if (GAME_DATA == null) {
			GAME_DATA = new GBGameData();
	
			GAME_DATA.setCurrentDay(PREFS.getInt("currentDay", 1));
			GAME_DATA.setCurrentMonth(PREFS.getInt("currentMonth", 1));
			GAME_DATA.setCurrentYear(PREFS.getInt("currentYear", 2014));
			GAME_DATA.setTotalDay(PREFS.getInt("totalDay", 0));
			GAME_DATA.setCurrentGold(PREFS.getInt("currentGold", 1));
			GAME_DATA.setDayState(GBDayState.valueOf(PREFS.getString("dayState",
					GBDayState.MORNING.toString())));
			GAME_DATA.setLevel(PREFS.getInt("level", 1));
			GAME_DATA.setNextLevel(PREFS.getInt("nextLevel", 50));
			GAME_DATA.setExperience(PREFS.getInt("experience", 0));
			GAME_DATA.setTotalCustomers(PREFS.getInt("totalCustomer", 0));
			GAME_DATA.setStamina(PREFS.getInt("stamina", 10)); // pano logic nito?
																// haha
			GAME_DATA.setDayTotalCustomer(PREFS.getInt("day_customer", 0));
			GAME_DATA.setDayTotalGold(PREFS.getInt("day_gold", 0));
			GAME_DATA.setDayTotalRatings(PREFS.getInt("day_rating", 0));
			GAME_DATA.setDayTotalExperience(PREFS.getInt("day_experience", 0));
	
			List<GBIngredient> parsed = parseIngredientString(PREFS.getString(
					"ingredients", ""));
			GAME_DATA.setIngredients(parsed);
			
			GBEconomics.loadData(PREFS);
		}
	}
	
	private static List<GBIngredient> parseIngredientString(String string) {
		List<GBIngredient> retVal = new ArrayList<GBIngredient>();

		if (string.length() > 0) {
			String[] items = string.split(":");
			int n = items.length;
			for (int i = 0; i < n; i++) {
				String[] data = items[i].split("x");
				if (data.length == 2) {
					GBIngredient ingredient = GBIngredientsFactory
							.getIngredientById(Integer.valueOf(data[0]));
					ingredient.setQuantity(Integer.valueOf(data[1]));
					retVal.add(ingredient);
				}
			}
		}

		return retVal;
	}

	public static void setContextRef(Context context) {
		CONTEXT_REF = context;
	}
}
