package com.gj.gb.popup;

import java.util.List;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import com.gj.gb.R;
import com.gj.gb.factory.GBIngredientsFactory;
import com.gj.gb.logic.RewardSystem;
import com.gj.gb.model.GBGameData;
import com.gj.gb.model.GBIngredient;
import com.gj.gb.model.GBIngredient.IngredientCategory;
import com.gj.gb.util.GBDataManager;

public class GBMiniGameRewardPopop extends Activity {

	public static final String KEY_EXTRA_POINTS = "points";
	public static final String KEY_EXTRA_CATEGORY = "category";
	public static final int REQUEST_CODE_REWARD = 13000;

	private OnClickListener mOnClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			int id = v.getId();

			switch (id) {
			case R.id.returnTown:
				setResult(RESULT_CANCELED);
				finish();
				break;
			case R.id.playAgain:
				setResult(RESULT_OK);
				finish();
				break;
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.popup_minigame_reward);

		findViewById(R.id.returnTown).setOnClickListener(mOnClickListener);
		findViewById(R.id.playAgain).setOnClickListener(mOnClickListener);

		Bundle extra = getIntent().getExtras();
		int points = extra.getInt(KEY_EXTRA_POINTS);
		IngredientCategory category = (IngredientCategory) extra
				.getSerializable(KEY_EXTRA_CATEGORY);

		
		GBGameData data = GBDataManager.getGameData();
		data.setExperience((data.getExperience() +(points*0.1f)));
		
		int rarityLvl = RewardSystem.getRarityLevelFromScore(points);
		List<GBIngredient> rewardsAvailable = GBIngredientsFactory
				.findIngredientByCategoryAndLessThanEqualRarity(category,
						rarityLvl);
		List<Bitmap> rewards = RewardSystem.getRewards(this, rewardsAvailable);
		LinearLayout rewardsHolder = (LinearLayout) findViewById(R.id.rewards);
		Resources res = getResources();
		for (Bitmap reward : rewards) {
			ImageView rewardImageView = new ImageView(this);
			rewardImageView.setImageBitmap(reward);
			rewardImageView.setLayoutParams(new LayoutParams(res
					.getDimensionPixelSize(R.dimen.dp_75), res
					.getDimensionPixelSize(R.dimen.dp_75)));
			rewardsHolder.addView(rewardImageView);
		}
		((TextView) findViewById(R.id.taunt)).setText(RewardSystem.getTaunt(
				res, rarityLvl));
	}
}
