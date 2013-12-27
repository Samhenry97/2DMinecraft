package com.henry.mine.blocks;

import com.henry.mine.base.Game;
import com.henry.mine.utils.Frames;
import com.henry.mine.utils.Tex;

public class BWater extends Block {
	
	private Game game;
	
	public BWater(float x, float y, Game game) {
		super(x, y, Frames.bedrock, game);
		this.game = game;
	}

	public BWater(float x, float y, float width, float height, Game game) {
		super(x, y, width, height, Frames.bedrock, game);
		this.game = game;
	}
	
	public void render() {
		Tex.bindTexture(Tex.water);
		super.render();
	}
	
	public void update() {
		super.update();
		if(game.getBlock(x, y + Block.SIZE) == null) {
			game.addWaterBlock(x, y + Block.SIZE);
		}
	}
	
	public void addPickUp() {
		
	}

}
