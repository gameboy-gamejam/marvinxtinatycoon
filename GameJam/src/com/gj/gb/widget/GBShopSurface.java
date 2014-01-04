package com.gj.gb.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GBShopSurface extends SurfaceView implements Runnable {

	private Thread thread = null;
	private SurfaceHolder holder;
	private boolean isRunning = false;
	
	public GBShopSurface(Context context) {
		super(context);
		holder = getHolder();
	}

	public GBShopSurface(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		holder = getHolder();
	}

	public GBShopSurface(Context context, AttributeSet attrs) {
		super(context, attrs);
		holder = getHolder();
	}

	public void destroy() {
		isRunning = false;
		while (true) {
			try {
				thread.join();
				break;
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		thread = null;
	}

	@Override
	public void run() {
		while (isRunning) {
			
		}
	}
	
	public void pause() {
		
	}
	
	public void resume() {
		if (thread == null) {
			isRunning = true;
			thread = new Thread(this);
			thread.start();
		}
	}
}
