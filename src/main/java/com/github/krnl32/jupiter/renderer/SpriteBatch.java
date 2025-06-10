package com.github.krnl32.jupiter.renderer;

import com.github.krnl32.jupiter.asset.AssetManager;
import com.github.krnl32.jupiter.asset.TextureAsset;
import org.joml.Vector3f;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.system.MemoryUtil.NULL;

public class SpriteBatch {
	private final int MAX_QUAD_SIZE = 1000;
	private final int MAX_QUAD_VERTEX_SIZE = MAX_QUAD_SIZE * 4;
	private final int MAX_QUAD_INDEX_SIZE = MAX_QUAD_SIZE * 6;
	private final int MAX_VERTICES_PER_QUAD = 4;
	private final int MAX_VERTEX_SIZE = 10;
	private final int MAX_TEXTURE_SLOTS = 32;

	private final VertexArray vertexArray;
	private final List<Texture2D> textures = new ArrayList<>(MAX_TEXTURE_SLOTS);
	private final float[] textureUV;
	private final Texture2D defaultTexture;

	private float[] quadVertices;
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
			new VertexBufferAttribute("a_Color", 4, ShaderDataType.Float, false, 0),
			new VertexBufferAttribute("a_TextureUV", 2, ShaderDataType.Float, false, 0),
			new VertexBufferAttribute("a_TextureID", 1, ShaderDataType.Float, false, 0)
		);
		vertexBuffer.setLayout(layout);

		IndexBuffer indexBuffer = new IndexBuffer(quadIndices, GL_STATIC_DRAW);

		vertexArray.addVertexBuffer(vertexBuffer);
		vertexArray.setIndexBuffer(indexBuffer);
		vertexArray.unbind();

		textureUV = new float[] {
			0.0f, 0.0f, // BL
			1.0f, 0.0f, // BR
			1.0f, 1.0f, // TR
			0.0f, 1.0f, // TL
		};

		defaultTexture = new Texture2D(1, 1, 4);
		defaultTexture.setBuffer(new int[]{0xffffffff});
	}

	public void begin() {
		quadCount = 0;
		quadIndex = 0;
		textures.clear();
		textures.add(defaultTexture);
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
			{ position.x - halfWidth, position.y - halfHeight, position.z }, // BL
			{ position.x + halfWidth, position.y - halfHeight, position.z }, // BR
			{ position.x + halfWidth, position.y + halfHeight, position.z }, // TR
			{ position.x - halfWidth, position.y + halfHeight, position.z }, // TL
		};

		TextureAsset textureAsset = AssetManager.getInstance().getAsset() // Fix?

		Texture2D texture = sprite.getTexture() != null ? sprite.getTexture() : defaultTexture;
		int textureSlot = getTextureSlot(texture);

		for (int i = 0; i < 4; i++) {
			quadVertices[quadIndex++] = positions[i][0];
			quadVertices[quadIndex++] = positions[i][1];
			quadVertices[quadIndex++] = positions[i][2];
			quadVertices[quadIndex++] = sprite.getColor().x;
			quadVertices[quadIndex++] = sprite.getColor().y;
			quadVertices[quadIndex++] = sprite.getColor().z;
			quadVertices[quadIndex++] = sprite.getColor().w;
			quadVertices[quadIndex++] = textureUV[i * 2];
			quadVertices[quadIndex++] = textureUV[i * 2 + 1];
			quadVertices[quadIndex++] = textureSlot;
		}

		quadCount++;
	}

	public void end() {
		if (quadCount == 0)
			return;

		for (int i = 0; i < textures.size(); i++)
			textures.get(i).bind(i);

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

	private int getTextureSlot(Texture2D texture) {
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
}
