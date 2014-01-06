package com.gj.gb.screen.shop;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

public class GBCustomerSprite {

	public enum GBSpriteVisibility {

		INVISIBLE,
		
		VISIBLE,
		
		GONE
	}

	private Bitmap bitmap;
	private Paint paint;

	private float x, y;

	private GBSpriteVisibility visibility = GBSpriteVisibility.INVISIBLE;

	private int id;
	
	public GBCustomerSprite(int id, Bitmap bitmap, float x, float y) {
		this.setId(id);
		this.x = x;
		this.y = y;
		
		this.bitmap = bitmap;
		this.paint = new Paint();
	}

	public void update(long elapse) {
		switch (visibility) {
		case VISIBLE:
		case INVISIBLE:
		case GONE:
		}
	}

	public void render(Canvas canvas) {
		if (visibility == GBSpriteVisibility.VISIBLE) {
			canvas.drawBitmap(bitmap, x, y, paint);
		}
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}
	
	public void setVisibility(GBSpriteVisibility visibility) {
		this.visibility = visibility;
	}

	public GBSpriteVisibility getVisibility() {
		return this.visibility;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}