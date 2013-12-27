package com.henry.mine.base;

import static org.lwjgl.opengl.GL11.GL_LIGHTING;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glEnable;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;

import com.henry.mine.blocks.BCoalOre;
import com.henry.mine.blocks.BDiamondOre;
import com.henry.mine.blocks.BDirt;
import com.henry.mine.blocks.BDoor;
import com.henry.mine.blocks.BGoldOre;
import com.henry.mine.blocks.BGrass;
import com.henry.mine.blocks.BIronOre;
import com.henry.mine.blocks.BSand;
import com.henry.mine.blocks.BStone;
import com.henry.mine.blocks.BWood;
import com.henry.mine.blocks.Chunk;
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
import com.henry.mine.pickups.PickUp;
import com.henry.mine.utils.Draw;
import com.henry.mine.utils.Physics;
import com.henry.mine.utils.Tex;

public class Inventory {
	public static int boxSize = 70;
	public static int inventY = 50;
	public static int inventOpenY = 150;
	public static int inventWidth = boxSize * 9;
	public static int startX = Display.getWidth() / 2 - inventWidth / 2;
	
	private boolean open = false;
	private Game game;
	private int selected = 0;
	private InventBox[] boxes = new InventBox[36];
	private InventBox mouseOn = null;
	private boolean changed = false;
	
	public Inventory(Game game) {
		initBoxes();
		this.game = game;
	}
	
	public void render() {
		glDisable(GL_LIGHTING);
		for(int i = 0; i < 9; i++) {
			boxes[i].render();
			if(i == selected) {
				Tex.bindTexture(Tex.highlight);
				Draw.drawTex(i * boxSize + startX, inventY, boxSize, boxSize);
			}
		}
		
		if(open) {
			for(int i = 9; i < boxes.length; i++) boxes[i].render();
			for(int i = 9; i < boxes.length; i++) boxes[i].renderContains();
		}
		for(int i = 0; i < 9; i++) boxes[i].renderContains();
		glEnable(GL_LIGHTING);
	}
	
	public void update() {
		for(InventBox ib : boxes) ib.update();
	}
	
	public void getMouseInput() {
		while(Mouse.next()) {
			if(Mouse.getEventButtonState()) {
				
			} else {
				if(Mouse.getEventButton() == 0) {
					if(open && mouseOn == null) {
						for(InventBox ib : boxes) {
							if(Physics.checkContains(ib, Mouse.getX(), Mouse.getY())) {
								if(ib.holds != null) {
									mouseOn = ib;
								}
								break;
							}
						}
					} else if(open && mouseOn != null) {
						boolean putBack = false;
						for(InventBox ib : boxes) {
							if(Physics.checkContains(ib, Mouse.getX(), Mouse.getY())) {
								if(ib != mouseOn) {
									if(ib.holds != null) {
										Object[] temp = ib.holds;
										ib.holds = mouseOn.holds;
										mouseOn.holds = temp;
										ib.hpX = ib.x + 4;
										ib.hpY = ib.y + 4;
										putBack = true;
									} else {
										ib.holds = mouseOn.holds;
										mouseOn.holds = null;
										putBack = true;
										mouseOn.hpX = mouseOn.x + 4;
										mouseOn.hpY = mouseOn.y + 4;
										mouseOn = null;
									}
								} else {
									mouseOn.hpX = mouseOn.x + 4;
									mouseOn.hpY = mouseOn.y + 4;
									putBack = true;
									mouseOn = null;
								}
							}
						}
						if(!putBack) {
							int total = 0;
							for(int i = 0; i < mouseOn.holds.length; i++) {
								if(mouseOn.holds[i] != null) total++;
							}
							for(int i = 0; i < total; i++) {
								addPickUp(mouseOn.holds[i]);
							}
							mouseOn.holds = null;
							mouseOn.hpX = mouseOn.x + 4;
							mouseOn.hpY = mouseOn.y + 4;
							mouseOn = null;
						}
					}
				}
			}
		}
		
		if(mouseOn != null) {
			mouseOn.hpX = Mouse.getX() - InventBox.picSize / 2;
			mouseOn.hpY = Mouse.getY() - InventBox.picSize / 2;;
		}
		
		
		if(!open) {
			if(Mouse.isButtonDown(0) && !changed) {
				for(int i = 0; i < 9; i++) {
					if(Physics.checkContains(boxes[i], Mouse.getX(), Mouse.getY())) {
						changed = true;
						selected = i;
						break;
					}
				}
			} else {
				changed = false;
			}
		}
		
	}
	
