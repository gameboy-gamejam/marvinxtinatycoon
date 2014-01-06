package com.gj.gb.stage.actors;

import com.gj.gb.R;
import com.gj.gb.stage.common.StageHelper;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

public class Pond {
    
    public static final int FISH_RARITY_SMALL   = 3000;
    public static final int FISH_RARITY_MEDIUM  = 3001;
    public static final int FISH_RARITY_BIG     = 3002;
    
    private static final float SCORE_RT_SMALL     = 1;
    private static final float SCORE_RT_MEDIUM    = 1;
    private static final float SCORE_RT_BIG       = 1;
    
    private int mFishRarity;
    
    private float mPosX;
    private float mPosY;
    private float mPosXRightBorder;
    private float mPosYBottomBorder;
    private int mWidth;
    private int mHeight;
    private Bitmap mCurrentSkin;
    
    public Pond(float posX, float posY, float posXRightBorder, float posYBottomBorder) {
        mPosX = posX;
        mPosY = posY;
        mPosXRightBorder = posXRightBorder;
        mPosYBottomBorder = posYBottomBorder;
        mHeight = (int) (mPosYBottomBorder - mPosY);
        mWidth = (int) (mPosXRightBorder - mPosX);
    }
    
    public void drawMe(Canvas canvas, Resources res){
        if (mCurrentSkin == null) {
            mCurrentSkin = BitmapFactory.decodeResource(res, R.drawable.pond);
            Bitmap.createScaledBitmap(mCurrentSkin, mWidth, mHeight, false);
        }
        canvas.drawBitmap(mCurrentSkin, mPosX, mPosY, null);
    }
    
    public boolean isHit(float touchPosX, float touchPosY){
        return StageHelper.isWithinBorders(mPosX, mPosXRightBorder, mPosY, 
                                           mPosYBottomBorder, touchPosX, touchPosY);
    }
    
    /**
     * 
     * - rarity -
     * 0: small
     * 1: medium
     * 2: big 
     */
    public void setFishRarity(int rarity){
        if(rarity == 0){
            mFishRarity = FISH_RARITY_SMALL;
        } else if(rarity == 1) {
            mFishRarity = FISH_RARITY_MEDIUM;
        } else if(rarity == 2) {
            mFishRarity = FISH_RARITY_BIG;
        }
    }
    
    public int getScore(int rarityOfFish){
        return (int) (rarityOfFish * getScoreRt());
    }
    
    private float getScoreRt() {
        if(mFishRarity == FISH_RARITY_MEDIUM){
            return SCORE_RT_MEDIUM;
        } else if(mFishRarity == FISH_RARITY_BIG) {
            return SCORE_RT_BIG;
        }
        return SCORE_RT_SMALL;
    }
}
