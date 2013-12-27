package com.henry.mine.entities;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;

import com.henry.mine.base.Game;
import com.henry.mine.blocks.Block;
import com.henry.mine.utils.Constants;
import com.henry.mine.utils.Rect;
import com.henry.mine.utils.Tex;

public class Player extends Entity {
	
	private boolean fall = true;
	private boolean jump = false;
	private boolean jumpEnabled = false;
	private int jDis = 0;
	private int jHeight = Constants.playerJHeight;
	private Game game;
	public Rect fov = new Rect(0, 0, 0, 0);
	public Rect window = new Rect(0, 0, 0, 0);
	
	public int health = 100;
	
	public Player(float x, float y, Game game) {
		this(x, y, Entity.SIZE, Entity.SIZE, game);
	}

	public Player(float x, float y, float width, float height, Game game) {
		super(x, y, width, height);
		this.game = game;
	}
	
	@Override
	public void render() {
		Tex.bindTexture(Tex.player);
		super.render();
	}
	
	@Override
	public void update() {
		fov.x = x - Block.SIZE * 5;
		fov.y = y - Block.SIZE * 5;
		fov.width = Block.SIZE * 10;
		fov.height = Block.SIZE * 10;
		window.x = x - Display.getWidth() / 2 - Block.SIZE * 2;
		window.width = Display.getWidth() + Block.SIZE * 4;
		window.y = y - Display.getHeight() / 2 - Block.SIZE * 2;
		window.height = Display.getHeight() + Block.SIZE * 4;
		
		boolean left = Keyboard.isKeyDown(Keyboard.KEY_LEFT) || Keyboard.isKeyDown(Keyboard.KEY_A);
		boolean right = Keyboard.isKeyDown(Keyboard.KEY_RIGHT) || Keyboard.isKeyDown(Keyboard.KEY_D);
		
		if(!(left && right)) {
			if(left) {
				if(game.getBlock(x, y) == null && game.getBlock(x, y+height-5) == null) {
					move(-1);
				}
			} else if(right) {
				if(game.getBlock(x+width, y) == null && game.getBlock(x+width, y+height-5) == null) {
					move(1);
				}
			}
		}
		
		if(!jump) {
			fall = game.getBlock(x+5, y-5) == null && game.getBlock(x+width-5, y-5) == null;
		}
		
		if((Keyboard.isKeyDown(Keyboard.KEY_UP) || Keyboard.isKeyDown(Keyboard.KEY_W) || Keyboard.isKeyDown(Keyboard.KEY_SPACE)) && game.mode == Game.GameMode.CREATIVE) {
			jump = true;
			fall = false;
		}
		
		if(!fall) {
			jump = Keyboard.isKeyDown(Keyboard.KEY_UP) || Keyboard.isKeyDown(Keyboard.KEY_W) || Keyboard.isKeyDown(Keyboard.KEY_SPACE);
			if(game.mode == Game.GameMode.SURVIVAL) {
				if(jump) jump = game.getBlock(x+5, y+height) == null && game.getBlock(x+width-5, y+height) == null;
			} else {
				jumpEnabled = game.getBlock(x+5, y+height) == null && game.getBlock(x+width-5, y+height) == null;
			}
		}
		
		if(fall) {
			y -= Constants.playerVSpeed;
		} else if(jump) {
			if(game.mode == Game.GameMode.SURVIVAL) {
				if(jDis <= jHeight) {
					y += Constants.playerVSpeed;
					jDis++;
				} else {
					jump = false;
					jDis = 0;
				}
			} else {
				if(jumpEnabled) {
					y += Constants.playerVSpeed;
				}
			}
		}
		if(!jump && !fall) {
			jDis = 0;
		}
	}
	
	public void move(int dir) {
		x += Constants.playerSpeed * dir;
	}

}
