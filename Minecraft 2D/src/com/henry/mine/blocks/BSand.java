package com.henry.mine.blocks;

import java.util.Random;

import com.henry.mine.base.Game;
import com.henry.mine.pickups.PBSand;
import com.henry.mine.utils.Frames;
import com.henry.mine.utils.Tex;

public class BSand extends Block {
	
	public BSand(float x, float y, Game game) {
		super(x, y, Frames.sand, game);
	}

	public BSand(float x, float y, float width, float height, Game game) {
		super(x, y, width, height, Frames.sand, game);
	}
	
	public void render() {
		Tex.bindTexture(Tex.sand);
		super.render();
	}
	
	public void update() {
		super.update();
	}
	
	public void addPickUp() {
		float x = new Random().nextInt(44) + this.x;
		game.pickups.add(new PBSand(x, y, game));
	}

}
