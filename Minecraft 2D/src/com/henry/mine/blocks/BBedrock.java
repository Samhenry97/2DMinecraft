package com.henry.mine.blocks;

import java.util.Random;

import com.henry.mine.base.Game;
import com.henry.mine.pickups.PBBedrock;
import com.henry.mine.utils.Frames;
import com.henry.mine.utils.Tex;

public class BBedrock extends Block {
	
	public BBedrock(float x, float y, Game game) {
		super(x, y, Frames.bedrock, game);
	}

	public BBedrock(float x, float y, float width, float height, Game game) {
		super(x, y, width, height, Frames.bedrock, game);
	}
	
	public void render() {
		Tex.bindTexture(Tex.bedrock);
		super.render();
	}
	
	public void addPickUp() {
		float x = new Random().nextInt(44) + this.x;
		game.pickups.add(new PBBedrock(x, y, game));
	}

}
