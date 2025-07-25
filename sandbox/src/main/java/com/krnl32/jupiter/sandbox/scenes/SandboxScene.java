package com.krnl32.jupiter.sandbox.scenes;

import com.krnl32.jupiter.engine.asset.AssetID;
import com.krnl32.jupiter.engine.asset.AssetManager;
import com.krnl32.jupiter.engine.asset.types.ScriptAsset;
import com.krnl32.jupiter.engine.asset.types.SpritesheetAsset;
import com.krnl32.jupiter.engine.components.gameplay.*;
import com.krnl32.jupiter.engine.components.input.KeyboardControlComponent;
import com.krnl32.jupiter.engine.components.physics.BoxCollider2DComponent;
import com.krnl32.jupiter.engine.components.physics.RigidBody2DComponent;
import com.krnl32.jupiter.engine.components.renderer.CameraComponent;
import com.krnl32.jupiter.engine.components.renderer.SpriteRendererComponent;
import com.krnl32.jupiter.engine.components.utility.TagComponent;
import com.krnl32.jupiter.engine.components.utility.UUIDComponent;
import com.krnl32.jupiter.engine.core.Logger;
import com.krnl32.jupiter.engine.ecs.Entity;
import com.krnl32.jupiter.engine.input.devices.KeyCode;
import com.krnl32.jupiter.engine.physics.BodyType;
import com.krnl32.jupiter.engine.renderer.Camera;
import com.krnl32.jupiter.engine.scene.Scene;
import com.krnl32.jupiter.engine.script.ScriptInstance;
import com.krnl32.jupiter.engine.serializer.SceneSerializer;
import com.krnl32.jupiter.engine.utility.FileIO;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;

import java.io.IOException;

public class SandboxScene extends Scene {
	private final int width, height;

	private AssetID redWarriorIdleSpritesheet;
	private SpritesheetAsset redWarriorIdleSpritesheetAsset;

	private AssetID testScriptID, test2ScriptID;

	public SandboxScene(int width, int height) {
		this.width = width;
		this.height = height;
	}

	@Override
	public void onCreate() {
		redWarriorIdleSpritesheet = AssetManager.getInstance().registerAndLoad("spritesheets/Units/Red/Warrior_Idle.json", () -> new SpritesheetAsset("spritesheets/Units/Red/Warrior_Idle.json"));
		if (redWarriorIdleSpritesheet == null)
			Logger.critical("Game Failed to Load Spritesheet Asset({})", "spritesheets/Units/Red/Warrior_Idle.json");
		redWarriorIdleSpritesheetAsset = AssetManager.getInstance().getAsset(redWarriorIdleSpritesheet);

		testScriptID = AssetManager.getInstance().register("scripts/test.lua", () -> new ScriptAsset("scripts/test.lua"));
		if (testScriptID == null)
			Logger.critical("Game Failed to Load Script Asset({})", "scripts/test.lua");

		test2ScriptID = AssetManager.getInstance().register("scripts/test2.lua", () -> new ScriptAsset("scripts/test2.lua"));
		if (test2ScriptID == null)
			Logger.critical("Game Failed to Load Script Asset({})", "scripts/test2.lua");
	}

	@Override
	public void onActivate() {
		Entity cameraEntity = createEntity();
		cameraEntity.addComponent(new UUIDComponent());
		cameraEntity.addComponent(new TransformComponent(new Vector3f(0.0f, 0.0f, 3.0f), new Vector3f(0.0f, 0.0f, 0.0f), new Vector3f(1.0f, 1.0f, 1.0f)));
		cameraEntity.addComponent(new CameraComponent(new Camera(new Vector3f(0.0f, 0.0f, 3.0f), new Vector3f(0.0f, 1.0f, 0.0f), -90.0f, 0.0f, 0.0f, 50.0f, 1.0f, 45.0f, 10.0f, false), true));
		cameraEntity.getComponent(CameraComponent.class).camera.setOrthographic(10.0f, 0.1f, 100.0f);
		cameraEntity.getComponent(CameraComponent.class).camera.setViewport(width, height);
		//cameraEntity.addComponent(new KeyboardMovementComponent(10, KeyCode.SPACE, KeyCode.LEFT_CONTROL, KeyCode.W, KeyCode.S, KeyCode.A, KeyCode.D));

		Entity spaceshipRedEntity = createEntity();
		spaceshipRedEntity.addComponent(new UUIDComponent());
		spaceshipRedEntity.addComponent(new TagComponent("SpaceshipRed"));
		spaceshipRedEntity.addComponent(new TransformComponent(new Vector3f(0.0f, 0.0f, -1.0f), new Vector3f(0.0f, 0.0f, 0.0f), new Vector3f(2.0f, 2.0f, 1.0f)));
		spaceshipRedEntity.addComponent(new SpriteRendererComponent(-1, new Vector4f(1.0f, 1.0f, 1.0f, 1.0f), redWarriorIdleSpritesheetAsset.getSprite("Warrior_Idle_0").getTextureAssetID(), redWarriorIdleSpritesheetAsset.getSprite("Warrior_Idle_0").getTextureUV()));
		spaceshipRedEntity.addComponent(new KeyboardControlComponent(KeyCode.W, KeyCode.S, KeyCode.UNKNOWN, KeyCode.UNKNOWN, KeyCode.A, KeyCode.D, KeyCode.Q, KeyCode.E, KeyCode.SPACE, KeyCode.LEFT_SHIFT));
		//spaceshipRedEntity.addComponent(new ProjectileEmitterComponent(KeyCode.LEFT_CONTROL, 15.55f, 25.0f, new Sprite(1, new Vector4f(1.0f, 1.0f, 1.0f, 1.0f), laserRedID)));
		spaceshipRedEntity.addComponent(new HealthComponent(100, 100));
		spaceshipRedEntity.addComponent(new TeamComponent(1));
		//spaceshipRedEntity.addComponent(new DeathEffectComponent(20, new Sprite(0, new Vector4f(1.0f, 0.45f, 0.0f, 0.95f), starParticleID)));
		spaceshipRedEntity.addComponent(new RigidBody2DComponent(BodyType.DYNAMIC, new Vector2f(0.0f, 0.0f), 1.5f, 6.0f, 1.0f, 0.0f, false, false));
		spaceshipRedEntity.addComponent(new BoxCollider2DComponent(new Vector2f(1.0f, 1.0f)));
		spaceshipRedEntity.addComponent(new MovementIntentComponent());
		spaceshipRedEntity.addComponent(new ForceMovementComponent());
		//spaceshipRedEntity.addComponent(ScriptLoader.loadScript(System.getProperty("user.dir") + "/assets/scripts/test.lua", spaceshipRedEntity));
		//spaceshipRedEntity.addComponent(new ScriptComponent(new ScriptComponent(testScriptID), new ScriptComponent(test2ScriptID)));
		spaceshipRedEntity.addComponent(new ScriptComponent(new ScriptInstance(testScriptID)));

		SceneSerializer sceneSerializer = new SceneSerializer();
		try {
			FileIO.writeFileContent(System.getProperty("user.dir") + "/assets/scenes/sandbox2.json", sceneSerializer.serialize(this).toString(4));
			//sceneSerializer.deserialize(new JSONObject(FileIO.readFileContent(System.getProperty("user.dir") + "/assets/scenes/sandbox.json")), this);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void onUnload() {
	}
}
