package com.gj.gb.util;

import java.util.Random;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;

public class Utils {

	public static final Random RANDOM = new Random(System.currentTimeMillis());
	
	public static Bitmap getBitmapFromDrawable(Context context, String prefix, int id) {
		return getBitmapFromDrawable(context, prefix+"_"+id);
	}
	
	public static Bitmap getBitmapFromDrawable(Context context, String name) {
		int resid = context.getResources().getIdentifier(name, "drawable", context.getPackageName());
		BitmapDrawable drawable = (BitmapDrawable) context.getResources().getDrawable(resid);
		return drawable.getBitmap();
	}
}