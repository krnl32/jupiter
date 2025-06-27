package com.krnl32.jupiter.renderer;

import org.joml.Matrix4f;
import org.joml.Vector4f;

public class SpriteRenderBatch extends RenderBatch {

	@Override
	public void addQuad(Matrix4f transform, RenderPacket renderPacket, float[] uvs) {
		if (quadCount >= MAX_QUADS) {
			end();
			begin();
		}

		// Centered
		Vector4f[] localPositions = new Vector4f[] {
			new Vector4f(-0.5f, -0.5f, 1.0f, 1.0f), // BL
			new Vector4f( 0.5f, -0.5f, 1.0f, 1.0f), // BR
			new Vector4f( 0.5f,  0.5f, 1.0f, 1.0f), // TR
			new Vector4f(-0.5f,  0.5f, 1.0f, 1.0f)  // TL
		};

		int textureSlot = getTextureSlot(renderPacket.getTexture() != null ? renderPacket.getTexture() : defaultTexture);

		for (int i = 0; i < 4; i++) {
			Vector4f worldPos = new Vector4f();
			transform.transform(localPositions[i], worldPos);

			vertices[vertexIndex++] = worldPos.x;
			vertices[vertexIndex++] = worldPos.y;
			vertices[vertexIndex++] = worldPos.z;

			vertices[vertexIndex++] = renderPacket.getColor().x;
			vertices[vertexIndex++] = renderPacket.getColor().y;
			vertices[vertexIndex++] = renderPacket.getColor().z;
			vertices[vertexIndex++] = renderPacket.getColor().w;

			vertices[vertexIndex++] = uvs[i * 2];
			vertices[vertexIndex++] = uvs[i * 2 + 1];
			vertices[vertexIndex++] = textureSlot;
		}

		quadCount++;
	}
}
