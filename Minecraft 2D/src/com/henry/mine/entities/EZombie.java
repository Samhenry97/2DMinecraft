package com.henry.mine.entities;

import com.henry.mine.base.Game;
import com.henry.mine.utils.Constants;
import com.henry.mine.utils.Draw;
import com.henry.mine.utils.Tex;

public class EZombie extends Enemy {

	public EZombie(float x, float y, Player player, Game game) {
		this(x, y, Constants.zombieWidth, Constants.zombieHeight, Constants.zombieHealth, player, game);
	}

	public EZombie(float x, float y, float width, float height, int health,	Player player, Game game) {
		super(x, y, width, height, health, player, game);
		speed = Constants.zombieSpeed;
		baseSpeed = Constants.zombieSpeed;
	}
	
	@Override
	public void update() {
		super.update();
	}
	
	@Override
	public void render() {
		Tex.bindTexture(Tex.zombie);
		Draw.drawTexWithOffs(x, y, width, height);
	}
}

