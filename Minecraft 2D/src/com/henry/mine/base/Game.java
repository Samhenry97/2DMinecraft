package com.henry.mine.base;

import static org.lwjgl.opengl.GL11.GL_DIFFUSE;
import static org.lwjgl.opengl.GL11.GL_LIGHT0;
import static org.lwjgl.opengl.GL11.GL_LIGHT1;
import static org.lwjgl.opengl.GL11.GL_POSITION;
import static org.lwjgl.opengl.GL11.glLight;

import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.Random;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;

import com.henry.mine.blocks.Block;
import com.henry.mine.blocks.Chunk;
import com.henry.mine.entities.ECreeper;
import com.henry.mine.entities.EGoomba;
import com.henry.mine.entities.ESpider;
import com.henry.mine.entities.EZombie;
import com.henry.mine.entities.Enemy;
import com.henry.mine.entities.Player;
import com.henry.mine.pickups.PickUp;
import com.henry.mine.utils.Constants;
import com.henry.mine.utils.Highlight;
import com.henry.mine.utils.Physics;
import com.henry.mine.utils.Utils;

public class Game {
	
	public static enum LandType {MOUNT_1, MOUNT_2, HILL, VALLEY_1, VALLEY_2, VALLEY};
	public static enum Biome {NORMAL, DESERT, SNOW, MUSHROOM, SWAMP, OCEAN};
	public static enum GameMode {CREATIVE, SURVIVAL, HARDCORE, PEACEFUL};
	
	FloatBuffer posLight0 = Utils.toFloatBuffer(new float[]{3f, 3f, 3f, 1f});
    FloatBuffer colLight0 = Utils.toFloatBuffer(new float[]{10f, 10f, 10f, 1f});
    FloatBuffer dirLight1 = Utils.toFloatBuffer(new float[]{0f, 500f, 0f, 0f});
    FloatBuffer colLight1 = Utils.toFloatBuffer(new float[]{10f, 10f, 10f, 1f});

	public static float xOffs = 0f;
	public static float yOffs = 0f;
	
	public static boolean paused = false;
	
	public ArrayList<Chunk> chunks;
	public ArrayList<Enemy> enemies;
	public ArrayList<PickUp> pickups;
	public GameMode mode = GameMode.SURVIVAL;
	public Block pressing = null;
	public Player player;
	public Inventory invent = new Inventory(this);
	private Highlight hl;
	private int lastChunk = 0;
	private LandType lastBiome;
	private LandType firstBiome;
	private int firstChunk = 0;
	private int totalChunks = 0;
	
	private int spawnTime = 100;
	private int spawn = 0;
	
	public Game() {
		initChunks();
		player = new Player(0, Chunk.NORM_BH / 2, this);
		initEnemies();
		pickups = new ArrayList<PickUp>();
		hl = new Highlight(this);
	}
	
	public void render() {
		posLight0 = Utils.toFloatBuffer(new float[]{player.x - xOffs + 32, player.y - yOffs + 32, 10f, 1f});
		glLight(GL_LIGHT0, GL_DIFFUSE, colLight0);
	    glLight(GL_LIGHT0, GL_POSITION, posLight0);
	    glLight(GL_LIGHT1, GL_DIFFUSE, colLight1);
	    glLight(GL_LIGHT1, GL_POSITION, dirLight1);
		
		xOffs = player.x - Display.getWidth() / 2;
		yOffs = player.y - Display.getHeight() / 2;
		for(Chunk chunk : chunks) {
			if(chunk.isInView()) {
				chunk.render();
			}
		}
		for(PickUp pickup : pickups) { 
			pickup.render();
		}
		player.render();
		hl.render();
		for(Enemy enemy : enemies) {
			enemy.render();
		}
		invent.render();
	}
	
	public void update() {
		if(!paused) {
			for(Chunk chunk : chunks) {
				if(chunk.isInView()) chunk.update();
			}
			PickUp remove = null;
			for(PickUp pickup : pickups) {
				if(pickup.isInView()) {
					pickup.update();
					if(pickup.pickedUp) {
						remove = pickup;
					}
				}
			}
			if(remove != null) remove(remove);
			player.update();
			hl.update();
			Enemy dead = null;
			for(Enemy enemy : enemies) {
				if(enemy.dead) dead = enemy;
				enemy.update();
			}
			spawnEnemies();
			if(dead != null) remove(dead);
			if(player.x > lastChunk * Chunk.NORM_BW - Block.SIZE * Chunk.NORM_W) {
				chunks.add(new Chunk(lastChunk * Chunk.NORM_BW, Chunk.NORM_W, Chunk.NORM_H, LandType.HILL, this));
				totalChunks++;
				lastChunk++;
			} else if(player.x < firstChunk * Chunk.NORM_BW + Block.SIZE * Chunk.NORM_W) {
				chunks.add(new Chunk(firstChunk * Chunk.NORM_BW - Chunk.NORM_BW, Chunk.NORM_W, Chunk.NORM_H, LandType.HILL, this));
				firstChunk--;
				totalChunks++;
			}
		}
		invent.update();
		
		checkIfActive();
	}
	
