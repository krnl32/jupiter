package com.krnl32.jupiter.engine.renderer.renderpass;

import com.krnl32.jupiter.engine.renderer.Camera;
import com.krnl32.jupiter.engine.renderer.rendercommand.RenderCommand;

public interface RenderPass {
	void beginFrame(Camera camera);
	void endFrame();
	void submit(RenderCommand cmd);
}
