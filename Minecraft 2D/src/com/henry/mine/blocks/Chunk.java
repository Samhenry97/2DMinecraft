package com.henry.mine.blocks;

import java.util.Random;

import com.henry.mine.base.Game;
import com.henry.mine.base.Game.LandType;
import com.henry.mine.base.GameObject;
import com.henry.mine.utils.Physics;

public class Chunk extends GameObject {
	public static int NORM_W = 16;
	public static int NORM_H = 256;
	public static int NORM_BW = NORM_W * Block.SIZE;
	public static int NORM_BH = NORM_H * Block.SIZE;
	
	public Block[] blocks;
	public int bWidth;
	public int bHeight;
	public int id;
	public LandType land;
	
	private Game game;

	public Chunk(int x, int width, int height, LandType land, Game game) {
		this.x = x;
		this.y = 0;
		bWidth = width;
		bHeight = height;
		this.width = width * Block.SIZE;
		this.height = height * Block.SIZE;
		this.game = game;
		this.id = (int) (this.x / NORM_BW);
		this.land = land;
		
		blocks = new Block[width * height];
		initBlocks();
	}
	
	public void initBlocks() {
		Random rand = new Random();
		for(int yy = (int) bHeight-1; yy >= 0; yy--) {
			for(int xx = (int) bWidth-1; xx >= 0; xx--) {
				if(yy == 0) {
					blocks[xx + yy * (int) bWidth] = new BBedrock(x + xx * Block.SIZE, yy * Block.SIZE, game);
				} else if(yy > 0 && yy <= NORM_H/2 - 13) {
					if(rand.nextInt(20) == 15) {
						blocks[xx + yy * (int) bWidth] = new BCoalOre(x + xx * Block.SIZE, yy * Block.SIZE, game);
					} else if(rand.nextInt(40) == 15) {
						blocks[xx + yy * (int) bWidth] = new BIronOre(x + xx * Block.SIZE, yy * Block.SIZE, game);
					} else if(rand.nextInt(60) == 15) {
						blocks[xx + yy * (int) bWidth] = new BGoldOre(x + xx * Block.SIZE, yy * Block.SIZE, game);
					} else if(rand.nextInt(350) == 15) {
						blocks[xx + yy * (int) bWidth] = new BDiamondOre(x + xx * Block.SIZE, yy * Block.SIZE, game);
					} else {
						blocks[xx + yy * (int) bWidth] = new BStone(x + xx * Block.SIZE, yy * Block.SIZE, game);
					}
				} else if(yy > NORM_H/2 - 13 && yy <= NORM_H/2 - 10) {
					blocks[xx + yy * (int) bWidth] = new BStone(x + xx * Block.SIZE, yy * Block.SIZE, game);
				} else if(yy > NORM_H/2 - 10 && yy < NORM_H/2-1) {
					blocks[xx + yy * (int) bWidth] = new BDirt(x + xx * Block.SIZE, yy * Block.SIZE, game);
				} else if(yy == NORM_H/2-1) {
					blocks[xx + yy * (int) bWidth] = new BGrass(x + xx * Block.SIZE, yy * Block.SIZE, game);
				} else {
					blocks[xx + yy * (int) bWidth] = null;
				}
			}
		}
		blocks[130 * (int) bWidth] = new BWater(x, 130 * Block.SIZE, game);
		addBiome(rand);
	}
	
	public void addBiome(Random rand) {
		int height = rand.nextInt(3) + 3;
		int xx = rand.nextInt(3)+2;
		addTree(xx, height);
		height = rand.nextInt(3) + 3;
		xx = rand.nextInt(3) + 10;
		addTree(xx, height);
		
		if(rand.nextInt(3) == 1) {
			int caveY = rand.nextInt(5) + 50;
			addCave(caveY, rand);
			caveY = rand.nextInt(5) + 20;
			addCave(caveY, rand);
		}
	}
	
