package com.gj.gb.stage.actors;

import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import com.gj.gb.R;

public class Carrot {
	
	public static final int STATE_EMPTY = 1000;
	public static final int STATE_SEEDED = 1001;
	public static final int STATE_SPROUT = 1002;
	public static final int STATE_RIPE = 1003;
	public static final int STATE_SPOILED = 1004;
	public static final int STATE_HARVESTED = 1005;

	private int mState;
	private int mPosX;
	private int mPosY;

	// Down time in ms
	private long mSeedDownTime;
	private long mSproutDownTime;
	private long mRipeDownTime;
	private long mSpoiledDownTime;
	private long mHarvestDownTimeNoFade;
	private long mHarvestDownTimeHalfFade;
	private long mHarvestDownTimeFullFade;

	public Carrot(int posX, int posY) {
		mPosX = posX;
		mPosY = posY;
	}

	public void drawMe(Canvas canvas, Resources res, long inGameCurrentTime) {// like one of your french girls
		if(mState == STATE_EMPTY) {
			canvas.drawBitmap(BitmapFactory.decodeResource(res, R.drawable.carrot_empty), mPosX, mPosY, null);
		} else if(mState == STATE_SEEDED){
			if(inGameCurrentTime > mSeedDownTime){
				canvas.drawBitmap(BitmapFactory.decodeResource(res, R.drawable.carrot_sprout), mPosX, mPosY, null);
				mState = STATE_SPROUT;
			} else {
				canvas.drawBitmap(BitmapFactory.decodeResource(res, R.drawable.carrot_seeded), mPosX, mPosY, null);
			}
		} else if(mState == STATE_SPROUT){
			if(inGameCurrentTime > mSproutDownTime){
				canvas.drawBitmap(BitmapFactory.decodeResource(res, R.drawable.carrot_ripe), mPosX, mPosY, null);
				mState = STATE_RIPE;
			} else {
				canvas.drawBitmap(BitmapFactory.decodeResource(res, R.drawable.carrot_sprout), mPosX, mPosY, null);
			}
		} else if(mState == STATE_RIPE){
			if(inGameCurrentTime > mRipeDownTime){
				canvas.drawBitmap(BitmapFactory.decodeResource(res, R.drawable.carrot_spoiled), mPosX, mPosY, null);
				mState = STATE_SPOILED;
			} else {
				canvas.drawBitmap(BitmapFactory.decodeResource(res, R.drawable.carrot_ripe), mPosX, mPosY, null);
			}
		} else if(mState == STATE_SPOILED){
			if(inGameCurrentTime > mSpoiledDownTime){
				canvas.drawBitmap(BitmapFactory.decodeResource(res, R.drawable.carrot_empty), mPosX, mPosY, null);
				mState = STATE_EMPTY;
			} else {
				canvas.drawBitmap(BitmapFactory.decodeResource(res, R.drawable.carrot_spoiled), mPosX, mPosY, null);
			}
		} else if(mState == STATE_HARVESTED){
			if(mHarvestDownTimeNoFade == 1000){
				mHarvestDownTimeNoFade += inGameCurrentTime;
				mHarvestDownTimeHalfFade += mHarvestDownTimeNoFade + inGameCurrentTime;
				mHarvestDownTimeFullFade += mHarvestDownTimeHalfFade + inGameCurrentTime;
			}
			if(inGameCurrentTime > mHarvestDownTimeNoFade){
				canvas.drawBitmap(BitmapFactory.decodeResource(res, R.drawable.carrot_harvested), mPosX, mPosY, null);
			} else if(inGameCurrentTime > mHarvestDownTimeNoFade){
				canvas.drawBitmap(BitmapFactory.decodeResource(res, R.drawable.carrot_harvest_fade_half), mPosX, mPosY, null);
			} else if(inGameCurrentTime > mHarvestDownTimeNoFade){
				canvas.drawBitmap(BitmapFactory.decodeResource(res, R.drawable.carrot_harvested_fade_full), mPosX, mPosY, null);
				mState = STATE_EMPTY;
			}
		}
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
		mSproutDownTime += mSeedDownTime + timeSeeded;
		mRipeDownTime += mSproutDownTime + timeSeeded;
		mSpoiledDownTime += mRipeDownTime + timeSeeded;
	}
}
