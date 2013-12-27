package com.henry.mine.utils;

import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glTexCoord2f;
import static org.lwjgl.opengl.GL11.glVertex2f;

import com.henry.mine.base.Game;

public class Draw {

	public static void drawTex(float x, float y, float width, float height) {
		glPushMatrix();
		{
			glBegin(GL_QUADS);
			{
				glTexCoord2f(1, 1); glVertex2f(x, y);
				glTexCoord2f(1, 0); glVertex2f(x, y + height);
				glTexCoord2f(0, 0); glVertex2f(x + width, y + height);
				glTexCoord2f(0, 1); glVertex2f(x + width, y);
			}
			glEnd();
		}
		glPopMatrix();
	}
	
	public static void drawTexWithOffs(float x, float y, float width, float height) {
		glPushMatrix();
		{
			glBegin(GL_QUADS);
			{
				glTexCoord2f(1, 1); glVertex2f(x - Game.xOffs, y - Game.yOffs);
				glTexCoord2f(1, 0); glVertex2f(x - Game.xOffs, y + height - Game.yOffs);
				glTexCoord2f(0, 0); glVertex2f(x + width - Game.xOffs, y + height - Game.yOffs);
				glTexCoord2f(0, 1); glVertex2f(x + width - Game.xOffs, y - Game.yOffs);
			}
			glEnd();
		}
		glPopMatrix();
	}
	
	public static void drawRect(float x, float y, float width, float height) {
		glPushMatrix();
		{
			glBegin(GL_QUADS);
			{
				glVertex2f(x, y);
				glVertex2f(x, y + height);
				glVertex2f(x + width, y + height);
				glVertex2f(x + width, y);
			}
			glEnd();
		}
		glPopMatrix();
	}
	
	public static void drawRectWithOffs(float x, float y, float width, float height) {
		glPushMatrix();
		{
			glBegin(GL_QUADS);
			{
				glVertex2f(x - Game.xOffs, y - Game.yOffs);
				glVertex2f(x - Game.xOffs, y + height - Game.yOffs);
				glVertex2f(x + width - Game.xOffs, y + height - Game.yOffs);
				glVertex2f(x + width - Game.xOffs, y - Game.yOffs);
			}
			glEnd();
		}
		glPopMatrix();
	}

}
