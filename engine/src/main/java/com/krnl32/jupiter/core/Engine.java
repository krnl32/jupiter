package com.krnl32.jupiter.core;

import com.krnl32.jupiter.asset.AssetID;
import com.krnl32.jupiter.asset.AssetManager;
import com.krnl32.jupiter.asset.ShaderAsset;
import com.krnl32.jupiter.event.EventBus;
import com.krnl32.jupiter.events.window.WindowCloseEvent;
import com.krnl32.jupiter.input.Input;
import com.krnl32.jupiter.renderer.Renderer;
import com.krnl32.jupiter.renderer.Shader;
import com.krnl32.jupiter.renderer.WorldRenderPass;
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
		if(!onInit())
			Logger.critical("Failed to run onInit");

		running = true;

		float lastTime = 0.0f;
		while (running) {
			float time = Timer.getTimeSeconds();
			float dt = time - lastTime;
			lastTime = time;

			if(dt > 0) {
				//System.out.printf("FPS: %f\n", (1/dt));
				onUpdate(dt);

				renderer.beginFrame();
				onRender(dt, renderer);
				renderer.endFrame();
			}

			Input.getInstance().reset();
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

	private void initRenderPass(Renderer renderer) {
		// Setup Default Shader
		AssetID defaultShaderID = AssetManager.getInstance().registerAndLoad("shaders/default", () -> new ShaderAsset("shaders/default_vertex.glsl", "shaders/default_fragment.glsl"));
		if (defaultShaderID == null)
			Logger.critical("Engine Failed to Load Default Engine Shader Asset({})", "shaders/default");

		ShaderAsset shaderAsset = AssetManager.getInstance().getAsset(defaultShaderID);
		if (shaderAsset == null || !shaderAsset.isLoaded())
			Logger.critical("Engine Default Shader Null or Not LOADED");

		Shader shader = shaderAsset.getShader();
		shader.bind();
		shader.setMat4("u_Model", new Matrix4f().identity());

		int[] samplers = new int[32];
		for (int i = 0; i < 32; i++)
			samplers[i] = i;
		shader.setIntArray("u_Textures", samplers);
		shader.unbind();

		renderer.addRenderPass(new WorldRenderPass(shader));
	}
}
