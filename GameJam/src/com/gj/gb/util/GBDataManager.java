package com.gj.gb.util;

import android.content.Context;
import android.content.SharedPreferences;

public class GBDataManager {

	protected static Context contextRef;
	
	protected static SharedPreferences prefs;
	
	public static void init(Context context) {
		GBDataManager.contextRef = context;
		GBDataManager.prefs = context.getSharedPreferences("gb-prefs", Context.MODE_PRIVATE);
	}
	
	public static void cleanup() {
		GBDataManager.contextRef = null;
	}
	
	public static boolean hasSaveData() {
		return prefs.getAll().size() > 0;
	}
	
	public static void clear() {
		prefs.edit().clear().commit();
	}
}
