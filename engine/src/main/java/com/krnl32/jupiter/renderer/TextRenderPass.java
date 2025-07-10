package com.krnl32.jupiter.renderer;

import com.krnl32.jupiter.event.EventBus;
import com.krnl32.jupiter.events.scene.ViewportResizeEvent;
import org.joml.Matrix4f;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.lwjgl.opengl.GL11.*;

public class TextRenderPass implements RenderPass {
	private final List<RenderTextCommand> renderTextCommands;
	private final TextRenderBatch textRenderBatch;
	private final Shader shader;
	private int screenWidth, screenHeight;
	private final Matrix4f projectionMatrix;

	public TextRenderPass(Shader shader, int screenWidth, int screenHeight) {
		this.renderTextCommands = new ArrayList<>();
		this.textRenderBatch = new TextRenderBatch();
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
		renderTextCommands.clear();
		textRenderBatch.begin();

		shader.bind();
		if (camera != null) {
			shader.setMat4("u_View", camera.getViewMatrix());
		}
		shader.setMat4("u_Projection", projectionMatrix);
		shader.unbind();
	}

	@Override
	public void endFrame() {
		renderTextCommands.sort((a, b) -> {
			int cmp = ClipRect.compareClipRect(a.getClipRect(), b.getClipRect());
			if (cmp != 0)
				return cmp;
			int aZ = a.getRenderPacket().getIndex();
			int bZ = b.getRenderPacket().getIndex();
			return Integer.compare(aZ, bZ);
		});

		ClipRect currentClip = null;
		for (RenderTextCommand cmd : renderTextCommands) {
			ClipRect clipRect = cmd.getClipRect();

			// If ClipRect Changed, Flush Batch
			if (!Objects.equals(currentClip, clipRect)) {
				shader.bind();
				textRenderBatch.end();
				shader.unbind();

				if (clipRect != null) {
					glEnable(GL_SCISSOR_TEST);
					glScissor(clipRect.getX(), (screenHeight - clipRect.getY() - clipRect.getHeight()), clipRect.getWidth(), clipRect.getHeight());
				} else {
					glDisable(GL_SCISSOR_TEST);
				}

				textRenderBatch.begin();
				currentClip = clipRect;
			}

			textRenderBatch.addQuad(cmd.getTransform(), cmd.getRenderPacket(), cmd.getTextureUV());
		}

		shader.bind();
		textRenderBatch.end();
		shader.unbind();

		glDisable(GL_SCISSOR_TEST);
	}

	@Override
	public void submit(RenderCommand cmd) {
		if (cmd instanceof RenderTextCommand textCommand) {
			renderTextCommands.add(textCommand);
		}
	}
}