	public void getKeyInput() {
		if(Keyboard.getEventKeyState()) {
			if(Keyboard.getEventKey() == Keyboard.KEY_1) selected = 0;
			else if(Keyboard.getEventKey() == Keyboard.KEY_2) selected = 1;
			else if(Keyboard.getEventKey() == Keyboard.KEY_3) selected = 2;
			else if(Keyboard.getEventKey() == Keyboard.KEY_4) selected = 3;
			else if(Keyboard.getEventKey() == Keyboard.KEY_5) selected = 4;
			else if(Keyboard.getEventKey() == Keyboard.KEY_6) selected = 5;
			else if(Keyboard.getEventKey() == Keyboard.KEY_7) selected = 6;
			else if(Keyboard.getEventKey() == Keyboard.KEY_8) selected = 7;
			else if(Keyboard.getEventKey() == Keyboard.KEY_9) selected = 8;
		} else {
			if(Keyboard.getEventKey() == Keyboard.KEY_E) open = !open;
		}
	}
	
	public void initBoxes() {
		for(int i = 0; i < 9; i++) {
			boxes[i] = new InventBox(i * boxSize + startX, inventY, game);
		}
		for(int y = 0; y < 3; y++) {
			for(int x = 0; x < 9; x++) {
				boxes[9 + (x + y * 9)] = new InventBox(x * boxSize + startX, inventOpenY + y * boxSize, game);
			}
		}
	}
	
	public boolean isOpen() {
		return open;
	}
	
	public void setOpen(boolean open) {
		this.open = open;
	}
	
