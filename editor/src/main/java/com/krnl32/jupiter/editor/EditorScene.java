package com.krnl32.jupiter.editor;

import com.krnl32.jupiter.components.gameplay.TransformComponent;
import com.krnl32.jupiter.components.physics.BoxCollider2DComponent;
import com.krnl32.jupiter.components.physics.RigidBody2DComponent;
import com.krnl32.jupiter.components.renderer.CameraComponent;
import com.krnl32.jupiter.components.renderer.SpriteRendererComponent;
import com.krnl32.jupiter.components.utility.TagComponent;
import com.krnl32.jupiter.components.utility.UUIDComponent;
import com.krnl32.jupiter.ecs.Entity;
import com.krnl32.jupiter.physics.BodyType;
import com.krnl32.jupiter.renderer.Camera;
import com.krnl32.jupiter.scene.Scene;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;

public class EditorScene extends Scene {
	@Override
	public void onCreate() {
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


		for (int i = 0; i < 7; i++) {
			Entity box = createEntity();
			box.addComponent(new UUIDComponent());
			box.addComponent(new TagComponent("box" + i));
			box.addComponent(new TransformComponent(new Vector3f(-3.0f + ((float) (i * 1.5f + Math.random() * 0.5f)), 3.5f + ((float) (Math.random() * 1.5f)), -1.0f), new Vector3f(0.0f, 0.0f, 0.0f), new Vector3f(1.0f, 1.0f, 1.0f)));
			box.addComponent(new SpriteRendererComponent(-1, new Vector4f((float) Math.random(), (float) Math.random(), (float) Math.random(), 1.0f), null));
			box.addComponent(new BoxCollider2DComponent(new Vector2f(1.0f, 1.0f)));
			box.addComponent(new RigidBody2DComponent(BodyType.DYNAMIC));

			Entity floor = createEntity();
			floor.addComponent(new UUIDComponent());
			floor.addComponent(new TagComponent("floor" + i));
			floor.addComponent(new TransformComponent(new Vector3f(-3.0f + i, -3.5f, -1.0f), new Vector3f(0.0f, 0.0f, 0.0f), new Vector3f(1.0f, 1.0f, 1.0f)));
			floor.addComponent(new SpriteRendererComponent(-1, new Vector4f(1.0f, 1.0f, 1.0f, 1.0f), null));
			floor.addComponent(new BoxCollider2DComponent(new Vector2f(1.0f, 1.0f)));
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
