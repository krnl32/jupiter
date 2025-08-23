package com.krnl32.jupiter.engine.sceneserializer.jnative.components.gameplay;

import com.krnl32.jupiter.engine.components.gameplay.ScriptComponent;
import com.krnl32.jupiter.engine.core.Logger;
import com.krnl32.jupiter.engine.sceneserializer.ComponentSerializer;
import com.krnl32.jupiter.engine.sceneserializer.jnative.utility.JComponentSerializerUtility;
import com.krnl32.jupiter.engine.sceneserializer.jnative.utility.JSerializerUtility;
import com.krnl32.jupiter.engine.sceneserializer.resolvers.EntityResolver;
import com.krnl32.jupiter.engine.script.ScriptInstance;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.List;

/*
 * == ScriptComponent (size: 17 x N Bytes) ==
 * 17xN Bytes	Scripts		ScriptInstance x N
 */
public class JScriptComponentSerializer implements ComponentSerializer<ScriptComponent, byte[]> {
	private static final int SIZE = 17;
	private static final ByteOrder ENDIANNESS = ByteOrder.LITTLE_ENDIAN;

	@Override
	public byte[] serialize(ScriptComponent component) {
		int scriptCount = component.scripts.size();
		ByteBuffer buffer = ByteBuffer.allocate(SIZE * scriptCount);

		for (ScriptInstance script : component.scripts) {
			byte[] scriptData = JComponentSerializerUtility.serializeScriptInstance(script);

			if (scriptData == null) {
				Logger.error("JScriptComponentSerializer Serialize Failed for AssetId({})", script.getScriptAssetId());
				return null;
			}

			buffer.put(scriptData);
		}

		return buffer.array();
	}

	@Override
	public ScriptComponent deserialize(byte[] data, EntityResolver resolver) {
		if (data.length % SIZE != 0) {
			Logger.error("JScriptComponentSerializer Deserialize failed, Corrupted Data, Size({})", data.length);
			return null;
		}

		List<ScriptInstance> scripts = new ArrayList<>();
		int scriptCount = data.length / SIZE;
		ByteBuffer buffer = ByteBuffer.wrap(data).order(ENDIANNESS);

		for (int i = 0; i < scriptCount; i++) {
			byte[] scriptData = JSerializerUtility.deserializeByteArray(buffer, SIZE);
			ScriptInstance scriptInstance = JComponentSerializerUtility.deserializeScriptInstance(scriptData);

			if (scriptInstance == null) {
				Logger.error("JScriptComponentSerializer Deserialize Failed for ScriptInstance({})", i);
				return null;
			}

			scripts.add(scriptInstance);
		}

		return new ScriptComponent(scripts);
	}
}
