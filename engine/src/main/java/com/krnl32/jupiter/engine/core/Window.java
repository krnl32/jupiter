package com.krnl32.jupiter.engine.core;

import com.krnl32.jupiter.engine.event.EventBus;
import com.krnl32.jupiter.engine.events.filesystem.FileDragDropEvent;
import com.krnl32.jupiter.engine.events.input.*;
import com.krnl32.jupiter.engine.events.window.WindowCloseEvent;
import com.krnl32.jupiter.engine.events.window.WindowResizeEvent;
import com.krnl32.jupiter.engine.input.devices.KeyCode;
import com.krnl32.jupiter.engine.input.devices.MouseCode;
import org.joml.Vector2f;
import org.lwjgl.Version;
import org.lwjgl.glfw.GLFWDropCallback;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;

import java.nio.file.Path;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.system.MemoryUtil.NULL;

public class Window {
	private WindowSettings settings;
	private long window;

	public Window(WindowSettings settings) {
		this.settings = settings;
		if (!init()) {
			Logger.critical("Window Failed to init");
			return;
		}
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

	public String getName() {
		return settings.getName();
	}

	public int getWidth() {
		return settings.getWidth();
	}

	public int getHeight() {
		return settings.getHeight();
	}

	public long getNativeWindow() {
		return window;
	}

	private boolean init() {
		Logger.info("LWJGL Version: {}", Version.getVersion());

		GLFWErrorCallback.createPrint(System.err).set();

		if (!glfwInit()) {
			Logger.critical("Unable to initialize GLFW");
			return false;
		}

		glfwDefaultWindowHints();
		glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
		glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);
		glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 4);
		glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 6);
		glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
		glfwWindowHint(GLFW_OPENGL_DEBUG_CONTEXT, GLFW_TRUE);

		window = glfwCreateWindow(settings.getWidth(), settings.getHeight(), settings.getName(), NULL, NULL);
		if (window == NULL) {
			Logger.critical("Failed to create the GLFW window");
			return false;
		}

		glfwMakeContextCurrent(window);
		glfwSwapInterval(settings.isVSync() ? 1 : 0);

		glfwShowWindow(window);
		GL.createCapabilities();
		return true;
	}

	private void setupEvents() {
		glfwSetWindowCloseCallback(window, window -> {
			EventBus.getInstance().emit(new WindowCloseEvent());
		});

		glfwSetWindowSizeCallback(window, (window, width, height) -> {
			settings.setWidth(width);
			settings.setHeight(height);
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

		glfwSetDropCallback(window, (window, count, names) -> {
			for (int i = 0; i < count; i++) {
				String filePath = GLFWDropCallback.getName(names, i);
				EventBus.getInstance().emit(new FileDragDropEvent(Path.of(filePath)));
			}
		});
	}
}
