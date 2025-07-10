package com.krnl32.jupiter.renderer;

import com.krnl32.jupiter.event.EventBus;
import com.krnl32.jupiter.events.scene.ViewportResizeEvent;
import org.joml.Matrix4f;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.lwjgl.opengl.GL11.*;

public class UIRenderPass implements RenderPass {
	private final List<RenderUICommand> renderUICommands;
	private final UIRenderBatch uiRenderBatch;
	private final Shader shader;
	private int screenWidth, screenHeight;
	private final Matrix4f projectionMatrix;

	public UIRenderPass(Shader shader, int screenWidth, int screenHeight) {
		this.renderUICommands = new ArrayList<>();
		this.uiRenderBatch = new UIRenderBatch();
		this.shader = shader;
		this.screenWidth = screenWidth;
		this.screenHeight = screenHeight;
		this.projectionMatrix = new Matrix4f().ortho(0.0f, screenWidth, screenHeight, 0.0f, 0.1f, 100.0f);

		EventBus.getInstance().register(ViewportResizeEvent.class, event -> {
			this.screenWidth = event.getWidth();
			this.screenHeight = event.getHeight();
			this.projectionMatrix.setOrtho(0.0f, screenWidth, screenHeight, 0.0f, 0.1f, 100.0f);
		});
	}

	@Override
	public void beginFrame(Camera camera) {
		renderUICommands.clear();
		uiRenderBatch.begin();

		shader.bind();
		if (camera != null) {
			shader.setMat4("u_View", camera.getViewMatrix());
		}
		shader.setMat4("u_Projection", projectionMatrix);
		shader.unbind();
	}

	@Override
	public void endFrame() {
		renderUICommands.sort((a, b) -> {
			int cmp = ClipRect.compareClipRect(a.getClipRect(), b.getClipRect());
			if (cmp != 0)
				return cmp;
			int aZ = a.getRenderPacket().getIndex();
			int bZ = b.getRenderPacket().getIndex();
			return Integer.compare(aZ, bZ);
		});

		ClipRect currentClip = null;
		for (RenderUICommand cmd : renderUICommands) {
			ClipRect clipRect = cmd.getClipRect();

			// If ClipRect Changed, Flush Batch
			if (!Objects.equals(currentClip, clipRect)) {
				shader.bind();
				uiRenderBatch.end();
				shader.unbind();

				if (clipRect != null) {
					glEnable(GL_SCISSOR_TEST);
					glScissor(clipRect.getX(), (screenHeight - clipRect.getY() - clipRect.getHeight()), clipRect.getWidth(), clipRect.getHeight());
				} else {
					glDisable(GL_SCISSOR_TEST);
				}

				uiRenderBatch.begin();
				currentClip = clipRect;
			}

			uiRenderBatch.addQuad(cmd.getTransform(), cmd.getRenderPacket(), cmd.getTextureUV());
		}

		shader.bind();
		uiRenderBatch.end();
		shader.unbind();

		glDisable(GL_SCISSOR_TEST);
	}

	@Override
	public void submit(RenderCommand cmd) {
		if (cmd instanceof RenderUICommand uiCommand) {
			renderUICommands.add(uiCommand);
		}
	}
}
