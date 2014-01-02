package com.gj.gb.stage.common;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PowerManager;
import android.view.KeyEvent;
import android.view.SurfaceHolder;
import android.view.View.OnTouchListener;

import com.gj.gb.screen.GBSplash;

public abstract class Stage extends Activity implements SurfaceHolder.Callback {
	public static final int REQUEST_CODE_GAME_START = 11000;
	public static final int REQUEST_CODE_SHOW_EXIT_MENU = 11001;
	protected boolean mIsGameFinish = false;
	protected boolean mIsShowReadyInstruction = false;
	protected OnTouchListener mOnTouchListener;

	protected abstract void playGame();// director of the stage
	
	protected abstract void resumeGame();
	
	protected abstract void endGame();

	protected abstract void showReadyInstruction();

	protected abstract void showInGameMenu();

	protected abstract void showPointsAndReward();

	protected abstract void releaseResources();

	private void disruptAndExitGame() {
		mIsGameFinish = true;
		endGame();
		showPointsAndReward();
	}
	
	protected void closeAllExceptSplash(){
        Intent intent = new Intent(this, GBSplash.class);
        intent.putExtra("ExitMe", true);//TODO add me to GBSplash (add finish();)
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

	//TODO baka pedeng iakyat
	@Override
	public boolean dispatchKeyEvent(KeyEvent event) {
		if (event.getAction() == KeyEvent.ACTION_DOWN && event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
			showInGameMenu();
		}
		return super.dispatchKeyEvent(event);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// TODO set phone state listener
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == REQUEST_CODE_GAME_START) {
			if (resultCode == RESULT_OK) {
				playGame();
			} else if (resultCode == RESULT_CANCELED) {
				finish();
			}
		} if (requestCode == REQUEST_CODE_SHOW_EXIT_MENU) {
            if (resultCode == RESULT_OK) {
                finish();
            } else if (resultCode == RESULT_CANCELED) {
                resumeGame();
            }
        }  
	}

	@Override
	protected void onPause() {
		super.onPause();
		if (isFinishing()) {
			releaseResources();
		} else {
			boolean isScreenOn = ((PowerManager) this.getApplicationContext()
					.getSystemService(Context.POWER_SERVICE)).isScreenOn();
			if (!isScreenOn) {
				disruptAndExitGame();
			}
		}
	}

	@Override
	protected void onUserLeaveHint() {
		super.onUserLeaveHint();
		releaseResources();
		finish();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		mOnTouchListener = null;
	}
}
