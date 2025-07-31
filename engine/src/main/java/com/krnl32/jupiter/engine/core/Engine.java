package com.krnl32.jupiter.engine.core;

import com.krnl32.jupiter.engine.asset.AssetID;
import com.krnl32.jupiter.engine.asset.types.ShaderAsset;
import com.krnl32.jupiter.engine.event.EventBus;
import com.krnl32.jupiter.engine.events.window.WindowCloseEvent;
import com.krnl32.jupiter.engine.input.InputActionSystem;
import com.krnl32.jupiter.engine.input.InputDeviceSystem;
import com.krnl32.jupiter.engine.project.ProjectContext;
import com.krnl32.jupiter.engine.renderer.*;
import com.krnl32.jupiter.engine.serializer.utility.DefaultComponentSerializers;
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
		initRenderPass(renderer);

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
		AssetID worldShaderID = ProjectContext.getAssetManager().registerAndLoad("shaders/world", () -> new ShaderAsset("Shaders/WorldVertex.glsl", "Shaders/WorldFragment.glsl"));
		if (worldShaderID == null) {
			Logger.critical("Engine Failed to Load World Shader Asset({})", "shaders/world");
			return;
		}

		ShaderAsset worldShaderAsset = ProjectContext.getAssetManager().getAsset(worldShaderID);
		if (worldShaderAsset == null || !worldShaderAsset.isLoaded()) {
			Logger.critical("Engine World Shader Null or Not LOADED");
			return;
		}

		Shader worldShader = worldShaderAsset.getShader();
		worldShader.bind();
		worldShader.setMat4("u_Model", new Matrix4f().identity());
		worldShader.setIntArray("u_Textures", shaderSamplers);
		worldShader.unbind();

		renderer.addRenderPass(new WorldRenderPass(worldShader));

		// Setup UI Shader & UIRenderPass
		AssetID uiShaderID = ProjectContext.getAssetManager().registerAndLoad("shaders/ui", () -> new ShaderAsset("Shaders/UIVertex.glsl", "Shaders/UIFragment.glsl"));
		if (uiShaderID == null) {
			Logger.critical("Engine Failed to Load UI Shader Asset({})", "shaders/ui");
			return;
		}

		ShaderAsset uiShaderAsset = ProjectContext.getAssetManager().getAsset(uiShaderID);
		if (uiShaderAsset == null || !uiShaderAsset.isLoaded()) {
			Logger.critical("Engine UI Shader Null or Not LOADED");
			return;
		}

		Shader uiShader = uiShaderAsset.getShader();
		uiShader.bind();
		uiShader.setMat4("u_Model", new Matrix4f().identity());
		uiShader.setIntArray("u_Textures", shaderSamplers);
		uiShader.unbind();

		renderer.addRenderPass(new UIRenderPass(uiShader, getWindow().getWidth(), getWindow().getHeight()));

		// Setup Text Shader & TextRenderPass
		AssetID textShaderID = ProjectContext.getAssetManager().registerAndLoad("shaders/text", () -> new ShaderAsset("Shaders/TextVertex.glsl", "Shaders/TextFragment.glsl"));
		if (textShaderID == null) {
			Logger.critical("Engine Failed to Load UI Shader Asset({})", "shaders/text");
			return;
		}

		ShaderAsset textShaderAsset = ProjectContext.getAssetManager().getAsset(textShaderID);
		if (textShaderAsset == null || !textShaderAsset.isLoaded()) {
			Logger.critical("Engine Text Shader Null or Not LOADED");
			return;
		}

		Shader textShader = textShaderAsset.getShader();
		textShader.bind();
		textShader.setMat4("u_Model", new Matrix4f().identity());
		textShader.setIntArray("u_Textures", shaderSamplers);
		textShader.unbind();

		renderer.addRenderPass(new TextRenderPass(textShader, getWindow().getWidth(), getWindow().getHeight()));
	}
}
