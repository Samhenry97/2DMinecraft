package com.henry.mine.pickups;

import com.henry.mine.base.Game;
import com.henry.mine.utils.Tex;

public class PBGrass extends PickUp {

	public PBGrass(float x, float y, Game game) {
		super(x, y, game);
	}

	public PBGrass(float x, float y, float width, float height, Game game) {
		super(x, y, width, height, game);
	}
	
	@Override
	public void render() {
		Tex.bindTexture(Tex.grass);
		super.render();
	}

}
