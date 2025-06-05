package com.github.krnl32.jupiter;

import com.github.krnl32.jupiter.components.CameraComponent;
import com.github.krnl32.jupiter.components.MovementComponent;
import com.github.krnl32.jupiter.components.TransformComponent;
import com.github.krnl32.jupiter.core.Engine;
import com.github.krnl32.jupiter.game.GameObject;
import com.github.krnl32.jupiter.game.Level;
import com.github.krnl32.jupiter.game.Scene;
import com.github.krnl32.jupiter.game.World;
import com.github.krnl32.jupiter.gameobjects.EmptyGameObject;
import com.github.krnl32.jupiter.renderer.Camera;
import com.github.krnl32.jupiter.renderer.Renderer;
import com.github.krnl32.jupiter.scenes.GamePlayScene;
import org.joml.Vector3f;

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

		GameObject camera = new EmptyGameObject();
		camera.addComponent(new TransformComponent(new Vector3f(1.0f, 1.0f, 1.0f),new Vector3f(1.0f, 1.0f, 1.0f),new Vector3f(1.0f, 1.0f, 1.0f)));
		camera.addComponent(new CameraComponent(new Camera(), true));

		GameObject go = new EmptyGameObject();
		go.addComponent(new TransformComponent(new Vector3f(1.0f, 1.0f, 1.0f),new Vector3f(1.0f, 1.0f, 1.0f),new Vector3f(1.0f, 1.0f, 1.0f)));
		go.addComponent(new MovementComponent());

		Scene level1Scene = new GamePlayScene(level1);
		level1Scene.addGameObject(camera);
		level1Scene.addGameObject(go);

		world.addScene("level1", level1Scene);
		world.switchScene("level1");


		return true;
	}

	@Override
	public void onUpdate(float dt) {
		//if(Input.getInstance().isKeyUp(KeyCode.A))
		//	System.out.println("A is Up");

		/*
		if(Input.getInstance().isKeyDown(KeyCode.A))
			System.out.println("A is Down");

		if(Input.getInstance().isKeyPressed(KeyCode.E))
			System.out.println("E is Pressed");

		if(Input.getInstance().isKeyReleased(KeyCode.E))
			System.out.println("E is Released");

		if(Input.getInstance().isMouseButtonDown(MouseCode.Button1))
			System.out.println("Button1 Down");

		if(Input.getInstance().isMouseButtonPressed(MouseCode.Button2))
			System.out.println("Button2 Pressed");

		if(Input.getInstance().isMouseButtonReleased(MouseCode.Button2))
			System.out.println("Button2 Released");

		//Logger.info("Cursor({}, {})", Input.getInstance().getMouseCursorPosition().x, Input.getInstance().getMouseCursorPosition().y);
		//Logger.info("Cursor Delta({}, {})", Input.getInstance().getMouseCursorDelta().x, Input.getInstance().getMouseCursorDelta().y);

		if(Input.getInstance().isMouseScrollingDown())
			System.out.println("Mouse Scrolling Down");
		*/

		world.onUpdate(dt);
	}

	@Override
	public void onRender(float dt, Renderer renderer) {
		world.onRender(dt, renderer);
	}
}
