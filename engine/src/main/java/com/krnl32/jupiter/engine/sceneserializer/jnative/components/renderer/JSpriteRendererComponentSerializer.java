package com.krnl32.jupiter.engine.sceneserializer.jnative.components.renderer;

import com.krnl32.jupiter.engine.asset.handle.AssetId;
import com.krnl32.jupiter.engine.components.renderer.SpriteRendererComponent;
import com.krnl32.jupiter.engine.core.Logger;
import com.krnl32.jupiter.engine.project.ProjectContext;
import com.krnl32.jupiter.engine.sceneserializer.ComponentSerializer;
import com.krnl32.jupiter.engine.sceneserializer.jnative.utility.JSerializerUtility;
import com.krnl32.jupiter.engine.sceneserializer.resolvers.EntityResolver;
import org.joml.Vector4f;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.UUID;

/*
 * == SpriteRendererComponent (size: 68 Bytes) ==
 * 4  Bytes	Index			uint32
 * 16 Bytes	Color			float32 * 4
 * 16 Bytes	TextureAssetID	UUID
 * 32 Bytes	TextureUV		float32 * 8
 */
public class JSpriteRendererComponentSerializer implements ComponentSerializer<SpriteRendererComponent, byte[]> {
	private static final int SIZE = 68;
	private static final ByteOrder ENDIANNESS = ByteOrder.LITTLE_ENDIAN;

	@Override
	public byte[] serialize(SpriteRendererComponent component) {
		UUID textureAssetUUID = component.textureAssetId != null
			? component.textureAssetId.getId()
			: new UUID(0L, 0L);

		ByteBuffer buffer = ByteBuffer.allocate(SIZE).order(ENDIANNESS);
		JSerializerUtility.serializeInt(buffer, component.index);
		JSerializerUtility.serializeVector4f(buffer, component.color);
		JSerializerUtility.serializeUUID(buffer, textureAssetUUID);
		JSerializerUtility.serializeFloatArray(buffer, component.textureUV);

		return buffer.array();
	}

	@Override
	public SpriteRendererComponent deserialize(byte[] data, EntityResolver resolver) {
		if (data.length != SIZE) {
			Logger.error("JSpriteRendererComponentSerializer Deserialize failed, Corrupted Data, Size({})", data.length);
			return null;
		}

		ByteBuffer buffer = ByteBuffer.wrap(data).order(ENDIANNESS);
		int index = JSerializerUtility.deserializeInt(buffer);
		Vector4f color = JSerializerUtility.deserializeVector4f(buffer);
		UUID textureAssetUUID = JSerializerUtility.deserializeUUID(buffer);
		float[] textureUV = JSerializerUtility.deserializeFloatArray(buffer, 8);

		AssetId textureAssetId = null;

		if (!textureAssetUUID.equals(new UUID(0L, 0L))) {
			textureAssetId = new AssetId(textureAssetUUID);

			if (!ProjectContext.getInstance().getAssetManager().isAssetRegistered(textureAssetId)) {
				Logger.error("JSpriteRendererComponentSerializer Deserialize Failed to Load Texture({}) Not Registered", textureAssetId);
				return null;
			}
		}

		return new SpriteRendererComponent(
			index,
			color,
			textureAssetId,
			textureUV
		);
	}
}
