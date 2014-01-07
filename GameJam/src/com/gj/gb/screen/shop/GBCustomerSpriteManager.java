package com.gj.gb.screen.shop;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.view.MotionEvent;

import com.gj.gb.model.GBNewCustomer;
import com.gj.gb.screen.shop.GBCustomerSprite.GBSpriteVisibility;
import com.gj.gb.util.ImageCache;
import com.gj.gb.util.Utils;

public class GBCustomerSpriteManager {

	private static final int SPACE_BUFFER = 50;
	
	private Activity activity;
	
	private List<GBCustomerSprite> sprites;
	
	private float scaleWidth, scaleHeight;
	
	private int screenWidth, screenHeight;

	private int customerWidth;

	private GBRestaurantDataListener listener;
	
	public GBCustomerSpriteManager(Activity activity, int width, int height) {
		sprites = new ArrayList<GBCustomerSprite>();
		this.activity = activity;
		
		screenWidth = width;
		screenHeight = height;
		
		scaleWidth = width / 960;
		scaleHeight = height / 720;
		
		customerWidth = (screenWidth - (SPACE_BUFFER*4)) / 4;
	}
	
	public void createSpriteFromCustomer(GBNewCustomer customer) {
		
		Bitmap spriteBitmap = ImageCache.getBitmap("scaled_customer_" + (customer.getAvatar()-1));
		if (spriteBitmap == null) {
			Bitmap bitmap = ImageCache.getBitmap(activity, "customer_" + (customer.getAvatar()-1));
			spriteBitmap = Utils.getResizedBitmap(bitmap, scaleWidth, scaleHeight);

			ImageCache.putBitmap(spriteBitmap, "scaled_customer_" + (customer.getAvatar()-1));
		}
		
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
		float x = ((SPACE_BUFFER * (slot+1)) /* buffer */ + (customerWidth * slot)) * scaleWidth;
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
