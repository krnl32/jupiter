package com.krnl32.jupiter.core;

import com.krnl32.jupiter.asset.AssetID;
import com.krnl32.jupiter.asset.AssetManager;
import com.krnl32.jupiter.asset.types.ShaderAsset;
import com.krnl32.jupiter.event.EventBus;
import com.krnl32.jupiter.events.window.WindowCloseEvent;
import com.krnl32.jupiter.input.InputActionSystem;
import com.krnl32.jupiter.input.InputDeviceSystem;
import com.krnl32.jupiter.renderer.*;
import com.krnl32.jupiter.serializer.utility.DefaultComponentSerializers;
import com.krnl32.jupiter.utility.Timer;
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

		// Setup Default Engine Shader & Default RenderPasses
		initRenderPass(renderer);

		// Setup Engine Events
		EventBus.getInstance().register(WindowCloseEvent.class, event -> {
			running = false;
		});
	}

	public void run() {
		DefaultComponentSerializers.registerAll();

		if(!onInit())
			Logger.critical("Failed to run onInit");

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

	private void initRenderPass(Renderer renderer) {
		int[] shaderSamplers = new int[32];
		for (int i = 0; i < 32; i++)
			shaderSamplers[i] = i;

		// Setup World Shader & WorldRenderPass
		AssetID worldShaderID = AssetManager.getInstance().registerAndLoad("shaders/world", () -> new ShaderAsset("shaders/world_vertex.glsl", "shaders/world_fragment.glsl"));
		if (worldShaderID == null)
			Logger.critical("Engine Failed to Load World Shader Asset({})", "shaders/world");

		ShaderAsset worldShaderAsset = AssetManager.getInstance().getAsset(worldShaderID);
		if (worldShaderAsset == null || !worldShaderAsset.isLoaded())
			Logger.critical("Engine World Shader Null or Not LOADED");

		Shader worldShader = worldShaderAsset.getShader();
		worldShader.bind();
		worldShader.setMat4("u_Model", new Matrix4f().identity());
		worldShader.setIntArray("u_Textures", shaderSamplers);
		worldShader.unbind();

		renderer.addRenderPass(new WorldRenderPass(worldShader));

		// Setup UI Shader & UIRenderPass
		AssetID uiShaderID = AssetManager.getInstance().registerAndLoad("shaders/ui", () -> new ShaderAsset("shaders/ui_vertex.glsl", "shaders/ui_fragment.glsl"));
		if (uiShaderID == null)
			Logger.critical("Engine Failed to Load UI Shader Asset({})", "shaders/ui");

		ShaderAsset uiShaderAsset = AssetManager.getInstance().getAsset(uiShaderID);
		if (uiShaderAsset == null || !uiShaderAsset.isLoaded())
			Logger.critical("Engine UI Shader Null or Not LOADED");

		Shader uiShader = uiShaderAsset.getShader();
		uiShader.bind();
		uiShader.setMat4("u_Model", new Matrix4f().identity());
		uiShader.setIntArray("u_Textures", shaderSamplers);
		uiShader.unbind();

		renderer.addRenderPass(new UIRenderPass(uiShader, getWindow().getWidth(), getWindow().getHeight()));

		// Setup Text Shader & TextRenderPass
		AssetID textShaderID = AssetManager.getInstance().registerAndLoad("shaders/text", () -> new ShaderAsset("shaders/text_vertex.glsl", "shaders/text_fragment.glsl"));
		if (textShaderID == null)
			Logger.critical("Engine Failed to Load UI Shader Asset({})", "shaders/text");

		ShaderAsset textShaderAsset = AssetManager.getInstance().getAsset(textShaderID);
		if (textShaderAsset == null || !textShaderAsset.isLoaded())
			Logger.critical("Engine Text Shader Null or Not LOADED");

		Shader textShader = textShaderAsset.getShader();
		textShader.bind();
		textShader.setMat4("u_Model", new Matrix4f().identity());
		textShader.setIntArray("u_Textures", shaderSamplers);
		textShader.unbind();

		renderer.addRenderPass(new TextRenderPass(textShader, getWindow().getWidth(), getWindow().getHeight()));
	}
}
