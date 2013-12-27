package com.henry.mine.entities;

import com.henry.mine.base.Game;
import com.henry.mine.utils.Constants;
import com.henry.mine.utils.Draw;
import com.henry.mine.utils.Tex;

public class EGoomba extends Enemy {

	public EGoomba(float x, float y, Player player, Game game) {
		this(x, y, Constants.goombaWidth, Constants.goombaHeight, Constants.goombaHealth, player, game);
	}

	public EGoomba(float x, float y, float width, float height, int health,	Player player, Game game) {
		super(x, y, width, height, health, player, game);
		speed = Constants.goombaSpeed;
		baseSpeed = Constants.goombaSpeed;
	}
	
	@Override
	public void update() {
		super.update();
	}
	
	@Override
	public void render() {
		Tex.bindTexture(Tex.goomba);
		Draw.drawTexWithOffs(x, y, width, height);
	}
}

