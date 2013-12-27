package com.henry.mine.blocks;

import com.henry.mine.base.Game;
import com.henry.mine.base.GameObject;
import com.henry.mine.utils.Draw;
import com.henry.mine.utils.Frames;
import com.henry.mine.utils.Physics;

public class Block extends GameObject {
	
	public static int SIZE = 64;
	public static int BIN_S = 6;
	
	protected Game game;
	protected Frames frames;
	public boolean collInvolved = true;
	
	public Block(float x, float y, Frames frames, Game game) {
		this(x, y, SIZE, SIZE, frames, game);
	}
	
	public Block(float x, float y, float width, float height, Frames frames, Game game) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.frames = new Frames(frames.getFrame());
		this.game = game;
	}
	
	@Override
	public void update() {
		if(game.pressing != this) {
			if(game.mode == Game.GameMode.CREATIVE) {
				if(frames.getFrame() != 0) {
					toCreative();
				} 
			} else if(game.mode == Game.GameMode.SURVIVAL) {
				if(frames.getFrame() != frames.getDefault()) {
					toSurvival();
				}
			}
		}
	}
	
	@Override
	public void render() {
		Draw.drawTexWithOffs(x, y, width, height);
	}
	
	public boolean decFrame() {
		return frames.decFrame();
	}
	
	public void resetFrames() {
		frames.resetFrame();
	}
	
	public void toSurvival() {
		frames.resetFrame();
	}
	
	public void toCreative() {
		frames.zeroFrame();
	}
	
	public boolean isInView() {
		return Physics.checkIntersects(this, game.player.window);
	}
	
	public void addPickUp() {
		System.out.println("You're calling this block's superclass method... it won't do anything.");
	}

}