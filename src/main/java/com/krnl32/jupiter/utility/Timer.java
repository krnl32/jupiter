package com.krnl32.jupiter.utility;

import static org.lwjgl.glfw.GLFW.glfwGetTime;

public class Timer {
	public static float getTimeSeconds() {
		return (float)glfwGetTime();
	}

	public static float getTimeMilliSeconds() {
		return (float)glfwGetTime() * 1000;
	}
}
