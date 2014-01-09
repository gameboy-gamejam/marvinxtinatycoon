package com.gj.gb.screen.shop;

import android.graphics.Bitmap;
import android.graphics.BlurMaskFilter;
import android.graphics.BlurMaskFilter.Blur;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;

import com.gj.gb.logic.GBEconomics;
import com.gj.gb.model.GBNewCustomer;
import com.gj.gb.model.GBNewCustomer.GBCustomerState;
import com.gj.gb.model.GBRecipe;
import com.gj.gb.util.GBDataManager;

public class GBCustomerSprite {

	public enum GBSpriteVisibility {

		INVISIBLE,

		VISIBLE,

		GONE
	}

	private Bitmap bitmap1, bitmap2, bitmap3, glowBitmap;
	private Paint paint, glowPaint, shadowPaint;

	private float x, y;
	private float pointY, targetY;

	private GBSpriteVisibility visibility = GBSpriteVisibility.INVISIBLE;

	private GBNewCustomer customer;

	private boolean served = false;
	private boolean selected = false;
	private GBRestaurantDataListener listener;
	private Bitmap thoughtCloud;
	private Bitmap defaultThought;
	private Bitmap orderBitmap;

	public GBCustomerSprite(GBNewCustomer customer, Bitmap bitmap1, Bitmap bitmap2, Bitmap bitmap3, float x,
			float y) {
		this.customer = customer;
		this.x = x;
		this.y = y;

		this.pointY = y;
		this.targetY = y - 50;

		this.paint = new Paint();
		this.paint.setTextSize(50.0f);

        this.bitmap1 = bitmap1;
        this.bitmap2 = bitmap2;
        this.bitmap3 = bitmap3;
        
		this.glowBitmap = bitmap1.extractAlpha();
		this.glowPaint = new Paint();

		int glowColor = Color.rgb(0, 192, 255);
		int glowRadius = 16;
		this.glowPaint.setColor(glowColor);
		this.glowPaint
				.setMaskFilter(new BlurMaskFilter(glowRadius, Blur.OUTER));
		this.shadowPaint = new Paint();
        shadowPaint.setAntiAlias(true);
        shadowPaint.setColor(Color.WHITE);
        shadowPaint.setTextSize(45.0f);
        shadowPaint.setStrokeWidth(2.0f);
        shadowPaint.setStyle(Paint.Style.STROKE);
        shadowPaint.setShadowLayer(5.0f, 10.0f, 10.0f, Color.BLACK);
	}

	public void update(long elapse) {

	}
	

	public void render(Canvas canvas) {
		if (visibility == GBSpriteVisibility.VISIBLE) {
			if (selected) {
				canvas.drawBitmap(glowBitmap, x, y, glowPaint);
			}
			GBCustomerState state = customer.getState();
			
			if (state == GBCustomerState.LEAVING) {
				canvas.drawBitmap(bitmap3, x, y, paint);
			} else if (customer.getSomething()) {
				canvas.drawBitmap(bitmap2, x, y, paint);
			} else {
				canvas.drawBitmap(bitmap1, x, y, paint);
			}
			canvas.drawBitmap(thoughtCloud, x, y-thoughtCloud.getHeight(), paint);
			if (customer.getOrder() == null) {
				canvas.drawBitmap(defaultThought, x, y-thoughtCloud.getHeight(), paint);
			} else {
				canvas.drawBitmap(orderBitmap, x, y-thoughtCloud.getHeight(), paint);
			}
		} else if (visibility == GBSpriteVisibility.GONE && served) {
			// draw points
			if (pointY >= targetY) {
				int goldEarned = GBEconomics
						.getRecipePrice(customer.getOrder())
						+ customer.getTip();
				canvas.drawText("+" + goldEarned + "G", x, pointY, shadowPaint);
				pointY -= 2;
			}
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
		this.pointY = y;
		this.targetY = y - 50;
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
		if (visibility == GBSpriteVisibility.VISIBLE
				&& customer.getState() == GBCustomerState.WAITING) {
			int action = event.getAction();
			float eventX = event.getX();
			float eventY = event.getY();
			int width = bitmap1.getWidth();
			int height = bitmap1.getHeight();

			// touch event is outside range
			if (eventX <= (x + 5) || eventY <= (y + 5)
					|| eventX >= (x + width - 5)
					|| eventY >= (y + height - 5)) {
				selected = false;
				return;
			}
			
			if (action == MotionEvent.ACTION_DOWN) {
				selected = true;
			} else if (action == MotionEvent.ACTION_UP) {
				selected = false;
				if (GBDataManager.getGameData().hasDish(customer.getOrder().getId())) {
					served = true;
					customer.setState(GBCustomerState.SERVED);
					listener.onDishServed(customer);
				}
			}
		} else if (customer.getState() == GBCustomerState.LEAVING
				|| customer.getState() == GBCustomerState.DECIDING) {
			int action = event.getAction();
			float eventX = event.getX();
			float eventY = event.getY();
			int width = bitmap1.getWidth();
			int height = bitmap1.getHeight();

			// touch event is outside range
			if (eventX <= (x + 5) || eventY <= (y + 5)
					|| eventX >= (x + width - 5)
					|| eventY >= (y + height - 5)) {
				selected = false;
				return;
			}
			
			if (action == MotionEvent.ACTION_DOWN) {
				selected = true;
			} else if (action == MotionEvent.ACTION_UP) {
				selected = false;
				if (customer.getState() == GBCustomerState.LEAVING) {
					served = true;
					customer.setState(GBCustomerState.SERVED);
					listener.onDishServed(customer);
				}
			}			
		}
	}
	
	public int getWidth() {
		return bitmap1.getWidth();
	}
	
	public int getHeight() {
		return bitmap1.getHeight();
	}

	public void setListener(GBRestaurantDataListener listener) {
		this.listener = listener;
	}

	public void setThoughtBitmap(Bitmap thoughtCloud, Bitmap defaultThought) {
		this.thoughtCloud = thoughtCloud;
		this.defaultThought = defaultThought;
	}

	public boolean hasOrder() {
		return customer.getOrder() != null;
	}

	public GBRecipe getOrder() {
		return customer.getOrder();
	}

	public void setOrderBitmap(Bitmap bm) {
		this.orderBitmap = bm;
	}
}