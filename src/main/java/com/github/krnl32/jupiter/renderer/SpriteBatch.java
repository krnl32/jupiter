package com.github.krnl32.jupiter.renderer;

import org.joml.Vector3f;

public class SpriteBatch {
	// data for vao, vbo, etc

	public void begin() {
		// prepare
		// (clear vertex/index array data, etc...)
		// Track Stats (quadCount, etc...)
	}

	public void addSprite(Vector3f position, Sprite sprite) {
		// Add to Data(arrays,vectors,etc...)
		// if spriteCount > MaxSpriteCount -> Render(); Begin();
	}

	public void render() {
		// render all(Upload Data to GPU, bind VAO, VBO, etc..., Draw Call)
	}
}
