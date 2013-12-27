package com.henry.mine.blocks;

import com.henry.mine.base.Game;
import com.henry.mine.utils.Frames;
import com.henry.mine.utils.Tex;

public class BLeaf extends Block {
	
	public BLeaf(float x, float y, Game game) {
		super(x, y, Frames.leaf, game);
	}

	public BLeaf(float x, float y, float width, float height, Game game) {
		super(x, y, width, height, Frames.leaf, game);
	}
	
	@Override
	public void render() {
		Tex.bindTexture(Tex.leaf);
		super.render();
	}
	
	public void addPickUp() {
		
	}

}
