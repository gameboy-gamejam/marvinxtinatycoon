package com.gj.gb.stage.actors;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;

import com.gj.gb.R;

public class Basket {
    
    private static final int WIDTH = 200;//TODO check again
    private static final int HEIGHT = 200;
    private static final int MOVEMENT_PER_FRAME = 10;//TODO testing
    
    private int mPosX;
    private int mPosY;
    private float mRecordedTilt;
    private int mMovement;
    
    private float mWallLeftPos;
    private float mWallRightPos;
    
    private Bitmap mCurrentSkin;
    
    private Rect basketSize;
    
    public Basket(int basketPosX, int basketPosY) {
        mPosX = basketPosX;
        mPosY = basketPosY;
        mMovement = MOVEMENT_PER_FRAME;
        basketSize = new Rect(mPosX, mPosY, mPosX+WIDTH, mPosY+HEIGHT);
    }
    
    public void setWallBorder(float wallLeftPos, float wallRightPos){
        mWallLeftPos = wallLeftPos;
        mWallRightPos = wallRightPos;
    }
    
    public void setTilt(float tilt){
        if((mRecordedTilt > tilt && mMovement > 0) || (mRecordedTilt < tilt && mMovement < 0)) {
            mMovement*=1;
        }
    }
    
    public Rect getBasketSize(){
    	return basketSize;
    }

    public void drawMe(Canvas canvas, Resources res){
        if (mCurrentSkin == null) {
            mCurrentSkin = BitmapFactory.decodeResource(res, R.drawable.shoppingbasket);
            Bitmap.createScaledBitmap(mCurrentSkin, WIDTH, HEIGHT, false);
        }
        if((mWallLeftPos < mPosX + mMovement)|| (mWallRightPos > mPosX + mMovement)){
            mPosX+= mMovement;
        }
        canvas.drawBitmap(mCurrentSkin, mPosX, mPosY, null);
    }
}
