package com.krnl32.jupiter.editor.editor;

import com.krnl32.jupiter.editor.asset.EditorAssetManager;
import com.krnl32.jupiter.editor.asset.loaders.PlainScriptAssetLoader;
import com.krnl32.jupiter.editor.asset.loaders.PlainTextureAssetLoader;
import com.krnl32.jupiter.editor.asset.serializers.JSONDTOSceneAssetSerializer;
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
import com.krnl32.jupiter.editor.renderer.RendererRegistry;
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
import com.krnl32.jupiter.engine.asset.database.AssetDatabase;
import com.krnl32.jupiter.engine.asset.handle.AssetType;
import com.krnl32.jupiter.engine.asset.loader.AssetLoaderRegistry;
import com.krnl32.jupiter.engine.asset.loader.types.DTOSceneAssetLoader;
import com.krnl32.jupiter.engine.asset.registry.AssetRegistry;
import com.krnl32.jupiter.engine.asset.registry.AssetRegistrySerializer;
import com.krnl32.jupiter.engine.asset.serializer.AssetSerializerRegistry;
import com.krnl32.jupiter.engine.asset.types.SceneAsset;
import com.krnl32.jupiter.engine.asset.types.ScriptAsset;
import com.krnl32.jupiter.engine.asset.types.TextureAsset;
import com.krnl32.jupiter.engine.cloner.SceneCloner;
import com.krnl32.jupiter.engine.cloner.utility.DefaultComponentCloners;
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
import com.krnl32.jupiter.engine.core.EngineSettings;
import com.krnl32.jupiter.engine.core.Logger;
import com.krnl32.jupiter.engine.event.EventBus;
import com.krnl32.jupiter.engine.project.ProjectContext;
import com.krnl32.jupiter.engine.project.ProjectLoader;
import com.krnl32.jupiter.engine.project.model.Project;
import com.krnl32.jupiter.engine.renderer.FrameBufferAttachmentFormat;
import com.krnl32.jupiter.engine.renderer.Framebuffer;
import com.krnl32.jupiter.engine.renderer.ProjectionType;
import com.krnl32.jupiter.engine.renderer.Renderer;
import com.krnl32.jupiter.engine.scene.Scene;
import com.krnl32.jupiter.engine.scene.SceneManager;
import com.krnl32.jupiter.engine.serializer.utility.DTOComponentSerializers;
import com.krnl32.jupiter.engine.utility.FileIO;
import org.joml.Vector4f;
import org.json.JSONObject;

import java.nio.file.Path;
import java.util.List;

public class Editor extends Engine {
	private Framebuffer framebuffer;
	private EditorUI editorUI;
	private SceneManager sceneManager;
	private EditorState editorState;
	private EditorCamera editorCamera;
	private final Path projectDirectory;
	private String currentSceneName;

	public Editor(EngineSettings engineSettings, Path projectDirectory) {
		super(engineSettings);
		this.editorState = EditorState.STOP;
		this.projectDirectory = projectDirectory;

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
		// Register Engine Components
		registerAssetSerializers();
		registerAssetLoaders();
		registerECSComponentSerializers();
		DefaultComponentCloners.registerAll();

		// Load Project
		if (!loadProject(projectDirectory)) {
			Logger.critical("Editor Failed to Initialize Project({})", projectDirectory);
			return false;
		}

		// Register Editor Components
		registerComponentFactories();
		registerComponentRenderers();

		// Setup Scene
		sceneManager = new SceneManager();

		String startupScenePath = ProjectContext.getInstance().getProject().getStartup().getScenePath();
		if (startupScenePath != null && !startupScenePath.isEmpty()) {
			SceneAsset sceneAsset = ((EditorAssetManager) ProjectContext.getInstance().getAssetManager()).getAsset(startupScenePath);
			if (sceneAsset == null) {
				Logger.critical("Editor Failed to Load Startup Scene({})", startupScenePath);
				return false;
			}

			currentSceneName = sceneAsset.getScene().getName();
			sceneManager.addScene(currentSceneName, sceneAsset.getScene());
			sceneManager.switchScene(currentSceneName);
		} else {
			currentSceneName = "Sandbox";
			sceneManager.addScene(currentSceneName, new EditorScene(currentSceneName));
			sceneManager.switchScene(currentSceneName);
		}

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
		Scene clone = SceneCloner.clone(sceneManager.getScene(), false);
		sceneManager.setScene(clone);
		editorState = EditorState.PLAY;
	}

	private void pause() {
		editorState = EditorState.PAUSE;
	}

	private void stop() {
		sceneManager.switchScene(currentSceneName);
		editorState = EditorState.STOP;
	}

	private void registerAssetSerializers() {
		AssetSerializerRegistry.register(AssetType.SCENE, SceneAsset.class, new JSONDTOSceneAssetSerializer());
	}

	private void registerAssetLoaders() {
		AssetLoaderRegistry.register(AssetType.TEXTURE, TextureAsset.class, new PlainTextureAssetLoader());
		AssetLoaderRegistry.register(AssetType.SCENE, SceneAsset.class, new DTOSceneAssetLoader());
		AssetLoaderRegistry.register(AssetType.SCRIPT, ScriptAsset.class, new PlainScriptAssetLoader());
	}

	private void registerECSComponentSerializers() {
		DTOComponentSerializers.registerAll();
	}

	private boolean loadProject(Path projectDirectory) {
		Project project = ProjectLoader.load(projectDirectory);
		if (project == null) {
			Logger.error("Editor Failed to Load Project({})", projectDirectory);
			return false;
		}

		try {
			// Setup Asset Manager
			Path assetRegistryPath = projectDirectory.resolve(project.getPaths().getAssetRegistryPath());
			JSONObject assetRegistryData = new JSONObject(FileIO.readFileContent(assetRegistryPath));
			AssetRegistry assetRegistry = AssetRegistrySerializer.deserialize(assetRegistryData);

			Path assetDatabasePath = projectDirectory.resolve(project.getPaths().getAssetDatabasePath());
			AssetDatabase assetDatabase = new AssetDatabase();
			assetDatabase.loadFromDisk(assetDatabasePath);

			ProjectContext.init(projectDirectory, project, new EditorAssetManager(assetRegistry, assetDatabase));
		} catch (Exception e) {
			Logger.error("Editor Failed to Load Project({}): {}", projectDirectory, e.getMessage());
			return false;
		}

		return true;
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
