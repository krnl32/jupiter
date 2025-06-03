package com.github.krnl32.jupiter.renderer;

import org.joml.Vector4f;

public class RenderSpriteCommand implements RenderCommand {
	private float x;
	private float y;
	private int width;
	private int height;
	private int index;
	private Vector4f color;

	public RenderSpriteCommand(float x, float y, int width, int height, int index, Vector4f color) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.index = index;
		this.color = color;
	}

	@Override
	public void execute(Renderer renderer) {
		//renderer bind shader/camera stuff (Maybe Camera not Here)
		//renderer draw quad
		renderer.drawRectange((int)x, (int)y, width, height);
	}
}
