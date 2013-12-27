package com.henry.mine.blocks;

import java.util.Random;

import com.henry.mine.base.Game;
import com.henry.mine.pickups.PBWood;
import com.henry.mine.utils.Frames;
import com.henry.mine.utils.Tex;

public class BWood extends Block {
	
	public BWood(float x, float y, Game game) {
		super(x, y, Frames.wood, game);
	}

	public BWood(float x, float y, float width, float height, Game game) {
		super(x, y, width, height, Frames.wood, game);
	}
	
	public void render() {
		Tex.bindTexture(Tex.wood);
		super.render();
	}
	
	public void addPickUp() {
		float x = new Random().nextInt(44) + this.x;
		game.pickups.add(new PBWood(x, y, game));
	}

}
