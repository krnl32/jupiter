package com.krnl32.jupiter.scenes.levels;

import com.krnl32.jupiter.asset.*;
import com.krnl32.jupiter.components.*;
import com.krnl32.jupiter.core.Logger;
import com.krnl32.jupiter.ecs.Entity;
import com.krnl32.jupiter.input.KeyCode;
import com.krnl32.jupiter.model.Sprite;
import com.krnl32.jupiter.renderer.Camera;
import com.krnl32.jupiter.scene.Scene;
import com.krnl32.jupiter.serializer.SceneSerializer;
import com.krnl32.jupiter.systems.*;
import com.krnl32.jupiter.systems.ui.*;
import com.krnl32.jupiter.utility.FileIO;
import org.joml.Vector3f;
import org.joml.Vector4f;

import java.io.IOException;

import static org.joml.Math.toRadians;

public class Level1Scene extends Scene {
	private final int width, height;
	private Entity cameraEntity;
	private Entity playerSpaceship, enemySpaceship;
	private AssetID spaceshipRedID, spaceshipBlueID;
	private AssetID laserRedID, laserBlueID;
	private AssetID starParticleID;
	private AssetID spaceshipSpritesheetID;
	private AssetID arialFontID;
	private SpritesheetAsset spaceshipSpritesheetAsset;

	public Level1Scene(int width, int height) {
		this.width = width;
		this.height = height;
	}

	@Override
	public void onCreate() {
		// Register Assets
		spaceshipRedID = AssetManager.getInstance().registerAndLoad("textures/players/spaceshipRed.png", () -> new TextureAsset("textures/players/spaceshipRed.png"));
		if (spaceshipRedID == null)
			Logger.critical("Game Failed to Load Texture Asset({})", "textures/players/spaceshipRed.png");

		spaceshipBlueID = AssetManager.getInstance().registerAndLoad("textures/players/spaceshipBlue.png", () -> new TextureAsset("textures/players/spaceshipBlue.png"));
		if (spaceshipBlueID == null)
			Logger.critical("Game Failed to Load Texture Asset({})", "textures/players/spaceshipBlue.png");

		laserRedID = AssetManager.getInstance().registerAndLoad("textures/projectiles/laserRed.png", () -> new TextureAsset("textures/projectiles/laserRed.png"));
		if (laserRedID == null)
			Logger.critical("Game Failed to Load Texture Asset({})", "textures/projectiles/laserRed.png");

		laserBlueID = AssetManager.getInstance().registerAndLoad("textures/projectiles/laserBlue.png", () -> new TextureAsset("textures/projectiles/laserBlue.png"));
		if (laserBlueID == null)
			Logger.critical("Game Failed to Load Texture Asset({})", "textures/projectiles/laserBlue.png");

		starParticleID = AssetManager.getInstance().registerAndLoad("textures/particles/star3.png", () -> new TextureAsset("textures/particles/star3.png"));
		if (starParticleID == null)
			Logger.critical("Game Failed to Load Texture Asset({})", "textures/particles/star3.png");

		spaceshipSpritesheetID = AssetManager.getInstance().registerAndLoad("spritesheets/spaceship/spaceship.json", () -> new SpritesheetAsset("spritesheets/spaceship/spaceship.json"));
		if (spaceshipSpritesheetID == null)
			Logger.critical("Game Failed to Load Spritesheet Asset({})", "spritesheets/spaceship/spaceship.json");
		spaceshipSpritesheetAsset = AssetManager.getInstance().getAsset(spaceshipSpritesheetID);

		arialFontID = AssetManager.getInstance().registerAndLoad("fonts/arial.ttf", () -> new FontAsset("fonts/arial.ttf", 34));
		if (arialFontID == null)
			Logger.critical("Game Failed to Load Font Asset({})", "fonts/arial.ttf");

		// Register Systems
		addSystem(new KeyboardControlSystem(getRegistry()), 1, true);
		addSystem(new CameraSystem(getRegistry()), 2, true);
		addSystem(new RenderSystem(getRegistry()));
		addSystem(new LifetimeSystem(getRegistry()));
		addSystem(new ProjectileEmitterSystem(getRegistry()));
		addSystem(new DamageSystem(getRegistry()));
		addSystem(new HealthSystem(getRegistry()));
		addSystem(new DestroySystem(getRegistry()));
		addSystem(new BlinkSystem(getRegistry()));
		addSystem(new ParticleSystem(getRegistry()));
		addSystem(new DeathEffectSystem(getRegistry()));
		addSystem(new UILayoutSystem(getRegistry()));
		addSystem(new UIInputSystem(getRegistry()));
		addSystem(new UIRenderSystem(getRegistry()));
		addSystem(new UIButtonSystem(getRegistry()));
		addSystem(new UITextRenderSystem(getRegistry()));
		addSystem(new UIScrollSystem(getRegistry()));
	}

