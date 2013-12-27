package com.henry.mine.blocks;

import java.util.Random;

import com.henry.mine.base.Game;
import com.henry.mine.pickups.PICoal;
import com.henry.mine.utils.Frames;
import com.henry.mine.utils.Tex;

public class BCoalOre extends Block {
	
	public BCoalOre(float x, float y, Game game) {
		super(x, y, Frames.coalOre, game);
	}

	public BCoalOre(float x, float y, float width, float height, Game game) {
		super(x, y, width, height, Frames.coalOre, game);
	}
	
	public void render() {
		Tex.bindTexture(Tex.coalOre);
		super.render();
	}
	
	public void addPickUp() {
		float x = new Random().nextInt(44) + this.x;
		game.pickups.add(new PICoal(x, y, game));
	}

}
