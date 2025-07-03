package com.krnl32.jupiter.renderer;

import com.krnl32.jupiter.event.EventBus;
import com.krnl32.jupiter.events.window.WindowResizeEvent;
import org.joml.Matrix4f;

import java.util.ArrayList;
import java.util.List;

public class TextRenderPass implements RenderPass {
	private final List<RenderTextCommand> renderTextCommands;
	private final TextRenderBatch textRenderBatch;
	private final Shader shader;
	private final Matrix4f projectionMatrix;

	public TextRenderPass(Shader shader, int screenWidth, int screenHeight) {
		this.renderTextCommands = new ArrayList<>();
		this.textRenderBatch = new TextRenderBatch();
		this.shader = shader;
		this.projectionMatrix = new Matrix4f().ortho(0.0f, screenWidth, screenHeight, 0.0f, 0.1f, 100.0f);

		EventBus.getInstance().register(WindowResizeEvent.class, event -> {
			this.projectionMatrix.setOrtho(0.0f, event.getWidth(), event.getHeight(), 0.0f, 0.1f, 100.0f);
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
			int aZ = a.getRenderPacket().getIndex();
			int bZ = b.getRenderPacket().getIndex();
			return Integer.compare(aZ, bZ);
		});

		for (RenderTextCommand cmd : renderTextCommands) {
			textRenderBatch.addQuad(cmd.getTransform(), cmd.getRenderPacket(), cmd.getTextureUV());
		}

		shader.bind();
		textRenderBatch.end();
		shader.unbind();
	}

	@Override
	public void submit(RenderCommand cmd) {
		if (cmd instanceof RenderTextCommand textCommand) {
			renderTextCommands.add(textCommand);
		}
	}
}
