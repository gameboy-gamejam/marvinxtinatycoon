package com.gj.gb.logic;

import java.util.ArrayList;
import java.util.List;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;

import com.gj.gb.factory.GBIngredientsFactory;
import com.gj.gb.model.GBRecipe;
import com.gj.gb.util.Utils;

public class GBEconomics {

	protected static final float[] MARKET_RATE = { 0.5f, 0.75f, 1.0f, 1.5f, 2.0f };
	
	protected static float currentMarketRate = 1.0f;
	
	/* decreases as day increase, when value is equal to 0, market rate is changed */
	protected static int marketRateDuration = 5;

	/* every 7 days, market demand changes */
	protected static int marketDemandDuration = 7;
	
	protected static final int MIN_CUSTOMER = 5;
	
	// this should be called everytime the player advances to next day
	public static void update() {
		updateMarketRate();
		updateMarketDemand();
	}
	
	public static int recomputePrice(int originalPrice) {
		float newPrice = currentMarketRate * originalPrice;
		return Math.round(newPrice);
	}
	
	public static int getRateColor() {
		if (currentMarketRate < 1) {
			return Color.BLUE;
		} else if (currentMarketRate > 1) {
			return Color.RED;
		} else {
			return Color.BLACK;
		}
	}

	private static void updateMarketDemand() {
		marketDemandDuration--;
		
		if (marketDemandDuration == 0) {
			shuffleMarketList();
			
			// reset duration to 7 days
			marketDemandDuration = 7;
		}
	}

	private static void shuffleMarketList() {
		List<Integer> toBeRemoved = new ArrayList<Integer>();
		int n = onMarket.size();
		
		while(toBeRemoved.size() < 5) {
			int index = Utils.RANDOM.nextInt(n);
			int iid = onMarket.get(index);
			if (toBeRemoved.contains(iid)) {
				continue;
			} else {
				toBeRemoved.add(iid);
			}
		}
		
		for (int i=0; i<5; i++) {
			onMarket.remove(Integer.valueOf(toBeRemoved.get(i)));
		}
		
		List<Integer> toBeAdded = new ArrayList<Integer>();
		int m = offMarket.size();

		while(toBeAdded.size() < 5) {
			int index = Utils.RANDOM.nextInt(m);
			int iid = offMarket.get(index);
			if (toBeAdded.contains(iid)) {
				continue;
			} else {
				toBeAdded.add(iid);
			}
		}
		
		for (int i=0; i<5; i++) {
			offMarket.remove(Integer.valueOf(toBeAdded.get(i)));
		}
		
		onMarket.addAll(0, toBeAdded);
		offMarket.addAll(toBeRemoved);
	}

	private static void updateMarketRate() {
		marketRateDuration--;
		
		if (marketRateDuration == 0) {
			float rate = MARKET_RATE[Utils.RANDOM.nextInt(MARKET_RATE.length)];
			if (rate == currentMarketRate) {
				currentMarketRate = 1.0f;
			} else {
				currentMarketRate = rate;
			}
			/* randomly selects value from 2-10 */
			marketRateDuration = Utils.RANDOM.nextInt(9) + 2;
		}
	}

	public static int getDayCustomerCount(int ratings) {
		boolean influx = isCustomerInflux();
		
		int max = MIN_CUSTOMER + (MIN_CUSTOMER * ratings);
		int customer = Utils.RANDOM.nextInt(max) + MIN_CUSTOMER;
		
		if (influx) {
			// if there is influx of customer, there will be at least
			// a minimum of 7 customer in the player's restaurant
			customer += Utils.RANDOM.nextInt(8) + 2;
		}
		
		return customer;
	}
	
	/* generate random value between 1 to 10, if number is equal to 2
	 * there will be customer influx */
	private static boolean isCustomerInflux() {
		int random = Utils.RANDOM.nextInt(10) + 1;
		return random == 2;
	}
	
	protected static List<Integer> onMarket = new ArrayList<Integer>();
	protected static List<Integer> offMarket = new ArrayList<Integer>();

	public static List<Integer> getMarketList() {
		return onMarket;
	}

	public static List<Integer> getOffMarketList() {
		return offMarket;
	}
	
	public static void cleanup() {
		onMarket.clear();
		offMarket.clear();
		
		onMarket = null;
		offMarket = null;
	}
	
	public static void saveData(Editor edit) {
		String market = convertIngredientIdList(GBEconomics.getMarketList());
		String unlocked = convertIngredientIdList(GBEconomics.getOffMarketList());
		edit.putString("market_list", market);
		edit.putString("off_list", unlocked);

		edit.putFloat("current_rate", currentMarketRate);
		edit.putInt("market_rate_duration", marketRateDuration);
		edit.putInt("market_demand_duration", marketDemandDuration);
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
	
	public static void loadData(SharedPreferences prefs) {
		if (onMarket != null) {
			onMarket.clear();
		}
		onMarket = parseIngredientIdString(prefs.getString("market_list", ""));
	
		if (offMarket != null) {
			offMarket.clear();
		}
		offMarket = parseIngredientIdString(prefs.getString("off_list", ""));
		
		currentMarketRate = prefs.getFloat("current_rate", 1.0f);
		marketRateDuration = prefs.getInt("market_rate_duration", 5);
		marketDemandDuration = prefs.getInt("market_demand_duration", 7);
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

	public static void initMarket() {
		onMarket.clear();
		onMarket.add(5);
		onMarket.add(7);
		onMarket.add(8);
		onMarket.add(9);
		onMarket.add(10);
		onMarket.add(11);
		onMarket.add(13);
		onMarket.add(16);
		onMarket.add(17);
		onMarket.add(20);
		onMarket.add(21);
		onMarket.add(25);
		onMarket.add(28);
		onMarket.add(30);
		onMarket.add(32);
		onMarket.add(33);
		onMarket.add(38);
		onMarket.add(40);
		onMarket.add(42);
		onMarket.add(44);
		
		offMarket.clear();
		offMarket.add(0);
		offMarket.add(1);
		offMarket.add(2);
		offMarket.add(3);
		offMarket.add(4);
		offMarket.add(6);
		offMarket.add(12);
		offMarket.add(14);
		offMarket.add(15);
		offMarket.add(19);
		offMarket.add(20);
		offMarket.add(22);
		offMarket.add(23);
		offMarket.add(24);
		offMarket.add(26);
		offMarket.add(27);
		offMarket.add(29);
		offMarket.add(31);
		offMarket.add(34);
		offMarket.add(35);
		offMarket.add(36);
		offMarket.add(37);
		offMarket.add(39);
		offMarket.add(41);
		offMarket.add(43);
		offMarket.add(45);
		offMarket.add(46);
	}
	
	public static int getRecipePrice(GBRecipe recipe) {
		List<Integer> ingredients = recipe.getIngredients();
		
		int n = ingredients.size();
		int totalPrice = 0;
		
		for (int i=0; i<n; i++) {
			totalPrice += GBIngredientsFactory.getIngredientById(ingredients.get(i)).getPrice();
		}
		
		totalPrice -= 20;
		
		if (totalPrice < 10) totalPrice = 10;
		
		return totalPrice;
	}
}