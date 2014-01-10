package com.gj.gb.stage.actors;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import com.gj.gb.R;

public class Basket {

	private static final int WIDTH = 256;// TODO check again
	private static final int HEIGHT = 150;
	private static final int MOVEMENT_PER_FRAME = 9;// TODO testing

	private float mPosX;
	private float mPosY;
	private int mMovement;

	private float mWallLeftPos;
	private float mWallRightPos;

	private Bitmap mCurrentSkin;

	public Basket(int basketPosX, int basketPosY, Resources res) {
		mPosX = basketPosX;
		mPosY = basketPosY;
		mMovement = MOVEMENT_PER_FRAME;
		mCurrentSkin = BitmapFactory.decodeResource(res,
				R.drawable.shoppingbasket);
		Bitmap.createScaledBitmap(mCurrentSkin, WIDTH, HEIGHT, false);
	}

	public void setWallBorder(float wallLeftPos, float wallRightPos) {
		mWallLeftPos = wallLeftPos;
		mWallRightPos = wallRightPos;
	}

	public void setTilt(float tilt) {
		if ((tilt > 0 && mMovement < 0) || (tilt < 0 && mMovement > 0)) {
			mMovement *= -1;
		}
	}

	public float getLeftBorderPos() {
		return mPosX;
	}

	public float getTopBorderPos() {
		return mPosY;
	}

	public float getRightBorderPos() {
		return mPosX + WIDTH;
	}

	public float getBottomBorderPos() {
		return mPosY + HEIGHT;
	}

	public void drawMe(Canvas canvas) {
		if ((mWallLeftPos < mPosX + mMovement)
				&& (mWallRightPos > mPosX + mMovement)) {
			mPosX += mMovement;
		}
		canvas.drawBitmap(mCurrentSkin, mPosX, mPosY, null);
	}

}
