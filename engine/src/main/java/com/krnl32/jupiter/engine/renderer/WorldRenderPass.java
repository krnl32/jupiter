package com.krnl32.jupiter.engine.renderer;

import java.util.ArrayList;
import java.util.List;

public class WorldRenderPass implements RenderPass {
	private final List<RenderSpriteCommand> renderSpriteCommands;
	private final SpriteRenderBatch spriteRenderBatch;
	private final Shader shader;

	public WorldRenderPass(Shader shader) {
		this.renderSpriteCommands = new ArrayList<>();
		this.spriteRenderBatch = new SpriteRenderBatch();
		this.shader = shader;
	}

	@Override
	public void beginFrame(Camera camera) {
		renderSpriteCommands.clear();
		spriteRenderBatch.begin();

		shader.bind();
		if (camera != null) {
			shader.setMat4("u_View", camera.getViewMatrix());
			shader.setMat4("u_Projection", camera.getProjectionMatrix());
		}
		shader.unbind();
	}

	@Override
	public void endFrame() {
		renderSpriteCommands.sort((a, b) -> {
			int aZ = a.getRenderPacket().getIndex();
			int bZ = b.getRenderPacket().getIndex();
			return Integer.compare(aZ, bZ);
		});

		for (RenderSpriteCommand cmd : renderSpriteCommands) {
			spriteRenderBatch.addQuad(cmd.getTransform(), cmd.getRenderPacket(), cmd.getTextureUV());
		}

		shader.bind();
		spriteRenderBatch.end();
		shader.unbind();
	}

	@Override
	public void submit(RenderCommand cmd) {
		if (cmd instanceof RenderSpriteCommand spriteCommand) {
			renderSpriteCommands.add(spriteCommand);
		}
	}
}
