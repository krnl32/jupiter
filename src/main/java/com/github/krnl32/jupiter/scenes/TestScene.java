package com.github.krnl32.jupiter.scenes;

import com.github.krnl32.jupiter.asset.AssetID;
import com.github.krnl32.jupiter.asset.AssetManager;
import com.github.krnl32.jupiter.asset.TextureAsset;
import com.github.krnl32.jupiter.components.*;
import com.github.krnl32.jupiter.core.Logger;
import com.github.krnl32.jupiter.ecs.Entity;
import com.github.krnl32.jupiter.game.Scene;
import com.github.krnl32.jupiter.input.KeyCode;
import com.github.krnl32.jupiter.renderer.Camera;
import com.github.krnl32.jupiter.renderer.Sprite;
import com.github.krnl32.jupiter.systems.CameraSystem;
import com.github.krnl32.jupiter.systems.KeyboardMovementSystem;
import com.github.krnl32.jupiter.systems.MovementSystem;
import com.github.krnl32.jupiter.systems.RenderSystem;
import org.joml.Vector3f;
import org.joml.Vector4f;

public class TestScene extends Scene {
	private int width, height;
	private Entity cameraEntity;
	private Entity spaceshipEntity;
	private AssetID spaceshipRedID;

	public TestScene(int width, int height) {
		this.width = width;
		this.height = height;
	}

	@Override
	public void onCreate() {
		spaceshipRedID = AssetManager.getInstance().registerAndLoad("textures/spaceship_red.png", () -> new TextureAsset("spaceship_red.png"));
		if (spaceshipRedID == null)
			Logger.critical("Game Failed to Load Texture Asset({})", "textures/spaceship_red.png");

		addSystem(new MovementSystem(getRegistry()), 0, true);
		addSystem(new KeyboardMovementSystem(getRegistry()), 1, true);
		addSystem(new CameraSystem(getRegistry()), 2, true);
		addSystem(new RenderSystem(getRegistry()));
	}

	@Override
	public void onActivate() {
		cameraEntity = createEntity();
		cameraEntity.addComponent(new TransformComponent(new Vector3f(1.0f, 1.0f, 1.0f), new Vector3f(1.0f, 1.0f, 1.0f), new Vector3f(1.0f, 1.0f, 1.0f)));
		cameraEntity.addComponent(new CameraComponent(new Camera(new Vector3f(0.0f, 0.0f, -1.0f), new Vector3f(0.0f, 1.0f, 0.0f), -90.0f, 0.0f, 0.0f, 50.0f, 1.0f, 45.0f, 10.0f, false), true));
		cameraEntity.getComponent(CameraComponent.class).camera.setOrthographic(10.0f, 0.0f, 100.0f);
		cameraEntity.getComponent(CameraComponent.class).camera.setViewport(width, height);
		//cameraEntity.addComponent(new KeyboardMovementComponent(10, KeyCode.SPACE, KeyCode.LEFT_CONTROL, KeyCode.W, KeyCode.S, KeyCode.A, KeyCode.D));

		spaceshipEntity = createEntity();
		spaceshipEntity.addComponent(new TransformComponent(new Vector3f(1.0f, -4.0f, 0.0f), new Vector3f(1.0f, 1.0f, 1.0f), new Vector3f(1.0f, 1.0f, 1.0f)));
		spaceshipEntity.addComponent(new SpriteRendererComponent(new Sprite(1, 1, 0, new Vector4f(1.0f, 1.0f, 1.0f, 1.0f), spaceshipRedID)));
		spaceshipEntity.addComponent(new KeyboardMovementComponent(10, KeyCode.W, KeyCode.S, KeyCode.UNKNOWN, KeyCode.UNKNOWN, KeyCode.A, KeyCode.D));
		spaceshipEntity.addComponent(new RigidBodyComponent(new Vector3f(0.0f, 1.0f, 0.0f)));
	}

	@Override
	public void onUnload() {
		if (cameraEntity != null)
			destroyEntity(cameraEntity);

		if (spaceshipEntity != null)
			destroyEntity(spaceshipEntity);
	}
}