	@Override
	public void onActivate() {
		cameraEntity = createEntity();
		cameraEntity.addComponent(new UUIDComponent());
		cameraEntity.addComponent(new TransformComponent(new Vector3f(1.0f, 1.0f, 1.0f), new Vector3f(1.0f, 1.0f, 1.0f), new Vector3f(1.0f, 1.0f, 1.0f)));
		cameraEntity.addComponent(new CameraComponent(new Camera(new Vector3f(0.0f, 0.0f, -1.0f), new Vector3f(0.0f, 1.0f, 0.0f), -90.0f, 0.0f, 0.0f, 50.0f, 1.0f, 45.0f, 10.0f, false), true));
		cameraEntity.getComponent(CameraComponent.class).camera.setOrthographic(10.0f, 0.1f, 100.0f);
		cameraEntity.getComponent(CameraComponent.class).camera.setViewport(width, height);
		//cameraEntity.addComponent(new KeyboardMovementComponent(10, KeyCode.SPACE, KeyCode.LEFT_CONTROL, KeyCode.W, KeyCode.S, KeyCode.A, KeyCode.D));

		playerSpaceship = createEntity();
		playerSpaceship.addComponent(new UUIDComponent());
		playerSpaceship.addComponent(new TagComponent("playerSpaceship"));
		playerSpaceship.addComponent(new TransformComponent(new Vector3f(1.0f, -3.0f, -1.0f), new Vector3f(0.0f, 0.0f, 0.0f), new Vector3f(1.0f, 1.0f, 1.0f)));
		playerSpaceship.addComponent(new SpriteRendererComponent(-1, new Vector4f(1.0f, 1.0f, 1.0f, 1.0f), spaceshipRedID));
		//playerSpaceship.addComponent(new KeyboardControlComponent(10, 10, KeyCode.W, KeyCode.S, KeyCode.UNKNOWN, KeyCode.UNKNOWN, KeyCode.A, KeyCode.D, KeyCode.Q, KeyCode.E));
		//playerSpaceship.addComponent(new RigidBodyComponent(new Vector3f(0.0f, 0.0f, 0.0f)));
		playerSpaceship.addComponent(new ProjectileEmitterComponent(KeyCode.SPACE, 15.55f, 10.0f, new Sprite(1, new Vector4f(1.0f, 1.0f, 1.0f, 1.0f), laserRedID)));
		playerSpaceship.addComponent(new HealthComponent(100, 100));
		playerSpaceship.addComponent(new TeamComponent(1));
		playerSpaceship.addComponent(new DeathEffectComponent(20, new Sprite(0, new Vector4f(1.0f, 0.45f, 0.0f, 0.95f), starParticleID)));

		int totalEnemies = 3;
		for (int i = 0; i < totalEnemies; i++) {
			enemySpaceship = createEntity();
			enemySpaceship.addComponent(new UUIDComponent());
			enemySpaceship.addComponent(new TagComponent("enemySpaceship" + i));
			enemySpaceship.addComponent(new TransformComponent(new Vector3f(-3.0f * i, 5.0f, -1.0f), new Vector3f(0.0f, 0.0f, toRadians(180.0f)), new Vector3f(1.0f, 1.0f, 1.0f)));
			enemySpaceship.addComponent(new SpriteRendererComponent(-1, new Vector4f(1.0f, 1.0f, 1.0f, 1.0f), spaceshipSpritesheetAsset.getSprite("playerShip1_blue.png").getTextureAssetID(), spaceshipSpritesheetAsset.getSprite("playerShip1_blue.png").getTextureUV()));
			//enemySpaceship.addComponent(new RigidBody2DComponent(new Vector3f(1.0f, 0.0f, 0.0f)));
			enemySpaceship.addComponent(new HealthComponent(100, 100));
			enemySpaceship.addComponent(new TeamComponent(2));
			enemySpaceship.addComponent(new DeathEffectComponent(20, new Sprite(0, new Vector4f(1.0f, 0.45f, 0.0f, 0.95f), starParticleID)));
			enemySpaceship.addComponent(new ProjectileEmitterComponent(null, 15.55f, 10.0f, new Sprite(1, new Vector4f(1.0f, 1.0f, 1.0f, 1.0f), laserBlueID)));
		}

		SceneSerializer sceneSerializer = new SceneSerializer();
		try {
			FileIO.writeFileContent(System.getProperty("user.dir") + "/assets/scenes/level1.json", sceneSerializer.serialize(this).toString(4));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void onUnload() {
		if (cameraEntity != null)
			destroyEntity(cameraEntity);
		if (playerSpaceship != null)
			destroyEntity(playerSpaceship);
	}
}
