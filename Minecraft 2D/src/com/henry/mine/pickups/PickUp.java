package com.henry.mine.pickups;

import com.henry.mine.base.Game;
import com.henry.mine.base.GameObject;
import com.henry.mine.utils.Constants;
import com.henry.mine.utils.Draw;
import com.henry.mine.utils.Physics;

public class PickUp extends GameObject {
	
	protected Game game;
	private boolean fall = false;
	private float vSpeed = 4f;
	public boolean pickedUp = false;
	
	public PickUp(float x, float y, Game game) {
		this(x, y, Constants.pickUpSize, Constants.pickUpSize, game);
	}
	
	public PickUp(float x, float y, float width, float height, Game game) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.game = game;
	}

	@Override
	public void update() {
		fall = game.getBlock(x + 5, y) == null && game.getBlock(x + width - 5, y) == null;
		if(fall) {
			y -= vSpeed;
		}
		
		if(Physics.checkIntersects(this, game.player)) {
			if(game.invent.add(this)) {
				pickedUp = true;
			}
		}
	}

	@Override
	public void render() {
		Draw.drawTexWithOffs(x, y, width, height);
	}
	
	public boolean isInView() {
		return Physics.checkIntersects(this, game.player.window);
	}

}
