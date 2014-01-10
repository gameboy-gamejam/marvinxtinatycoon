package com.gj.gb.stage.actors;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;

import com.gj.gb.R;

public class Apple {

	private static final int WIDTH = 100;
	private static final int HEIGHT = 100;
	private static final int mMovement = 35;

	private float mWallPosY;

	private int mPosX;
	private int mPosY;

	private Bitmap mCurrentSkin;

	private boolean isMissBasket;

	private Paint red;

	public Apple(int posX, int posY, int wallPosY) {
		mPosX = posX;
		mPosY = posY;
		mWallPosY = wallPosY;
		isMissBasket = false;
	}

	public void drawMe(Canvas canvas, Resources res) {
		if (red == null) {
			mCurrentSkin = BitmapFactory.decodeResource(res, R.drawable.apple);
			Bitmap.createScaledBitmap(mCurrentSkin, WIDTH, HEIGHT, false);
			red = new Paint();
			red.setColor(res.getColor(R.color.red));
			red.setStyle(Style.FILL);
		} else {
			mPosY += mMovement;
		}
		canvas.drawBitmap(mCurrentSkin, mPosX, mPosY, null);
	}

	public boolean isHitBasket(Basket basket) {
		if (basket.getTopBorderPos() < mPosY + HEIGHT) {
			if (!isMissBasket
					&& (mPosX + WIDTH > basket.getLeftBorderPos() && mPosX
							+ WIDTH < basket.getRightBorderPos())
					|| (mPosX > basket.getLeftBorderPos() && mPosX < basket
							.getRightBorderPos())) {
				return true;
			} else {
				isMissBasket = true;
			}
		}
		return false;
	}

	public boolean isHitGround() {
		if (mPosY + HEIGHT >= mWallPosY) {
			return true;
		}
		return false;
	}
}
