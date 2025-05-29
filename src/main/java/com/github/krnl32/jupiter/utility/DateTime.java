package com.github.krnl32.jupiter.utility;

import static org.lwjgl.glfw.GLFW.glfwGetTime;

public class DateTime {
	public static float getTimeSeconds() {
		return (float)glfwGetTime();
	}

	public static float getTimeMilliSeconds() {
		return (float)glfwGetTime() * 1000;
	}
}
