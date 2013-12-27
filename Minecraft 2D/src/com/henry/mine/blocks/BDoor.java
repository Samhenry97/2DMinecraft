package com.henry.mine.blocks;

import java.util.Random;

import org.lwjgl.input.Mouse;

import com.henry.mine.base.Game;
import com.henry.mine.pickups.PBDoor;
import com.henry.mine.utils.Draw;
import com.henry.mine.utils.Frames;
import com.henry.mine.utils.Physics;
import com.henry.mine.utils.Tex;

public class BDoor extends Block {
	
	private boolean open = false;
	private boolean changed = false;

	public BDoor(float x, float y, Game game) {
		super(x, y, Frames.door, game);
	}

	public BDoor(float x, float y, float width, float height, Game game) {
		super(x, y, width, height, Frames.door, game);
	}
	
	@Override
	public void update() {
		if(Mouse.isButtonDown(1)) {
			if(Physics.checkContains(this, Mouse.getX() + Game.xOffs, Mouse.getY() + Game.yOffs) && !changed) {
				if(open) {
					open = false;
					collInvolved = true;
				} else {
					open = true;
					collInvolved = false;
				}
				changed = true;
			}
		} else if(changed) changed = false;
		super.update();
	}
	
	@Override
	public void render() {
		if(!open) {
			Tex.bindTexture(Tex.doorBottom);
			Draw.drawTexWithOffs(x, y, width, height / 2);
			Tex.bindTexture(Tex.doorTop);
			Draw.drawTexWithOffs(x, y + height / 2, width, height / 2);
		} else {
			Tex.bindTexture(Tex.doorClosed);
			Draw.drawTexWithOffs(x, y, width, height);
		}
	}
	
	public void addPickUp() {
		float x = new Random().nextInt(44) + this.x;
		game.pickups.add(new PBDoor(x, y, game));
	}

}
