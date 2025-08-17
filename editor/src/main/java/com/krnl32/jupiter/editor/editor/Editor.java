package com.krnl32.jupiter.editor.editor;

import com.krnl32.jupiter.editor.asset.EditorAssetManager;
import com.krnl32.jupiter.editor.asset.importers.JSONSceneAssetImporter;
import com.krnl32.jupiter.editor.asset.loaders.EditorLuaScriptAssetLoader;
import com.krnl32.jupiter.editor.asset.loaders.EditorRasterTextureAssetLoader;
import com.krnl32.jupiter.editor.asset.loaders.EditorSceneAssetLoader;
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
import com.krnl32.jupiter.editor.panels.*;
import com.krnl32.jupiter.editor.renderer.EditorRendererRegistry;
import com.krnl32.jupiter.editor.renderer.asset.assets.SceneAssetRenderer;
import com.krnl32.jupiter.editor.renderer.asset.assets.TextureAssetRenderer;
import com.krnl32.jupiter.editor.renderer.component.components.effects.BlinkComponentRenderer;
import com.krnl32.jupiter.editor.renderer.component.components.effects.ParticleComponentRenderer;
import com.krnl32.jupiter.editor.renderer.component.components.gameplay.*;
import com.krnl32.jupiter.editor.renderer.component.components.physics.BoxCollider2DComponentRenderer;
import com.krnl32.jupiter.editor.renderer.component.components.physics.CircleCollider2DComponentRenderer;
import com.krnl32.jupiter.editor.renderer.component.components.physics.RigidBody2DComponentRenderer;
import com.krnl32.jupiter.editor.renderer.component.components.renderer.SpriteRendererComponentRenderer;
import com.krnl32.jupiter.editor.renderer.component.components.utility.LifetimeComponentRenderer;
import com.krnl32.jupiter.editor.renderer.component.components.utility.TagComponentRenderer;
import com.krnl32.jupiter.editor.renderer.component.components.utility.UUIDComponentRenderer;
import com.krnl32.jupiter.engine.asset.database.AssetPersistence;
import com.krnl32.jupiter.engine.asset.database.AssetRepository;
import com.krnl32.jupiter.engine.asset.handle.AssetType;
import com.krnl32.jupiter.editor.asset.importers.LuaScriptAssetImporter;
import com.krnl32.jupiter.editor.asset.importers.RasterTextureAssetImporter;
import com.krnl32.jupiter.engine.asset.loader.AssetLoaderRegistry;
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
import com.krnl32.jupiter.engine.script.lua.utility.LuaComponentBinders;
import com.krnl32.jupiter.engine.serializer.utility.DefaultComponentSerializers;
import org.joml.Vector4f;

import java.nio.file.Path;
import java.util.List;

public class Editor extends Engine {
	private Framebuffer framebuffer;
	private EditorUI editorUI;
	private SceneManager sceneManager;
	private EditorState editorState;
	private EditorCamera editorCamera;
	private final Path projectDirectory;
	private Scene previousScene;

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
		if (!initProjectContext(projectDirectory)) {
			Logger.critical("Editor Failed to Initialize Project({})", projectDirectory);
			return false;
		}

		// Register Engine Components
		registerAssetImporters();
		registerAssetLoaders();
		registerComponentBinders();
		registerComponentSerializers();
		registerComponentCloners();

		// Register Editor Components
		registerComponentFactories();
		registerComponentRenderers();
		registerAssetRenderers();

		// Setup Scene
		sceneManager = new SceneManager();

		String startupScenePath = ProjectContext.getInstance().getProject().getStartup().getScenePath();
		if (startupScenePath != null && !startupScenePath.isEmpty()) {
			SceneAsset sceneAsset = ((EditorAssetManager) ProjectContext.getInstance().getAssetManager()).getAsset(startupScenePath);
			if (sceneAsset == null) {
				Logger.critical("Editor Failed to Load Startup Scene({})", startupScenePath);
				return false;
			}

			sceneManager.addScene(sceneAsset.getScene().getName(), sceneAsset.getScene());
			sceneManager.switchScene(sceneAsset.getScene().getName());
		} else {
			sceneManager.addScene("Sandbox", new EditorScene("Sandbox"));
			sceneManager.switchScene("Sandbox");
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
		editorUI.addEditorPanel(new ContentBrowserPanel());

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
		renderer.setClearColor(new Vector4f(0.06f, 0.07f, 0.09f, 1.0f));

		if (editorState != EditorState.PLAY && renderer.getActiveCamera() != editorCamera.getCamera()) {
			renderer.setActiveCamera(editorCamera.getCamera());
		}

		sceneManager.onRender(dt, renderer);
	}

