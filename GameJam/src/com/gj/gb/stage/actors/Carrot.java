package com.gj.gb.stage.actors;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import com.gj.gb.R;
import com.gj.gb.stage.common.StageHelper;

public class Carrot {

	public static final int STATE_EMPTY		 	= 1000;
	public static final int STATE_SEEDED 		= 1001;
	public static final int STATE_SPROUT 		= 1002;
	public static final int STATE_RIPE 			= 1003;
	public static final int STATE_SPOILED 		= 1004;
	public static final int STATE_HARVESTED 	= 1005;

	private int mState;
	private float mPosX;
	private float mPosY;
	private float mPosXRightBorder;
	private float mPosYBottomBorder;
	private Bitmap currentSkin;

	// Down time in ms
	private long mSeedDownTime;
	private long mSproutDownTime;
	private long mRipeDownTime;
	private long mSpoiledDownTime;
	private long mHarvestDownTimeNoFade;
	private long mHarvestDownTimeHalfFade;
	private long mHarvestDownTimeFullFade;

	public Carrot(float posX, float posY, float posXRightBorder, float posYBottomBorder) {
		mPosX = posX;
		mPosY = posY;
		mPosXRightBorder = posXRightBorder;
		mPosYBottomBorder = posYBottomBorder;
	}

	public void drawMe(Canvas canvas, Resources res, long inGameCurrentTime) {
		if (mState == STATE_EMPTY && currentSkin == null) {
			currentSkin = BitmapFactory.decodeResource(res,	R.drawable.carrot_empty);
		} else if (mState == STATE_SEEDED && inGameCurrentTime > mSeedDownTime) {
			currentSkin = BitmapFactory.decodeResource(res,	R.drawable.carrot_sprout);
			mState = STATE_SPROUT;
		} else if (mState == STATE_SPROUT && inGameCurrentTime > mSproutDownTime) {
			currentSkin = BitmapFactory.decodeResource(res,	R.drawable.carrot_ripe);
			mState = STATE_RIPE;
		} else if (mState == STATE_RIPE && inGameCurrentTime > mRipeDownTime) {
			currentSkin = BitmapFactory.decodeResource(res,	R.drawable.carrot_spoiled);
			mState = STATE_SPOILED;
		} else if (mState == STATE_SPOILED && inGameCurrentTime > mSpoiledDownTime) {
			currentSkin = BitmapFactory.decodeResource(res,	R.drawable.carrot_empty);
			mState = STATE_EMPTY;
		} else if (mState == STATE_HARVESTED) {
			if (mHarvestDownTimeNoFade == 1000) {
				mHarvestDownTimeNoFade += inGameCurrentTime;
				mHarvestDownTimeHalfFade += mHarvestDownTimeNoFade;
				mHarvestDownTimeFullFade += mHarvestDownTimeHalfFade;
				currentSkin = BitmapFactory.decodeResource(res,	R.drawable.carrot_harvested);
			}
			if (inGameCurrentTime > mHarvestDownTimeNoFade) {
				currentSkin = BitmapFactory.decodeResource(res,	R.drawable.carrot_harvest_fade_half);
			} else if (inGameCurrentTime > mHarvestDownTimeHalfFade) {
				currentSkin = BitmapFactory.decodeResource(res,	R.drawable.carrot_harvested_fade_full);
			} else if (inGameCurrentTime > mHarvestDownTimeFullFade) {
				currentSkin = BitmapFactory.decodeResource(res,	R.drawable.carrot_empty);
				mState = STATE_EMPTY;
			}
		}
		canvas.drawBitmap(currentSkin, mPosX, mPosY, null);
	}
	
	public boolean isHit(float touchPosX, float touchPosY){
		return StageHelper.isWithinBorders(mPosX, mPosXRightBorder, mPosY, 
				mPosYBottomBorder, touchPosX, touchPosY);
	}

	private void initDownTime() {
		mSeedDownTime = 3000;
		mSproutDownTime = 3000;
		mRipeDownTime = 5000;
		mSpoiledDownTime = 3000;
		mHarvestDownTimeNoFade = 1000;
		mHarvestDownTimeHalfFade = 1000;
		mHarvestDownTimeFullFade = 1000;
	}

	public void setState(int state) {
		mState = state;
	}

	public void setTimeSeeded(int timeSeeded) {
		initDownTime();
		mSeedDownTime += timeSeeded;
		mSproutDownTime += mSeedDownTime;
		mRipeDownTime += mSproutDownTime;
		mSpoiledDownTime += mRipeDownTime;
	}
}
