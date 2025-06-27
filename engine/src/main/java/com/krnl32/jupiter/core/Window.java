package com.krnl32.jupiter.core;

import com.krnl32.jupiter.event.EventBus;
import com.krnl32.jupiter.events.input.*;
import com.krnl32.jupiter.events.window.WindowCloseEvent;
import com.krnl32.jupiter.events.window.WindowResizeEvent;
import com.krnl32.jupiter.input.KeyCode;
import com.krnl32.jupiter.input.MouseCode;
import org.joml.Vector2f;
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
		setupEvents();
	}

	public void update() {
		glfwSwapBuffers(window);
		glfwPollEvents();
	}

	public void destroy() {
		if (window != NULL) {
			glfwFreeCallbacks(window);
			glfwDestroyWindow(window);
			glfwTerminate();
			glfwSetErrorCallback(null).free();
		}
	}

	public String getTitle() {
		return title;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public long getNativeWindow() {
		return window;
	}

	private void init() {
		Logger.info("LWJGL Version: {}", Version.getVersion());

		GLFWErrorCallback.createPrint(System.err).set();

		if (!glfwInit())
			Logger.critical("Unable to initialize GLFW");

		glfwDefaultWindowHints();
		glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
		glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);
		glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 4);
		glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 6);
		glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
		glfwWindowHint(GLFW_OPENGL_DEBUG_CONTEXT, GLFW_TRUE);

		window = glfwCreateWindow(width, height, title, NULL, NULL);
		if (window == NULL)
			Logger.critical("Failed to create the GLFW window");

		glfwMakeContextCurrent(window);
		glfwSwapInterval(1);

		glfwShowWindow(window);
		GL.createCapabilities();
	}

	private void setupEvents() {
		glfwSetWindowCloseCallback(window, window -> {
			EventBus.getInstance().emit(new WindowCloseEvent());
		});

		glfwSetWindowSizeCallback(window, (window, width, height) -> {
			EventBus.getInstance().emit(new WindowResizeEvent(width, height));
		});

		glfwSetKeyCallback(window, (window, key, scancode, action, mods) -> {
			if (action == GLFW_PRESS)
				EventBus.getInstance().emit(new KeyPressEvent(KeyCode.fromCode(key)));
			else if(action == GLFW_RELEASE)
				EventBus.getInstance().emit(new KeyReleaseEvent(KeyCode.fromCode(key)));
			else if(action == GLFW_REPEAT)
				EventBus.getInstance().emit(new KeyDownEvent(KeyCode.fromCode(key)));
		});

		glfwSetMouseButtonCallback(window, (window, button, action, mods) -> {
			if (action == GLFW_PRESS)
				EventBus.getInstance().emit(new MouseButtonPressEvent(MouseCode.fromCode(button)));
			else if(action == GLFW_RELEASE)
				EventBus.getInstance().emit(new MouseButtonReleaseEvent(MouseCode.fromCode(button)));
		});

		glfwSetCursorPosCallback(window, (window, xpos, ypos) -> {
			EventBus.getInstance().emit(new MouseCursorEvent(new Vector2f((float)xpos, (float)ypos)));
		});

		glfwSetScrollCallback(window, (window, xoffset, yoffset) -> {
			EventBus.getInstance().emit(new MouseScrollEvent(new Vector2f((float)xoffset, (float)yoffset)));
		});
	}
}
