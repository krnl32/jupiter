package com.krnl32.jupiter.editor;

import com.krnl32.jupiter.asset.AssetManager;
import com.krnl32.jupiter.asset.TextureAsset;
import com.krnl32.jupiter.components.effects.BlinkComponent;
import com.krnl32.jupiter.components.gameplay.HealthComponent;
import com.krnl32.jupiter.components.gameplay.TeamComponent;
import com.krnl32.jupiter.components.gameplay.TransformComponent;
import com.krnl32.jupiter.components.physics.BoxCollider2DComponent;
import com.krnl32.jupiter.components.physics.CircleCollider2DComponent;
import com.krnl32.jupiter.components.physics.RigidBody2DComponent;
import com.krnl32.jupiter.components.utility.TagComponent;
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
import com.krnl32.jupiter.renderer.*;
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

		// Register Component Factories
		// Effects
		FactoryRegistry.registerComponentFactory(BlinkComponent.class, new BlinkComponentFactory());

		FactoryRegistry.registerComponentFactory(TransformComponent.class, new TransformComponentFactory());
		FactoryRegistry.registerComponentFactory(TagComponent.class, new TagComponentFactory());
		FactoryRegistry.registerComponentFactory(TeamComponent.class, new TeamComponentFactory());
		FactoryRegistry.registerComponentFactory(HealthComponent.class, new HealthComponentFactory());
		FactoryRegistry.registerComponentFactory(BoxCollider2DComponent.class, new BoxCollider2DComponentFactory());
		FactoryRegistry.registerComponentFactory(CircleCollider2DComponent.class, new CircleCollider2DComponentFactory());
		FactoryRegistry.registerComponentFactory(RigidBody2DComponent.class, new Rigidbody2DComponentFactory());

		// Register Component Renderers
		// Effects
		RendererRegistry.registerComponentRenderer(BlinkComponent.class, new BlinkComponentRenderer());

		RendererRegistry.registerComponentRenderer(TransformComponent.class, new TransformComponentRenderer());
		RendererRegistry.registerComponentRenderer(TagComponent.class, new TagComponentRenderer());
		RendererRegistry.registerComponentRenderer(TeamComponent.class, new TeamComponentRenderer());
		RendererRegistry.registerComponentRenderer(HealthComponent.class, new HealthComponentRenderer());
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
}
