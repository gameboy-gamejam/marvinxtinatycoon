package com.gj.gb.model;

import android.app.Activity;
import android.os.CountDownTimer;
import android.util.Log;
import android.widget.ProgressBar;

public class GBStove {
	public enum OvenStatus {
		VACANT, 
		COOKING, //RAW pa
		FINISHED, // Tama lng
		OVERCOOKED // sunog na
	}

	private OvenStatus mStatus;
	private int mDishId;
	private int mCount;
	private int mProgressBarId;
	private Activity mActivity;
	private CountDownTimer timer;
	

	public GBStove(Activity activity, int progressBarId) {
		this.mStatus = OvenStatus.VACANT;
		this.mDishId = -1;
		this.mCount = 0;
		this.mProgressBarId = progressBarId;
		this.mActivity = activity;
		((ProgressBar) mActivity.findViewById(mProgressBarId)).setMax(10);
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
	// tapos tatawagin to after ma press  yung cook button sa pop up
	// edi magiging COOKING status... sya
	// kapag na click ulit sya tapos cooking status pa rin lagot sya... raw yun..
	// after 10 sec lilipat na sya sa FINISHED
	// ayun ang sakto lng pero hindi pa dun nagtatapos
	// may after cooking pa... 
	// mga 5sec after mapunta sya sa FInished ay masusunog na  yung dish...
	// panic mode n yun
	// in short fail..
	//so kapag COOKING at OVERCOOKED yung status rereset ko n lng yung lahat
	public void startCooking() {
		mStatus = OvenStatus.COOKING;
		Log.d("Marvin_Debug", "moved to cooking");
		timer = new CountDownTimer(10000, 1000) {
			public void onTick(long m) {
				int sec = (int) (10 - (m / 1000));
				((ProgressBar) mActivity.findViewById(mProgressBarId)).setProgress(sec);
			}

			public void onFinish() {
				mStatus = OvenStatus.FINISHED;
				((ProgressBar) mActivity.findViewById(mProgressBarId)).setProgress(10);
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
}
