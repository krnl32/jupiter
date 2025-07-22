package com.krnl32.jupiter.scenes;

import com.krnl32.jupiter.asset.*;
import com.krnl32.jupiter.asset.types.FontAsset;
import com.krnl32.jupiter.asset.types.SpritesheetAsset;
import com.krnl32.jupiter.asset.types.TextureAsset;
import com.krnl32.jupiter.components.effects.DeathEffectComponent;
import com.krnl32.jupiter.components.gameplay.*;
import com.krnl32.jupiter.components.input.KeyboardControlComponent;
import com.krnl32.jupiter.components.physics.BoxCollider2DComponent;
import com.krnl32.jupiter.components.physics.RigidBody2DComponent;
import com.krnl32.jupiter.components.projectile.ProjectileEmitterComponent;
import com.krnl32.jupiter.components.renderer.CameraComponent;
import com.krnl32.jupiter.components.renderer.SpriteRendererComponent;
import com.krnl32.jupiter.components.ui.*;
import com.krnl32.jupiter.components.utility.TagComponent;
import com.krnl32.jupiter.components.utility.UUIDComponent;
import com.krnl32.jupiter.core.Logger;
import com.krnl32.jupiter.ecs.Entity;
import com.krnl32.jupiter.input.devices.KeyCode;
import com.krnl32.jupiter.model.Sprite;
import com.krnl32.jupiter.physics.BodyType;
import com.krnl32.jupiter.renderer.Camera;
import com.krnl32.jupiter.scene.Scene;
import com.krnl32.jupiter.serializer.SceneSerializer;
import com.krnl32.jupiter.ui.UIHierarchyManager;
import com.krnl32.jupiter.ui.layout.LayoutOverflow;
import com.krnl32.jupiter.ui.layout.LayoutType;
import com.krnl32.jupiter.ui.text.TextHorizontalAlign;
import com.krnl32.jupiter.ui.text.TextOverflow;
import com.krnl32.jupiter.ui.text.TextVerticalAlign;
import com.krnl32.jupiter.utility.FileIO;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;

import java.io.IOException;

import static org.joml.Math.toRadians;

public class TestScene extends Scene {
	private final int width, height;
	private Entity cameraEntity;
	private Entity spaceshipRedEntity, spaceshipBlueEntity;
	private AssetID spaceshipRedID, spaceshipBlueID;
	private AssetID laserRedID, laserBlueID;
	private AssetID starParticleID;
	private AssetID spaceshipSpritesheetID;
	private AssetID buttonBlueID;
	private AssetID arialFontID;
	private SpritesheetAsset spaceshipSpritesheetAsset;

	public TestScene(int width, int height) {
		this.width = width;
		this.height = height;
	}