	public boolean add(PickUp pu) {
		boolean added = false;
		for(InventBox ib : boxes) {
			if(ib.holds != null) {
				if(ib.holds[0] instanceof PBGrass && pu instanceof PBGrass) {
					for(int i = 0; i < ib.holds.length; i++) {
						if(ib.holds[i] == null) {
							ib.holds[i] = pu;
							added = true;
							break;
						}
					}
				} else if(ib.holds[0] instanceof PBDirt && pu instanceof PBDirt) {
					for(int i = 0; i < ib.holds.length; i++) {
						if(ib.holds[i] == null) {
							ib.holds[i] = pu;
							added = true;
							break;
						}
					}
				} else if(ib.holds[0] instanceof PICoal && pu instanceof PICoal) {
					for(int i = 0; i < ib.holds.length; i++) {
						if(ib.holds[i] == null) {
							ib.holds[i] = pu;
							added = true;
							break;
						}
					}
				} else if(ib.holds[0] instanceof PBBedrock && pu instanceof PBBedrock) {
					for(int i = 0; i < ib.holds.length; i++) {
						if(ib.holds[i] == null) {
							ib.holds[i] = pu;
							added = true;
							break;
						}
					}
				} else if(ib.holds[0] instanceof PIDiamond && pu instanceof PIDiamond) {
					for(int i = 0; i < ib.holds.length; i++) {
						if(ib.holds[i] == null) {
							ib.holds[i] = pu;
							added = true;
							break;
						}
					}
				} else if(ib.holds[0] instanceof PBDoor && pu instanceof PBDoor) {
					for(int i = 0; i < ib.holds.length; i++) {
						if(ib.holds[i] == null) {
							ib.holds[i] = pu;
							added = true;
							break;
						}
					}
				} else if(ib.holds[0] instanceof PBGoldOre && pu instanceof PBGoldOre) {
					for(int i = 0; i < ib.holds.length; i++) {
						if(ib.holds[i] == null) {
							ib.holds[i] = pu;
							added = true;
							break;
						}
					}
				} else if(ib.holds[0] instanceof PBStone && pu instanceof PBStone) {
					for(int i = 0; i < ib.holds.length; i++) {
						if(ib.holds[i] == null) {
							ib.holds[i] = pu;
							added = true;
							break;
						}
					}
				} else if(ib.holds[0] instanceof PBIronOre && pu instanceof PBIronOre) {
					for(int i = 0; i < ib.holds.length; i++) {
						if(ib.holds[i] == null) {
							ib.holds[i] = pu;
							added = true;
							break;
						}
					}
				} else if(ib.holds[0] instanceof PBWood && pu instanceof PBWood) {
					for(int i = 0; i < ib.holds.length; i++) {
						if(ib.holds[i] == null) {
							ib.holds[i] = pu;
							added = true;
							break;
						}
					}
				} else if(ib.holds[0] instanceof PBSand && pu instanceof PBSand) {
					for(int i = 0; i < ib.holds.length; i++) {
						if(ib.holds[i] == null) {
							ib.holds[i] = pu;
							added = true;
							break;
						}
					}
				}
			}
			if(added) break;
		}
		if(!added) {
			for(InventBox ib : boxes) {
				if(ib.holds == null) {
					if(pu instanceof PBGrass) {
						ib.holds = new PBGrass[InventBox.maxContain];
						ib.holds[0] = pu;
						break;
					} else if(pu instanceof PBDirt) {
						ib.holds = new PBDirt[InventBox.maxContain];
						ib.holds[0] = pu;
						break;
					} else if(pu instanceof PICoal) {
						ib.holds = new PICoal[InventBox.maxContain];
						ib.holds[0] = pu;
						break;
					} else if(pu instanceof PIDiamond) {
						ib.holds = new PIDiamond[InventBox.maxContain];
						ib.holds[0] = pu;
						break;
					} else if(pu instanceof PBDoor) {
						ib.holds = new PBDoor[InventBox.maxContain];
						ib.holds[0] = pu;
						break;
					} else if(pu instanceof PBBedrock) {
						ib.holds = new PBBedrock[InventBox.maxContain];
						ib.holds[0] = pu;
						break;
					} else if(pu instanceof PBGoldOre) {
						ib.holds = new PBGoldOre[InventBox.maxContain];
						ib.holds[0] = pu;
						break;
					} else if(pu instanceof PBStone) {
						ib.holds = new PBStone[InventBox.maxContain];
						ib.holds[0] = pu;
						break;
					} else if(pu instanceof PBIronOre) {
						ib.holds = new PBIronOre[InventBox.maxContain];
						ib.holds[0] = pu;
						break;
					} else if(pu instanceof PBWood) {
						ib.holds = new PBWood[InventBox.maxContain];
						ib.holds[0] = pu;
						break;
					} else if(pu instanceof PBSand) {
						ib.holds = new PBSand[InventBox.maxContain];
						ib.holds[0] = pu;
						break;
					}
				}
			}
		}
		return added;
	}
	
