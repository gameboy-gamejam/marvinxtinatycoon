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

	private Bitmap bitmap, glowBitmap;
	private Paint paint, glowPaint, shadowPaint;

	private float x, y;
	private float pointY, targetY;

	private GBSpriteVisibility visibility = GBSpriteVisibility.INVISIBLE;

	private GBNewCustomer customer;

	private String order;

	private boolean served = false;
	private boolean selected = false;
	private GBRestaurantDataListener listener;
	private Bitmap thoughtCloud;
	private Bitmap defaultThought;
	private Bitmap orderBitmap;

	public GBCustomerSprite(GBNewCustomer customer, Bitmap bitmap, float x,
			float y) {
		this.customer = customer;
		this.x = x;
		this.y = y;

		this.pointY = y;
		this.targetY = y - 50;

		this.bitmap = bitmap;
		this.paint = new Paint();
		this.paint.setTextSize(50.0f);

		this.glowBitmap = bitmap.extractAlpha();
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
		GBCustomerState state = customer.getState();

		// TODO: gawin tong images
		if (state == GBCustomerState.DECIDING) {
			order = "...";
		} else if (state == GBCustomerState.WAITING
				|| state == GBCustomerState.LEAVING) {
			order = customer.getOrder().getName();
		} else {
			order = "";
		}
	}
	

	public void render(Canvas canvas) {
		if (visibility == GBSpriteVisibility.VISIBLE) {
			if (selected) {
				canvas.drawBitmap(glowBitmap, x, y, glowPaint);
			}
			canvas.drawBitmap(bitmap, x, y, paint);
//			canvas.drawText(order, x, y - 100, paint);
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
			int width = bitmap.getWidth();
			int height = bitmap.getHeight();

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
			int width = bitmap.getWidth();
			int height = bitmap.getHeight();

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
		return bitmap.getWidth();
	}
	
	public int getHeight() {
		return bitmap.getHeight();
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