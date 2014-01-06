package com.gj.gb.screen.shop;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

public class GBCustomerSprite {

	public enum GBSpriteVisibility {

		INVISIBLE,
		
		FADING_IN,
		
		VISIBLE,
		
		FADING_OUT,
		
		GONE
	}

	private static final int FADE_MILLISECONDS = 3000; // 3 second fade effect
	private static final int FADE_STEP = 120;          // 120ms refresh
	// Calculate our alpha step from our fade parameters
	private static final int ALPHA_STEP = 255 / (FADE_MILLISECONDS / FADE_STEP);

	private Bitmap bitmap;
	private Paint paint;

	private float x, y;

	private GBSpriteVisibility visibility = GBSpriteVisibility.INVISIBLE;

	public GBCustomerSprite(Bitmap bitmap, float x, float y) {
		this.x = x;
		this.y = y;
		
		this.bitmap = bitmap;
		this.paint = new Paint();
		this.paint.setAlpha(0);
	}

	public void update(long elapse) {
		int alpha = paint.getAlpha();
		
		switch (visibility) {
		case VISIBLE:
			paint.setAlpha(255);
			break;
		case FADING_IN:
			if (alpha < 255) {
				alpha += ALPHA_STEP; 
			} else {
				alpha = 255;
			}
			paint.setAlpha(alpha);
			
			if (alpha == 255) {
				visibility = GBSpriteVisibility.VISIBLE;
			}
			break;
		case FADING_OUT:
			if (alpha > 0) {
				alpha -= ALPHA_STEP; 
			} else {
				alpha = 0;
			}
			paint.setAlpha(alpha);
			
			if (alpha == 255) {
				visibility = GBSpriteVisibility.GONE;
			}
			break;
		case INVISIBLE:
		case GONE:
		}
	}

	public void render(Canvas canvas) {
		if (visibility == GBSpriteVisibility.VISIBLE) {
			canvas.drawBitmap(bitmap, x, y, paint);
		} else if (visibility == GBSpriteVisibility.FADING_OUT) {
			canvas.drawBitmap(bitmap, x, y, paint);
		} else if (visibility == GBSpriteVisibility.FADING_IN) {
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
}