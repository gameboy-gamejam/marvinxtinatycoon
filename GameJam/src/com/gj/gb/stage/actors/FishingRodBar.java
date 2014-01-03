
package com.gj.gb.stage.actors;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;

import com.gj.gb.R;
import com.gj.gb.stage.common.StageHelper;

public class FishingRodBar {

    public static final int DIFFICULTY_NOVICE = 2000;
    public static final int DIFFICULTY_EASY = 2001;
    public static final int DIFFICULTY_NORMAL = 2002;
    public static final int DIFFICULTY_HARD = 2003;
    public static final int DIFFICULTY_EXPERT = 2004;
    public static final int RARITY_OF_FISH_MAX_PT = 500;//FIXME pau kaw na bahala magcheck nito
    
    private static final int BAR_WIDTH = 390;
    private static final int POINTER_WIDTH = 10;

    private static final float DISTANCE_CATCH_BAR_RT_EASY = 0.5f;
    private static final float DISTANCE_CATCH_BAR_RT_NORMAL = 0.25f;
    private static final float DISTANCE_CATCH_BAR_RT_HARD = 0.125f;

    private static final int TOTAL_TIME_MS_BAR_REACH_END_EASY = 7000;
    private static final int TOTAL_TIME_MS_BAR_REACH_END_NORMAL = 5000;
    private static final int TOTAL_TIME_MS_BAR_REACH_END_HARD = 3000;
    
    private static final int SCORE_RT_NOVICE   = 20;//FIXME pau kaw na bahala magcheck nito
    private static final int SCORE_RT_EASY     = 30;
    private static final int SCORE_RT_NORMAL   = 60;
    private static final int SCORE_RT_HARD     = 80;
    private static final int SCORE_RT_EXPERT   = 100;
    
    private static final int BAR_START_X = 205;

    private boolean mIsLakeWithFish;// TODO add ng points for rarity per lake 3 rarity option
    private int mRarityOfFish;// TODO add ng rarity ng lake
    
    private int mDifficulty;
    private float mCatchBarWidth;
    private float mMovementRate;
    private float mMovableDistanceWidth;
    
    private float mCatchBarPosX;
    private float mRodPointerPosX;
    
    private Paint mBarBorder;
    private Paint mFreeBarSpaceBackground;
    private Paint mCatchBarBackground;
    private Paint mRodPointerBackground;
    
    public FishingRodBar(Resources res) {
        mBarBorder = new Paint();
        mBarBorder.setColor(res.getColor(R.color.white));
        mBarBorder.setStyle(Style.FILL);
        mFreeBarSpaceBackground = new Paint();
        mFreeBarSpaceBackground.setColor(res.getColor(R.color.pale_green));
        mFreeBarSpaceBackground.setStyle(Style.FILL);
        mCatchBarBackground = new Paint();
        mCatchBarBackground.setColor(res.getColor(R.color.gold));
        mCatchBarBackground.setStyle(Style.FILL);
        mRodPointerBackground = new Paint();
        mRodPointerBackground.setColor(res.getColor(R.color.purple));
        mRodPointerBackground.setStyle(Style.FILL);
        mRodPointerPosX=BAR_START_X;
    }

    /*
     * - difficulty value - 0: novice 1: easy 2: normal 3: hard 4: expert
     */
    public void adjustDifficulty(int difficultyValue, int frameDelay) {
        if (difficultyValue == 0) {
            mDifficulty = DIFFICULTY_NOVICE;
            mCatchBarWidth = BAR_WIDTH * DISTANCE_CATCH_BAR_RT_EASY;
            mMovementRate = BAR_WIDTH/(TOTAL_TIME_MS_BAR_REACH_END_EASY/frameDelay);
        } else if (difficultyValue == 1) {
            mDifficulty = DIFFICULTY_EASY;
            mCatchBarWidth = BAR_WIDTH * DISTANCE_CATCH_BAR_RT_NORMAL;
            mMovementRate = BAR_WIDTH/(TOTAL_TIME_MS_BAR_REACH_END_EASY/frameDelay);
        } else if (difficultyValue == 2) {
            mDifficulty = DIFFICULTY_NORMAL;
            mCatchBarWidth = BAR_WIDTH * DISTANCE_CATCH_BAR_RT_NORMAL;
            mMovementRate = BAR_WIDTH/(TOTAL_TIME_MS_BAR_REACH_END_NORMAL/frameDelay);
        } else if (difficultyValue == 3) {
            mDifficulty = DIFFICULTY_HARD;
            mCatchBarWidth = BAR_WIDTH * DISTANCE_CATCH_BAR_RT_HARD;
            mMovementRate = BAR_WIDTH/(TOTAL_TIME_MS_BAR_REACH_END_NORMAL/frameDelay);
        } else if (difficultyValue == 4) {
            mDifficulty = DIFFICULTY_EXPERT;
            mCatchBarWidth = BAR_WIDTH * DISTANCE_CATCH_BAR_RT_HARD;
            mMovementRate = BAR_WIDTH/(TOTAL_TIME_MS_BAR_REACH_END_HARD/frameDelay);
        }
        mMovableDistanceWidth = BAR_WIDTH - mCatchBarWidth;
    }
    
    public void drawMe(Canvas canvas) {
        canvas.drawRect(BAR_START_X - 10, 50, BAR_WIDTH + BAR_START_X + 10, 150, mBarBorder);
        canvas.drawRect(BAR_START_X, 60, BAR_WIDTH + BAR_START_X, 140, mFreeBarSpaceBackground);
        canvas.drawRect(mCatchBarPosX, 60, mCatchBarPosX + mCatchBarWidth, 140, mCatchBarBackground);
        canvas.drawRect(mRodPointerPosX, 60, POINTER_WIDTH, 140, mRodPointerBackground);
        if((mMovementRate > 0 && BAR_START_X + mMovableDistanceWidth <= mRodPointerPosX)||(mMovementRate < 0 && BAR_START_X >= mRodPointerPosX)) {
            mMovementRate *= -1;
        }
        mRodPointerPosX += mMovementRate;
    }
    
    public int getScore() {
        return mRarityOfFish * getScoreRt(); 
    }
    
    private int getScoreRt() {
        if (mDifficulty == DIFFICULTY_EASY) {
            return SCORE_RT_EASY;
        } else if (mDifficulty == DIFFICULTY_NORMAL) {
            return SCORE_RT_NORMAL;
        } else if (mDifficulty == DIFFICULTY_HARD) {
            return SCORE_RT_HARD;
        } else if (mDifficulty == DIFFICULTY_EXPERT) {
            return SCORE_RT_EXPERT;
        }
        return SCORE_RT_NOVICE;
    }
    
    public boolean isHit() {
        return StageHelper.isWithinBorders(mCatchBarPosX, mCatchBarPosX+mCatchBarWidth, 1, 1, mRodPointerPosX, 1);
    }
    
    
    
    public float getMovableDistanceWidth() {
        return mMovableDistanceWidth;
    }
    
    public boolean isLakeWithFish() {
        return mIsLakeWithFish;
    }

    /**
     * @param catchBarPosX - random value from mMovableDistanceWidth
     */
    public void setCatchBarPosX(float catchBarPosX) {
        mCatchBarPosX = catchBarPosX + BAR_START_X;
    }

    public void setIsLakeWithFish(boolean isLakeWithFish) {
        mIsLakeWithFish = isLakeWithFish;
    }
    
    /**
     * 
     * @param rarityOfFish - random value from RARITY_OF_FISH_MAX_PT
     */
    public void setRarityOfFish(int rarityOfFish) {
        mRarityOfFish = rarityOfFish;
    }
}
