package com.gj.gb.screen.surface;

import android.content.Context;
import android.graphics.Canvas;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GBShopSurfaceView extends SurfaceView implements SurfaceHolder.Callback {

	protected SurfaceHolder surfaceHolder;
	
	protected GBShopThread thread;
	
	public GBShopSurfaceView(Context context) {
		super(context);
		thread = new GBShopThread(this);
		surfaceHolder = getHolder();
		surfaceHolder.addCallback(this);
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		thread.setRunning(true);
		thread.start();
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		boolean retry = true;
		thread.setRunning(false);
		
		while (retry) {
			try {
				thread.join();
				retry = false;
			} catch (InterruptedException e) {
				// do nothing
			}
		}
	}

	@Override
	public void draw(Canvas canvas) {
		super.draw(canvas);
	}

}
