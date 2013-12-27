package com.henry.mine.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

public class Tex {
	
	//Player texture
	public static Texture player = loadTexture("player.png");
	
	//Block textures
	public static Texture stone = loadTexture("stone.jpg");
	public static Texture grass = loadTexture("grass.jpg");
	public static Texture highlight = loadTexture("highlight.png");
	public static Texture bedrock = loadTexture("bedrock.jpg");
	public static Texture dirt = loadTexture("dirt.jpg");
	public static Texture wood = loadTexture("wood.jpg");
	public static Texture sand = loadTexture("sand.jpg");
	public static Texture coalOre = loadTexture("coal_ore.jpg");
	public static Texture diamondOre = loadTexture("diamond_ore.jpg");
	public static Texture goldOre = loadTexture("gold_ore.jpg");
	public static Texture ironOre = loadTexture("iron_ore.jpg");
	public static Texture doorTop = loadTexture("door_top.png");
	public static Texture doorBottom = loadTexture("door_bottom.png");
	public static Texture doorClosed = loadTexture("door_closed.png");
	public static Texture leaf = loadTexture("leaf.png");
	public static Texture water = loadTexture("water.png");
	
	//Item textures
	public static Texture coal = loadTexture("coal.png");
	public static Texture diamond = loadTexture("diamond.png");
	
	//Enemy textures
	public static Texture creepTop = loadTexture("creeper_top.png");
	public static Texture creepBottom = loadTexture("creeper_bottom.png");
	public static Texture spider = loadTexture("spider.png");
	public static Texture zombie = loadTexture("zombie.png");
	public static Texture goomba = loadTexture("goomba.png");
	
	//Number textures
	public static Texture num0 = loadTexture("0.png");
	public static Texture num1 = loadTexture("1.png");
	public static Texture num2 = loadTexture("2.png");
	public static Texture num3 = loadTexture("3.png");
	public static Texture num4 = loadTexture("4.png");
	public static Texture num5 = loadTexture("5.png");
	public static Texture num6 = loadTexture("6.png");
	public static Texture num7 = loadTexture("7.png");
	public static Texture num8 = loadTexture("8.png");
	public static Texture num9 = loadTexture("9.png");
	
	//Other textures
	public static Texture inventory = loadTexture("inventory.png");

	public static void bindTexture(Texture tex) {
		tex.bind();
	}
	
	public static Texture loadTexture(String key) {
		String imgFormat = "";
		if(key.contains("png")) imgFormat = "png";
		else if(key.contains("jpg")) imgFormat = "jpg";
		
		try {
			return TextureLoader.getTexture(imgFormat, new FileInputStream(new File("res/images/" + key)));
		} catch(IOException e) {
			e.printStackTrace();
			System.out.println("Error loading texture: " + key);
			System.exit(-1);
		}
		
		return null;
	}

}
