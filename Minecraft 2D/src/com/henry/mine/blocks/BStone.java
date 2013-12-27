package com.henry.mine.blocks;

import java.util.Random;

import com.henry.mine.base.Game;
import com.henry.mine.pickups.PBStone;
import com.henry.mine.utils.Frames;
import com.henry.mine.utils.Tex;

public class BStone extends Block {
	
	public BStone(float x, float y, Game game) {
		super(x, y, Frames.stone, game);
	}

	public BStone(float x, float y, float width, float height, Game game) {
		super(x, y, width, height, Frames.stone, game);
	}
	
	public void render() {
		Tex.bindTexture(Tex.stone);
		super.render();
	}
	
	public void addPickUp() {
		float x = new Random().nextInt(44) + this.x;
		game.pickups.add(new PBStone(x, y, game));
	}

}
