package com.henry.mine.base;

import com.henry.mine.pickups.PBBedrock;
import com.henry.mine.pickups.PBDirt;
import com.henry.mine.pickups.PBDoor;
import com.henry.mine.pickups.PBGoldOre;
import com.henry.mine.pickups.PBGrass;
import com.henry.mine.pickups.PBIronOre;
import com.henry.mine.pickups.PBSand;
import com.henry.mine.pickups.PBStone;
import com.henry.mine.pickups.PBWood;
import com.henry.mine.pickups.PICoal;
import com.henry.mine.pickups.PIDiamond;
import com.henry.mine.utils.Draw;
import com.henry.mine.utils.GameFont;
import com.henry.mine.utils.Tex;

public class InventBox extends GameObject {
	public static int picSize = Inventory.boxSize - 8;
	public static int maxContain = 64;
	
	public Object[] holds;
	public float hpX;
	public float hpY;
	public int total = 0;
	public boolean anchor = true;
	
	private Game game;
	
	public InventBox(float x, float y, Game game) {
		this.x = x;
		this.y = y;
		hpX = x + 4;
		hpY = y + 4;
		this.width = Inventory.boxSize;
		this.height = Inventory.boxSize;
		this.game = game;
	}

	@Override
	public void update() {
		
	}

	@Override
	public void render() {
		total = 0;
		if(holds != null) {
			for(Object ob : holds) {
				if(ob != null) total++;
			}
		}
		if(game != null);
		Tex.bindTexture(Tex.inventory);
		Draw.drawTex(x, y, width, height);
	}
	
	public void renderContains() {
		if(holds != null) {
			if(holds[0] != null) {
				if(holds[0] instanceof PBGrass) Tex.bindTexture(Tex.grass);
				else if(holds[0] instanceof PBDirt)	Tex.bindTexture(Tex.dirt);
				else if(holds[0] instanceof PICoal) Tex.bindTexture(Tex.coal);
				else if(holds[0] instanceof PBBedrock) Tex.bindTexture(Tex.bedrock);
				else if(holds[0] instanceof PIDiamond) Tex.bindTexture(Tex.diamond);
				else if(holds[0] instanceof PBDoor)	Tex.bindTexture(Tex.doorTop);
				else if(holds[0] instanceof PBGoldOre) Tex.bindTexture(Tex.goldOre);
				else if(holds[0] instanceof PBStone) Tex.bindTexture(Tex.stone);
				else if(holds[0] instanceof PBIronOre) Tex.bindTexture(Tex.ironOre);
				else if(holds[0] instanceof PBWood) Tex.bindTexture(Tex.wood);
				else if(holds[0] instanceof PBSand) Tex.bindTexture(Tex.sand);
				Draw.drawTex(hpX, hpY, picSize, picSize);
			}
		}
		GameFont.render(Integer.toString(total), hpX, hpY);
	}

}