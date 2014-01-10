package com.gj.gb.logic;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;

import com.gj.gb.R;
import com.gj.gb.model.GBIngredient;
import com.gj.gb.util.GBDataManager;
import com.gj.gb.util.ImageCache;

public class RewardSystem {
	public static final int MAX_POINTS_EARNED_PER_GAME = 500;
	public static final int MAX_REWARDS_GIVEN = 3;

	public static final int REWARD_RARITY_LVL_0 = 0;
	public static final int REWARD_RARITY_LVL_1 = 1;// common
	public static final int REWARD_RARITY_LVL_2 = 2;
	public static final int REWARD_RARITY_LVL_3 = 3;
	public static final int REWARD_RARITY_LVL_4 = 4;
	public static final int REWARD_RARITY_LVL_5 = 5;// rare

	private static final float REWARD_SCORE_RATE_LVL_1 = 0.2f;
	private static final float REWARD_SCORE_RATE_LVL_2 = 0.4f;
	private static final float REWARD_SCORE_RATE_LVL_3 = 0.6f;
	private static final float REWARD_SCORE_RATE_LVL_4 = 0.8f;
	private static final float REWARD_SCORE_RATE_LVL_5 = 1f;

	public static int getRarityLevelFromScore(int score) {
		float rate = score / MAX_POINTS_EARNED_PER_GAME;
		if (score == 0) {
			return REWARD_RARITY_LVL_0;
		} else if (rate > 0 && rate <= REWARD_SCORE_RATE_LVL_1) {
			return REWARD_RARITY_LVL_1;
		} else if (rate <= REWARD_SCORE_RATE_LVL_2) {
			return REWARD_RARITY_LVL_2;
		} else if (rate <= REWARD_SCORE_RATE_LVL_3) {
			return REWARD_RARITY_LVL_3;
		} else if (rate <= REWARD_SCORE_RATE_LVL_4) {
			return REWARD_RARITY_LVL_4;
		} else if (rate <= REWARD_SCORE_RATE_LVL_5) {
			return REWARD_RARITY_LVL_5;
		}
		return -1;
	}

	public static List<Bitmap> getRewards(Context context,
			List<GBIngredient> rewardsAvailable) {
		List<Bitmap> rewards = new ArrayList<Bitmap>();
		int rewardSize = rewardsAvailable.size();
		if (rewardSize > 0) {
			Random random = new Random(System.currentTimeMillis());
			for (int i = 0; i < MAX_REWARDS_GIVEN; i++) {
				int index = random.nextInt(rewardSize);
				GBIngredient ingredient = rewardsAvailable.get(index);
				int id = ingredient.getId();
				GBDataManager.getGameData().updateIngredient(id, 1);
				rewards.add(ImageCache.getBitmap(context, "ingredient_"
						+ (id + 1)));
			}
		}
		return rewards;
	}

	public static String getTaunt(Resources res, int rarityLvl) {
		if (rarityLvl == REWARD_RARITY_LVL_5) {
			return res.getString(R.string.taunt_excellent);
		} else if (rarityLvl == REWARD_RARITY_LVL_4) {
			return res.getString(R.string.taunt_great);
		} else if (rarityLvl == REWARD_RARITY_LVL_3) {
			return res.getString(R.string.taunt_good_job);
		} else if (rarityLvl == REWARD_RARITY_LVL_2) {
			return res.getString(R.string.taunt_better_luck);
		} else {
			return res.getString(R.string.taunt_thats_it);
		}
	}

}
