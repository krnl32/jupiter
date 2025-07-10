package com.krnl32.jupiter.renderer;

import com.krnl32.jupiter.event.EventBus;
import com.krnl32.jupiter.events.scene.ViewportResizeEvent;
import org.joml.Vector2f;
import org.joml.Vector4f;

import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glViewport;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL30.GL_FRAMEBUFFER;
import static org.lwjgl.opengl.GL30.glBindFramebuffer;

public class Renderer {
	private final List<RenderPass> renderPasses;
	private final Vector2f viewport;
	private final Vector4f clearColor;
	private Camera activeCamera;
	private Framebuffer framebuffer;

	public Renderer() {
		renderPasses = new ArrayList<>();
		viewport = new Vector2f();
		clearColor = new Vector4f(1.0f, 1.0f, 1.0f, 1.0f);

		EventBus.getInstance().register(ViewportResizeEvent.class, event -> {
			if (framebuffer != null) {
				framebuffer.resize(event.getWidth(), event.getHeight());
			}
			setViewPort(0, 0, event.getWidth(), event.getHeight());
		});
	}

	public void beginFrame() {
		if (framebuffer != null) {
			framebuffer.bind();
			glViewport(0, 0, framebuffer.getWidth(), framebuffer.getHeight());
		} else {
			glBindFramebuffer(GL_FRAMEBUFFER, 0);
		}

		for (RenderPass renderPass : renderPasses)
			renderPass.beginFrame(activeCamera);
	}

	public void endFrame() {
		setClearColor(clearColor);
		clear();

		for (RenderPass renderPass : renderPasses)
			renderPass.endFrame();

		if (framebuffer != null) {
			framebuffer.unbind();
			glViewport(0, 0, (int) viewport.x, (int) viewport.y);
		}
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
		clearColor.set(color);
		glClearColor(clearColor.x, clearColor.y, clearColor.z, clearColor.w);
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

	public void setFramebuffer(Framebuffer framebuffer) {
		this.framebuffer = framebuffer;
	}
}
