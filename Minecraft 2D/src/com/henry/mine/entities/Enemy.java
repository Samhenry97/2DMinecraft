package com.henry.mine.entities;

import java.util.Random;

import org.lwjgl.input.Mouse;

import com.henry.mine.base.Game;
import com.henry.mine.base.GameObject;
import com.henry.mine.utils.Constants;
import com.henry.mine.utils.Draw;
import com.henry.mine.utils.Physics;

public abstract class Enemy extends GameObject {
	
	public static final float SIZE = 64;
	
	protected Game game;
	private Player player;
	private boolean fall = false;
	private boolean jump = false;
	private boolean jumped = false;
	private boolean changed = false;
	private boolean follow = false;
	private Random rand = new Random();
	private int moveNum = rand.nextInt(50);
	private int moveDir = 0;
	private int moveDis = 0;
	private int waitTime = rand.nextInt(30);
	private int waitDis = 0;
	private int jDis = 0;
	private int hurtDir = 0;
	private int hurtTime = 0;
	private int jHeight = 20;
	private float vSpeed = 4f;
	protected float speed;
	protected float baseSpeed;
	public int health;
	public boolean dead = false;
	
	public Enemy(float x, float y, int health, Player player, Game game) {
		this(x, y, SIZE, SIZE, health, player, game);
	}
	
	public Enemy(float x, float y, float width, float height, int health, Player player, Game game) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.game = game;
		this.player = player;
		this.health = health;
		while(game.getBlock(x, y) != null) {
			y += SIZE;
		}
	}
	
	@Override
	public void update() {
		fall = game.getBlock(x+5, y) == null && game.getBlock(x+width-5, y) == null;
		if(Physics.checkIntersects(this, player.fov)) {
			if(!follow) {
				follow = true;
				speed += 1f;
				if(!fall) jump = true;
			}
		} else {
			if(follow) follow = false;
			if(speed != baseSpeed) speed = baseSpeed;
		}
		
		if(hurtDir == 0) {
			if(follow) {
				if(player.x < x) {
					if(game.getBlock(x, y+5) == null && game.getBlock(x, y+height-5) == null) {
						if(game.getBlock(x, y - height / 2) == null && !fall && !jump) {
							jump = true;
						}
						move(-1);
					} else if(!fall) jump = true;
				} else if(player.x > x) {
					if(game.getBlock(x+width, y+5) == null && game.getBlock(x+width, y+height-5) == null) {
						if(game.getBlock(x + width, y - height / 2) == null && !fall && !jump) jump = true;
						move(1);
					} else if(!fall) jump = true;
				} else {
					move(0);
				}
			} else {
				if(waitDis <= waitTime) waitDis++;
				else {
					if(moveDis <= moveNum) {
						moveDis++;
						if(moveDir > 0) {
							if(game.getBlock(x+width, y+5) == null && game.getBlock(x+width, y+height-5) == null) move(moveDir);
							else if(!jump && !fall) jump = true;
						} else if(moveDir < 0) {
							if(game.getBlock(x, y+5) == null && game.getBlock(x, y+height-5) == null) move(moveDir);
							else if(!jump && !fall) jump = true;
						}
					} else {
						waitTime = rand.nextInt(100) + 30;
						moveNum = rand.nextInt(40) + 50;
						moveDis = 0;
						waitDis = 0;
						moveDir = rand.nextInt(20) > 10 ? -1 : 1;
					}
				}
			}
		} else {
			if(hurtTime < Constants.hurtReset) {
				if(jHeight != 10) jHeight = 10;
				hurtTime++;
				if(hurtDir < 0) {
					if(game.getBlock(x, y+5) == null && game.getBlock(x, y+height-5) == null) move(hurtDir);
				} else if(hurtDir > 0) {
					if(game.getBlock(x+width, y+5) == null && game.getBlock(x+width, y+height-5) == null) move(hurtDir);
				} else System.out.println("That shouldn't be possible..... :P");
				if(!jump && !fall && !jumped) {
					jump = true;
					jumped = true;
				}
			} else {
				hurtDir = 0;
				hurtTime = 0;
				jumped = false;
				jHeight = Constants.enemyDefaultJHeight;
			}
		}
		
		if(fall && !jump) y -= vSpeed;
		
		if(jump) {
			jump = game.getBlock(x+5, y+height) == null && game.getBlock(x+width-5, y+height) == null;
			if(!jump) jDis = 0;
			if(jDis < jHeight) {
				jDis++;
				y += vSpeed;
			} else {
				jDis = 0;
				jump = false;
			}
		}
		
		if(Physics.checkIntersects(this, player)) {
			player.health--;
		}
		
		if(Mouse.isButtonDown(0)) {
			if(Physics.checkContains(this, Mouse.getX() + Game.xOffs, Mouse.getY() + Game.yOffs) && !changed) {
				if(Physics.checkContains(player.fov, Mouse.getX() + Game.xOffs, Mouse.getY() + Game.yOffs)) {
					changed = true;
					health--;
					if(player.x < x) hurtDir = 1;
					else if(player.x > x) hurtDir = -1;
					else hurtDir = -1;
					if(health <= 0) dead = true;
				}
			}
		} else if(changed) changed = false;
		
		if(game.getBlock(x + width / 2, y + height / 2) != null) y += 64;
	}
	
	@Override
	public void render() {
		Draw.drawTexWithOffs(x, y, width, height);
	}
	
	public void move(int dir) {
		x += speed * dir;
	}
	
}