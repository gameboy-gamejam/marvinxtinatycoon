package com.gj.gb.model;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.graphics.drawable.AnimationDrawable;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.gj.gb.R;

public class GBStove {
	public enum OvenStatus {
		VACANT, COOKING, // RAW pa
		FINISHED, // Tama lng
		OVERCOOKED, // sunog na
	}

	public enum ResultStatus {
		RAW, NORMAL, BUNRT
	}

	private OvenStatus mOvenStatus;
	private ResultStatus mResultStatus;
	private int mDishId;
	private int mCount;
	private int mProgressBarId;
	private int mResultId;
	private Activity mActivity;
	private CountDownTimer timer;
	private Animation fadeIn, fadeOut, shake;
	AnimationSet animation;

	public GBStove(Activity activity, int progressBarId, int resultId) {
		this.mOvenStatus = OvenStatus.VACANT;
		this.mDishId = -1;
		this.mCount = 0;
		this.mProgressBarId = progressBarId;
		this.mResultId = resultId;
		this.mActivity = activity;
		((ProgressBar) mActivity.findViewById(mProgressBarId)).setMax(10);
		fadeIn = AnimationUtils.loadAnimation(activity, R.anim.fade_in);
		fadeOut = AnimationUtils.loadAnimation(activity, R.anim.fade_out);
		shake = AnimationUtils.loadAnimation(activity, R.anim.shake);
		fadeIn.setDuration(500);
		shake.setDuration(2000);
		fadeOut.setDuration(500);

		shake.setStartOffset(1000);
		fadeOut.setStartOffset(6000);
		animation = new AnimationSet(false);
		animation.addAnimation(fadeIn);
		animation.addAnimation(shake);
		animation.addAnimation(fadeOut);
		// fadeIn.setAnimationListener(animation1Listener);
		// shake.setAnimationListener(animation2Listener);
		// fadeOut.setAnimationListener(animation3Listener);
		// animation.setAnimationListener(animation1Listener);

	}

	public ResultStatus getResultStatus() {
		return mResultStatus;
	}

	public void setResultStatus(ResultStatus status) {
		this.mResultStatus = status;
	}

	public OvenStatus getStatus() {
		return mOvenStatus;
	}

	public void setStatus(OvenStatus status) {
		this.mOvenStatus = status;
	}

	public int getDishId() {
		return mDishId;
	}

	public void setDishId(int dishId) {
		this.mDishId = dishId;
	}

	public int getCount() {
		return mCount;
	}

	public void setCount(int count) {
		this.mCount = count;
	}

	public void startCooking() {
		mOvenStatus = OvenStatus.COOKING;
		mResultStatus = ResultStatus.RAW;
		Log.d("Marvin_Debug", "moved to cooking");
		startCookingAnim();

		timer = new CountDownTimer(10000, 1000) {
			public void onTick(long m) {
				int sec = (int) (10 - (m / 1000));
				((ProgressBar) mActivity.findViewById(mProgressBarId))
						.setProgress(sec);
			}

			public void onFinish() {
				mOvenStatus = OvenStatus.FINISHED;
				mResultStatus = ResultStatus.NORMAL;
				((ProgressBar) mActivity.findViewById(mProgressBarId))
						.setProgress(10);
				Log.d("Marvin_Debug", "moved to finished");
				afterCooking();
			}
		}.start();
	}

	public void afterCooking() {

		timer = new CountDownTimer(5000, 1000) {
			public void onTick(long m) {

			}

			public void onFinish() {
				mOvenStatus = OvenStatus.OVERCOOKED;
				mResultStatus = ResultStatus.BUNRT;
				Log.d("Marvin_Debug", "moved to over cooked");
			}
		}.start();
	}

	public void reset() {
		timer.cancel();
		mOvenStatus = OvenStatus.VACANT;
		mActivity.runOnUiThread(new Runnable() {  
            @Override
            public void run() {
        		ImageView img = ((ImageView) mActivity.findViewById(mResultId));
        		img.setBackgroundResource(R.drawable.stove);
        		((ProgressBar) mActivity.findViewById(mProgressBarId)).setProgress(0);
            }
        });
	}

	public void startCookingAnim() {
		ImageView img = ((ImageView) mActivity.findViewById(mResultId));
		img.setBackgroundResource(R.drawable.anim_cooking);
		AnimationDrawable anim = (AnimationDrawable) img.getBackground();
		((ImageView) mActivity.findViewById(mResultId))
				.setVisibility(View.VISIBLE);
		anim.start();
	}

	public void playResultAnimation(ResultStatus status) {
		ImageView img = ((ImageView) mActivity.findViewById(mResultId));
		if (status == ResultStatus.RAW) {
			img.setBackgroundResource(R.drawable.anim_raw);
		} else if (status == ResultStatus.NORMAL) {
			img.setBackgroundResource(R.drawable.anim_finished);
		} else if (status == ResultStatus.BUNRT) {
			img.setBackgroundResource(R.drawable.anim_burnt);
		}
		AnimationDrawable anim = (AnimationDrawable) img.getBackground();
		anim.start();
		stopAnim(anim);
	}

	public void stopAnim(final AnimationDrawable animation) {
		long totalDuration = 0;
		for (int i = 0; i < animation.getNumberOfFrames(); i++) {
			totalDuration += animation.getDuration(i);
		}
		Timer timer = new Timer();

		TimerTask timerTask = new TimerTask() {
			@Override
			public void run() {
				animation.stop();
				reset();
			}
		};
		timer.schedule(timerTask, totalDuration);
	}
}
