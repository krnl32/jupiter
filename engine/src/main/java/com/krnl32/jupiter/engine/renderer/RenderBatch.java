package com.krnl32.jupiter.engine.renderer;

import org.joml.Matrix4f;

import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.GL_DYNAMIC_DRAW;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.system.MemoryUtil.NULL;

public abstract class RenderBatch {
	protected final int MAX_QUADS = 1000;
	protected final int VERTICES_PER_QUAD = 4;
	protected final int INDICES_PER_QUAD = 6;
	protected final int VERTEX_SIZE = 10;
	protected final int MAX_TEXTURE_SLOTS = 32;

	protected final VertexArray vertexArray;
	protected final List<Texture2D> textures = new ArrayList<>(MAX_TEXTURE_SLOTS);
	protected final Texture2D defaultTexture;
	protected float[] vertices;
	protected int quadCount;
	protected int vertexIndex;

	public RenderBatch() {
		vertices = new float[MAX_QUADS * VERTICES_PER_QUAD * VERTEX_SIZE];
		int[] indices = generateIndices();

		vertexArray = new VertexArray();
		vertexArray.bind();

		VertexBuffer vb = new VertexBuffer(vertices, GL_DYNAMIC_DRAW);
		VertexBufferLayout layout = new VertexBufferLayout(
			new VertexBufferAttribute("a_Position", 3, ShaderDataType.Float, false, 0),
			new VertexBufferAttribute("a_Color", 4, ShaderDataType.Float, false, 0),
			new VertexBufferAttribute("a_TextureUV", 2, ShaderDataType.Float, false, 0),
			new VertexBufferAttribute("a_TextureID", 1, ShaderDataType.Float, false, 0)
		);
		vb.setLayout(layout);

		IndexBuffer ib = new IndexBuffer(indices, GL_STATIC_DRAW);
		vertexArray.addVertexBuffer(vb);
		vertexArray.setIndexBuffer(ib);
		vertexArray.unbind();

		defaultTexture = new Texture2D(1, 1, 4);
		defaultTexture.setBuffer(new int[] { 0xffffffff });
	}

	public abstract void addQuad(Matrix4f transform, RenderPacket renderPacket, float[] uvs);

	public void begin() {
		quadCount = 0;
		vertexIndex = 0;
		textures.clear();
		textures.add(defaultTexture);
	}

	public void end() {
		if (quadCount == 0) return;

		for (int i = 0; i < textures.size(); i++)
			textures.get(i).bind(i);

		vertexArray.bind();
		vertexArray.getVertexBuffers().get(0).setBuffer(vertices, 0, quadCount * VERTICES_PER_QUAD * VERTEX_SIZE);
		glDrawElements(GL_TRIANGLES, quadCount * INDICES_PER_QUAD, GL_UNSIGNED_INT, NULL);
		vertexArray.unbind();
	}

	protected int getTextureSlot(Texture2D texture) {
		for (int i = 0; i < textures.size(); i++) {
			if (textures.get(i).getTextureID() == texture.getTextureID()) {
				return i;
			}
		}
		if (textures.size() >= MAX_TEXTURE_SLOTS) {
			end();
			begin();
		}
		textures.add(texture);
		return textures.size() - 1;
	}

	private int[] generateIndices() {
		int[] indices = new int[MAX_QUADS * INDICES_PER_QUAD];
		int offset = 0;
		for (int i = 0; i < indices.length; i += 6) {
			indices[i + 0] = offset + 0;
			indices[i + 1] = offset + 1;
			indices[i + 2] = offset + 2;
			indices[i + 3] = offset + 2;
			indices[i + 4] = offset + 3;
			indices[i + 5] = offset + 0;
			offset += 4;
		}
		return indices;
	}
}
