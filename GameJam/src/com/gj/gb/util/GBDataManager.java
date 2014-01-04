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

	public static void clear() {
		GAME_DATA = null;
		PREFS.edit().clear().commit();
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
			List<Integer> unlocked = new ArrayList<Integer>();
			unlocked.add(3);
			unlocked.add(4);
			unlocked.add(15);
			unlocked.add(22);
			unlocked.add(34);
			
			// init locked
			List<Integer> locked = new ArrayList<Integer>();
			locked.add(0);
			locked.add(1);
			locked.add(2);
			locked.add(6);
			locked.add(12);
			locked.add(14);
			locked.add(19);
			locked.add(20);
			locked.add(23);
			locked.add(24);
			locked.add(26);
			locked.add(27);
			locked.add(29);
			locked.add(31);
			locked.add(35);
			locked.add(36);
			locked.add(37);
			locked.add(39);
			locked.add(41);
			locked.add(43);
			locked.add(45);
			locked.add(46);
			
			GBEconomics.initMarket(market, unlocked, locked);
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
		
		String market = convertIngredientIdList(GBEconomics.getMarketList());
		String locked = convertIngredientIdList(GBEconomics.getLockedList());
		String unlocked = convertIngredientIdList(GBEconomics.getUnlockedList());
		edit.putString("market_list", market);
		edit.putString("locked_list", locked);
		edit.putString("unlocked_list", unlocked);

		edit.commit();
	}

	private static String convertIngredientIdList(List<Integer> ingredients) {
		int n = ingredients.size();
		String retVal = "";
		
		for (int i = 0; i < n; i++) {
			if (i > 0) {
				retVal += ":";
			}
			retVal += String.valueOf(ingredients.get(i));
		}
		
		return retVal;
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
		
		List<Integer> market = parseIngredientIdString(PREFS.getString("market_list", ""));
		List<Integer> locked = parseIngredientIdString(PREFS.getString("locked_list", ""));
		List<Integer> unlocked = parseIngredientIdString(PREFS.getString("unlocked_list", ""));
		GBEconomics.initMarket(market, locked, unlocked);
	}
	
	private static List<Integer> parseIngredientIdString(String string) {
		List<Integer> retVal = new ArrayList<Integer>();
		
		if (string.length() > 0) {
			String[] items = string.split(":");
			int n = items.length;
			for (int i = 0; i < n; i++) {
				retVal.add(Integer.valueOf(items[i]));
			}
		}
		
		return retVal;
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
