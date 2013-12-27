package com.henry.mine.entities;

import com.henry.mine.base.Game;
import com.henry.mine.utils.Constants;
import com.henry.mine.utils.Draw;
import com.henry.mine.utils.Tex;

public class ESpider extends Enemy {

	public ESpider(float x, float y, Player player, Game game) {
		this(x, y, Constants.spiderWidth, Constants.spiderHeight, Constants.spiderHealth, player, game);
	}

	public ESpider(float x, float y, float width, float height, int health,	Player player, Game game) {
		super(x, y, width, height, health, player, game);
		speed = Constants.spiderSpeed;
		baseSpeed = Constants.spiderSpeed;
	}
	
	@Override
	public void update() {
		super.update();
	}
	
	@Override
	public void render() {
		Tex.bindTexture(Tex.spider);
		Draw.drawTexWithOffs(x, y, width, height);
	}
}

