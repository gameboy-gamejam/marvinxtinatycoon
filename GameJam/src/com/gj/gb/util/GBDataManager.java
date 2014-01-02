package com.gj.gb.util;

import android.content.Context;
import android.content.SharedPreferences;

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
		GAME_DATA.setTotalDay(1);
		GAME_DATA.setCurrentGold(500);
		GAME_DATA.setCurrentRating(0);
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
}
