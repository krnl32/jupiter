package com.github.krnl32.jupiter;

import com.github.krnl32.jupiter.components.CameraComponent;
import com.github.krnl32.jupiter.components.MovementComponent;
import com.github.krnl32.jupiter.components.SpriteRendererComponent;
import com.github.krnl32.jupiter.components.TransformComponent;
import com.github.krnl32.jupiter.core.Engine;
import com.github.krnl32.jupiter.game.GameObject;
import com.github.krnl32.jupiter.game.Level;
import com.github.krnl32.jupiter.game.Scene;
import com.github.krnl32.jupiter.game.World;
import com.github.krnl32.jupiter.gameobjects.EmptyGameObject;
import com.github.krnl32.jupiter.input.KeyCode;
import com.github.krnl32.jupiter.renderer.Camera;
import com.github.krnl32.jupiter.renderer.Renderer;
import com.github.krnl32.jupiter.renderer.Sprite;
import com.github.krnl32.jupiter.renderer.Texture2D;
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
		world = new World();

		Level level1 = new Level();
		level1.loadFromFile("level1Data.txt");

		GameObject cameraObject = new EmptyGameObject();
		cameraObject.addComponent(new TransformComponent(new Vector3f(1.0f, 1.0f, 1.0f), new Vector3f(1.0f, 1.0f, 1.0f), new Vector3f(1.0f, 1.0f, 1.0f)));
		cameraObject.addComponent(new CameraComponent(new Camera(new Vector3f(0.0f, 0.0f, -1.0f), new Vector3f(0.0f, 1.0f, 0.0f), -90.0f, 0.0f, 0.0f, 50.0f, 1.0f, 45.0f, 10.0f), true));
		cameraObject.getComponent(CameraComponent.class).setPerspective(45.0f, 0.1f, 1000.0f);
		cameraObject.getComponent(CameraComponent.class).getCamera().setViewport(getWindow().getWidth(), getWindow().getHeight());
		cameraObject.addComponent(new MovementComponent(10, KeyCode.SPACE, KeyCode.LEFT_CONTROL, KeyCode.W, KeyCode.S, KeyCode.A, KeyCode.D));

		GameObject go = new EmptyGameObject();
		go.addComponent(new TransformComponent(new Vector3f(1.0f, 1.0f, -10.0f),new Vector3f(1.0f, 1.0f, 1.0f),new Vector3f(1.0f, 1.0f, 1.0f)));
		go.addComponent(new SpriteRendererComponent(new Sprite(2, 2, 0, new Vector4f(1.0f, 1.0f, 1.0f, 1.0f), new Texture2D((System.getProperty("user.dir") + "\\assets\\textures\\brick.png")))));

		GameObject go2 = new EmptyGameObject();
		go2.addComponent(new TransformComponent(new Vector3f(-10.0f, 1.0f, -10.0f),new Vector3f(1.0f, 1.0f, 1.0f),new Vector3f(1.0f, 1.0f, 1.0f)));
		go2.addComponent(new SpriteRendererComponent(new Sprite(2, 2, 0, new Vector4f(1.0f, 0.0f, 0.0f, 1.0f), null)));

		Scene level1Scene = new GamePlayScene(level1);
		level1Scene.addGameObject(cameraObject);
		level1Scene.addGameObject(go);
		level1Scene.addGameObject(go2);

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
