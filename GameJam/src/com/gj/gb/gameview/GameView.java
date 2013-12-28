package com.gj.gb.gameview;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.gj.gb.R;

public class GameView extends SurfaceView {

	private SurfaceHolder holder;

	private GameLoopThread gameLoopThread;

	private List<GBSpriteModel> sprites = new ArrayList<GBSpriteModel>();

	private long lastClick;

	public GameView(Context context) {

		super(context);

		gameLoopThread = new GameLoopThread(this);

		holder = getHolder();

		holder.addCallback(new SurfaceHolder.Callback() {

			@Override
			public void surfaceDestroyed(SurfaceHolder holder) {

			}

			@Override
			public void surfaceCreated(SurfaceHolder holder) {

				createSprites();

				gameLoopThread.setRunning(true);

				gameLoopThread.start();

			}

			@Override
			public void surfaceChanged(SurfaceHolder holder, int format,

			int width, int height) {

			}

		});

	}

	private void createSprites() {

		sprites.add(createSprite(R.drawable.bad1));

		sprites.add(createSprite(R.drawable.bad2));

	}

	private GBSpriteModel createSprite(int resouce) {

		Bitmap bmp = BitmapFactory.decodeResource(getResources(), resouce);

		return new GBSpriteModel(this, bmp);

	}

	@Override
	public void draw(Canvas canvas) {

		canvas.drawColor(Color.BLACK);

		for (GBSpriteModel sprite : sprites) {

			sprite.draw(canvas);

		}

	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {

		if (System.currentTimeMillis() - lastClick > 500) {

			lastClick = System.currentTimeMillis();

			synchronized (getHolder()) {

				for (int i = sprites.size() - 1; i >= 0; i--) {

					GBSpriteModel sprite = sprites.get(i);

					if (sprite.isCollition(event.getX(), event.getY())) {

						sprites.remove(sprite);

						break;

					}

				}

			}

		}

		return true;

	}

}