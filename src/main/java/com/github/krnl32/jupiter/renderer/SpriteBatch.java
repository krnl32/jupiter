package com.github.krnl32.jupiter.renderer;

import org.joml.Vector3f;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.system.MemoryUtil.NULL;

public class SpriteBatch {
	private final int MAX_QUAD_SIZE = 1000;
	private final int MAX_QUAD_VERTEX_SIZE = MAX_QUAD_SIZE * 4;
	private final int MAX_QUAD_INDEX_SIZE = MAX_QUAD_SIZE * 6;
	private final int MAX_VERTICES_PER_QUAD = 4;
	private final int MAX_VERTEX_SIZE = 7; // x,y,z,r,g,b,a
	private final int MAX_TEXTURE_SIZE = 32;

	private float[] quadVertices;
	private final VertexArray vertexArray;
	private int quadCount;
	private int quadIndex;

	public SpriteBatch() {
		quadVertices = new float[MAX_QUAD_VERTEX_SIZE * MAX_VERTEX_SIZE];
		int[] quadIndices = generateQuadIndices(MAX_QUAD_INDEX_SIZE);

		vertexArray = new VertexArray();
		vertexArray.bind();

		VertexBuffer vertexBuffer = new VertexBuffer(quadVertices, GL_STATIC_DRAW);
		VertexBufferLayout layout = new VertexBufferLayout(
			new VertexBufferAttribute("a_Position", 3, ShaderDataType.Float, false, 0),
			new VertexBufferAttribute("a_Color", 4, ShaderDataType.Float, false, 0)
		);
		vertexBuffer.setLayout(layout);

		IndexBuffer indexBuffer = new IndexBuffer(quadIndices, GL_STATIC_DRAW);

		vertexArray.addVertexBuffer(vertexBuffer);
		vertexArray.setIndexBuffer(indexBuffer);
		vertexArray.unbind();
	}

	public void begin() {
		// prepare
		// (clear vertex/index array data, etc...)
		// Track Stats (quadCount, etc...)
		quadCount = 0;
		quadIndex = 0;
	}

	public void addSprite(Vector3f position, Sprite sprite) {
		if (quadCount >= MAX_QUAD_SIZE) {
			end();
			begin();
		}

		// Center around Position
		float halfWidth = sprite.getWidth() / 2.0f;
		float halfHeight = sprite.getHeight() / 2.0f;

		float[][] positions = new float[][] {
			{ position.x - halfWidth, position.y - halfHeight, position.z },
			{ position.x + halfWidth, position.y - halfHeight, position.z },
			{ position.x + halfWidth, position.y + halfHeight, position.z },
			{ position.x - halfWidth, position.y + halfHeight, position.z }
		};

		for (int i = 0; i < 4; i++) {
			quadVertices[quadIndex++] = positions[i][0];
			quadVertices[quadIndex++] = positions[i][1];
			quadVertices[quadIndex++] = positions[i][2];
			quadVertices[quadIndex++] = sprite.getColor().x;
			quadVertices[quadIndex++] = sprite.getColor().y;
			quadVertices[quadIndex++] = sprite.getColor().z;
			quadVertices[quadIndex++] = sprite.getColor().w;
		}

		quadCount++;
	}

	public void end() {
		if (quadCount == 0)
			return;

		vertexArray.bind();
		vertexArray.getVertexBuffers().get(0).setBuffer(quadVertices, 0, quadCount * MAX_VERTICES_PER_QUAD * MAX_VERTEX_SIZE);
		glDrawElements(GL_TRIANGLES, vertexArray.getIndexBuffer().getSize(), GL_UNSIGNED_INT, NULL);
		vertexArray.unbind();
	}

	private int[] generateQuadIndices(int maxIndexSize) {
		int[] indices = new int[maxIndexSize];
		int offset = 0;
		for (int i = 0; i < maxIndexSize; i += 6) {
			indices[i] = offset;
			indices[i + 1] = 1 + offset;
			indices[i + 2] = 2 + offset;
			indices[i + 3] = 2 + offset;
			indices[i + 4] = 3 + offset;
			indices[i + 5] = offset;
			offset += 4;
		}
		return indices;
	}
}
