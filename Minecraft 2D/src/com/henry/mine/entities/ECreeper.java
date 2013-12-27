package com.henry.mine.entities;

import com.henry.mine.base.Game;
import com.henry.mine.utils.Constants;
import com.henry.mine.utils.Draw;
import com.henry.mine.utils.Tex;

public class ECreeper extends Enemy {

	public ECreeper(float x, float y, Player player, Game game) {
		this(x, y, Constants.creepWidth, Constants.creepHeight, Constants.creepHealth, player, game);
	}

	public ECreeper(float x, float y, float width, float height, int  health, Player player, Game game) {
		super(x, y, width, height, health, player, game);
		speed = Constants.creepSpeed;
		baseSpeed = Constants.creepSpeed;
	}
	
	@Override
	public void render() {
		Tex.bindTexture(Tex.creepBottom);
		Draw.drawTexWithOffs(x, y, width, height / 2);
		Tex.bindTexture(Tex.creepTop);
		Draw.drawTexWithOffs(x, y + (height / 2), width, height / 2);
	}
	
	@Override
	public void update() {
		super.update();
	}

}
