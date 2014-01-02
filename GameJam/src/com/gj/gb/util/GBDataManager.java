package com.gj.gb.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.gj.gb.model.GBGameData;
import com.gj.gb.model.GBGameData.GBDayState;

public class GBDataManager {

	protected static Context CONTEXT_REF;
	
	protected static SharedPreferences PREFS;
	
	protected static GBGameData GAME_DATA;
	
	public static void init(Context context) {
		GBDataManager.CONTEXT_REF = context;
		GBDataManager.PREFS = context.getSharedPreferences("gb-prefs", Context.MODE_PRIVATE);
	}
	
	public static void cleanup() {
		GBDataManager.CONTEXT_REF = null;
		GBDataManager.PREFS = null;
		GBDataManager.GAME_DATA = null;
	}
	
	public static boolean hasSaveData() {
		return PREFS.getAll().size() > 0;
	}
	
	public static void clear() {
		PREFS.edit().clear().commit();
	}
	
	public static void createData() {
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
	}
	
	public static GBGameData getGameData() {
		return GAME_DATA;
	}

	public static void saveData() {
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
		
		edit.commit();
	}

	public static void loadData() {
		GAME_DATA = new GBGameData();
		
		GAME_DATA.setCurrentDay(PREFS.getInt("currentDay", 1));
		GAME_DATA.setCurrentMonth(PREFS.getInt("currentMonth", 1));
		GAME_DATA.setCurrentYear(PREFS.getInt("currentYear", 2014));
		GAME_DATA.setTotalDay(PREFS.getInt("totalDay", 0));
		GAME_DATA.setCurrentGold(PREFS.getInt("currentGold", 1));
		GAME_DATA.setDayState(GBDayState.valueOf(PREFS.getString("dayState", GBDayState.MORNING.toString())));
		GAME_DATA.setLevel(PREFS.getInt("level", 1));
		GAME_DATA.setNextLevel(PREFS.getInt("nextLevel", 50));
		GAME_DATA.setExperience(PREFS.getInt("experience", 0));
		GAME_DATA.setTotalCustomers(PREFS.getInt("totalCustomer", 0));
		GAME_DATA.setStamina(PREFS.getInt("stamina", 10)); // pano logic nito? haha
	}
}
