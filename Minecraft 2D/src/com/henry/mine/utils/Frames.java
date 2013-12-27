package com.henry.mine.utils;

public class Frames {
	
	public static final Frames def = new Frames(10);
	public static Frames grass = def;
	public static Frames dirt = def;
	public static Frames stone = new Frames(40);
	public static Frames bedrock = new Frames(-1);
	public static Frames sand = new Frames(5);
	public static Frames wood = new Frames(30);
	public static Frames coalOre = new Frames(50);
	public static Frames ironOre = new Frames(75);
	public static Frames goldOre = new Frames(100);
	public static Frames diamondOre = new Frames(200);
	public static Frames door = new Frames(40);
	public static Frames leaf = new Frames(5);
	
	public static Frames[] allFrames = {
			grass, dirt, stone, 
			bedrock, sand, wood, 
			coalOre, ironOre, goldOre, 
			diamondOre, door
	};
	
	private int frames;
	private int defFrame;

	public Frames(int frames) {
		this.frames = frames;
		defFrame = frames;
	}
	
	public int getDefault() {
		return defFrame;
	}
	
	public boolean decFrame() {
		frames--;
		return frames <= 0;
	}
	
	public void incFrame() {
		frames++;
	}
	
	public void setFrame(int frame) {
		frames = frame;
	}
	
	public int getFrame() {
		return frames;
	}
	
	public void zeroFrame() {
		frames = 0;
	}
	
	public void resetFrame() {
		frames = defFrame;
	}

}