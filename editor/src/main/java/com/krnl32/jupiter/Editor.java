package com.krnl32.jupiter;

import com.krnl32.jupiter.asset.AssetID;
import com.krnl32.jupiter.asset.AssetManager;
import com.krnl32.jupiter.asset.ShaderAsset;
import com.krnl32.jupiter.components.*;
import com.krnl32.jupiter.core.Engine;
import com.krnl32.jupiter.core.Logger;
import com.krnl32.jupiter.editor.EditorUI;
import com.krnl32.jupiter.game.World;
import com.krnl32.jupiter.renderer.Renderer;
import com.krnl32.jupiter.scenes.EmptyScene;
import com.krnl32.jupiter.serializer.SerializerRegistry;
import com.krnl32.jupiter.serializer.components.*;
import org.joml.Vector4f;

public class Editor extends Engine {
	private World world;
	private EditorUI editorUI;

	public Editor(String name, int width, int height) {
		super(name, width, height);
	}

	@Override
	public boolean onInit() {
		// Register Component Serializers
		SerializerRegistry.registerComponentSerializer(UUIDComponent.class, new UUIDComponentSerializer());
		SerializerRegistry.registerComponentSerializer(TagComponent.class, new TagComponentSerializer());
		SerializerRegistry.registerComponentSerializer(TransformComponent.class, new TransformComponentSerializer());
		SerializerRegistry.registerComponentSerializer(RigidBodyComponent.class, new RigidBodyComponentSerializer());
		SerializerRegistry.registerComponentSerializer(SpriteRendererComponent.class, new SpriteRendererComponentSerializer());
		SerializerRegistry.registerComponentSerializer(CameraComponent.class, new CameraComponentSerializer());
		SerializerRegistry.registerComponentSerializer(KeyboardControlComponent.class, new KeyboardControlComponentSerializer());
		SerializerRegistry.registerComponentSerializer(TeamComponent.class, new TeamComponentSerializer());
		SerializerRegistry.registerComponentSerializer(HealthComponent.class, new HealthComponentSerializer());
		SerializerRegistry.registerComponentSerializer(LifetimeComponent.class, new LifetimeComponentSerializer());
		SerializerRegistry.registerComponentSerializer(BoxColliderComponent.class, new BoxColliderComponentSerializer());
		SerializerRegistry.registerComponentSerializer(ParticleComponent.class, new ParticleComponentSerializer());
		SerializerRegistry.registerComponentSerializer(BlinkComponent.class, new BlinkComponentSerializer());
		SerializerRegistry.registerComponentSerializer(DeathEffectComponent.class, new DeathEffectComponentSerializer());
		SerializerRegistry.registerComponentSerializer(ProjectileComponent.class, new ProjectileComponentSerializer());
		SerializerRegistry.registerComponentSerializer(ProjectileEmitterComponent.class, new ProjectileEmitterComponentSerializer());

		// Load Global Assets
		AssetID quadShaderID = AssetManager.getInstance().registerAndLoad("shaders/quad", () -> new ShaderAsset("shaders/quad_vertex.glsl", "shaders/quad_fragment.glsl"));
		if (quadShaderID == null)
			Logger.critical("Game Failed to Load Shader Asset({})", "shaders/quad");

		// Editor
		editorUI = new EditorUI(getWindow());

		world = new World();
		world.addScene("empty", new EmptyScene());
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

		editorUI.onRender(dt);
		world.onRender(dt, renderer);
	}
}
