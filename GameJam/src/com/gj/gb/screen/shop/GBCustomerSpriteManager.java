package com.gj.gb.screen.shop;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;
import android.view.MotionEvent;

import com.gj.gb.R.color;
import com.gj.gb.model.GBNewCustomer;
import com.gj.gb.model.GBRecipe;
import com.gj.gb.screen.shop.GBCustomerSprite.GBSpriteVisibility;
import com.gj.gb.util.ImageCache;
import com.gj.gb.util.Utils;

public class GBCustomerSpriteManager {

	private Activity activity;

	private List<GBCustomerSprite> sprites;

	private GBRestaurantDataListener listener;

	private float screenWidth, screenHeight;

	private float scaleWidthSample;
	private float bufferSpace;

	private Bitmap thoughtCloud, defaultThought;

	public GBCustomerSpriteManager(Activity activity, float width, float height) {
		sprites = new ArrayList<GBCustomerSprite>();
		this.activity = activity;

		this.screenWidth = width;
		this.screenHeight = height;

		this.scaleWidthSample = screenWidth / 5;
		this.bufferSpace = (screenWidth - (scaleWidthSample * 4)) / 5;
	}

	public void createSpriteFromCustomer(GBNewCustomer customer) {

		Bitmap spriteBitmap = ImageCache.getBitmap("scaled_customer_1");
		if (spriteBitmap == null) {
			Bitmap bitmap = ImageCache.getBitmap(activity, "customer_1");

			float imageRatio = scaleWidthSample / bitmap.getWidth();

			spriteBitmap = Utils.getResizedBitmap(bitmap, imageRatio,
					imageRatio);

			ImageCache.putBitmap(spriteBitmap, "scaled_customer_1");
		}

		thoughtCloud = ImageCache.getBitmap("scaled_image_thought");
		if (thoughtCloud == null) {
			thoughtCloud = ImageCache.getBitmap(activity, "image_thought");
			float imageRatio = scaleWidthSample / thoughtCloud.getWidth();
			thoughtCloud = Utils.getResizedBitmap(thoughtCloud, imageRatio,
					imageRatio);
			ImageCache.putBitmap(thoughtCloud, "scaled_image_thought");
		}

		defaultThought = ImageCache.getBitmap("scaled_image_thinking");
		if (defaultThought == null) {
			defaultThought = ImageCache.getBitmap(activity, "image_thinking");
			float imageRatio = scaleWidthSample / defaultThought.getWidth();
			defaultThought = Utils.getResizedBitmap(defaultThought, imageRatio,
					imageRatio);
			ImageCache.putBitmap(defaultThought, "scaled_image_thinking");
		}

		Bitmap bitmap2 = ImageCache.getBitmap("scaled_customer_1_2");
		if (bitmap2 == null) {
			Bitmap bitmap = ImageCache.getBitmap(activity, "customer_1_2");
			Log.w("test", "image 2 = " + (bitmap == null));
			float imageRatio = scaleWidthSample / bitmap.getWidth();
			bitmap2 = Utils.getResizedBitmap(bitmap, imageRatio, imageRatio);
			ImageCache.putBitmap(bitmap2, "scaled_customer_1_2");
		}

		Bitmap bitmap3 = ImageCache.getBitmap("scaled_customer_1_3");
		if (bitmap3 == null) {
			Bitmap bitmap = ImageCache.getBitmap(activity, "customer_1_3");
			Log.w("test", "image 3 = " + (bitmap == null));
			float imageRatio = scaleWidthSample / bitmap.getWidth();
			bitmap3 = Utils.getResizedBitmap(bitmap, imageRatio, imageRatio);
			ImageCache.putBitmap(bitmap3, "scaled_customer_1_3");
		}

		GBCustomerSprite sprite = new GBCustomerSprite(customer, spriteBitmap,
				bitmap2, bitmap3, 0, 0);
		sprite.setListener(listener);
		sprite.setThoughtBitmap(thoughtCloud, defaultThought);
		sprites.add(sprite);
	}

	public void update(long elapse) {
		int n = sprites.size();
		for (int i = 0; i < n; i++) {
			GBCustomerSprite sprite = sprites.get(i);
			sprite.update(elapse);
			if (sprite.hasOrder()) {
				loadOrderBitmap(sprite, sprite.getOrder());
			}
		}
	}

	private void loadOrderBitmap(GBCustomerSprite sprite, GBRecipe order) {
		int id = order.getId();
		Bitmap bm = ImageCache.getBitmap("scaled_recipe_" + id);
		if (bm == null) {
			bm = ImageCache.getBitmap(activity, "recipe_" + id);
			float src = thoughtCloud.getWidth();
			float dest = bm.getWidth();
			float imageRatio = src / dest;
			Log.w("test", "Cloud: " + thoughtCloud.getWidth());
			Log.w("test", "Ratio: " + imageRatio);
			bm = Utils.getResizedBitmap(bm, imageRatio, imageRatio);
			ImageCache.putBitmap(bm, "scaled_recipe_" + id);
		}
		sprite.setOrderBitmap(bm);
	}

	public void render(Canvas canvas) {
		int n = sprites.size();
		for (int i = 0; i < n; i++) {
			sprites.get(i).render(canvas);
		}
		Paint paint = new Paint();
		paint.setColor(color.gray_dark);
		canvas.drawRect(0, screenHeight - 100, screenWidth, screenHeight, paint);
	}

	public void makeCustomerSpriteVisible(int slot, int id) {
		int n = sprites.size();

		for (int i = 0; i < n; i++) {
			GBCustomerSprite sprite = sprites.get(i);
			if (sprite.getId() == id) {
				sprite.setVisibility(GBSpriteVisibility.VISIBLE);
				setLocation(sprite, slot);
				break;
			}
		}
	}

	private void setLocation(GBCustomerSprite sprite, int slot) {
		float x = ((slot + 1) * bufferSpace) + (sprite.getWidth() * slot);
		float y = screenHeight - sprite.getHeight() - 100;
		if (slot == 0) {
			sprite.setX(x);
			sprite.setY(y);
		} else if (slot == 1) {
			sprite.setX(x);
			sprite.setY(y);
		} else if (slot == 2) {
			sprite.setX(x);
			sprite.setY(y);
		} else if (slot == 3) {
			sprite.setX(x);
			sprite.setY(y);
		}
	}

	public void makeCustomerSpriteInvisible(int id) {
		int n = sprites.size();

		for (int i = 0; i < n; i++) {
			GBCustomerSprite sprite = sprites.get(i);
			if (sprite.getId() == id) {
				sprite.setVisibility(GBSpriteVisibility.GONE);
				break;
			}
		}
	}

	public boolean onTouch(MotionEvent event) {
		int n = sprites.size();

		for (int i = 0; i < n; i++) {
			sprites.get(i).onTouch(event);
		}
		return true;
	}

	public void setRestaurantListener(GBRestaurantDataListener listener) {
		this.listener = listener;
		int n = sprites.size();

		for (int i = 0; i < n; i++) {
			sprites.get(i).setListener(listener);
		}
	}
}
