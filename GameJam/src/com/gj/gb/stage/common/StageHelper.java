package com.gj.gb.stage.common;

import android.util.Log;

public class StageHelper {
	public static boolean isWithinBorders(float objBorderLeft, float objBorderRight, float objBorderTop, 
			float objBorderBottom, float touchPosX, float touchpOSY){
	    Log.d("tina", String.valueOf(objBorderLeft <= touchPosX) + " " + objBorderLeft+" ;"+ touchPosX);
	    Log.e("tina", String.valueOf(objBorderRight >= touchPosX) + " " + objBorderRight+" ;"+ touchPosX);
	    Log.i("tina", String.valueOf(objBorderTop <= touchpOSY) + " " + objBorderTop+" ;"+ touchpOSY);
	    Log.w("tina", String.valueOf(objBorderBottom <= touchpOSY) + " " + objBorderBottom+" ;"+ touchpOSY);
		return objBorderLeft <= touchPosX && objBorderRight >= touchPosX
			&& objBorderTop <= touchpOSY && objBorderBottom >= touchpOSY;
	}
}
