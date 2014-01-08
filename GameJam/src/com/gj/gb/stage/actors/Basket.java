package com.gj.gb.stage.actors;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import com.gj.gb.R;

public class Basket {
    
    private static final int BASKET_WIDTH = 200;//TODO check again
    private static final int BASKET_HEIGHT = 200;
    private static final int MOVEMENT_PER_FRAME = 10;//TODO testing
    
    private float mBasketPosX;
    private float mBasketPosY;
    private float mRecordedTilt;
    private int mMovement;
    
    private float mWallLeftPos;
    private float mWallRightPos;
    
    private Bitmap mCurrentSkin;
    
    public Basket(int basketPosX, int basketPosY) {
        mBasketPosX = basketPosX;
        mBasketPosY = basketPosY;
        mMovement = MOVEMENT_PER_FRAME;
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

    public void drawMe(Canvas canvas, Resources res){
        if (mCurrentSkin == null) {
            mCurrentSkin = BitmapFactory.decodeResource(res, R.drawable.shoppingbasket);
            Bitmap.createScaledBitmap(mCurrentSkin, BASKET_WIDTH, BASKET_HEIGHT, false);
        }
        if((mWallLeftPos < mBasketPosX + mMovement)|| (mWallRightPos > mBasketPosX + mMovement)){
            mBasketPosX+= mMovement;
        }
        canvas.drawBitmap(mCurrentSkin, mBasketPosX, mBasketPosY, null);
    }
}
