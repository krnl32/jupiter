package com.krnl32.jupiter.editor;

import com.krnl32.jupiter.asset.AssetManager;
import com.krnl32.jupiter.asset.TextureAsset;
import com.krnl32.jupiter.components.effects.BlinkComponent;
import com.krnl32.jupiter.components.effects.DeathEffectComponent;
import com.krnl32.jupiter.components.effects.ParticleComponent;
import com.krnl32.jupiter.components.gameplay.HealthComponent;
import com.krnl32.jupiter.components.gameplay.TeamComponent;
import com.krnl32.jupiter.components.gameplay.TransformComponent;
import com.krnl32.jupiter.components.input.KeyboardControlComponent;
import com.krnl32.jupiter.components.physics.BoxCollider2DComponent;
import com.krnl32.jupiter.components.physics.CircleCollider2DComponent;
import com.krnl32.jupiter.components.physics.RigidBody2DComponent;
import com.krnl32.jupiter.components.projectile.ProjectileComponent;
import com.krnl32.jupiter.components.projectile.ProjectileEmitterComponent;
import com.krnl32.jupiter.components.renderer.CameraComponent;
import com.krnl32.jupiter.components.renderer.SpriteRendererComponent;
import com.krnl32.jupiter.components.utility.LifetimeComponent;
import com.krnl32.jupiter.components.utility.TagComponent;
import com.krnl32.jupiter.components.utility.UUIDComponent;
import com.krnl32.jupiter.core.Engine;
import com.krnl32.jupiter.core.Logger;
import com.krnl32.jupiter.event.EventBus;
import com.krnl32.jupiter.events.editor.EditorPauseEvent;
import com.krnl32.jupiter.events.editor.EditorPlayEvent;
import com.krnl32.jupiter.events.editor.EditorStopEvent;
import com.krnl32.jupiter.factory.FactoryRegistry;
import com.krnl32.jupiter.factory.components.effects.BlinkComponentFactory;
import com.krnl32.jupiter.factory.components.gameplay.HealthComponentFactory;
import com.krnl32.jupiter.factory.components.gameplay.TeamComponentFactory;
import com.krnl32.jupiter.factory.components.gameplay.TransformComponentFactory;
import com.krnl32.jupiter.factory.components.physics.BoxCollider2DComponentFactory;
import com.krnl32.jupiter.factory.components.physics.CircleCollider2DComponentFactory;
import com.krnl32.jupiter.factory.components.physics.Rigidbody2DComponentFactory;
import com.krnl32.jupiter.factory.components.utility.TagComponentFactory;
import com.krnl32.jupiter.panels.InspectorPanel;
import com.krnl32.jupiter.panels.SceneHierarchyPanel;
import com.krnl32.jupiter.panels.ViewportPanel;
import com.krnl32.jupiter.renderer.FrameBufferAttachmentFormat;
import com.krnl32.jupiter.renderer.Framebuffer;
import com.krnl32.jupiter.renderer.Renderer;
import com.krnl32.jupiter.renderer.RendererRegistry;
import com.krnl32.jupiter.renderer.components.effects.BlinkComponentRenderer;
import com.krnl32.jupiter.renderer.components.gameplay.HealthComponentRenderer;
import com.krnl32.jupiter.renderer.components.gameplay.TeamComponentRenderer;
import com.krnl32.jupiter.renderer.components.gameplay.TransformComponentRenderer;
import com.krnl32.jupiter.renderer.components.physics.BoxCollider2DComponentRenderer;
import com.krnl32.jupiter.renderer.components.physics.CircleCollider2DComponentRenderer;
import com.krnl32.jupiter.renderer.components.physics.RigidBody2DComponentRenderer;
import com.krnl32.jupiter.renderer.components.utility.TagComponentRenderer;
import com.krnl32.jupiter.scene.Scene;
import com.krnl32.jupiter.scene.SceneManager;
import com.krnl32.jupiter.serializer.SceneSerializer;
import com.krnl32.jupiter.serializer.SerializerRegistry;
import com.krnl32.jupiter.serializer.components.effects.BlinkComponentSerializer;
import com.krnl32.jupiter.serializer.components.effects.DeathEffectComponentSerializer;
import com.krnl32.jupiter.serializer.components.effects.ParticleComponentSerializer;
import com.krnl32.jupiter.serializer.components.gameplay.HealthComponentSerializer;
import com.krnl32.jupiter.serializer.components.gameplay.TeamComponentSerializer;
import com.krnl32.jupiter.serializer.components.gameplay.TransformComponentSerializer;
import com.krnl32.jupiter.serializer.components.input.KeyboardControlComponentSerializer;
import com.krnl32.jupiter.serializer.components.projectile.ProjectileComponentSerializer;
import com.krnl32.jupiter.serializer.components.projectile.ProjectileEmitterComponentSerializer;
import com.krnl32.jupiter.serializer.components.renderer.CameraComponentSerializer;
import com.krnl32.jupiter.serializer.components.renderer.SpriteRendererComponentSerializer;
import com.krnl32.jupiter.serializer.components.utility.LifetimeComponentSerializer;
import com.krnl32.jupiter.serializer.components.utility.TagComponentSerializer;
import com.krnl32.jupiter.serializer.components.utility.UUIDComponentSerializer;
import org.joml.Vector4f;

