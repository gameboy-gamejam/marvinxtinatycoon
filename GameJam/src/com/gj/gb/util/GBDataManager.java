package com.gj.gb.util;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.widget.Toast;

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
		Toast.makeText(CONTEXT_REF, "CLEARED: " + PREFS.getAll().size(), Toast.LENGTH_SHORT).show();
	}
	
	public static void createData() {
		// temporary fix
		if (GAME_DATA == null) {
			GAME_DATA = new GBGameData();

			GAME_DATA.setCurrentDay(1);
			GAME_DATA.setCurrentMonth(1);
			GAME_DATA.setCurrentYear(2014);
			GAME_DATA.setTotalDay(0);
			GAME_DATA.setCurrentGold(500);
			GAME_DATA.setDayState(GBDayState.MORNING);
			GAME_DATA.setLevel(1);
			GAME_DATA.setNextLevel(50);
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
			
			// init on market
			List<Integer> market = new ArrayList<Integer>();
			market.add(5);
			market.add(7);
			market.add(8);
			market.add(9);
			market.add(10);
			market.add(11);
			market.add(13);
			market.add(16);
			market.add(17);
			market.add(20);
			market.add(21);
			market.add(25);
			market.add(28);
			market.add(30);
			market.add(32);
			market.add(33);
			market.add(38);
			market.add(40);
			market.add(42);
			market.add(44);
			
			// init unlocked
			List<Integer> offmarket = new ArrayList<Integer>();
			offmarket.add(0);
			offmarket.add(1);
			offmarket.add(2);
			offmarket.add(3);
			offmarket.add(4);
			offmarket.add(6);
			offmarket.add(12);
			offmarket.add(14);
			offmarket.add(15);
			offmarket.add(19);
			offmarket.add(20);
			offmarket.add(22);
			offmarket.add(23);
			offmarket.add(24);
			offmarket.add(26);
			offmarket.add(27);
			offmarket.add(29);
			offmarket.add(31);
			offmarket.add(34);
			offmarket.add(35);
			offmarket.add(36);
			offmarket.add(37);
			offmarket.add(39);
			offmarket.add(41);
			offmarket.add(43);
			offmarket.add(45);
			offmarket.add(46);
			
			GBEconomics.initMarket(market, offmarket);
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
}
