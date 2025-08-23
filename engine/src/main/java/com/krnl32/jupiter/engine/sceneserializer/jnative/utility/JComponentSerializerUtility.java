package com.krnl32.jupiter.engine.sceneserializer.jnative.utility;

import com.krnl32.jupiter.engine.asset.handle.AssetId;
import com.krnl32.jupiter.engine.core.Logger;
import com.krnl32.jupiter.engine.model.Sprite;
import com.krnl32.jupiter.engine.project.ProjectContext;
import com.krnl32.jupiter.engine.script.ScriptInstance;
import org.joml.Vector4f;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.UUID;

public class JComponentSerializerUtility {
	private static final int SCRIPT_INSTANCE_SIZE = 17;
	private static final int SPRITE_SIZE = 68;
	private static final ByteOrder ENDIANNESS = ByteOrder.LITTLE_ENDIAN;

	/*
	 * == ScriptInstance (size: 17 Bytes) ==
	 * 16 Bytes	AssetID		UUID
	 * 1  Byte	Disabled	uint8
	 */
	public static byte[] serializeScriptInstance(ScriptInstance scriptInstance) {
		if (!ProjectContext.getInstance().getAssetManager().isAssetRegistered(scriptInstance.getScriptAssetId())) {
			Logger.error("JComponentSerializerUtility serializeScriptInstance Failed, Script AssetId({}) Not Registered", scriptInstance.getScriptAssetId());
			return null;
		}

		ByteBuffer buffer = ByteBuffer.allocate(SCRIPT_INSTANCE_SIZE).order(ENDIANNESS);
		JSerializerUtility.serializeUUID(buffer, scriptInstance.getScriptAssetId().getId());
		JSerializerUtility.serializeBool(buffer, scriptInstance.isDisabled());

		return buffer.array();
	}

	public static ScriptInstance deserializeScriptInstance(byte[] data) {
		if (data.length != SCRIPT_INSTANCE_SIZE) {
			Logger.error("JComponentSerializerUtility deserializeScriptInstance failed, Corrupted Data, Size({})", data.length);
			return null;
		}

		ByteBuffer buffer = ByteBuffer.wrap(data).order(ENDIANNESS);
		UUID scriptAssetUUID = JSerializerUtility.deserializeUUID(buffer);
		boolean scriptDisabled = JSerializerUtility.deserializeBool(buffer);

		AssetId scriptAssetId = new AssetId(scriptAssetUUID);

		if (!ProjectContext.getInstance().getAssetManager().isAssetRegistered(scriptAssetId)) {
			Logger.error("JComponentSerializerUtility deserializeScriptInstance Failed, Script AssetId({}) Not Registered", scriptAssetId);
			return null;
		}

		return new ScriptInstance(scriptAssetId, scriptDisabled);
	}

	/*
	 * == Sprite (size: 68 Bytes) ==
	 * 4  Bytes	Index			uint32
	 * 16 Bytes	Color			float32 * 4
	 * 16 Bytes	TextureAssetID	UUID
	 * 32 Bytes	TextureUV		float32 * 8
	 */
	public static byte[] serializeSprite(Sprite sprite) {
		UUID textureAssetUUID = sprite.getTextureAssetId() != null
			? sprite.getTextureAssetId().getId()
			: new UUID(0L, 0L);

		ByteBuffer buffer = ByteBuffer.allocate(SPRITE_SIZE).order(ENDIANNESS);
		JSerializerUtility.serializeInt(buffer, sprite.getIndex());
		JSerializerUtility.serializeVector4f(buffer, sprite.getColor());
		JSerializerUtility.serializeUUID(buffer, textureAssetUUID);
		JSerializerUtility.serializeFloatArray(buffer, sprite.getTextureUV());

		return buffer.array();
	}

	public static Sprite deserializeSprite(byte[] data) {
		if (data.length != SPRITE_SIZE) {
			Logger.error("JComponentSerializerUtility deserializeSprite failed, Corrupted Data, Size({})", data.length);
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
				Logger.error("JComponentSerializerUtility deserializeSprite Failed to Load Texture({}) Not Registered", textureAssetId);
				return null;
			}
		}

		return new Sprite(
			index,
			color,
			textureAssetId,
			textureUV
		);
	}
}
