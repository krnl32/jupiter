package com.krnl32.jupiter.renderer;

public interface RenderPass {
	void beginFrame(Camera camera);
	void endFrame();
	void submit(RenderCommand cmd);
}
