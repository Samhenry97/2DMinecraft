package com.henry.mine.utils;

public class GameFont {
	
	public static void render(String text, float hpX, float hpY) {
		int startPos = 0;
		for(int i = 0; i < text.length(); i++) {
			switch(text.charAt(i)) {
				case '0' : Tex.bindTexture(Tex.num0); break;
				case '1' : Tex.bindTexture(Tex.num1); break;
				case '2' : Tex.bindTexture(Tex.num2); break;
				case '3' : Tex.bindTexture(Tex.num3); break;
				case '4' : Tex.bindTexture(Tex.num4); break;
				case '5' : Tex.bindTexture(Tex.num5); break;
				case '6' : Tex.bindTexture(Tex.num6); break;
				case '7' : Tex.bindTexture(Tex.num7); break;
				case '8' : Tex.bindTexture(Tex.num8); break;
				case '9' : Tex.bindTexture(Tex.num9); break;
				default : System.out.println("I don't recognize that letter...");
			}
			Draw.drawTex(hpX + startPos * 15, hpY, 15, 15);
			startPos++;
		}
	}

}
