package com.github.krnl32.jupiter;

import com.github.krnl32.jupiter.asset.AssetID;
import com.github.krnl32.jupiter.asset.AssetManager;
import com.github.krnl32.jupiter.asset.ShaderAsset;
import com.github.krnl32.jupiter.asset.TextureAsset;
import com.github.krnl32.jupiter.components.*;
import com.github.krnl32.jupiter.core.Engine;
import com.github.krnl32.jupiter.core.Logger;
import com.github.krnl32.jupiter.game.GameObject;
import com.github.krnl32.jupiter.game.Scene;
import com.github.krnl32.jupiter.game.World;
import com.github.krnl32.jupiter.gameobjects.EmptyGameObject;
import com.github.krnl32.jupiter.input.KeyCode;
import com.github.krnl32.jupiter.renderer.Camera;
import com.github.krnl32.jupiter.renderer.Renderer;
import com.github.krnl32.jupiter.renderer.Sprite;
import com.github.krnl32.jupiter.scenes.EmptyScene;
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
		AssetID spaceshipRedID = AssetManager.getInstance().registerAndLoad("textures/spaceship_red.png", () -> new TextureAsset("spaceship_red.png"));
		if (spaceshipRedID == null)
			Logger.critical("Game Failed to Load Texture Asset({})", "textures/spaceship_red.png");

		// Scene
		world = new World();

		GameObject cameraObject = new EmptyGameObject();
		cameraObject.addComponent(new TransformComponent(new Vector3f(1.0f, 1.0f, 1.0f), new Vector3f(1.0f, 1.0f, 1.0f), new Vector3f(1.0f, 1.0f, 1.0f)));
		cameraObject.addComponent(new CameraComponent(new Camera(new Vector3f(0.0f, 0.0f, -1.0f), new Vector3f(0.0f, 1.0f, 0.0f), -90.0f, 0.0f, 0.0f, 50.0f, 1.0f, 45.0f, 10.0f, false), true));
		cameraObject.getComponent(CameraComponent.class).setOrthographic(10.0f, 0.0f, 100.0f);
		cameraObject.getComponent(CameraComponent.class).getCamera().setViewport(getWindow().getWidth(), getWindow().getHeight());
		//cameraObject.addComponent(new KeyboardMovementComponent(10, KeyCode.SPACE, KeyCode.LEFT_CONTROL, KeyCode.W, KeyCode.S, KeyCode.A, KeyCode.D));

		GameObject spaceship = new EmptyGameObject();
		spaceship.addComponent(new TransformComponent(new Vector3f(1.0f, -4.0f, 0.0f), new Vector3f(1.0f, 1.0f, 1.0f), new Vector3f(1.0f, 1.0f, 1.0f)));
		spaceship.addComponent(new SpriteRendererComponent(new Sprite(1, 1, 0, new Vector4f(1.0f, 1.0f, 1.0f, 1.0f), spaceshipRedID)));
		spaceship.addComponent(new KeyboardMovementComponent(10, KeyCode.W, KeyCode.S, KeyCode.UNKNOWN, KeyCode.UNKNOWN, KeyCode.A, KeyCode.D));
		spaceship.addComponent(new RigidBodyComponent(new Vector3f(0.0f, 1.0f, 0.0f)));
		spaceship.addComponent(new MovementComponent());

		Scene emptyScene = new EmptyScene();
		emptyScene.addGameObject(cameraObject);
		emptyScene.addGameObject(spaceship);

		world.addScene("empty", emptyScene);
		world.switchScene("empty");

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
