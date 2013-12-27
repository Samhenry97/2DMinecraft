package com.henry.mine.base;

import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_COLOR_MATERIAL;
import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;
import static org.lwjgl.opengl.GL11.GL_LIGHT0;
import static org.lwjgl.opengl.GL11.GL_LIGHT1;
import static org.lwjgl.opengl.GL11.GL_LIGHTING;
import static org.lwjgl.opengl.GL11.GL_LIGHT_MODEL_AMBIENT;
import static org.lwjgl.opengl.GL11.GL_MODELVIEW;
import static org.lwjgl.opengl.GL11.GL_ONE_MINUS_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_PROJECTION;
import static org.lwjgl.opengl.GL11.GL_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glBlendFunc;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glLightModel;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glMatrixMode;
import static org.lwjgl.opengl.GL11.glOrtho;

import java.nio.FloatBuffer;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

import com.henry.mine.utils.Utils;

public class Engine {

    private static Game game;
    private static FloatBuffer lightCol = Utils.toFloatBuffer(new float[]{0.0f, 0.0f, 0.0f, 1f});
    private static boolean fullscreen = false;
    
    public static void main(String[] args) {
        initDisplay();
        initGL();
        
        initGame();
        
        gameLoop();
        cleanUp();
    }
    
    private static void initGame() {
        game = new Game();
    }
    
    private static void getInput() {
        game.getInput();
    }
    
    private static void update() {
        game.update();
    }
    
    private static void render() {    	
        glClear(GL_COLOR_BUFFER_BIT);
        glClearColor(0.2f, 0.4f, 1f, 1f);
        glLoadIdentity();
        
        game.render();
        
        Display.update();
        Display.sync(60);
    }
    
    private static void gameLoop() {
        while(!Display.isCloseRequested()) {
            getInput();
            update();
            render();
        }
    }
    
    private static void initGL() {
        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        glOrtho(0, Display.getWidth(), 0, Display.getHeight(), -1, 1);
        glMatrixMode(GL_MODELVIEW);
        glClearColor(0, 0, 0, 1);
        glDisable(GL_DEPTH_TEST);
        glEnable(GL_TEXTURE_2D);
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        glEnable(GL_COLOR_MATERIAL);
        glEnable(GL_LIGHTING);
        glEnable(GL_LIGHT0);
        glEnable(GL_LIGHT1);
        glLightModel(GL_LIGHT_MODEL_AMBIENT, lightCol);
    }
    
    private static void cleanUp() {
        Display.destroy();
        Keyboard.destroy();
        Mouse.destroy();
        Sound.back.stopRunning();
    }
    
    private static void initDisplay() {
        try {
        	if(fullscreen) {
        		Display.setFullscreen(true);
        	} else {
        		Display.setDisplayMode(new DisplayMode(1000, 600));
        		Display.setTitle("2D Minecraft! :D");
        	}
            Display.create();
            Display.setVSyncEnabled(true);
            Keyboard.create();
            Mouse.create();
        } catch(LWJGLException e) {
            Logger.getLogger(Engine.class.getName()).log(Level.SEVERE, null, e);
        }
    }
}