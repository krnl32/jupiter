package com.github.krnl32.jupiter.scenes;

import com.github.krnl32.jupiter.asset.AssetID;
import com.github.krnl32.jupiter.asset.AssetManager;
import com.github.krnl32.jupiter.asset.TextureAsset;
import com.github.krnl32.jupiter.components.*;
import com.github.krnl32.jupiter.core.Logger;
import com.github.krnl32.jupiter.ecs.Entity;
import com.github.krnl32.jupiter.game.Scene;
import com.github.krnl32.jupiter.input.KeyCode;
import com.github.krnl32.jupiter.model.Sprite;
import com.github.krnl32.jupiter.renderer.Camera;
import com.github.krnl32.jupiter.serializer.SceneSerializer;
import com.github.krnl32.jupiter.systems.*;
import com.github.krnl32.jupiter.utility.FileIO;
import org.joml.Vector3f;
import org.joml.Vector4f;

import java.io.IOException;

import static java.lang.System.exit;

public class TestScene extends Scene {
	private final int width, height;
	private Entity cameraEntity;
	private Entity spaceshipRedEntity, spaceshipBlueEntity;
	private AssetID spaceshipRedID, spaceshipBlueID;
	private AssetID laserRedID, laserBlueID;
	private AssetID starParticleID;

	public TestScene(int width, int height) {
		this.width = width;
		this.height = height;
	}

	@Override
	public void onCreate() {
		spaceshipRedID = AssetManager.getInstance().registerAndLoad("textures/players/spaceship_red.png", () -> new TextureAsset("players/spaceship_red.png"));
		if (spaceshipRedID == null)
			Logger.critical("Game Failed to Load Texture Asset({})", "textures/players/spaceship_red.png");

		spaceshipBlueID = AssetManager.getInstance().registerAndLoad("textures/players/spaceship_blue.png", () -> new TextureAsset("players/spaceship_blue.png"));
		if (spaceshipBlueID == null)
			Logger.critical("Game Failed to Load Texture Asset({})", "textures/players/spaceship_blue.png");

		laserRedID = AssetManager.getInstance().registerAndLoad("textures/bullets/laser_red.png", () -> new TextureAsset("bullets/laser_red.png"));
		if (laserRedID == null)
			Logger.critical("Game Failed to Load Texture Asset({})", "textures/bullets/laser_red.png");

		laserBlueID = AssetManager.getInstance().registerAndLoad("textures/bullets/laser_blue.png", () -> new TextureAsset("bullets/laser_blue.png"));
		if (laserBlueID == null)
			Logger.critical("Game Failed to Load Texture Asset({})", "textures/bullets/laser_blue.png");

		starParticleID = AssetManager.getInstance().registerAndLoad("textures/particles/star3.png", () -> new TextureAsset("particles/star3.png"));
		if (starParticleID == null)
			Logger.critical("Game Failed to Load Texture Asset({})", "textures/particles/star3.png");

		// Systems
		addSystem(new MovementSystem(getRegistry()), 0, true);
		addSystem(new KeyboardControlSystem(getRegistry()), 1, true);
		addSystem(new CameraSystem(getRegistry()), 2, true);
		addSystem(new RenderSystem(getRegistry()));
		addSystem(new LifetimeSystem(getRegistry()));
		addSystem(new ProjectileEmitterSystem(getRegistry()));
		addSystem(new CollisionSystem(getRegistry()));
		addSystem(new DamageSystem(getRegistry()));
		addSystem(new HealthSystem(getRegistry()));
		addSystem(new DestroySystem(getRegistry()));
		addSystem(new BlinkSystem(getRegistry()));
		addSystem(new ParticleSystem(getRegistry()));
		addSystem(new DeathEffectSystem(getRegistry()));
	}

