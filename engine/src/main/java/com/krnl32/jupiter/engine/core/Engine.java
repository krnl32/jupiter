package com.krnl32.jupiter.engine.core;

import com.krnl32.jupiter.engine.event.EventBus;
import com.krnl32.jupiter.engine.events.window.WindowCloseEvent;
import com.krnl32.jupiter.engine.input.InputActionSystem;
import com.krnl32.jupiter.engine.input.InputDeviceSystem;
import com.krnl32.jupiter.engine.renderer.*;
import com.krnl32.jupiter.engine.serializer.utility.DefaultComponentSerializers;
import com.krnl32.jupiter.engine.utility.FileIO;
import com.krnl32.jupiter.engine.utility.Timer;
import org.joml.Matrix4f;

public abstract class Engine {
	private boolean running;
	private Window window;
	private Renderer renderer;

	public Engine(String name, int width, int height) {
		running = false;
		window = new Window(name, width, height);

		// Setup Renderer
		renderer = new Renderer();
		renderer.setDepthTest(false);
		renderer.setAlphaBlending(true);

		// Setup Engine Events
		EventBus.getInstance().register(WindowCloseEvent.class, event -> {
			running = false;
		});
	}

	public void run() {
		// Register ECS Component Serializers
		DefaultComponentSerializers.registerAll();

		if(!onInit()) {
			Logger.critical("Failed to run onInit");
			return;
		}

		// Setup Default Engine Shader & Default RenderPasses
		if (!initDefaultRenderPass(renderer)) {
			Logger.critical("Failed to init Default RenderPass");
			return;
		}

		running = true;

		float lastTime = 0.0f;
		while (running) {
			float time = Timer.getTimeSeconds();
			float dt = time - lastTime;
			lastTime = time;

			InputActionSystem.getInstance().onUpdate();

			if(dt > 0) {
				//System.out.printf("FPS: %f\n", (1/dt));
				onUpdate(dt);

				renderer.beginFrame();
				onRender(dt, renderer);
				renderer.endFrame();
			}

			InputDeviceSystem.getInstance().reset();
			window.update();
		}

		window.destroy();
	}

	public abstract boolean onInit();
	public abstract void onUpdate(float dt);
	public abstract void onRender(float dt, Renderer renderer);

	protected Window getWindow() {
		return window;
	}

	protected Renderer getRenderer() {
		return renderer;
	}

	private boolean initDefaultRenderPass(Renderer renderer) {
		int[] shaderSamplers = new int[32];
		for (int i = 0; i < 32; i++)
			shaderSamplers[i] = i;

		try {
			// Setup World Shader & WorldRenderPass
			Shader worldShader = new Shader(FileIO.readResourceFileContent("Assets/Shaders/WorldVertex.glsl"), FileIO.readResourceFileContent("Assets/Shaders/WorldFragment.glsl"));
			worldShader.bind();
			worldShader.setMat4("u_Model", new Matrix4f().identity());
			worldShader.setIntArray("u_Textures", shaderSamplers);
			worldShader.unbind();
			renderer.addRenderPass(new WorldRenderPass(worldShader));

			// Setup UI Shader & UIRenderPass
			Shader uiShader = new Shader(FileIO.readResourceFileContent("Assets/Shaders/UIVertex.glsl"), FileIO.readResourceFileContent("Assets/Shaders/UIFragment.glsl"));
			uiShader.bind();
			uiShader.setMat4("u_Model", new Matrix4f().identity());
			uiShader.setIntArray("u_Textures", shaderSamplers);
			uiShader.unbind();
			renderer.addRenderPass(new UIRenderPass(uiShader, getWindow().getWidth(), getWindow().getHeight()));

			// Setup Text Shader & TextRenderPass
			Shader textShader = new Shader(FileIO.readResourceFileContent("Assets/Shaders/TextVertex.glsl"), FileIO.readResourceFileContent("Assets/Shaders/TextFragment.glsl"));
			textShader.bind();
			textShader.setMat4("u_Model", new Matrix4f().identity());
			textShader.setIntArray("u_Textures", shaderSamplers);
			textShader.unbind();
			renderer.addRenderPass(new TextRenderPass(textShader, getWindow().getWidth(), getWindow().getHeight()));
		} catch (Exception e) {
			Logger.critical(e.getMessage());
			return false;
		}

		return true;
	}
}
