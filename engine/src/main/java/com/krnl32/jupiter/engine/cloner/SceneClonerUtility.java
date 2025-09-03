package com.krnl32.jupiter.engine.cloner;

import com.krnl32.jupiter.engine.physics.PhysicsSettings;
import com.krnl32.jupiter.engine.scene.SceneSettings;
import org.joml.Vector3f;

public class SceneClonerUtility {
	public static SceneSettings cloneSceneSettings(SceneSettings sceneSettings) {
		PhysicsSettings clonePhysicsSettings = clonePhysicsSettings(sceneSettings.getPhysicsSettings());
		return new SceneSettings(clonePhysicsSettings);
	}

	private static PhysicsSettings clonePhysicsSettings(PhysicsSettings physicsSettings) {
		boolean isEnabled = physicsSettings.isEnabled();
		Vector3f gravity = new Vector3f(physicsSettings.getGravity());
		return new PhysicsSettings(isEnabled, gravity);
	}
}
