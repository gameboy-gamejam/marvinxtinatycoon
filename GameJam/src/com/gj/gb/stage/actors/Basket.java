package com.gj.gb.stage.actors;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;

import com.gj.gb.R;

public class Basket {
    
    private static final int WIDTH = 400;//TODO check again
    private static final int HEIGHT = 400;
    private static final int MOVEMENT_PER_FRAME = 15;//TODO testing
    
    private int mPosX;
    private int mPosY;
    private int mMovement;
    
    private float mWallLeftPos;
    private float mWallRightPos;
    
    private Bitmap mCurrentSkin;
    
    private Rect basketSize;
    
    public Basket(int basketPosX, int basketPosY, Resources res) {
        mPosX = basketPosX;
        mPosY = basketPosY;
        mMovement = MOVEMENT_PER_FRAME;
        basketSize = new Rect(mPosX, mPosY, mPosX+WIDTH, mPosY+HEIGHT);
        mCurrentSkin = BitmapFactory.decodeResource(res, R.drawable.shoppingbasket);
        Bitmap.createScaledBitmap(mCurrentSkin, WIDTH, HEIGHT, false);
    }
    
    public void setWallBorder(float wallLeftPos, float wallRightPos){
        mWallLeftPos = wallLeftPos;
        mWallRightPos = wallRightPos;
    }
    
    public void setTilt(float tilt){
        if((tilt > 0 && mMovement < 0)||(tilt < 0 && mMovement > 0)) {
            mMovement*=-1;
        }
    }
    
    public Rect getBasketSize(){
    	return basketSize;
    }

    public void drawMe(Canvas canvas){
        if((mWallLeftPos < mPosX + mMovement) && (mWallRightPos > mPosX + mMovement)){
            mPosX+= mMovement;
        }
        canvas.drawBitmap(mCurrentSkin, mPosX, mPosY, null);
    }
}
