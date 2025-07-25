package com.krnl32.jupiter.editor.editor;

import com.krnl32.jupiter.engine.asset.AssetID;
import com.krnl32.jupiter.engine.asset.AssetManager;
import com.krnl32.jupiter.engine.asset.types.ScriptAsset;
import com.krnl32.jupiter.engine.components.gameplay.ScriptComponent;
import com.krnl32.jupiter.engine.components.gameplay.TransformComponent;
import com.krnl32.jupiter.engine.components.physics.BoxCollider2DComponent;
import com.krnl32.jupiter.engine.components.physics.CircleCollider2DComponent;
import com.krnl32.jupiter.engine.components.physics.RigidBody2DComponent;
import com.krnl32.jupiter.engine.components.renderer.CameraComponent;
import com.krnl32.jupiter.engine.components.renderer.SpriteRendererComponent;
import com.krnl32.jupiter.engine.components.utility.TagComponent;
import com.krnl32.jupiter.engine.components.utility.UUIDComponent;
import com.krnl32.jupiter.engine.core.Logger;
import com.krnl32.jupiter.engine.ecs.Entity;
import com.krnl32.jupiter.engine.physics.BodyType;
import com.krnl32.jupiter.engine.renderer.Camera;
import com.krnl32.jupiter.engine.scene.Scene;
import com.krnl32.jupiter.engine.script.ScriptInstance;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;

public class EditorScene extends Scene {
	private AssetID testScriptID, test2ScriptID;

	@Override
	public void onCreate() {
		// Sample Code....

		// Assets
		testScriptID = AssetManager.getInstance().register("scripts/test.lua", () -> new ScriptAsset("scripts/test.lua"));
		if (testScriptID == null)
			Logger.critical("Game Failed to Load Script Asset({})", "scripts/test.lua");

		test2ScriptID = AssetManager.getInstance().register("scripts/test2.lua", () -> new ScriptAsset("scripts/test2.lua"));
		if (test2ScriptID == null)
			Logger.critical("Game Failed to Load Script Asset({})", "scripts/test2.lua");

		// Entities
		Entity cameraEntity = createEntity();
		cameraEntity.addComponent(new TagComponent("Camera"));
		cameraEntity.addComponent(new UUIDComponent());
		cameraEntity.addComponent(new TransformComponent(new Vector3f(0.0f, 0.0f, 3.0f), new Vector3f(0.0f, 0.0f, 0.0f), new Vector3f(1.0f, 1.0f, 1.0f)));
		cameraEntity.addComponent(new CameraComponent(new Camera(new Vector3f(0.0f, 0.0f, 3.0f), new Vector3f(0.0f, 1.0f, 0.0f), -90.0f, 0.0f, 0.0f, 50.0f, 1.0f, 45.0f, 10.0f, false), true));
		cameraEntity.getComponent(CameraComponent.class).camera.setOrthographic(10.0f, 0.1f, 100.0f);
		//cameraEntity.getComponent(CameraComponent.class).camera.setViewport(1920, 1080);
		//cameraEntity.addComponent(new KeyboardMovementComponent(10, KeyCode.SPACE, KeyCode.LEFT_CONTROL, KeyCode.W, KeyCode.S, KeyCode.A, KeyCode.D));

		Entity entity = createEntity();
		entity.addComponent(new UUIDComponent());
		entity.addComponent(new TagComponent("redBox"));
		entity.addComponent(new TransformComponent(new Vector3f(0.0f, 0.0f, -1.0f), new Vector3f(0.0f, 0.0f, 0.0f), new Vector3f(1.0f, 1.0f, 1.0f)));
		entity.addComponent(new SpriteRendererComponent(-1, new Vector4f(1.0f, 0.0f, 0.0f, 1.0f), null));
		entity.addComponent(new RigidBody2DComponent(BodyType.DYNAMIC));
		entity.addComponent(new BoxCollider2DComponent(new Vector2f(1.0f, 1.0f)));
		entity.addComponent(new ScriptComponent(new ScriptInstance(testScriptID), new ScriptInstance(test2ScriptID)));

		for (int i = 0; i < 7; i++) {
			Entity box = createEntity();
			box.addComponent(new UUIDComponent());
			box.addComponent(new TagComponent("box" + i));

			// Position and Scale
			float posX = -3.0f + (i * 1.5f) + (float)(Math.random() * 0.5f);
			float posY = 3.5f + (float)(Math.random() * 1.5f);
			float scale = 0.8f + (float)(Math.random() * 0.4f);

			box.addComponent(new TransformComponent(new Vector3f(posX, posY, -1.0f), new Vector3f(0.0f, 0.0f, 0.0f), new Vector3f(scale, scale, 1.0f)));
			box.addComponent(new SpriteRendererComponent(-1, new Vector4f((float)Math.random(), (float)Math.random(), (float)Math.random(), 1.0f), null));
			box.addComponent(new CircleCollider2DComponent(0.5f * scale, new Vector2f(0.0f, 0.0f), 0.2f, 1.0f, false));
			box.addComponent(new RigidBody2DComponent(BodyType.DYNAMIC, new Vector2f((float)(Math.random() * 2 - 1), (float)(Math.random() * 2)), 0.1f, 0.05f, 1.0f, 1.0f, false, true));

			Entity floor = createEntity();
			floor.addComponent(new UUIDComponent());
			floor.addComponent(new TagComponent("floor" + i));
			floor.addComponent(new TransformComponent(new Vector3f(-3.0f + i, -3.5f, -1.0f), new Vector3f(0.0f, 0.0f, 0.0f), new Vector3f(1.0f, 1.0f, 1.0f)));
			floor.addComponent(new SpriteRendererComponent(-1, new Vector4f(1.0f, 1.0f, 1.0f, 1.0f), null));
			floor.addComponent(new BoxCollider2DComponent(new Vector2f(1.0f, 1.0f), new Vector2f(0.0f, 0.0f), 0.8f, 1.0f, false));
			floor.addComponent(new RigidBody2DComponent(BodyType.STATIC));
		}
	}

	@Override
	public void onActivate() {
	}

	@Override
	public void onUnload() {
	}
}
