package com.krnl32.jupiter.engine.sceneserializer.jnative.utility;

import com.krnl32.jupiter.engine.asset.handle.AssetId;
import com.krnl32.jupiter.engine.core.Logger;
import com.krnl32.jupiter.engine.project.ProjectContext;
import com.krnl32.jupiter.engine.script.ScriptInstance;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.UUID;

public class JComponentSerializerUtility {
	private static final int SCRIPT_INSTANCE_SIZE = 17;

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

		ByteBuffer buffer = ByteBuffer.allocate(SCRIPT_INSTANCE_SIZE).order(ByteOrder.LITTLE_ENDIAN);
		JSerializerUtility.serializeUUID(buffer, scriptInstance.getScriptAssetId().getId());
		JSerializerUtility.serializeBool(buffer, scriptInstance.isDisabled());

		return buffer.array();
	}

	public static ScriptInstance deserializeScriptInstance(byte[] data) {
		if (data.length != SCRIPT_INSTANCE_SIZE) {
			Logger.error("JComponentSerializerUtility deserializeScriptInstance failed, Corrupted Data, Size({})", data.length);
			return null;
		}

		ByteBuffer buffer = ByteBuffer.wrap(data).order(ByteOrder.LITTLE_ENDIAN);
		UUID scriptAssetUUID = JSerializerUtility.deserializeUUID(buffer);
		boolean scriptDisabled = JSerializerUtility.deserializeBool(buffer);

		AssetId scriptAssetId = new AssetId(scriptAssetUUID);

		if (!ProjectContext.getInstance().getAssetManager().isAssetRegistered(scriptAssetId)) {
			Logger.error("JComponentSerializerUtility deserializeScriptInstance Failed, Script AssetId({}) Not Registered", scriptAssetId);
			return null;
		}

		return new ScriptInstance(scriptAssetId, scriptDisabled);
	}
}
