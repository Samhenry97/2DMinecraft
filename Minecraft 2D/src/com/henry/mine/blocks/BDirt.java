package com.henry.mine.blocks;

import java.util.Random;

import com.henry.mine.base.Game;
import com.henry.mine.pickups.PBDirt;
import com.henry.mine.utils.Frames;
import com.henry.mine.utils.Tex;

public class BDirt extends Block {
	
	public BDirt(float x, float y, Game game) {
		super(x, y, Frames.dirt, game);
	}

	public BDirt(float x, float y, float width, float height, Game game) {
		super(x, y, width, height, Frames.dirt, game);
	}
	
	public void render() {
		Tex.bindTexture(Tex.dirt);
		super.render();
	}
	
	public void addPickUp() {
		float x = new Random().nextInt(44) + this.x;
		game.pickups.add(new PBDirt(x, y, game));
	}

}
