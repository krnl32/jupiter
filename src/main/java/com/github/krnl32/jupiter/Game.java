package com.github.krnl32.jupiter;

import com.github.krnl32.jupiter.asset.*;
import com.github.krnl32.jupiter.components.CameraComponent;
import com.github.krnl32.jupiter.components.MovementComponent;
import com.github.krnl32.jupiter.components.TransformComponent;
import com.github.krnl32.jupiter.core.Engine;
import com.github.krnl32.jupiter.core.Logger;
import com.github.krnl32.jupiter.game.GameObject;
import com.github.krnl32.jupiter.game.Scene;
import com.github.krnl32.jupiter.game.World;
import com.github.krnl32.jupiter.gameobjects.EmptyGameObject;
import com.github.krnl32.jupiter.input.KeyCode;
import com.github.krnl32.jupiter.renderer.Camera;
import com.github.krnl32.jupiter.renderer.Renderer;
import com.github.krnl32.jupiter.scenes.GamePlayScene;
import org.joml.Vector3f;
import org.joml.Vector4f;

public class Game extends Engine {
	private World world;

	public Game(String name, int width, int height) {
		super(name, width, height);
	}

	@Override
	public boolean onInit() {
		// Load Shaders
		AssetID quadShaderID = AssetManager.getInstance().registerAndLoad("shaders/quad", () -> new ShaderAsset("quad_vertex.glsl", "quad_fragment.glsl"));
		if (quadShaderID == null)
			Logger.critical("Game Failed to Load Shader Asset({})", "shaders/quad");

		// Load Textures
		AssetID brickAssetID = AssetManager.getInstance().registerAndLoad("textures/brick.png", () -> new TextureAsset("brick.png"));
		if (brickAssetID == null)
			Logger.critical("Game Failed to Load Texture Asset({})", "textures/brick.png");

		// Load Levels
		AssetID level1AssetID = AssetManager.getInstance().register("levels/level1", () -> new LevelAsset("level1.json"));
		if (level1AssetID == null)
			Logger.critical("Game Failed to Register Level Asset({})", "levels/level1");

		world = new World();

		// Scene
		GameObject cameraObject = new EmptyGameObject();
		cameraObject.addComponent(new TransformComponent(new Vector3f(1.0f, 1.0f, 1.0f), new Vector3f(1.0f, 1.0f, 1.0f), new Vector3f(1.0f, 1.0f, 1.0f)));
		cameraObject.addComponent(new CameraComponent(new Camera(new Vector3f(0.0f, 0.0f, -1.0f), new Vector3f(0.0f, 1.0f, 0.0f), -90.0f, 0.0f, 0.0f, 50.0f, 1.0f, 45.0f, 10.0f), true));
		cameraObject.getComponent(CameraComponent.class).setPerspective(45.0f, 0.1f, 1000.0f);
		cameraObject.getComponent(CameraComponent.class).getCamera().setViewport(getWindow().getWidth(), getWindow().getHeight());
		cameraObject.addComponent(new MovementComponent(10, KeyCode.SPACE, KeyCode.LEFT_CONTROL, KeyCode.W, KeyCode.S, KeyCode.A, KeyCode.D));

		Scene level1Scene = new GamePlayScene(level1AssetID);
		level1Scene.addGameObject(cameraObject);

		world.addScene("level1", level1Scene);
		world.switchScene("level1");

		return true;
	}

	@Override
	public void onUpdate(float dt) {
		world.onUpdate(dt);
	}

	@Override
	public void onRender(float dt, Renderer renderer) {
		renderer.setClearColor(new Vector4f(0.07f, 0.13f, 0.17f, 1.0f));
		renderer.clear();
		world.onRender(dt, renderer);
	}
}