	@Override
	public void onCreate() {
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

		buttonBlueID = AssetManager.getInstance().registerAndLoad("textures/ui/buttons/buttonBlue.png", () -> new TextureAsset("textures/ui/buttons/buttonBlue.png"));
		if (buttonBlueID == null)
			Logger.critical("Game Failed to Load Texture Asset({})", "textures/ui/buttons/buttonBlue.png");

		arialFontID = AssetManager.getInstance().registerAndLoad("fonts/arial.ttf", () -> new FontAsset("fonts/arial.ttf", 34));
		if (arialFontID == null)
			Logger.critical("Game Failed to Load Font Asset({})", "fonts/arial.ttf");
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

		spaceshipRedEntity = createEntity();
		spaceshipRedEntity.addComponent(new UUIDComponent());
		spaceshipRedEntity.addComponent(new TagComponent("SpaceshipRed"));
		spaceshipRedEntity.addComponent(new TransformComponent(new Vector3f(1.0f, -3.0f, -1.0f), new Vector3f(0.0f, 0.0f, 0.0f), new Vector3f(1.0f, 1.0f, 1.0f)));
		spaceshipRedEntity.addComponent(new SpriteRendererComponent(-1, new Vector4f(1.0f, 1.0f, 1.0f, 1.0f), spaceshipRedID));
		spaceshipRedEntity.addComponent(new KeyboardControlComponent(KeyCode.W, KeyCode.S, KeyCode.UNKNOWN, KeyCode.UNKNOWN, KeyCode.A, KeyCode.D, KeyCode.Q, KeyCode.E, KeyCode.SPACE, KeyCode.LEFT_SHIFT));
		spaceshipRedEntity.addComponent(new ProjectileEmitterComponent(KeyCode.LEFT_CONTROL, 15.55f, 25.0f, new Sprite(1, new Vector4f(1.0f, 1.0f, 1.0f, 1.0f), laserRedID)));
		spaceshipRedEntity.addComponent(new HealthComponent(100, 100));
		spaceshipRedEntity.addComponent(new TeamComponent(1));
		spaceshipRedEntity.addComponent(new DeathEffectComponent(20, new Sprite(0, new Vector4f(1.0f, 0.45f, 0.0f, 0.95f), starParticleID)));
		spaceshipRedEntity.addComponent(new RigidBody2DComponent(BodyType.DYNAMIC, new Vector2f(0.0f, 0.0f), 1.5f, 6.0f, 1.0f, 1.0f, false, false));
		spaceshipRedEntity.addComponent(new BoxCollider2DComponent(new Vector2f(1.0f, 1.0f)));
		spaceshipRedEntity.addComponent(new MovementIntentComponent());
		spaceshipRedEntity.addComponent(new ForceMovementComponent());

		Entity staticFloor = createEntity();
		staticFloor.addComponent(new UUIDComponent());
		staticFloor.addComponent(new TagComponent("staticFloor"));
		staticFloor.addComponent(new TransformComponent(new Vector3f(-3.0f, -2.5f, -1.0f), new Vector3f(0.0f, 0.0f, 0.0f), new Vector3f(1.0f, 1.0f, 1.0f)));
		staticFloor.addComponent(new SpriteRendererComponent(-2, new Vector4f(1.0f, 1.0f, 1.0f, 1.0f), null));
		RigidBody2DComponent staticFloorRigidBody2D = new RigidBody2DComponent(BodyType.STATIC);
		staticFloorRigidBody2D.bodyType = BodyType.STATIC;
		staticFloor.addComponent(staticFloorRigidBody2D);
		staticFloor.addComponent(new BoxCollider2DComponent(new Vector2f(1.0f, 1.0f)));

		spaceshipBlueEntity = createEntity();
		spaceshipBlueEntity.addComponent(new UUIDComponent());
		spaceshipBlueEntity.addComponent(new TagComponent("SpaceshipBlue"));
		spaceshipBlueEntity.addComponent(new TransformComponent(new Vector3f(-3.0f, 5.0f, -1.0f), new Vector3f(0.0f, 0.0f, 0.0f), new Vector3f(1.0f, 1.0f, 1.0f)));
		spaceshipBlueEntity.addComponent(new SpriteRendererComponent(-2, new Vector4f(1.0f, 1.0f, 1.0f, 1.0f), spaceshipSpritesheetAsset.getSprite("playerShip1_blue.png").getTextureAssetID(), spaceshipSpritesheetAsset.getSprite("playerShip1_blue.png").getTextureUV()));
		spaceshipBlueEntity.addComponent(new HealthComponent(100, 100));
		spaceshipBlueEntity.addComponent(new TeamComponent(2));
		spaceshipBlueEntity.addComponent(new DeathEffectComponent(20, new Sprite(0, new Vector4f(1.0f, 0.45f, 0.0f, 0.95f), starParticleID)));
		spaceshipBlueEntity.addComponent(new RigidBody2DComponent(BodyType.DYNAMIC, new Vector2f(0.0f, 0.0f), 1.5f, 6.0f, 1.0f, 1.0f, false, false));
		spaceshipBlueEntity.addComponent(new BoxCollider2DComponent(new Vector2f(1.0f, 1.0f)));

		// UI
		Entity uiRoot = createEntity();
		uiRoot.addComponent(new UITransformComponent(new Vector3f(50.0f, 1000.0f, -1.0f), new Vector3f(0.0f, 0.0f, 0.0f), new Vector3f(500.0f, 90.0f, 1.0f)));
		uiRoot.addComponent(new UIRenderComponent(-1, new Vector4f(0.5f, 0.5f, 0.5f, 0.5f), null));
		uiRoot.addComponent(new UILayoutComponent(LayoutType.VERTICAL, LayoutOverflow.SCALE, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, true));

		Entity button = createEntity();
		button.addComponent(new UITransformComponent(new Vector3f(0.0f, 200.0f, -1.0f), new Vector3f(0.0f, 0.0f, 0.0f), new Vector3f(222.0f, 39.0f, 1.0f)));
		button.addComponent(new UIRenderComponent(-1, new Vector4f(1.0f, 1.0f, 1.0f, 1.0f), buttonBlueID));
		button.addComponent(new UIButtonComponent((entity) -> {
			Entity spaceship = createEntity();
			spaceship.addComponent(new UUIDComponent());
			spaceship.addComponent(new TagComponent("SpaceshipEnemyBlue"));
			spaceship.addComponent(new TransformComponent(new Vector3f(-3.0f, 5.0f, -1.0f), new Vector3f(0.0f, 0.0f, toRadians(180.0f)), new Vector3f(1.0f, 1.0f, 1.0f)));
			spaceship.addComponent(new SpriteRendererComponent(-1, new Vector4f(1.0f, 1.0f, 1.0f, 1.0f), spaceshipSpritesheetAsset.getSprite("playerShip1_blue.png").getTextureAssetID(), spaceshipSpritesheetAsset.getSprite("playerShip1_blue.png").getTextureUV()));
			spaceship.addComponent(new HealthComponent(100, 100));
			spaceship.addComponent(new TeamComponent(2));
			spaceship.addComponent(new DeathEffectComponent(20, new Sprite(0, new Vector4f(1.0f, 0.45f, 0.0f, 0.95f), starParticleID)));
			spaceship.addComponent(new ProjectileEmitterComponent(null, 15.55f, 10.0f, new Sprite(1, new Vector4f(1.0f, 1.0f, 1.0f, 1.0f), laserBlueID)));
			spaceship.addComponent(new RigidBody2DComponent(BodyType.DYNAMIC, new Vector2f(0.0f, 0.0f), 1.5f, 6.0f, 1.0f, 1.0f, false, false));
			spaceship.addComponent(new BoxCollider2DComponent(new Vector2f(1.0f, 1.0f)));
		}));
		button.addComponent(new UITextComponent("Spawn Enemy Blue", new Vector4f(0.118f, 0.118f, 0.705f, 1.0f), arialFontID, TextHorizontalAlign.CENTER, TextVerticalAlign.CENTER, TextOverflow.SCALE));
		UIHierarchyManager.attach(uiRoot, button);

		Entity button2 = createEntity();
		button2.addComponent(new UITransformComponent(new Vector3f(0.0f, 250.0f, -1.0f), new Vector3f(0.0f, 0.0f, 0.0f), new Vector3f(222.0f, 39.0f, 1.0f)));
		button2.addComponent(new UIRenderComponent(-1, new Vector4f(1.0f, 1.0f, 1.0f, 1.0f), buttonBlueID));
		button2.addComponent(new UIButtonComponent((entity) -> {
			Entity spaceship = createEntity();
			spaceship.addComponent(new UUIDComponent());
			spaceship.addComponent(new TagComponent("SpaceshipEnemyRed"));
			spaceship.addComponent(new TransformComponent(new Vector3f(-3.0f, 5.0f, -1.0f), new Vector3f(0.0f, 0.0f, toRadians(180.0f)), new Vector3f(1.0f, 1.0f, 1.0f)));
			spaceship.addComponent(new SpriteRendererComponent(-1, new Vector4f(1.0f, 1.0f, 1.0f, 1.0f), spaceshipSpritesheetAsset.getSprite("playerShip1_red.png").getTextureAssetID(), spaceshipSpritesheetAsset.getSprite("playerShip1_red.png").getTextureUV()));
			spaceship.addComponent(new HealthComponent(100, 100));
			spaceship.addComponent(new TeamComponent(2));
			spaceship.addComponent(new DeathEffectComponent(20, new Sprite(0, new Vector4f(1.0f, 0.45f, 0.0f, 0.95f), starParticleID)));
			spaceship.addComponent(new ProjectileEmitterComponent(null, 15.55f, 10.0f, new Sprite(1, new Vector4f(1.0f, 1.0f, 1.0f, 1.0f), laserBlueID)));
			spaceship.addComponent(new RigidBody2DComponent(BodyType.DYNAMIC, new Vector2f(0.0f, 0.0f), 1.5f, 6.0f, 1.0f, 1.0f, false, false));
			spaceship.addComponent(new BoxCollider2DComponent(new Vector2f(1.0f, 1.0f)));
		}));
		button2.addComponent(new UITextComponent("Spawn Enemy Red", new Vector4f(0.705f, 0.118f, 0.118f, 1.0f), arialFontID, TextHorizontalAlign.CENTER, TextVerticalAlign.CENTER, TextOverflow.SCALE));
		UIHierarchyManager.attach(uiRoot, button2);

		SceneSerializer sceneSerializer = new SceneSerializer();
		try {
			FileIO.writeFileContent(System.getProperty("user.dir") + "/assets/scenes/test.json", sceneSerializer.serialize(this).toString(4));
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
