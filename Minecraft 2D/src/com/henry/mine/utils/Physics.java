package com.henry.mine.utils;

import org.lwjgl.util.Point;
import org.lwjgl.util.Rectangle;

import com.henry.mine.base.GameObject;

public class Physics {

	public static boolean checkContains(GameObject go1, float x, float y) {
		return new Rectangle((int) go1.x, (int) go1.y, (int) go1.width, (int) go1.height).contains(new Point((int) x, (int) y));
	}
	
	public static boolean checkIntersects(GameObject go1, GameObject go2) {
		Rectangle rect1 = new Rectangle((int) go1.x, (int) go1.y, (int) go1.width, (int) go1.height);
		Rectangle rect2 = new Rectangle((int) go2.x, (int) go2.y, (int) go2.width, (int) go2.height);
		
		return rect1.intersects(rect2);
	}
	
	public static boolean checkIntersects(GameObject go1, Rectangle rect) {
		Rectangle rect1 = new Rectangle((int) go1.x, (int) go1.y, (int) go1.width, (int) go1.height);
		
		return rect1.intersects(rect);
	}

}
