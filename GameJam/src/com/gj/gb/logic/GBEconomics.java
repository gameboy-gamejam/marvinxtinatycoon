package com.gj.gb.logic;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Color;

import com.gj.gb.util.Utils;

public class GBEconomics {

	protected static final float[] MARKET_RATE = { 0.5f, 0.75f, 1.0f, 1.5f, 2.0f };
	
	protected static float currentMarketRate = 1.0f;
	
	/* decreases as day increase, when value is equal to 0, market rate is changed */
	protected static int marketRateDuration = 5;

	/* every 7 days, market demand changes */
	protected static int marketDemandDuration = 7;
	
	protected static final int MIN_CUSTOMER = 3;
	
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
			
			// reset duration to 7 days
			marketDemandDuration = 7;
		}
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
			// a minimum of 4 customer in the player's restaurant
			customer += Utils.RANDOM.nextInt(8) + 3;
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
	protected static List<Integer> unlocked = new ArrayList<Integer>();
	protected static List<Integer> locked = new ArrayList<Integer>();

	public static void initMarket(List<Integer> onMarket, List<Integer> unlocked, List<Integer> locked) {
		GBEconomics.onMarket.clear();
		GBEconomics.onMarket.addAll(onMarket);
		
		GBEconomics.locked.clear();
		GBEconomics.locked.addAll(locked);
		
		GBEconomics.unlocked.clear();
		GBEconomics.unlocked.addAll(unlocked);
	}
	
	public static List<Integer> getMarketList() {
		return onMarket;
	}
	
	public static List<Integer> getLockedList() {
		return locked;
	}
	
	public static List<Integer> getUnlockedList() {
		return unlocked;
	}
	
	public static void cleanup() {
		onMarket.clear();
		unlocked.clear();
		locked.clear();
		
		onMarket = null;
		unlocked = null;
		locked = null;
	}
}