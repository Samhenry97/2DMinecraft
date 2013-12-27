package com.henry.mine.pickups;

import com.henry.mine.base.Game;
import com.henry.mine.utils.Draw;
import com.henry.mine.utils.Tex;

public class PBDoor extends PickUp {

	public PBDoor(float x, float y, Game game) {
		super(x, y, game);
	}

	public PBDoor(float x, float y, float width, float height, Game game) {
		super(x, y, width, height, game);
	}
	
	@Override
	public void render() {
		Tex.bindTexture(Tex.doorBottom);
		Draw.drawTexWithOffs(x, y, width, height/2);
		Tex.bindTexture(Tex.doorTop); 
		Draw.drawTexWithOffs(x, y + height / 2, width, height);
	}

}
