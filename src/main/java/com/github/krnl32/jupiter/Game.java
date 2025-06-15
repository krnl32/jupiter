package com.github.krnl32.jupiter;

import com.github.krnl32.jupiter.asset.AssetID;
import com.github.krnl32.jupiter.asset.AssetManager;
import com.github.krnl32.jupiter.asset.SceneAsset;
import com.github.krnl32.jupiter.asset.ShaderAsset;
import com.github.krnl32.jupiter.components.*;
import com.github.krnl32.jupiter.core.Engine;
import com.github.krnl32.jupiter.core.Logger;
import com.github.krnl32.jupiter.game.World;
import com.github.krnl32.jupiter.renderer.Renderer;
import com.github.krnl32.jupiter.scenes.GamePlayScene;
import com.github.krnl32.jupiter.serializer.SerializerRegistry;
import com.github.krnl32.jupiter.serializer.components.*;
import org.joml.Vector4f;

public class Game extends Engine {
	private World world;

	public Game(String name, int width, int height) {
		super(name, width, height);
	}

	@Override
	public boolean onInit() {
		// Register Component Serializers
		SerializerRegistry.registerComponentSerializer(IDComponent.class, new IDComponentSerializer());
		SerializerRegistry.registerComponentSerializer(TransformComponent.class, new TransformComponentSerializer());
		SerializerRegistry.registerComponentSerializer(SpriteRendererComponent.class, new SpriteRendererComponentSerializer());
		SerializerRegistry.registerComponentSerializer(RigidBodyComponent.class, new RigidBodyComponentSerializer());
		SerializerRegistry.registerComponentSerializer(CameraComponent.class, new CameraComponentSerializer());
		SerializerRegistry.registerComponentSerializer(KeyboardMovementComponent.class, new KeyboardMovementComponentSerializer());

		// Load Global Assets
		AssetID quadShaderID = AssetManager.getInstance().registerAndLoad("shaders/quad", () -> new ShaderAsset("quad_vertex.glsl", "quad_fragment.glsl"));
		if (quadShaderID == null)
			Logger.critical("Game Failed to Load Shader Asset({})", "shaders/quad");

		// Scene
		AssetID level1AssetID = AssetManager.getInstance().register("scenes/level1", () -> new SceneAsset("level1.json"));
		if (level1AssetID == null)
			Logger.critical("Game Failed to Register Scene Asset({})", "scenes/level1");

		world = new World();
		world.addScene("level1", new GamePlayScene(level1AssetID));
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
