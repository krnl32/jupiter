package com.krnl32.jupiter.editor.editor;

import com.krnl32.jupiter.engine.asset.AssetManager;
import com.krnl32.jupiter.engine.asset.types.TextureAsset;
import com.krnl32.jupiter.engine.components.effects.BlinkComponent;
import com.krnl32.jupiter.engine.components.effects.DeathEffectComponent;
import com.krnl32.jupiter.engine.components.effects.ParticleComponent;
import com.krnl32.jupiter.engine.components.gameplay.*;
import com.krnl32.jupiter.engine.components.input.KeyboardControlComponent;
import com.krnl32.jupiter.engine.components.physics.BoxCollider2DComponent;
import com.krnl32.jupiter.engine.components.physics.CircleCollider2DComponent;
import com.krnl32.jupiter.engine.components.physics.RigidBody2DComponent;
import com.krnl32.jupiter.engine.components.projectile.ProjectileComponent;
import com.krnl32.jupiter.engine.components.projectile.ProjectileEmitterComponent;
import com.krnl32.jupiter.engine.components.renderer.CameraComponent;
import com.krnl32.jupiter.engine.components.renderer.SpriteRendererComponent;
import com.krnl32.jupiter.engine.components.utility.LifetimeComponent;
import com.krnl32.jupiter.engine.components.utility.TagComponent;
import com.krnl32.jupiter.engine.components.utility.UUIDComponent;
import com.krnl32.jupiter.engine.core.Engine;
import com.krnl32.jupiter.engine.core.Logger;
import com.krnl32.jupiter.engine.event.EventBus;
import com.krnl32.jupiter.engine.renderer.FrameBufferAttachmentFormat;
import com.krnl32.jupiter.engine.renderer.Framebuffer;
import com.krnl32.jupiter.engine.renderer.ProjectionType;
import com.krnl32.jupiter.engine.renderer.Renderer;
import com.krnl32.jupiter.editor.events.editor.EditorPauseEvent;
import com.krnl32.jupiter.editor.events.editor.EditorPlayEvent;
import com.krnl32.jupiter.editor.events.editor.EditorStopEvent;
import com.krnl32.jupiter.editor.factory.FactoryRegistry;
import com.krnl32.jupiter.editor.factory.components.effects.BlinkComponentFactory;
import com.krnl32.jupiter.editor.factory.components.effects.DeathEffectComponentFactory;
import com.krnl32.jupiter.editor.factory.components.effects.ParticleComponentFactory;
import com.krnl32.jupiter.editor.factory.components.gameplay.*;
import com.krnl32.jupiter.editor.factory.components.input.KeyboardControlComponentFactory;
import com.krnl32.jupiter.editor.factory.components.physics.BoxCollider2DComponentFactory;
import com.krnl32.jupiter.editor.factory.components.physics.CircleCollider2DComponentFactory;
import com.krnl32.jupiter.editor.factory.components.physics.Rigidbody2DComponentFactory;
import com.krnl32.jupiter.editor.factory.components.projectile.ProjectileComponentFactory;
import com.krnl32.jupiter.editor.factory.components.projectile.ProjectileEmitterComponentFactory;
import com.krnl32.jupiter.editor.factory.components.renderer.CameraComponentFactory;
import com.krnl32.jupiter.editor.factory.components.renderer.SpriteRendererComponentFactory;
import com.krnl32.jupiter.editor.factory.components.utility.LifetimeComponentFactory;
import com.krnl32.jupiter.editor.factory.components.utility.TagComponentFactory;
import com.krnl32.jupiter.editor.factory.components.utility.UUIDComponentFactory;
import com.krnl32.jupiter.editor.panels.InspectorPanel;
import com.krnl32.jupiter.editor.panels.SceneHierarchyPanel;
import com.krnl32.jupiter.editor.panels.ViewportPanel;
import com.krnl32.jupiter.editor.renderer.*;
import com.krnl32.jupiter.editor.renderer.components.effects.BlinkComponentRenderer;
import com.krnl32.jupiter.editor.renderer.components.effects.ParticleComponentRenderer;
import com.krnl32.jupiter.editor.renderer.components.gameplay.*;
import com.krnl32.jupiter.editor.renderer.components.physics.BoxCollider2DComponentRenderer;
import com.krnl32.jupiter.editor.renderer.components.physics.CircleCollider2DComponentRenderer;
import com.krnl32.jupiter.editor.renderer.components.physics.RigidBody2DComponentRenderer;
import com.krnl32.jupiter.editor.renderer.components.renderer.SpriteRendererComponentRenderer;
import com.krnl32.jupiter.editor.renderer.components.utility.LifetimeComponentRenderer;
import com.krnl32.jupiter.editor.renderer.components.utility.TagComponentRenderer;
import com.krnl32.jupiter.editor.renderer.components.utility.UUIDComponentRenderer;
import com.krnl32.jupiter.engine.scene.Scene;
import com.krnl32.jupiter.engine.scene.SceneManager;
import com.krnl32.jupiter.engine.serializer.SceneSerializer;
import org.joml.Vector4f;

import java.util.List;

public class Editor extends Engine {
	private Framebuffer framebuffer;
	private EditorUI editorUI;
	private SceneManager sceneManager;
	private EditorState editorState;
	private SceneSerializer sceneSerializer;
	private EditorCamera editorCamera;

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

		// Register Editor Components
		registerComponentFactories();
		registerComponentRenderers();

		// Setup Scene
		sceneSerializer = new SceneSerializer();

