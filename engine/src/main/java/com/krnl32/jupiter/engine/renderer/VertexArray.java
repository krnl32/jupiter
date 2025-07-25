package com.krnl32.jupiter.engine.renderer;

import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL30.*;

public class VertexArray {
	private final int arrayID;
	private final List<VertexBuffer> vertexBuffers = new ArrayList<>();
	private IndexBuffer indexBuffer;

	public VertexArray() {
		arrayID = glGenVertexArrays();
	}

	public void destroy() {
		glDeleteVertexArrays(arrayID);
	}

	public void bind() {
		glBindVertexArray(arrayID);
	}

	public void unbind() {
		glBindVertexArray(0);
	}

	public List<VertexBuffer> getVertexBuffers() {
		return vertexBuffers;
	}

	public void addVertexBuffer(VertexBuffer vertexBuffer) {
		glBindVertexArray(arrayID);
		vertexBuffer.bind();

		for (int i = 0; i < vertexBuffer.getLayout().getAttributes().size(); i++) {
			VertexBufferAttribute attrib = vertexBuffer.getLayout().getAttributes().get(i);
			glVertexAttribPointer(i, attrib.getSize(), attrib.getType().getNativeType(), attrib.isNormalized(), vertexBuffer.getLayout().getStride(), attrib.getOffset());
			glEnableVertexAttribArray(i);
		}

		vertexBuffers.add(vertexBuffer);
	}

	public IndexBuffer getIndexBuffer() {
		return indexBuffer;
	}

	public void setIndexBuffer(IndexBuffer indexBuffer) {
		glBindVertexArray(arrayID);
		indexBuffer.bind();
		this.indexBuffer = indexBuffer;
	}
}
