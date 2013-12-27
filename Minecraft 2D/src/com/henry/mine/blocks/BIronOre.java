package com.henry.mine.blocks;

import java.util.Random;

import com.henry.mine.base.Game;
import com.henry.mine.pickups.PBIronOre;
import com.henry.mine.utils.Frames;
import com.henry.mine.utils.Tex;

public class BIronOre extends Block {
	
	public BIronOre(float x, float y, Game game) {
		super(x, y, Frames.ironOre, game);
	}

	public BIronOre(float x, float y, float width, float height, Game game) {
		super(x, y, width, height, Frames.ironOre, game);
	}
	
	public void render() {
		Tex.bindTexture(Tex.ironOre);
		super.render();
	}
	
	public void addPickUp() {
		float x = new Random().nextInt(44) + this.x;
		game.pickups.add(new PBIronOre(x, y, game));
	}

}
