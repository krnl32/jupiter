package com.github.krnl32.jupiter.renderer;

import static org.lwjgl.opengl.GL15.*;

public class VertexBuffer {
	private final int bufferID;
	private VertexBufferLayout layout;

	public VertexBuffer(float[] data, int usage) {
		bufferID = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, bufferID);
		glBufferData(GL_ARRAY_BUFFER, data, usage);
	}

	public void destroy() {
		glDeleteBuffers(bufferID);
	}

	public void bind() {
		glBindBuffer(GL_ARRAY_BUFFER, bufferID);
	}

	public void unbind() {
		glBindBuffer(GL_ARRAY_BUFFER, 0);
	}

	public void setBuffer(float[] data) {
		glBindBuffer(GL_ARRAY_BUFFER, bufferID);
		glBufferSubData(GL_ARRAY_BUFFER, 0, data);
	}

	public VertexBufferLayout getLayout() {
		return layout;
	}

	public void setLayout(VertexBufferLayout layout) {
		this.layout = layout;
	}
}