		sceneManager = new SceneManager();
		sceneManager.addScene("editor", new EditorScene());
		sceneManager.switchScene("editor");

		// FrameBuffer
		framebuffer = new Framebuffer(getWindow().getWidth(), getWindow().getHeight(), List.of(FrameBufferAttachmentFormat.RGBA8, FrameBufferAttachmentFormat.RED_INTEGER));
		getRenderer().setFramebuffer(framebuffer);

		editorCamera = new EditorCamera(ProjectionType.ORTHOGRAPHIC, 10.0f);

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
		} else {
			editorCamera.onUpdate(dt);
		}

		editorUI.onUpdate(dt);
		editorUI.onRender(dt);
	}

	@Override
	public void onRender(float dt, Renderer renderer) {
		renderer.setClearColor(new Vector4f(0.07f, 0.13f, 0.17f, 1.0f));

		if (editorState != EditorState.PLAY && renderer.getActiveCamera() != editorCamera.getCamera()) {
			renderer.setActiveCamera(editorCamera.getCamera());
		}

		sceneManager.onRender(dt, renderer);
	}

	private void play() {
		Scene clone = sceneSerializer.clone(sceneManager.getScene(), false);
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

	private void registerComponentFactories() {
		// Effects
		FactoryRegistry.registerComponentFactory(BlinkComponent.class, new BlinkComponentFactory());
		FactoryRegistry.registerComponentFactory(DeathEffectComponent.class, new DeathEffectComponentFactory());
		FactoryRegistry.registerComponentFactory(ParticleComponent.class, new ParticleComponentFactory());

		// Gameplay
		FactoryRegistry.registerComponentFactory(TransformComponent.class, new TransformComponentFactory());
		FactoryRegistry.registerComponentFactory(TeamComponent.class, new TeamComponentFactory());
		FactoryRegistry.registerComponentFactory(HealthComponent.class, new HealthComponentFactory());
		FactoryRegistry.registerComponentFactory(MovementIntentComponent.class, new MovementIntentComponentFactory());
		FactoryRegistry.registerComponentFactory(ForceMovementComponent.class, new ForceMovementComponentFactory());
		FactoryRegistry.registerComponentFactory(ScriptComponent.class, new ScriptComponentFactory());

		// Input
		FactoryRegistry.registerComponentFactory(KeyboardControlComponent.class, new KeyboardControlComponentFactory());

		// Physics
		FactoryRegistry.registerComponentFactory(RigidBody2DComponent.class, new Rigidbody2DComponentFactory());
		FactoryRegistry.registerComponentFactory(BoxCollider2DComponent.class, new BoxCollider2DComponentFactory());
		FactoryRegistry.registerComponentFactory(CircleCollider2DComponent.class, new CircleCollider2DComponentFactory());

		// Projectile
		FactoryRegistry.registerComponentFactory(ProjectileComponent.class, new ProjectileComponentFactory());
		FactoryRegistry.registerComponentFactory(ProjectileEmitterComponent.class, new ProjectileEmitterComponentFactory());

		// Renderer
		FactoryRegistry.registerComponentFactory(CameraComponent.class, new CameraComponentFactory());
		FactoryRegistry.registerComponentFactory(SpriteRendererComponent.class, new SpriteRendererComponentFactory());

		// Utility
		FactoryRegistry.registerComponentFactory(UUIDComponent.class, new UUIDComponentFactory());
		FactoryRegistry.registerComponentFactory(TagComponent.class, new TagComponentFactory());
		FactoryRegistry.registerComponentFactory(LifetimeComponent.class, new LifetimeComponentFactory());
	}

	private void registerComponentRenderers() {
		// Effects
		RendererRegistry.registerComponentRenderer(BlinkComponent.class, new BlinkComponentRenderer());
		RendererRegistry.registerComponentRenderer(ParticleComponent.class, new ParticleComponentRenderer());

		// Gameplay
		RendererRegistry.registerComponentRenderer(TransformComponent.class, new TransformComponentRenderer());
		RendererRegistry.registerComponentRenderer(TeamComponent.class, new TeamComponentRenderer());
		RendererRegistry.registerComponentRenderer(HealthComponent.class, new HealthComponentRenderer());
		RendererRegistry.registerComponentRenderer(MovementIntentComponent.class, new MovementIntentComponentRenderer());
		RendererRegistry.registerComponentRenderer(ForceMovementComponent.class, new ForceMovementComponentRenderer());
		RendererRegistry.registerComponentRenderer(ScriptComponent.class, new ScriptComponentRenderer());

		// Input

		// Physics
		RendererRegistry.registerComponentRenderer(RigidBody2DComponent.class, new RigidBody2DComponentRenderer());
		RendererRegistry.registerComponentRenderer(BoxCollider2DComponent.class, new BoxCollider2DComponentRenderer());
		RendererRegistry.registerComponentRenderer(CircleCollider2DComponent.class, new CircleCollider2DComponentRenderer());

		// Projectile

		// Renderer
		RendererRegistry.registerComponentRenderer(SpriteRendererComponent.class, new SpriteRendererComponentRenderer());

		// Utility
		RendererRegistry.registerComponentRenderer(UUIDComponent.class, new UUIDComponentRenderer());
		RendererRegistry.registerComponentRenderer(TagComponent.class, new TagComponentRenderer());
		RendererRegistry.registerComponentRenderer(LifetimeComponent.class, new LifetimeComponentRenderer());
	}
}
