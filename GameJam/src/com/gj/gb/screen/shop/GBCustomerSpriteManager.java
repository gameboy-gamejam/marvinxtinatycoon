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

public class GBCustomerSpriteManager {

	private Activity activity;
	
	private List<GBCustomerSprite> sprites;
	
	public GBCustomerSpriteManager(Activity activity) {
		sprites = new ArrayList<GBCustomerSprite>();
		this.activity = activity;
	}
	
	public void createSpriteFromCustomer(GBNewCustomer customer) {
		Bitmap bitmap = ImageCache.getBitmap(activity, "customer_" + (customer.getAvatar()-1));
		
		sprites.add(new GBCustomerSprite(customer, bitmap, 0, 0));
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
		if (slot == 0) {
			sprite.setX(20);
			sprite.setY(300);
		} else if (slot == 1) {
			sprite.setX(170);
			sprite.setY(300);
		} else if (slot == 2) {
			sprite.setX(320);
			sprite.setY(300);
		} else if (slot == 3) {
			sprite.setX(470);
			sprite.setY(300);
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
}
