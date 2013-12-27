package com.henry.mine.blocks;

import java.util.Random;

import com.henry.mine.base.Game;
import com.henry.mine.pickups.PIDiamond;
import com.henry.mine.utils.Frames;
import com.henry.mine.utils.Tex;

public class BDiamondOre extends Block {
	
	public BDiamondOre(float x, float y, Game game) {
		super(x, y, Frames.diamondOre, game);
	}

	public BDiamondOre(float x, float y, float width, float height, Game game) {
		super(x, y, width, height, Frames.diamondOre, game);
	}
	
	public void render() {
		Tex.bindTexture(Tex.diamondOre);
		super.render();
	}
	
	public void addPickUp() {
		float x = new Random().nextInt(44) + this.x;
		game.pickups.add(new PIDiamond(x, y, game));
	}

}
