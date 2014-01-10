package com.gj.gb.factory;

import java.util.ArrayList;
import java.util.List;

import com.gj.gb.logic.RewardSystem;
import com.gj.gb.model.GBIngredient;
import com.gj.gb.model.GBIngredient.IngredientCategory;

public enum GBIngredientsFactory {
	APPLE(0, "APPLE", IngredientCategory.FRUIT, 15,
			RewardSystem.REWARD_RARITY_LVL_1), BACON(1, "BACON",
			IngredientCategory.MEAT, 50, RewardSystem.REWARD_RARITY_LVL_2), BANANA(
			2, "BANANA", IngredientCategory.FRUIT, 10,
			RewardSystem.REWARD_RARITY_LVL_1), BASIL(3, "BASIL",
			IngredientCategory.OTHERS, 25, RewardSystem.REWARD_RARITY_LVL_3), BAYLEAF(
			4, "BAYLEAF", IngredientCategory.OTHERS, 5,
			RewardSystem.REWARD_RARITY_LVL_1), BEANS(5, "BEANS",
			IngredientCategory.CROPS, 12, RewardSystem.REWARD_RARITY_LVL_1), BEEF(
			6, "BEEF", IngredientCategory.MEAT, 70,
			RewardSystem.REWARD_RARITY_LVL_2), BREAD(7, "BREAD",
			IngredientCategory.OTHERS, 8, RewardSystem.REWARD_RARITY_LVL_1), BUTTER(
			8, "BUTTER", IngredientCategory.OTHERS, 20,
			RewardSystem.REWARD_RARITY_LVL_1), CABBAGE(9, "CABBAGE",
			IngredientCategory.CROPS, 12, RewardSystem.REWARD_RARITY_LVL_1), CARROT(
			10, "CARROT", IngredientCategory.CROPS, 8,
			RewardSystem.REWARD_RARITY_LVL_1), CHEESE(11, "CHEESE",
			IngredientCategory.OTHERS, 20, RewardSystem.REWARD_RARITY_LVL_1), CHICKEN(
			12, "CHICKEN", IngredientCategory.MEAT, 50,
			RewardSystem.REWARD_RARITY_LVL_2), CHILI(13, "CHILI",
			IngredientCategory.CROPS, 5, RewardSystem.REWARD_RARITY_LVL_1), CHOCOLATE(
			14, "CHOCOLATE", IngredientCategory.OTHERS, 30,
			RewardSystem.REWARD_RARITY_LVL_3), CRAB(15, "CRAB",
			IngredientCategory.FISH, 40, RewardSystem.REWARD_RARITY_LVL_3), EGG(
			16, "EGG", IngredientCategory.OTHERS, 5,
			RewardSystem.REWARD_RARITY_LVL_1), GARLIC(17, "GARLIC",
			IngredientCategory.CROPS, 5, RewardSystem.REWARD_RARITY_LVL_1), GINGER(
			18, "GINGER", IngredientCategory.CROPS, 8,
			RewardSystem.REWARD_RARITY_LVL_1), ICECREAM(19, "ICE CREAM",
			IngredientCategory.OTHERS, 25, RewardSystem.REWARD_RARITY_LVL_2), LAMB(
			20, "LAMB", IngredientCategory.MEAT, 85,
			RewardSystem.REWARD_RARITY_LVL_4), LEEK(21, "LEEK",
			IngredientCategory.OTHERS, 4, RewardSystem.REWARD_RARITY_LVL_1), LEMON(
			22, "LEMON", IngredientCategory.FRUIT, 20,
			RewardSystem.REWARD_RARITY_LVL_2), LIME(23, "LIME",
			IngredientCategory.FRUIT, 20, RewardSystem.REWARD_RARITY_LVL_2), LOBSTER(
			24, "LOBSTER", IngredientCategory.FISH, 90,
			RewardSystem.REWARD_RARITY_LVL_4), MILK(25, "MILK",
			IngredientCategory.OTHERS, 15, RewardSystem.REWARD_RARITY_LVL_1), MUSHROOM(
			26, "MUSHROOM", IngredientCategory.CROPS, 18,
			RewardSystem.REWARD_RARITY_LVL_2), NOODLES(27, "NOODLES",
			IngredientCategory.OTHERS, 20, RewardSystem.REWARD_RARITY_LVL_2), ONION(
			28, "ONION", IngredientCategory.CROPS, 5,
			RewardSystem.REWARD_RARITY_LVL_1), PASTA(29, "PASTA",
			IngredientCategory.OTHERS, 35, RewardSystem.REWARD_RARITY_LVL_2), PEAS(
			30, "PEAS", IngredientCategory.OTHERS, 5,
			RewardSystem.REWARD_RARITY_LVL_1), PEPPERONI(31, "PEPPERONI",
			IngredientCategory.OTHERS, 55, RewardSystem.REWARD_RARITY_LVL_3), PORK(
			32, "PORK", IngredientCategory.MEAT, 50,
			RewardSystem.REWARD_RARITY_LVL_2), POTATO(33, "POTATO",
			IngredientCategory.CROPS, 5, RewardSystem.REWARD_RARITY_LVL_1), PRAWN(
			34, "PRAWN", IngredientCategory.FISH, 20,
			RewardSystem.REWARD_RARITY_LVL_1), PUMPKIN(35, "PUMPKIN",
			IngredientCategory.CROPS, 30, RewardSystem.REWARD_RARITY_LVL_3), RICE(
			36, "RICE", IngredientCategory.OTHERS, 20,
			RewardSystem.REWARD_RARITY_LVL_1), SAFFRON(37, "SAFFRON",
			IngredientCategory.CROPS, 50, RewardSystem.REWARD_RARITY_LVL_4), SALAD(
			38, "SALAD", IngredientCategory.OTHERS, 15,
			RewardSystem.REWARD_RARITY_LVL_1), SALMON(39, "SALMON",
			IngredientCategory.FISH, 100, RewardSystem.REWARD_RARITY_LVL_4), SEAWEED(
			40, "SEAWEED", IngredientCategory.FISH, 10,
			RewardSystem.REWARD_RARITY_LVL_1), STRAWBERRY(41, "STRAWBERRY",
			IngredientCategory.FRUIT, 20, RewardSystem.REWARD_RARITY_LVL_3), SWEETCORN(
			42, "SWEETCORN", IngredientCategory.OTHERS, 18,
			RewardSystem.REWARD_RARITY_LVL_2), TOFU(43, "TOFU",
			IngredientCategory.OTHERS, 10, RewardSystem.REWARD_RARITY_LVL_1), TOMATO(
			44, "TOMATO", IngredientCategory.CROPS, 5,
			RewardSystem.REWARD_RARITY_LVL_1), TUNA(45, "TUNA",
			IngredientCategory.FISH, 115, RewardSystem.REWARD_RARITY_LVL_4), WASABI(
			46, "WASABI", IngredientCategory.OTHERS, 30,
			RewardSystem.REWARD_RARITY_LVL_3);

	private int id;
	private String name;
	private IngredientCategory category;
	private int price;
	private int rarityLevel;

	private GBIngredientsFactory(int id, String name,
			IngredientCategory category, int price, int rarityLevel) {
		this.id = id;
		this.name = name;
		this.category = category;
		this.price = price;
		this.rarityLevel = rarityLevel;
	}

	public static final GBIngredient getIngredientById(int id) {
		for (GBIngredientsFactory ingredient : values()) {
			if (ingredient.id == id) {
				return new GBIngredient(ingredient);
			}
		}
		return null;
	}

	public static final List<GBIngredient> findIngredientByCategoryAndLessThanEqualRarity(
			IngredientCategory category, int rarity) {
		List<GBIngredient> ingredients = new ArrayList<GBIngredient>();
		for (GBIngredientsFactory ingredient : values()) {
			if (ingredient.category == category
					&& ingredient.rarityLevel <= rarity) {
				ingredients.add(new GBIngredient(ingredient));
			}
		}
		return ingredients;
	}

	// getters and setters
	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public int getPrice() {
		return price;
	}

	public int getRarityLevel() {
		return rarityLevel;
	}

	public IngredientCategory getCategory() {
		return category;
	}
}
