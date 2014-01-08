package com.gj.gb.model;

import com.gj.gb.R;

import android.app.Activity;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.Animation.AnimationListener;
import android.widget.ImageView;
import android.widget.ProgressBar;

public class GBStove {
	public enum OvenStatus {
		VACANT, COOKING, // RAW pa
		FINISHED, // Tama lng
		OVERCOOKED // sunog na
	}

	private OvenStatus mStatus;
	private int mDishId;
	private int mCount;
	private int mProgressBarId;
	private int mResultId;
	private Activity mActivity;
	private CountDownTimer timer;
	private Animation fadeIn, fadeOut, shake;
	AnimationSet animation;

	public GBStove(Activity activity, int progressBarId, int resultId) {
		this.mStatus = OvenStatus.VACANT;
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
//		fadeIn.setAnimationListener(animation1Listener);
//		shake.setAnimationListener(animation2Listener);
//		fadeOut.setAnimationListener(animation3Listener);
		animation.setAnimationListener(animation1Listener);

	}

	public OvenStatus getStatus() {
		return mStatus;
	}

	public void setStatus(OvenStatus status) {
		this.mStatus = status;
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

	// process
	// at start nasa VACANT yung stove
	// tapos tatawagin to after ma press yung cook button sa pop up
	// edi magiging COOKING status... sya
	// kapag na click ulit sya tapos cooking status pa rin lagot sya... raw
	// yun..
	// after 10 sec lilipat na sya sa FINISHED
	// ayun ang sakto lng pero hindi pa dun nagtatapos
	// may after cooking pa...
	// mga 5sec after mapunta sya sa FInished ay masusunog na yung dish...
	// panic mode n yun
	// in short fail..
	// so kapag COOKING at OVERCOOKED yung status rereset ko n lng yung lahat
	public void startCooking() {
		mStatus = OvenStatus.COOKING;
		Log.d("Marvin_Debug", "moved to cooking");
		timer = new CountDownTimer(10000, 1000) {
			public void onTick(long m) {
				int sec = (int) (10 - (m / 1000));
				((ProgressBar) mActivity.findViewById(mProgressBarId))
						.setProgress(sec);
			}

			public void onFinish() {
				mStatus = OvenStatus.FINISHED;
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
				mStatus = OvenStatus.OVERCOOKED;
				Log.d("Marvin_Debug", "moved to over cooked");
			}
		}.start();
	}

	public void reset() {
		timer.cancel();
		mStatus = OvenStatus.VACANT;
		((ProgressBar) mActivity.findViewById(mProgressBarId)).setProgress(0);
	}

	public void startAnim() {
		
		
		if (mStatus == OvenStatus.COOKING) {
			((ImageView) mActivity.findViewById(mResultId))
					.setBackgroundResource(R.drawable.raw);
		} else if (mStatus == OvenStatus.FINISHED) {
			((ImageView) mActivity.findViewById(mResultId))
					.setBackgroundResource(R.drawable.normal);
		} else if (mStatus == OvenStatus.OVERCOOKED) {
			((ImageView) mActivity.findViewById(mResultId))
					.setBackgroundResource(R.drawable.raw);
		}
		
		reset();
		((ImageView) mActivity.findViewById(mResultId)).startAnimation(animation);

		((ImageView) mActivity.findViewById(mResultId)).setVisibility(View.VISIBLE);
		
	}

	AnimationListener animation1Listener = new AnimationListener() {

		@Override
		public void onAnimationStart(Animation animation) {


		}

		@Override
		public void onAnimationRepeat(Animation animation) {

		}

		@Override
		public void onAnimationEnd(Animation animation) {
			Log.d("Marvin_Debug", "sdfsdfsdfs");
			((ImageView) mActivity.findViewById(mResultId))
			.setVisibility(View.GONE);
			
		}
	};
}
