package com.gj.gb.stage.actors;

import com.gj.gb.R;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;

public class Apple {
	
	private static final int WIDTH = 100;
	private static final int HEIGHT = 100;
	
	private static final int mMovement = 20;
	private float mWallPosY;
	
	private int mPosX;
	private int mPosY;
	
	private Bitmap mCurrentSkin;
	
	public Apple(int posX, int posY, int wallPosY) {
		mPosX = posX;
		mPosY = posY;
		mWallPosY = wallPosY;
	}
	
	public void drawMe(Canvas canvas, Resources res){
		if (mCurrentSkin == null) {
            mCurrentSkin = BitmapFactory.decodeResource(res, R.drawable.apple);
            Bitmap.createScaledBitmap(mCurrentSkin, WIDTH, HEIGHT, false);
        } else {
        	mPosY+= mMovement;
        }
		canvas.drawBitmap(mCurrentSkin, mPosX, mPosY, null);
	}
	
	public boolean isHitBasket(Rect rect){
	    if((mPosY+HEIGHT >= rect.top && mPosY+HEIGHT <= rect.bottom)
	            && ((mPosX+WIDTH > rect.left && mPosX+WIDTH < rect.right) 
	                    || (mPosX > rect.left && mPosX < rect.right))){
	        return true;
	    }
		return false;
	}
	
	public boolean isHitGround() {
		if(mPosY + HEIGHT >= mWallPosY){
			return true;
		}
		return false;
	}
}
