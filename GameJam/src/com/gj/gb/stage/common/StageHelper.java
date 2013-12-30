package com.gj.gb.stage.common;

public class StageHelper {
	public static boolean isWithinBorders(float objBorderLeft, float objBorderRight, float objBorderTop, 
			float objBorderBottom, float touchPosX, float touchpOSY){
		return objBorderLeft <= touchPosX && objBorderRight >= touchPosX
			&& objBorderTop <= touchpOSY && objBorderBottom >= touchpOSY;
	}
}
