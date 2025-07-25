package com.krnl32.jupiter.engine.renderer;

import static org.lwjgl.opengl.GL15.*;

public class IndexBuffer {
	private final int bufferID;
	private final int size;

	public IndexBuffer(int[] data, int usage) {
		bufferID = glGenBuffers();
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, bufferID);
		glBufferData(GL_ELEMENT_ARRAY_BUFFER, data, usage);
		size = data.length;
	}

	public void destroy() {
		glDeleteBuffers(bufferID);
	}

	public void bind() {
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, bufferID);
	}

	public void unbind() {
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
	}

	public int getSize() {
		return size;
	}
}