import java.util.List;

public class Editor extends Engine {
	private Framebuffer framebuffer;
	private EditorUI editorUI;
	private SceneManager sceneManager;
	private EditorState editorState;
	private SceneSerializer sceneSerializer;

	public Editor(String name, int width, int height) {
		super(name, width, height);
		editorState = EditorState.STOP;

		EventBus.getInstance().register(EditorPlayEvent.class, event -> {
			play();
		});
		EventBus.getInstance().register(EditorPauseEvent.class, event -> {
			pause();
		});
		EventBus.getInstance().register(EditorStopEvent.class, event -> {
			stop();
		});
	}

	@Override
	public boolean onInit() {
		// Register Assets
		AssetManager.getInstance().registerAndLoad("editorPlayButton", () -> new TextureAsset("textures/ui/buttons/play.png"));
		if (AssetManager.getInstance().registerAndLoad("editorPlayButton", () -> new TextureAsset("textures/ui/buttons/play.png")) == null)
			Logger.critical("Editor Failed to Load Texture Asset({})", "textures/ui/buttons/play.png");

		AssetManager.getInstance().registerAndLoad("editorPauseButton", () -> new TextureAsset("textures/ui/buttons/pause.png"));
		if (AssetManager.getInstance().registerAndLoad("editorPauseButton", () -> new TextureAsset("textures/ui/buttons/pause.png")) == null)
			Logger.critical("Editor Failed to Load Texture Asset({})", "textures/ui/buttons/pause.png");

		AssetManager.getInstance().registerAndLoad("editorStopButton", () -> new TextureAsset("textures/ui/buttons/stop.png"));
		if (AssetManager.getInstance().registerAndLoad("editorStopButton", () -> new TextureAsset("textures/ui/buttons/stop.png")) == null)
			Logger.critical("Editor Failed to Load Texture Asset({})", "textures/ui/buttons/stop.png");

		// Register Component Serializers
		SerializerRegistry.registerComponentSerializer(UUIDComponent.class, new UUIDComponentSerializer());
		SerializerRegistry.registerComponentSerializer(TagComponent.class, new TagComponentSerializer());
		SerializerRegistry.registerComponentSerializer(TransformComponent.class, new TransformComponentSerializer());
		SerializerRegistry.registerComponentSerializer(SpriteRendererComponent.class, new SpriteRendererComponentSerializer());
		SerializerRegistry.registerComponentSerializer(CameraComponent.class, new CameraComponentSerializer());
		SerializerRegistry.registerComponentSerializer(KeyboardControlComponent.class, new KeyboardControlComponentSerializer());
		SerializerRegistry.registerComponentSerializer(TeamComponent.class, new TeamComponentSerializer());
		SerializerRegistry.registerComponentSerializer(HealthComponent.class, new HealthComponentSerializer());
		SerializerRegistry.registerComponentSerializer(LifetimeComponent.class, new LifetimeComponentSerializer());
		SerializerRegistry.registerComponentSerializer(ParticleComponent.class, new ParticleComponentSerializer());
		SerializerRegistry.registerComponentSerializer(BlinkComponent.class, new BlinkComponentSerializer());
		SerializerRegistry.registerComponentSerializer(DeathEffectComponent.class, new DeathEffectComponentSerializer());
		SerializerRegistry.registerComponentSerializer(ProjectileComponent.class, new ProjectileComponentSerializer());
		SerializerRegistry.registerComponentSerializer(ProjectileEmitterComponent.class, new ProjectileEmitterComponentSerializer());

		// Register Component Factories
		FactoryRegistry.registerComponentFactory(TransformComponent.class, new TransformComponentFactory());
		FactoryRegistry.registerComponentFactory(TagComponent.class, new TagComponentFactory());
		FactoryRegistry.registerComponentFactory(TeamComponent.class, new TeamComponentFactory());
		FactoryRegistry.registerComponentFactory(HealthComponent.class, new HealthComponentFactory());
		FactoryRegistry.registerComponentFactory(BlinkComponent.class, new BlinkComponentFactory());
		FactoryRegistry.registerComponentFactory(BoxCollider2DComponent.class, new BoxCollider2DComponentFactory());
		FactoryRegistry.registerComponentFactory(CircleCollider2DComponent.class, new CircleCollider2DComponentFactory());
		FactoryRegistry.registerComponentFactory(RigidBody2DComponent.class, new Rigidbody2DComponentFactory());

		// Register Component Renderers
		RendererRegistry.registerComponentRenderer(TransformComponent.class, new TransformComponentRenderer());
		RendererRegistry.registerComponentRenderer(TagComponent.class, new TagComponentRenderer());
		RendererRegistry.registerComponentRenderer(TeamComponent.class, new TeamComponentRenderer());
		RendererRegistry.registerComponentRenderer(HealthComponent.class, new HealthComponentRenderer());
		RendererRegistry.registerComponentRenderer(BlinkComponent.class, new BlinkComponentRenderer());
		RendererRegistry.registerComponentRenderer(BoxCollider2DComponent.class, new BoxCollider2DComponentRenderer());
		RendererRegistry.registerComponentRenderer(CircleCollider2DComponent.class, new CircleCollider2DComponentRenderer());
		RendererRegistry.registerComponentRenderer(RigidBody2DComponent.class, new RigidBody2DComponentRenderer());

		// Setup Scene
		sceneSerializer = new SceneSerializer();

		sceneManager = new SceneManager();
		sceneManager.addScene("editor", new EditorScene());
		sceneManager.switchScene("editor");

		// FrameBuffer
		framebuffer = new Framebuffer(getWindow().getWidth(), getWindow().getHeight(), List.of(FrameBufferAttachmentFormat.RGBA8, FrameBufferAttachmentFormat.RED_INTEGER));
		getRenderer().setFramebuffer(framebuffer);

		// Editor
		editorUI = new EditorUI(getWindow());
		editorUI.addEditorPanel(new ViewportPanel(framebuffer));
		editorUI.addEditorPanel(new SceneHierarchyPanel(sceneManager.getScene()));
		editorUI.addEditorPanel(new InspectorPanel());

		return true;
	}

	@Override
	public void onUpdate(float dt) {
		if (editorState == EditorState.PLAY) {
			sceneManager.onUpdate(dt);
		}
		editorUI.onUpdate(dt);
		editorUI.onRender(dt);
	}

	@Override
	public void onRender(float dt, Renderer renderer) {
		renderer.setClearColor(new Vector4f(0.07f, 0.13f, 0.17f, 1.0f));
		sceneManager.onRender(dt, renderer);
	}

	private void play() {
		Scene clone = sceneSerializer.clone(sceneManager.getScene(), true);
		sceneManager.setScene(clone);
		editorState = EditorState.PLAY;
	}

	private void pause() {
		editorState = EditorState.PAUSE;
	}

	private void stop() {
		sceneManager.switchScene("editor");
		editorState = EditorState.STOP;
	}
}
