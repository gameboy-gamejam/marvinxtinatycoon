package com.gj.gb.logic;

import java.util.List;

import com.gj.gb.factory.GBIngredientsFactory;
import com.gj.gb.model.GBNewCustomer;
import com.gj.gb.model.GBNewCustomer.GBCustomerState;

public class GBStatsHelper {

	public static int getExperience(GBNewCustomer customer) {
		List<Integer> ingredient = customer.getOrder().getIngredients();
		int n = ingredient.size();
		
		int totalRarity = 0;
		for (int i=0; i<n; i++) {
			totalRarity += GBIngredientsFactory.getIngredientById(ingredient.get(i)).getRarity();
		}
		totalRarity /= 3; // average rarity
		
		int hit = 3 - customer.getHit();
		int experience = totalRarity + hit;
		
		return experience;
	}
	
	public static int getRatings(GBNewCustomer customer) {
		int defaultRate = customer.getState() == GBCustomerState.SERVED ? 2 : 0;
		defaultRate -= customer.getHit();
		return defaultRate;
	}
	
	public static int getGoldEarn(GBNewCustomer customer) {
		int price = GBEconomics.getRecipePrice(customer.getOrder());
		int tip = customer.getTip();		
		return price + tip;
	}
	
	public static int calculateNextLevel(int currentLevel, int currentNextLevel) {
		int nextLevel = currentLevel + 1;
		float x3 = nextLevel * nextLevel * nextLevel;
		float x2 = -6.25f * (nextLevel * nextLevel);
		float x1 = 11.75f * nextLevel;
		float c = -6.5f;
		
		float y = x3 + x2 + x1 + c;
		y = y * -1;
		
		int toNextLevel = currentNextLevel;
		toNextLevel *= y;
		
		return toNextLevel;
	}
} 
