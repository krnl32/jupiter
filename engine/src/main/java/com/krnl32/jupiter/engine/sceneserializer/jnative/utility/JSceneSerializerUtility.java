package com.krnl32.jupiter.engine.sceneserializer.jnative.utility;

import com.krnl32.jupiter.engine.physics.PhysicsSettings;
import com.krnl32.jupiter.engine.scene.SceneSettings;
import org.joml.Vector3f;
import org.joml.Vector3fc;

import java.nio.ByteBuffer;

/*
 * SceneSettings (size: 13 Bytes)
 * ------------------------------------------
 * 1 Byte 	Physics Enabled	(0x0=False, 0x1=True)
 * 12 Bytes	Gravity			float32 x 3
 */
public class JSceneSerializerUtility {
	public static byte[] serializerSceneSettings(SceneSettings settings) {
		ByteBuffer buffer = ByteBuffer.allocate(13);
		serializePhysicsSettings(buffer, settings.getPhysicsSettings());
		return buffer.array();
	}

	public static SceneSettings deserializeSceneSettings(byte[] data) {
		ByteBuffer buffer = ByteBuffer.wrap(data);
		PhysicsSettings physicsSettings = deserializePhysicsSettings(buffer);
		return new SceneSettings(physicsSettings);
	}

	private static void serializePhysicsSettings(ByteBuffer buffer, PhysicsSettings settings) {
		boolean physicsEnabled = settings.isEnabled();
		Vector3fc physicsGravity = settings.getGravity();

		buffer.put((byte) (physicsEnabled ? 0x1 : 0x0));
		JSerializerUtility.serializeVector3f(buffer, physicsGravity);
	}

	private static PhysicsSettings deserializePhysicsSettings(ByteBuffer buffer) {
		boolean physicsEnabled = buffer.get() == 0x1;
		Vector3f physicsGravity = JSerializerUtility.deserializeVector3f(buffer);
		return new PhysicsSettings(physicsEnabled, physicsGravity);
	}
}
