package com.henry.mine.blocks;

import java.util.Random;

import com.henry.mine.base.Game;
import com.henry.mine.pickups.PBDirt;
import com.henry.mine.utils.Frames;
import com.henry.mine.utils.Tex;

public class BGrass extends Block {
	
	public BGrass(float x, float y, Game game) {
		super(x, y, Frames.grass, game);
	}

	public BGrass(float x, float y, float width, float height, Game game) {
		super(x, y, width, height, Frames.grass, game);
	}
	
	public void render() {
		Tex.bindTexture(Tex.grass);
		super.render();
	}
	
	public void addPickUp() {
		float x = new Random().nextInt(44) + this.x;
		game.pickups.add(new PBDirt(x, y, game));
	}
}
