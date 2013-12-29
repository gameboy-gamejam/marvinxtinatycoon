package com.gj.gb.gameview;

import android.graphics.Bitmap;

public class GBDishModel {
	private Bitmap mImage;
	private String mName;
	private String mDescription;

	public GBDishModel(Bitmap image, String name) {
		super();
		this.mImage = image;
		this.mName = name;
	}

	public Bitmap getImage() {
		return mImage;
	}

	public void setImage(Bitmap image) {
		this.mImage = image;
	}

	public String getTitle() {
		return mName;
	}

	public void setTitle(String name) {
		this.mName = name;
	}

}