	private void play() {
		previousScene = sceneManager.getScene();
		Scene clone = SceneCloner.clone(sceneManager.getScene(), false);
		sceneManager.setScene(clone);
		editorState = EditorState.PLAY;
	}

	private void pause() {
		editorState = EditorState.PAUSE;
	}

	private void stop() {
		sceneManager.setScene(previousScene);
		editorState = EditorState.STOP;
	}

	private boolean initProjectContext(Path projectDirectory) {
		Project project = ProjectLoader.load(projectDirectory);
		if (project == null) {
			Logger.error("Editor Failed to Load Project({})", projectDirectory);
			return false;
		}

		try {
			// Setup Asset Manager
			Path assetDatabasePath = projectDirectory.resolve(project.getPaths().getAssetDatabasePath());
			Path assetRegistryPath = projectDirectory.resolve(project.getPaths().getAssetRegistryPath());
			ProjectContext.init(projectDirectory, project, new EditorAssetManager(new AssetRepository(new AssetPersistence(assetDatabasePath, assetRegistryPath))));
		} catch (Exception e) {
			Logger.error("Editor Failed to Load Project({}): {}", projectDirectory, e.getMessage());
			return false;
		}

		return true;
	}

	private void registerAssetImporters() {
		((EditorAssetManager) ProjectContext.getInstance().getAssetManager()).registerAssetImporter(new RasterTextureAssetImporter());
		((EditorAssetManager) ProjectContext.getInstance().getAssetManager()).registerAssetImporter(new JSONSceneAssetImporter());
		((EditorAssetManager) ProjectContext.getInstance().getAssetManager()).registerAssetImporter(new LuaScriptAssetImporter());
	}

	private void registerAssetLoaders() {
		AssetLoaderRegistry.register(AssetType.TEXTURE, TextureAsset.class, new EditorRasterTextureAssetLoader());
		AssetLoaderRegistry.register(AssetType.SCENE, SceneAsset.class, new EditorSceneAssetLoader());
		AssetLoaderRegistry.register(AssetType.SCRIPT, ScriptAsset.class, new EditorLuaScriptAssetLoader());
	}

	private void registerComponentBinders() {
		LuaComponentBinders.registerAll();
	}

	private void registerComponentSerializers() {
		DefaultComponentSerializers.registerAll();
	}

	private void registerComponentCloners() {
		DefaultComponentCloners.registerAll();
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
		EditorRendererRegistry.registerComponentRenderer(BlinkComponent.class, new BlinkComponentRenderer());
		EditorRendererRegistry.registerComponentRenderer(ParticleComponent.class, new ParticleComponentRenderer());

		// Gameplay
		EditorRendererRegistry.registerComponentRenderer(TransformComponent.class, new TransformComponentRenderer());
		EditorRendererRegistry.registerComponentRenderer(TeamComponent.class, new TeamComponentRenderer());
		EditorRendererRegistry.registerComponentRenderer(HealthComponent.class, new HealthComponentRenderer());
		EditorRendererRegistry.registerComponentRenderer(MovementIntentComponent.class, new MovementIntentComponentRenderer());
		EditorRendererRegistry.registerComponentRenderer(ForceMovementComponent.class, new ForceMovementComponentRenderer());
		EditorRendererRegistry.registerComponentRenderer(ScriptComponent.class, new ScriptComponentRenderer());

		// Input

		// Physics
		EditorRendererRegistry.registerComponentRenderer(RigidBody2DComponent.class, new RigidBody2DComponentRenderer());
		EditorRendererRegistry.registerComponentRenderer(BoxCollider2DComponent.class, new BoxCollider2DComponentRenderer());
		EditorRendererRegistry.registerComponentRenderer(CircleCollider2DComponent.class, new CircleCollider2DComponentRenderer());

		// Projectile

		// Renderer
		EditorRendererRegistry.registerComponentRenderer(SpriteRendererComponent.class, new SpriteRendererComponentRenderer());

		// Utility
		EditorRendererRegistry.registerComponentRenderer(UUIDComponent.class, new UUIDComponentRenderer());
		EditorRendererRegistry.registerComponentRenderer(TagComponent.class, new TagComponentRenderer());
		EditorRendererRegistry.registerComponentRenderer(LifetimeComponent.class, new LifetimeComponentRenderer());
	}

	private void registerAssetRenderers() {
		EditorRendererRegistry.registerAssetRenderer(AssetType.TEXTURE, new TextureAssetRenderer());
		EditorRendererRegistry.registerAssetRenderer(AssetType.SCENE, new SceneAssetRenderer());
	}
}
