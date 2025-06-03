package com.github.krnl32.jupiter.renderer;

import java.util.ArrayList;
import java.util.List;

public class Renderer {
	private final List<RenderCommand> commandQueue = new ArrayList<>();

	public void beginFrame() {
		// clear screen
		commandQueue.clear();
	}

	public void endFrame() {
		for (var cmd: commandQueue)
			cmd.execute(this);
	}

	public void submit(RenderCommand cmd) {
		commandQueue.add(cmd);
	}

	public void drawRectange(int x, int y, int width, int height) {

	}
}
