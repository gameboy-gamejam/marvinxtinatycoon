package com.gj.gb.stage.actors;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import com.gj.gb.R;

public class Fisher {

	public static final int STATE_IS_FISHING	= 5000;
    public static final int STATE_ON_STANDBY	= 5001;
	
	private int mState;
	private float mPosX;
    private float mPosY;
    private float mPosXRightBorder;
    private float mPosYBottomBorder;
    private int mWidth;
    private int mHeight;
	private Bitmap mFishing;
	private Bitmap mStandBy;
	
	public Fisher(float posX, float posY, float posXRightBorder, float posYBottomBorder, Resources res) {
        mPosX = posX;
        mPosY = posY;
        mPosXRightBorder = posXRightBorder;
        mPosYBottomBorder = posYBottomBorder;
        mHeight = (int) (mPosYBottomBorder - mPosY);
        mWidth = (int) (mPosXRightBorder - mPosX);
        mState = STATE_ON_STANDBY;
        mFishing = BitmapFactory.decodeResource(res, R.drawable.fishing);
        Bitmap.createScaledBitmap(mFishing, mWidth, mHeight, false);
        mStandBy = BitmapFactory.decodeResource(res, R.drawable.standby);
        Bitmap.createScaledBitmap(mStandBy, mWidth, mHeight, false);
    }
	
	public void drawMe(Canvas canvas){
        if (mState == STATE_ON_STANDBY) {
        	canvas.drawBitmap(mStandBy, mPosX, mPosY, null);
        } else {
        	canvas.drawBitmap(mFishing, mPosX, mPosY, null);
        }
    }

	public void setState(int state) {
		mState = state;
	}
}
