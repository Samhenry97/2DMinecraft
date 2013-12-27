package com.henry.mine.base;

public abstract class GameObject {
	
	public float x;
	public float y;
	public float width;
	public float height;
	
	public abstract void update();
	public abstract void render();

}
