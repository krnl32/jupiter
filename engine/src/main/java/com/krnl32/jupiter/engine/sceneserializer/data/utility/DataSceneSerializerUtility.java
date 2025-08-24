package com.krnl32.jupiter.engine.sceneserializer.data.utility;

import com.krnl32.jupiter.engine.physics.PhysicsSettings;
import com.krnl32.jupiter.engine.scene.SceneSettings;
import org.joml.Vector3f;
import org.joml.Vector3fc;

import java.util.Map;

public class DataSceneSerializerUtility {
	public static Map<String, Object> serializerSceneSettings(SceneSettings settings) {
		Map<String, Object> physicsSettings = serializePhysicsSettings(settings.getPhysicsSettings());

		return Map.of(
			"physicsSettings", physicsSettings
		);
	}

	public static SceneSettings deserializeSceneSettings(Map<String, Object> settings) {
		Map<String, Object> physicsSettingsSerialized = (Map<String, Object>) settings.get("physicsSettings");
		PhysicsSettings physicsSettings = deserializePhysicsSettings(physicsSettingsSerialized);

		return new SceneSettings(physicsSettings);
	}

	private static Map<String, Object> serializePhysicsSettings(PhysicsSettings settings) {
		boolean physicsEnabled = settings.isEnabled();
		Vector3fc physicsGravity = settings.getGravity();
		Map<String, Object> physicsGravitySerialized = DataSerializerUtility.serializeVector3f(physicsGravity);

		return Map.of(
			"enabled", physicsEnabled,
			"gravity", physicsGravitySerialized
		);
	}

	private static PhysicsSettings deserializePhysicsSettings(Map<String, Object> settings) {
		boolean physicsEnabled = (boolean) settings.get("enabled");
		Map<String, Object> physicsGravitySerialized = (Map<String, Object>) settings.get("gravity");
		Vector3f physicsGravity = DataSerializerUtility.deserializeVector3f(physicsGravitySerialized);

		return new PhysicsSettings(physicsEnabled, physicsGravity);
	}
}
