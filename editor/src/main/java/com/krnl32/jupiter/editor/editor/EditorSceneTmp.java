package com.krnl32.jupiter.editor.editor;

import com.krnl32.jupiter.engine.asset.handle.AssetId;
import com.krnl32.jupiter.engine.asset.types.TextureAsset;
import com.krnl32.jupiter.engine.components.gameplay.TransformComponent;
import com.krnl32.jupiter.engine.components.physics.BoxCollider2DComponent;
import com.krnl32.jupiter.engine.components.physics.RigidBody2DComponent;
import com.krnl32.jupiter.engine.components.renderer.CameraComponent;
import com.krnl32.jupiter.engine.components.renderer.SpriteRendererComponent;
import com.krnl32.jupiter.engine.components.utility.TagComponent;
import com.krnl32.jupiter.engine.components.utility.UUIDComponent;
import com.krnl32.jupiter.engine.ecs.Entity;
import com.krnl32.jupiter.engine.physics.BodyType;
import com.krnl32.jupiter.engine.project.ProjectContext;
import com.krnl32.jupiter.engine.renderer.Camera;
import com.krnl32.jupiter.engine.scene.Scene;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;

import java.util.UUID;

public class EditorSceneTmp extends Scene {
	@Override
	public void onCreate() {
		// Sample Code....
		// Entities
		Entity cameraEntity = createEntity();
		cameraEntity.addComponent(new TagComponent("Camera"));
		cameraEntity.addComponent(new UUIDComponent());
		cameraEntity.addComponent(new TransformComponent(new Vector3f(0.0f, 0.0f, 3.0f), new Vector3f(0.0f, 0.0f, 0.0f), new Vector3f(1.0f, 1.0f, 1.0f)));
		cameraEntity.addComponent(new CameraComponent(new Camera(new Vector3f(0.0f, 0.0f, 3.0f), new Vector3f(0.0f, 1.0f, 0.0f), -90.0f, 0.0f, 0.0f, 50.0f, 1.0f, 45.0f, 10.0f, false), true));
		cameraEntity.getComponent(CameraComponent.class).camera.setOrthographic(10.0f, 0.1f, 100.0f);
		//cameraEntity.getComponent(CameraComponent.class).camera.setViewport(1920, 1080);
		//cameraEntity.addComponent(new KeyboardMovementComponent(10, KeyCode.SPACE, KeyCode.LEFT_CONTROL, KeyCode.W, KeyCode.S, KeyCode.A, KeyCode.D));

		// Asset
		TextureAsset asset = ProjectContext.getInstance().getAssetManager().getAsset(new AssetId(UUID.fromString("7d78d687-fc7b-4fe8-9a01-e12bdf3fdb16")));

		Entity entity = createEntity();
		entity.addComponent(new UUIDComponent());
		entity.addComponent(new TagComponent("Box"));
		entity.addComponent(new TransformComponent(new Vector3f(0.0f, 0.0f, -1.0f), new Vector3f(0.0f, 0.0f, 0.0f), new Vector3f(5.0f, 5.0f, 1.0f)));
		entity.addComponent(new SpriteRendererComponent(-1, new Vector4f(1.0f, 1.0f, 1.0f, 1.0f), asset.getId()));
		entity.addComponent(new RigidBody2DComponent(BodyType.DYNAMIC));
		entity.addComponent(new BoxCollider2DComponent(new Vector2f(1.0f, 1.0f)));
	}

	@Override
	public void onActivate() {
	}

	@Override
	public void onUnload() {
	}
}
