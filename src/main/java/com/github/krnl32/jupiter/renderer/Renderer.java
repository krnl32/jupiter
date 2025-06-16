package com.github.krnl32.jupiter.renderer;

import com.github.krnl32.jupiter.asset.AssetManager;
import com.github.krnl32.jupiter.asset.ShaderAsset;
import com.github.krnl32.jupiter.core.Logger;
import com.github.krnl32.jupiter.event.EventBus;
import com.github.krnl32.jupiter.events.window.WindowResizeEvent;
import org.joml.Matrix4f;
import org.joml.Vector4f;

import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glViewport;
import static org.lwjgl.opengl.GL15.*;

public class Renderer {
	private final List<RenderCommand> commandQueue = new ArrayList<>();
	private final SpriteBatch spriteBatch = new SpriteBatch();
	private Camera activeCamera;
	private Shader shader;

	public Renderer() {
		ShaderAsset shaderAsset = AssetManager.getInstance().getAsset("shaders/quad");
		if (shaderAsset == null || !shaderAsset.isLoaded())
			Logger.critical("Renderer Quad Shader Null or Not LOADED");

		shader = shaderAsset.getShader();
		shader.bind();
		shader.setMat4("u_Model", new Matrix4f().identity());

		int[] samplers = new int[32];
		for (int i = 0; i < 32; i++)
			samplers[i] = i;
		shader.setIntArray("u_Textures", samplers);

		EventBus.getInstance().register(WindowResizeEvent.class, event -> {
			setViewPort(0, 0, event.getWidth(), event.getHeight());
		});
	}

	public void beginFrame() {
		shader.bind();
		commandQueue.clear();
		spriteBatch.begin();
	}

	public void endFrame() {
		if (activeCamera != null) {
			shader.setMat4("u_View", activeCamera.getViewMatrix());
			shader.setMat4("u_Projection", activeCamera.getProjectionMatrix());
		}

		for (var cmd: commandQueue)
			cmd.execute(this);
		spriteBatch.end();

		shader.unbind();
	}

	public void submit(RenderCommand cmd) {
		commandQueue.add(cmd);
	}

	public void drawSprite(Matrix4f transform, SpriteRenderData spriteRenderData) {
		spriteBatch.addSprite(transform, spriteRenderData);
	}

	public void setActiveCamera(Camera activeCamera) {
		this.activeCamera = activeCamera;
	}

	public void setClearColor(Vector4f color) {
		glClearColor(color.x, color.y, color.z, color.w);
	}

	public void clear() {
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
	}

	public void setViewPort(int x, int y, int width, int height) {
		glViewport(x, y, width, height);
	}

	public void setDepthTest(boolean state) {
		if (state)
			glEnable(GL_DEPTH_TEST);
		else
			glDisable(GL_DEPTH_TEST);
	}

	public void setAlphaBlending(boolean state) {
		if (state) {
			glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
			glEnable(GL_BLEND);
		} else {
			glDisable(GL_BLEND);
		}
	}
}
