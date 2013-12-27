package com.henry.mine.utils;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;

public class Utils {
	
	public static FloatBuffer toFloatBuffer(float[] arg) {
    	FloatBuffer ret = BufferUtils.createFloatBuffer(arg.length);
    	ret.put(arg);
    	ret.flip();
    	return ret;
    }

}
