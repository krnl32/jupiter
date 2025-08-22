package com.krnl32.jupiter.engine.asset.serializers;

import com.krnl32.jupiter.engine.asset.serializer.AssetSerializer;
import com.krnl32.jupiter.engine.asset.types.SceneAsset;
import com.krnl32.jupiter.engine.core.Logger;
import com.krnl32.jupiter.engine.scene.Scene;
import com.krnl32.jupiter.engine.scene.SceneSettings;
import com.krnl32.jupiter.engine.sceneserializer.SceneSerializer;
import com.krnl32.jupiter.engine.sceneserializer.jnative.model.JScene;
import com.krnl32.jupiter.engine.sceneserializer.jnative.model.JSceneHeader;
import com.krnl32.jupiter.engine.sceneserializer.jnative.utility.JSceneSerializerUtility;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;

/**
 * Jupiter .jscene Binary Format (v1.0)
 *
 * HEADER (size: 16 bytes)
 * ------------------------------------------
 * 4 Bytes  Magic			"JSCN"
 * 1 Byte   Version			0x01
 * 1 Byte	Endianness		(0x1 = Little Endian, 0x2 = Big Endian)
 * 1 Byte	CompressionType	(0x0=None)
 * 9 Bytes	RESERVED
 *
 * SETTINGS (size: 13 Bytes)
 * ------------------------------------------
 * 1 Byte 	Physics Enabled	(0x0=False, 0x1=True)
 * 12 Bytes	Gravity			float32 x 3
 *
 *
 * =============== SCENE DATA ===============
 * METADATA (size: 5+N Bytes)
 * ------------------------------------------
 * 1 Byte	SceneNameLength	uint8
 * N Bytes	SceneName
 * 4 Bytes  EntityCount		uint32
 *
 * ENTITY (size: 20 Bytes) REPEATED LIST
 * ------------------------------------------
 * 16	Bytes	UUID			uint128
 * 4	Bytes	ComponentCount	uint32
 *
 * COMPONENT (size: 8+N Bytes)
 * ------------------------------------------
 * 4 Bytes 	ComponentType	uint32
 * 4 Bytes	ComponentSize	uint32
 * N Bytes	ComponentData
 */
public class JSceneAssetSerializer implements AssetSerializer<SceneAsset, JScene> {
	private static final byte[] MAGIC = new byte[]{'J', 'S', 'C', 'N'};
	private static final byte VERSION = 0x1;
	private static final byte ENDIANNESS = 0x1;
	private static final byte COMPRESSION_TYPE = 0x0;
	private static final int SCENE_SETTINGS_SIZE_BYTES = 13;

	private final SceneSerializer<byte[]> sceneSerializer;

	public JSceneAssetSerializer(SceneSerializer<byte[]> sceneSerializer) {
		this.sceneSerializer = sceneSerializer;
	}

	@Override
	public byte[] serialize(SceneAsset asset) {
		ByteArrayOutputStream dataStream = new ByteArrayOutputStream();

		try {
			// Header
			dataStream.write(MAGIC);
			dataStream.write(VERSION);
			dataStream.write(ENDIANNESS);
			dataStream.write(COMPRESSION_TYPE);
			dataStream.write(new byte[9]);

			// Scene SETTINGS
			byte[] sceneSettingsData = JSceneSerializerUtility.serializerSceneSettings(asset.getSettings());
			dataStream.write(sceneSettingsData);

			// Scene DATA
			byte[] sceneData = sceneSerializer.serialize(asset.getScene());
			dataStream.write(sceneData);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		return dataStream.toByteArray();
	}

	@Override
	public JScene deserialize(byte[] data) {
		ByteArrayInputStream dataStream = new ByteArrayInputStream(data);

		// Validate Magic
		byte[] magic = new byte[4];
		dataStream.read(magic, 0, 4);

		if (!Arrays.equals(magic, MAGIC)) {
			Logger.error("JSceneAssetSerializer Failed to Deserialize Invalid Magic Header");
			return null;
		}

		// Validate Version
		int version = dataStream.read();

		if (version > VERSION) {
			Logger.error("JSceneAssetSerializer Failed to Deserialize Invalid Version");
			return null;
		}

		// Validate Endianness
		int endianness = dataStream.read();

		if (endianness > ENDIANNESS) {
			Logger.error("JSceneAssetSerializer Failed to Deserialize Invalid Endianness");
			return null;
		}

		// Validate Compression Type
		int compressionType = dataStream.read();

		if (compressionType > COMPRESSION_TYPE) {
			Logger.error("JSceneAssetSerializer Failed to Deserialize Invalid Compression Type");
			return null;
		}

		// Rest of HEADER
		dataStream.skip(9);

		// Scene SETTINGS
		byte[] sceneSettingsData = new byte[SCENE_SETTINGS_SIZE_BYTES];

		if (dataStream.read(sceneSettingsData, 0, SCENE_SETTINGS_SIZE_BYTES) != SCENE_SETTINGS_SIZE_BYTES) {
			Logger.error("JSceneAssetSerializer Failed to Deserialize Corrupted Scene Settings");
			return null;
		}

		SceneSettings sceneSettings = JSceneSerializerUtility.deserializeSceneSettings(sceneSettingsData);

		// Scene DATA
		byte[] sceneData = dataStream.readAllBytes();
		Scene scene = sceneSerializer.deserialize(sceneData, sceneSettings);

		return new JScene(
			new JSceneHeader(),
			sceneSettings,
			scene
		);
	}
}
