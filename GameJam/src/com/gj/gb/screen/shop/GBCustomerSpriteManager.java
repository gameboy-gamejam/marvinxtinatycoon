package com.gj.gb.screen.shop;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.Log;
import android.view.MotionEvent;

import com.gj.gb.model.GBNewCustomer;
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
	
	public GBCustomerSpriteManager(Activity activity, float width, float height) {
		sprites = new ArrayList<GBCustomerSprite>();
		this.activity = activity;
		
		this.screenWidth = width;
		this.screenHeight = height;
		
		this.scaleWidthSample = screenWidth / 5;
		this.bufferSpace = (screenWidth-(scaleWidthSample*4))/5;
	}
	
	public void createSpriteFromCustomer(GBNewCustomer customer) {
		
		Bitmap spriteBitmap = ImageCache.getBitmap("scaled_customer_" + (customer.getAvatar()-1));
		if (spriteBitmap == null) {
			Bitmap bitmap = ImageCache.getBitmap(activity, "recipe_" + (customer.getAvatar()-1));
			Log.w("test", "Bitmap: " + bitmap.getWidth() + "x" + bitmap.getHeight());
			
			float imageRatio = scaleWidthSample / bitmap.getWidth();
			Log.w("test", "Ratio: " + imageRatio);
			
			spriteBitmap = Utils.getResizedBitmap(bitmap, imageRatio, imageRatio);
			Log.w("test", "Scaled Bitmap: " + spriteBitmap.getWidth() + "x" + spriteBitmap.getHeight());

			ImageCache.putBitmap(spriteBitmap, "scaled_customer_" + (customer.getAvatar()-1));
		}
//		
		GBCustomerSprite sprite = new GBCustomerSprite(customer, spriteBitmap, 0, 0);
		sprite.setListener(listener);
		sprites.add(sprite);
	}
	
	public void update(long elapse) {
		int n = sprites.size();
		for (int i=0; i<n; i++) {
			sprites.get(i).update(elapse);
		}
	}
	
	public void render(Canvas canvas) {
		int n = sprites.size();
		for (int i=0; i<n; i++) {
			sprites.get(i).render(canvas);
		}
	}
	
	public void makeCustomerSpriteVisible(int slot, int id) {
		int n = sprites.size();
		
		for (int i=0; i<n; i++) {
			GBCustomerSprite sprite = sprites.get(i);
			if (sprite.getId() == id) {
				sprite.setVisibility(GBSpriteVisibility.VISIBLE);
				setLocation(sprite, slot);
				break;
			}
		}
	}

	private void setLocation(GBCustomerSprite sprite, int slot) {
		float x = ((slot+1) * bufferSpace) + (sprite.getWidth() * slot);
		float y = 300;
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
		
		for (int i=0; i<n; i++) {
			GBCustomerSprite sprite = sprites.get(i);
			if (sprite.getId() == id) {
				sprite.setVisibility(GBSpriteVisibility.GONE);
				break;
			}
		}
	}

	public boolean onTouch(MotionEvent event) {
		int n = sprites.size();
		
		for (int i=0; i<n; i++) {
			sprites.get(i).onTouch(event);
		}
		return true;
	}

	public void setRestaurantListener(GBRestaurantDataListener listener) {
		this.listener = listener;
		int n = sprites.size();

		for (int i=0; i<n; i++) {
			sprites.get(i).setListener(listener);
		}
	}
}