	public void getInput() {		
		//Mouse
		if(!invent.isOpen()) {
			if(Mouse.isButtonDown(0)) { //Mouse pressing on a block
				if(Physics.checkContains(player.fov, Mouse.getX() + xOffs, Mouse.getY() + yOffs)) {
					Block nowPressing = getBlock(Mouse.getX() + xOffs, Mouse.getY() + yOffs);
					if(nowPressing != pressing && pressing != null) {
						pressing.resetFrames();
						pressing = nowPressing;
					} else pressing = nowPressing;
				}
			} else {
				if(pressing != null) {
					pressing.resetFrames();
					pressing = null;
				} else pressing = null;
			}
			if(pressing != null) {
				if(pressing.decFrame()) {
					nullifyBlock(pressing);
				}
			}
			if(Mouse.isButtonDown(1)) {
				if(getBlock(Mouse.getX() + xOffs, Mouse.getY() + yOffs) == null) {
					addBlock(Mouse.getX() + xOffs, Mouse.getY() + yOffs);
				}
			}
		} else paused = true;
		
		invent.getMouseInput();
		
		//Keyboard
		while (Keyboard.next()) {
			invent.getKeyInput();
		    if (Keyboard.getEventKeyState()) {
		    	
		    } else {
		    	if(Keyboard.getEventKey() == Keyboard.KEY_C) {
		    		if(mode != GameMode.CREATIVE) {
		    			mode = GameMode.CREATIVE;
		    		}
		    	} else if(Keyboard.getEventKey() == Keyboard.KEY_X) {
		    		if(mode != GameMode.SURVIVAL) {
		    			mode = GameMode.SURVIVAL;
		    		}
		    	} else if(Keyboard.getEventKey() == Keyboard.KEY_P) {
		    		paused = !paused;
		    	}
		    }
		}
	}
	
	public void spawnEnemies() {
		if(spawn <= spawnTime) {
			spawn++;
		} else {
			spawn = 0;
			Random rand = new Random();
			spawnTime = rand.nextInt(200) + 100;
			int enemyType = rand.nextInt(Constants.totalEnemies);
			int x = rand.nextInt((totalChunks-1) * Chunk.NORM_BW);
			x += firstChunk * Chunk.NORM_BW;
			int y = 0;
			switch(enemyType) {
				case 0 : enemies.add(new ECreeper(x, y, player, this)); break;
				case 1 : enemies.add(new ESpider(x, y, player, this)); break;
				case 2 : enemies.add(new EZombie(x, y, player, this)); break;
				case 3 : enemies.add(new EGoomba(x, y, player, this)); break;
				default : System.out.println("Random enemies unhandled number");
			}
		}
	}
	
	public Block getBlock(float x, float y) {
		for(Chunk chunk : chunks) {
			if(Physics.checkContains(chunk, x, y)) {
				return chunk.getBlock(x, y);
			}
		}
		return null;
	}
	
	public void addWaterBlock(float x, float y) {
		for(Chunk chunk : chunks) {
			if(Physics.checkContains(chunk, x, y)) {
				while(x % Block.SIZE != 0) x--;
				while(y % Block.SIZE != 0) y--;
				chunk.addBlock(x, y - Block.SIZE * 2);
			}
		}
	}
	
	public void addBlock(float x, float y) {
		for(Chunk chunk : chunks) {
			if(Physics.checkContains(chunk, x, y)) {
				if(getBlock(x - Block.SIZE, y) != null || getBlock(x + Block.SIZE, y) != null || getBlock(x, y + Block.SIZE) != null || getBlock(x, y - Block.SIZE) != null) {
					while(x % Block.SIZE != 0) x--;
					while(y % Block.SIZE != 0) y--;
					invent.addBlock(chunk, x, y);
				}
			}
		}
	}
	
	public void nullifyBlock(Block b) {
		for(Chunk chunk : chunks) {
			if(Physics.checkContains(chunk, b.x, b.y)) {
				chunk.getAndNullifyBlock(b);
			}
		}
	}
	
	public void checkIfActive() {
		if(!Display.isActive() && !paused) {
			paused = true;
		} else if(Display.isActive() && paused){
			paused = false;
		}
	}
	
	public void remove(GameObject go) {
		if(go instanceof Enemy) {
			enemies.remove(go);
		} else if(go instanceof PickUp) {
			pickups.remove(go);
		}
	}
	
	public void initChunks() {
		chunks = new ArrayList<Chunk>();
		chunks.add(new Chunk(0, Chunk.NORM_W, Chunk.NORM_H, LandType.HILL, this));
		chunks.add(new Chunk(Chunk.NORM_BW, Chunk.NORM_W, Chunk.NORM_H, LandType.HILL, this));
		lastChunk = 2;
		chunks.add(new Chunk(-Chunk.NORM_W * Block.SIZE, Chunk.NORM_W, Chunk.NORM_H, LandType.HILL, this));
		firstChunk = -1;
		totalChunks = 3;
	}
	
	public void initEnemies() {
		enemies = new ArrayList<Enemy>();
	}

}