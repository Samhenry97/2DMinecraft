package com.henry.mine.entities;

import com.henry.mine.base.GameObject;
import com.henry.mine.utils.Draw;

public abstract class Entity extends GameObject {
	
	public static float SIZE = 64f;
	
	public Entity(float x, float y) {
		this(x, y, SIZE, SIZE);
	}

	public Entity(float x, float y, float width, float height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	@Override
	public void render() {
		Draw.drawTexWithOffs(x, y, width, height);
	}
	
	@Override
	public void update() {
		
	}
}