	public void addBlock(Chunk chunk, float x, float y) {
		if(boxes[selected].holds != null) {
			if(boxes[selected].holds[0] != null) {
				int ret = chunk.getRet(x, y);
				if(ret != -1) {
					if(boxes[selected].holds[0] instanceof PBGrass) {
						float xPos = x - Chunk.NORM_BW * chunk.id;
						chunk.getBlocks()[ret] = new BGrass(chunk.x + xPos, chunk.y + y, game);
						nullifyOne();
					} else if(boxes[selected].holds[0] instanceof PBDirt) {
						float xPos = x - Chunk.NORM_BW * chunk.id;
						chunk.getBlocks()[ret] = new BDirt(chunk.x + xPos, chunk.y + y, game);
						nullifyOne();
					} else if(boxes[selected].holds[0] instanceof PICoal) {
						float xPos = x - Chunk.NORM_BW * chunk.id;
						chunk.getBlocks()[ret] = new BCoalOre(chunk.x + xPos, chunk.y + y, game);
						nullifyOne();
					} else if(boxes[selected].holds[0] instanceof PIDiamond) {
						float xPos = x - Chunk.NORM_BW * chunk.id;
						chunk.getBlocks()[ret] = new BDiamondOre(chunk.x + xPos, chunk.y + y, game);
						nullifyOne();
					} else if(boxes[selected].holds[0] instanceof PBStone) {
						float xPos = x - Chunk.NORM_BW * chunk.id;
						chunk.getBlocks()[ret] = new BStone(chunk.x + xPos, chunk.y + y, game);
						nullifyOne();
					} else if(boxes[selected].holds[0] instanceof PBDoor) {
						float xPos = x - Chunk.NORM_BW * chunk.id;
						chunk.getBlocks()[ret] = new BDoor(chunk.x + xPos, chunk.y + y, game);
						nullifyOne();
					} else if(boxes[selected].holds[0] instanceof PBGoldOre) {
						float xPos = x - Chunk.NORM_BW * chunk.id;
						chunk.getBlocks()[ret] = new BGoldOre(chunk.x + xPos, chunk.y + y, game);
						nullifyOne();
					} else if(boxes[selected].holds[0] instanceof PBIronOre) {
						float xPos = x - Chunk.NORM_BW * chunk.id;
						chunk.getBlocks()[ret] = new BIronOre(chunk.x + xPos, chunk.y + y, game);
						nullifyOne();
					} else if(boxes[selected].holds[0] instanceof PBWood) {
						float xPos = x - Chunk.NORM_BW * chunk.id;
						chunk.getBlocks()[ret] = new BWood(chunk.x + xPos, chunk.y + y, game);
						nullifyOne();
					} else if(boxes[selected].holds[0] instanceof PBSand) {
						float xPos = x - Chunk.NORM_BW * chunk.id;
						chunk.getBlocks()[ret] = new BSand(chunk.x + xPos, chunk.y + y, game);
						nullifyOne();
					}
				}
			}
		}
	}
	
	public void nullifyOne() {
		for(int i = 0; i < boxes[selected].holds.length; i++) {
			if(boxes[selected].holds[i] == null) {
				boxes[selected].holds[i-1] = null;
				if(boxes[selected].holds[1] == null) {
					boxes[selected].holds = null;
					break;
				}
			}
			if(i == boxes[selected].holds.length - 1) {
				boxes[selected].holds[i] = null;
			}
		}
	}
	
	public void addPickUp(Object o) {
		if(o instanceof PBGrass) {
			game.pickups.add(new PBGrass(Mouse.getX() + Game.xOffs, Mouse.getY() + Game.yOffs, game));
		} else if(o instanceof PBDirt) {
			game.pickups.add(new PBDirt(Mouse.getX() + Game.xOffs, Mouse.getY() + Game.yOffs, game));
		} else if(o instanceof PICoal) {
			game.pickups.add(new PICoal(Mouse.getX() + Game.xOffs, Mouse.getY() + Game.yOffs, game));
		} else if(o instanceof PIDiamond) {
			game.pickups.add(new PIDiamond(Mouse.getX() + Game.xOffs, Mouse.getY() + Game.yOffs, game));
		} else if(o instanceof PBDoor) {
			game.pickups.add(new PBDoor(Mouse.getX() + Game.xOffs, Mouse.getY() + Game.yOffs, game));
		} else if(o instanceof PBBedrock) {
			game.pickups.add(new PBBedrock(Mouse.getX() + Game.xOffs, Mouse.getY() + Game.yOffs, game));
		} else if(o instanceof PBGoldOre) {
			game.pickups.add(new PBGoldOre(Mouse.getX() + Game.xOffs, Mouse.getY() + Game.yOffs, game));
		} else if(o instanceof PBStone) {
			game.pickups.add(new PBStone(Mouse.getX() + Game.xOffs, Mouse.getY() + Game.yOffs, game));
		} else if(o instanceof PBIronOre) {
			game.pickups.add(new PBIronOre(Mouse.getX() + Game.xOffs, Mouse.getY() + Game.yOffs, game));
		} else if(o instanceof PBWood) {
			game.pickups.add(new PBWood(Mouse.getX() + Game.xOffs, Mouse.getY() + Game.yOffs, game));
		} else if(o instanceof PBSand) {
			game.pickups.add(new PBSand(Mouse.getX() + Game.xOffs, Mouse.getY() + Game.yOffs, game));
		} else {
			System.out.println("UNRECOGNIZED TYPE");
		}
	}
}