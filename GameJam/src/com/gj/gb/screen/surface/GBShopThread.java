package com.gj.gb.screen.surface;

import android.graphics.Canvas;

public class GBShopThread extends Thread {

	protected GBShopSurfaceView view;
	protected boolean running = false;
	
	public GBShopThread(GBShopSurfaceView view) {
		this.view = view;
	}
	
	public void setRunning(boolean run) {
		this.running = run;
	}

	@Override
	public void run() {
		super.run();
		while (running) {
			Canvas canvas = null;
			try {
				canvas = view.getHolder().lockCanvas();
				synchronized (view.getHolder()) {
					view.draw(canvas);
				}
			} finally {
				if (canvas != null) {
					view.getHolder().unlockCanvasAndPost(canvas);
				}
			}
		}
	}
	
}