	public void addTree(int xx, int height) {
		for(int yy = NORM_H / 2; yy < NORM_H / 2 + height + 1; yy++) {
			blocks[xx + yy * bWidth] = new BWood(this.x + xx * Block.SIZE, yy * Block.SIZE, game);
			if(yy > NORM_H / 2 + height - 2 && yy <= NORM_H / 2 + height) {
				blocks[xx - 2 + yy * bWidth] = new BLeaf(this.x + (xx - 2) * Block.SIZE, yy * Block.SIZE, game);
				blocks[xx - 1 + yy * bWidth] = new BLeaf(this.x + (xx - 1) * Block.SIZE, yy * Block.SIZE, game);
				blocks[xx + 1 + yy * bWidth] = new BLeaf(this.x + (xx + 1) * Block.SIZE, yy * Block.SIZE, game);
				blocks[xx + 2 + yy * bWidth] = new BLeaf(this.x + (xx + 2) * Block.SIZE, yy * Block.SIZE, game);
			}
		}
		int yy = NORM_H / 2 + (int) height + 1;
		blocks[xx - 1 + yy * bWidth] = new BLeaf(this.x + (xx - 1) * Block.SIZE, yy * Block.SIZE, game);
		blocks[xx + yy * bWidth] = new BLeaf(this.x + xx * Block.SIZE, yy * Block.SIZE, game);
		blocks[xx + 1 + yy * bWidth] = new BLeaf(this.x + (xx + 1) * Block.SIZE, yy * Block.SIZE, game);
		blocks[xx + (yy + 1) * bWidth] = new BLeaf(this.x + xx * Block.SIZE, (yy + 1) * Block.SIZE, game);
	}
	
	public void addCave(int yy, Random rand) {
		for(int xx = 0; xx < NORM_W; xx++) {
			for(int y = 0; y < 4; y++) {
				if(y == 0 || y == 3) {
					if(xx < 5 || xx > NORM_W - 5) {
						 int nullify = rand.nextInt(4);
						 if(nullify != 1) blocks[xx + (yy + y) * bWidth] = null;
					}
				} else {
					blocks[xx + (yy + y) * bWidth] = null;
				}
			}
		}
	}
	
	public void update() {
		for(Block block : blocks) {
			if(block != null) {
				if(block.isInView()) block.update();
			}
		}
	}
	
	public void render() {
		for(Block block : blocks) {
			if(block != null) {
				if(block.isInView()) block.render();
			}
		}
	}
	
	public Block getBlock(float x, float y) {
		while(x % Block.SIZE != 0) x--;
		while(y % Block.SIZE != 0) y--;
		
		x = (int) x >> Block.BIN_S;
		y = (int) y >> Block.BIN_S;
		
		int ret = (int) (x + (y - id) * bWidth);
		if(ret > 0 && ret < bWidth * bHeight) return blocks[ret];
		else return null;
	}
	
	public void addBlock(float x, float y) {
		float xPos = x - Chunk.NORM_BW * id;
		float yPos = y;
		x = (int) x >> Block.BIN_S;
		y = (int) y >> Block.BIN_S;
		
		int ret = (int) (x + (y - id) * bWidth);
		if(ret > 0 && ret < bWidth * bHeight) {
			blocks[ret] = new BWater(this.x + xPos, this.y + yPos, game);
		}
	}
	
	public int getRet(float x, float y) {
		x = (int) x >> Block.BIN_S;
		y = (int) y >> Block.BIN_S;
		
		int ret = (int) (x + (y - id) * bWidth);
		if(ret > 0 && ret < bWidth * bHeight) {
			return ret;
		} else return -1;
	}
	
	public void getAndNullifyBlock(Block b) {
		int xPos = (int) b.x >> Block.BIN_S;
		int yPos = (int) b.y >> Block.BIN_S;
		
		int ret = (int) (xPos + (yPos - id) * bWidth);
		
		b.addPickUp();
		
		if(!(ret > 0 && ret < bWidth * bHeight)) return;
		blocks[ret] = null;
	}
	
	public boolean isInView() {
		return Physics.checkIntersects(this, game.player.window);
	}
	
	public Block[] getBlocks() {
		return blocks;
	}

}