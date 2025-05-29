package com.github.krnl32.jupiter.core;

import org.lwjgl.Version;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.system.MemoryUtil.NULL;

public class Window {
	private String title;
	private int width;
	private int height;
	private long window;

	public Window(String title, int width, int height) {
		this.title = title;
		this.width = width;
		this.height = height;
		init();
	}

	public void update() {
		glfwSwapBuffers(window);
		glfwPollEvents();
	}

	private void init() {
		Logger.info("LWJGL Version: {}", Version.getVersion());

		GLFWErrorCallback.createPrint(System.err).set();

		if (!glfwInit())
			Logger.critical("Unable to initialize GLFW");

		glfwDefaultWindowHints();
		glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
		glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);

		window = glfwCreateWindow(width, height, title, NULL, NULL);
		if (window == NULL)
			Logger.critical("Failed to create the GLFW window");

		glfwMakeContextCurrent(window);
		glfwSwapInterval(1);

		glfwShowWindow(window);
		GL.createCapabilities();
	}

	public void destroy() {
		if (window != NULL) {
			glfwFreeCallbacks(window);
			glfwDestroyWindow(window);
			glfwTerminate();
			glfwSetErrorCallback(null).free();
		}
	}
}
