package com.gj.gb.logic;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Color;

import com.gj.gb.util.Utils;

public class GBEconomics {

	protected static final float[] MARKET_RATE = { 0.5f, 0.75f, 1.0f, 1.5f, 2.0f };
	
	protected static float currentMarketRate = 2.0f;
	
	/* decreases as day increase, when value is equal to 0, market rate is changed */
	protected static int marketRateDuration = 5;
	
	/* recipes that are in demand are more likely to be ordered in your restaurant if you have one */
	protected static List<Integer> inDemandRecipe = new ArrayList<Integer>();
	
	/* every 7 days, market demand changes */
	protected static int marketDemandDuration = 7;
	
	protected static final int MIN_CUSTOMER = 3;
	
	// this should be called everytime the player advances to next day
	public static void update() {
		updateMarketRate();
		updateMarketDemand();
	}
	
	public static int recomputePrice(int originalPrice) {
		int newPrice = (int) (currentMarketRate * originalPrice);
		return newPrice;
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
			// TODO: randomly select 5 recipe in demand
			
			// reset duration to 7 days
			marketDemandDuration = 7;
		}
	}

	private static void updateMarketRate() {
		marketRateDuration--;
		
		if (marketRateDuration == 0) {
			int rate = Utils.RANDOM.nextInt(MARKET_RATE.length);
			if (rate == currentMarketRate) {
				currentMarketRate = 1.0f;
			} else {
				currentMarketRate = rate;
			}
			/* randomly selects value from 2-10 */
			marketRateDuration = Utils.RANDOM.nextInt(9) + 2;
		}
	}

	public static List<Integer> getAllInDemandRecipe() {
		return inDemandRecipe;
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

	public static void cleanup() {
		inDemandRecipe.clear();
		inDemandRecipe = null;
	}
}