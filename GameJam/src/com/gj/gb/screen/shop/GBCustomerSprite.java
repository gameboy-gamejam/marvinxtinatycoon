package com.gj.gb.screen.shop;

import android.graphics.Bitmap;
import android.graphics.BlurMaskFilter;
import android.graphics.BlurMaskFilter.Blur;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;

import com.gj.gb.model.GBNewCustomer;
import com.gj.gb.model.GBNewCustomer.GBCustomerState;

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

	private GBNewCustomer customer;
	
	private String order;
	
	public GBCustomerSprite(GBNewCustomer customer, Bitmap bitmap, float x, float y) {
		this.customer = customer;
		this.x = x;
		this.y = y;
		
		this.bitmap = bitmap;
		this.paint = new Paint();
		this.paint.setTextSize(50.0f);
	}

	public void update(long elapse) {
		GBCustomerState state = customer.getState();

		// TODO: gawin tong images
		if (state == GBCustomerState.DECIDING) {
			order = "...";
		} else if (state == GBCustomerState.WAITING) {
			order = customer.getOrder().getName();
		} else {
			order = "";
		}
	}

	public void render(Canvas canvas) {
		if (visibility == GBSpriteVisibility.VISIBLE) {
			/* glow effect */
			Bitmap bitmap2 = bitmap.extractAlpha();
			Paint paint2 = new Paint();
					int glowColor = Color.rgb(0, 192, 255);
					paint2.setColor(glowColor);
					int glowRadius = 16;
					paint2.setMaskFilter(new BlurMaskFilter(glowRadius, Blur.OUTER));
					canvas.drawBitmap(bitmap2, x, y, paint2);
					/* */
			canvas.drawBitmap(bitmap, x, y, paint);
			canvas.drawText(order, x, y - 100, paint);
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
		return customer.getId();
	}

	// issue all visible sprites are removed: FIXED
	public void onTouch(MotionEvent event) {
		if (visibility == GBSpriteVisibility.VISIBLE && customer.getState() == GBCustomerState.WAITING) {
			int action = event.getAction();
			float eventX = event.getX();
			float eventY = event.getY();
			int width = bitmap.getWidth();
			int height = bitmap.getHeight();
			
			if (action == MotionEvent.ACTION_DOWN) {
				
				// touch event is outside range
				if (eventX <= (x+5) || eventY <= (y+5) || eventX >= (x+width-5) || eventY >= (y+height-5)) {
					return;
				}
			} else if (action == MotionEvent.ACTION_UP) {

				if (eventX <= (x+5) || eventY <= (y+5) || eventX >= (x+width-5) || eventY >= (y+height-5)) {
					return;
				}
				
				customer.setState(GBCustomerState.SERVED);
			}
		}
	}
}