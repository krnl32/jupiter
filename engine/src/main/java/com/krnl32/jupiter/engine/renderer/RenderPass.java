package com.krnl32.jupiter.engine.renderer;

public interface RenderPass {
	void beginFrame(Camera camera);
	void endFrame();
	void submit(RenderCommand cmd);
}
