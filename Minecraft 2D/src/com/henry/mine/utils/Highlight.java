package com.henry.mine.utils;

import org.lwjgl.input.Mouse;

import com.henry.mine.base.Game;
import com.henry.mine.base.GameObject;
import com.henry.mine.blocks.Block;

public class Highlight extends GameObject {
	
	private boolean doRender = false;
	private Game game;
	
	public Highlight(Game game) {
		this(0, 0, Block.SIZE, Block.SIZE, game);
	}
	
	public Highlight(float x, float y, Game game) {
		this(x, y, Block.SIZE, Block.SIZE, game);
	}

	public Highlight(float x, float y, float width, float height, Game game) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.game = game;
	}

	@Override
	public void update() {
		if(!game.invent.isOpen()) {
			x = Mouse.getX();
			y = Mouse.getY();
			
			Block on = game.getBlock(x + Game.xOffs, y + Game.yOffs);
			if(on != null) {
				doRender = true;
				x = on.x;
				y = on.y;
			} else {
				doRender = false;
			}
			
			if(doRender) {
				if(!Physics.checkIntersects(this, game.player.fov)) {
					doRender = false;
				}
			}
		}
	}

	@Override
	public void render() {
		if(doRender) {
			Tex.bindTexture(Tex.highlight);
			Draw.drawTexWithOffs(x, y, width, height);
		}
	}

}
