package com.krnl32.jupiter.renderer;

import com.krnl32.jupiter.event.EventBus;
import com.krnl32.jupiter.events.window.WindowResizeEvent;
import org.joml.Vector2f;
import org.joml.Vector4f;

import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glViewport;
import static org.lwjgl.opengl.GL15.*;

public class Renderer {
	private final List<RenderPass> renderPasses;
	private final Vector2f viewport;
	private Camera activeCamera;

	public Renderer() {
		renderPasses = new ArrayList<>();
		viewport = new Vector2f();

		EventBus.getInstance().register(WindowResizeEvent.class, event -> {
			viewport.set(event.getWidth(), event.getHeight());
			setViewPort(0, 0, event.getWidth(), event.getHeight());
		});
	}

	public void beginFrame() {
		for (RenderPass renderPass : renderPasses)
			renderPass.beginFrame(activeCamera);
	}

	public void endFrame() {
		for (RenderPass renderPass : renderPasses)
			renderPass.endFrame();
	}

	public void submit(RenderCommand cmd) {
		for (RenderPass renderPass : renderPasses)
			renderPass.submit(cmd);
	}

	public void addRenderPass(RenderPass renderPass) {
		renderPasses.add(renderPass);
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

	public Vector2f getViewport() {
		return viewport;
	}

	public void setViewPort(int x, int y, int width, int height) {
		viewport.set(width, height);
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