	@Override
	public void onActivate() {
		cameraEntity = createEntity();
		cameraEntity.addComponent(new IDComponent());
		cameraEntity.addComponent(new TransformComponent(new Vector3f(1.0f, 1.0f, 1.0f), new Vector3f(1.0f, 1.0f, 1.0f), new Vector3f(1.0f, 1.0f, 1.0f)));
		cameraEntity.addComponent(new CameraComponent(new Camera(new Vector3f(0.0f, 0.0f, -1.0f), new Vector3f(0.0f, 1.0f, 0.0f), -90.0f, 0.0f, 0.0f, 50.0f, 1.0f, 45.0f, 10.0f, false), true));
		cameraEntity.getComponent(CameraComponent.class).camera.setOrthographic(10.0f, 0.0f, 100.0f);
		cameraEntity.getComponent(CameraComponent.class).camera.setViewport(width, height);
		//cameraEntity.addComponent(new KeyboardMovementComponent(10, KeyCode.SPACE, KeyCode.LEFT_CONTROL, KeyCode.W, KeyCode.S, KeyCode.A, KeyCode.D));

		spaceshipRedEntity = createEntity();
		spaceshipRedEntity.addComponent(new IDComponent());
		spaceshipRedEntity.addComponent(new TagComponent("SpaceshipRed"));
		spaceshipRedEntity.addComponent(new TransformComponent(new Vector3f(1.0f, -3.0f, 0.0f), new Vector3f(0.0f, 0.0f, 0.0f), new Vector3f(1.0f, 1.0f, 1.0f)));
		spaceshipRedEntity.addComponent(new SpriteRendererComponent(1, new Vector4f(1.0f, 1.0f, 1.0f, 1.0f), spaceshipRedID));
		spaceshipRedEntity.addComponent(new KeyboardControlComponent(10, 10, KeyCode.W, KeyCode.S, KeyCode.UNKNOWN, KeyCode.UNKNOWN, KeyCode.A, KeyCode.D, KeyCode.Q, KeyCode.E));
		spaceshipRedEntity.addComponent(new RigidBodyComponent(new Vector3f(0.0f, 0.0f, 0.0f)));
//		spaceshipRedEntity.addComponent(new ProjectileEmitterComponent(KeyCode.SPACE, 15.55f, 10.0f, new Sprite(1, new Vector4f(1.0f, 1.0f, 1.0f, 1.0f), laserRedID)));
		spaceshipRedEntity.addComponent(new BoxColliderComponent(new Vector3f(1.0f, 1.0f, 1.0f)));
		spaceshipRedEntity.addComponent(new HealthComponent(100, 100));
		spaceshipRedEntity.addComponent(new TeamComponent(1));
		spaceshipRedEntity.addComponent(new DeathEffectComponent(20, new Sprite(0, new Vector4f(1.0f, 0.45f, 0.0f, 0.95f), starParticleID)));
		spaceshipRedEntity.addComponent(new ProjectileComponent(spaceshipRedEntity, 12.25f, true));

//		spaceshipBlueEntity = createEntity();
//		spaceshipBlueEntity.addComponent(new IDComponent());
//		spaceshipBlueEntity.addComponent(new TagComponent("SpaceshipBlue"));
//		spaceshipBlueEntity.addComponent(new TransformComponent(new Vector3f(-3.0f, 5.0f, 0.0f), new Vector3f(0.0f, 0.0f, 0.0f), new Vector3f(1.0f, 1.0f, 1.0f)));
//		spaceshipBlueEntity.addComponent(new SpriteRendererComponent(1, new Vector4f(1.0f, 1.0f, 1.0f, 1.0f), spaceshipBlueID));
//		spaceshipBlueEntity.addComponent(new RigidBodyComponent(new Vector3f(1.0f, 0.0f, 0.0f)));
//		spaceshipBlueEntity.addComponent(new BoxColliderComponent(new Vector3f(1.0f, 1.0f, 1.0f)));
//		spaceshipBlueEntity.addComponent(new HealthComponent(100, 100));
//		spaceshipBlueEntity.addComponent(new TeamComponent(2));
//		spaceshipBlueEntity.addComponent(new DeathEffectComponent(20, new Sprite(0, new Vector4f(1.0f, 0.45f, 0.0f, 0.95f), starParticleID)));


		SceneSerializer sceneSerializer = new SceneSerializer();
		try {
			FileIO.writeFileContent("C:\\Users\\DMP\\IdeaProjects\\jupiter\\assets\\scenes\\test.json", sceneSerializer.serialize(this).toString(4));
			exit(0);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void onUnload() {
		if (cameraEntity != null)
			destroyEntity(cameraEntity);
		if (spaceshipRedEntity != null)
			destroyEntity(spaceshipRedEntity);
		if (spaceshipBlueEntity != null)
			destroyEntity(spaceshipBlueEntity);
	}
}
