package com.henry.mine.blocks;

import java.util.Random;

import com.henry.mine.base.Game;
import com.henry.mine.pickups.PBGoldOre;
import com.henry.mine.utils.Frames;
import com.henry.mine.utils.Tex;

public class BGoldOre extends Block {
	
	public BGoldOre(float x, float y, Game game) {
		super(x, y, Frames.goldOre, game);
	}

	public BGoldOre(float x, float y, float width, float height, Game game) {
		super(x, y, width, height, Frames.goldOre, game);
	}
	
	public void render() {
		Tex.bindTexture(Tex.goldOre);
		super.render();
	}
	
	public void addPickUp() {
		float x = new Random().nextInt(44) + this.x;
		game.pickups.add(new PBGoldOre(x, y, game));
	}

}
