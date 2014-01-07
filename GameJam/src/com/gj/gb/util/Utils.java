package com.gj.gb.util;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Random;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
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
    
    public static String formatNum(long number, String format) {
        NumberFormat formatter = new DecimalFormat(format);
        return formatter.format(number);
    }

	public static CharSequence formatDate(int currentDay, int currentMonth,
			int currentYear) {
		return "Year " + currentYear + " - " + parseMonth(currentMonth) + " " + currentDay;
	}

	private static String parseMonth(int mm) {
		switch (mm) {
		case 1: return "Jan";
		case 2: return "Feb";
		case 3: return "Mar";
		case 4: return "Apr";
		case 5: return "May";
		case 6: return "Jun";
		case 7: return "Jul";
		case 8: return "Aug";
		case 9: return "Sep";
		case 10: return "Oct";
		case 11: return "Nov";
		case 12: return "Dec";
		}
		return null;
	}
	
	public static Intent getIntent(Context context, Class<?> cls) {
	    Intent intent = new Intent(context, cls);
	    intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
	    return intent;
	}
	
	public static Bitmap getResizedBitmap(Bitmap bm, float scaleWidth, float scaleHeight) {
	    int width = bm.getWidth();
	    int height = bm.getHeight();
	    
	    // CREATE A MATRIX FOR THE MANIPULATION
	    Matrix matrix = new Matrix();
	    // RESIZE THE BIT MAP
	    matrix.postScale(scaleWidth, scaleHeight);

	    // "RECREATE" THE NEW BITMAP
	    Bitmap resizedBitmap = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, false);
	    return resizedBitmap;
	}
}