package com.krnl32.jupiter.editor.editor;

import com.krnl32.jupiter.editor.asset.EditorAssetManager;
import com.krnl32.jupiter.editor.build.BuildContext;
import com.krnl32.jupiter.editor.build.BuildPipeline;
import com.krnl32.jupiter.editor.build.steps.AssetRegistryGenerationStep;
import com.krnl32.jupiter.editor.build.steps.AssetSerializationStep;
import com.krnl32.jupiter.editor.build.steps.AssetValidationStep;
import com.krnl32.jupiter.editor.events.editor.EditorBuildEvent;
import com.krnl32.jupiter.editor.events.editor.EditorPauseEvent;
import com.krnl32.jupiter.editor.events.editor.EditorPlayEvent;
import com.krnl32.jupiter.editor.events.editor.EditorStopEvent;
import com.krnl32.jupiter.editor.panels.ContentBrowserPanel;
import com.krnl32.jupiter.editor.panels.InspectorPanel;
import com.krnl32.jupiter.editor.panels.SceneHierarchyPanel;
import com.krnl32.jupiter.editor.panels.ViewportPanel;
import com.krnl32.jupiter.engine.asset.database.AssetPersistence;
import com.krnl32.jupiter.engine.asset.database.AssetRepository;
import com.krnl32.jupiter.engine.asset.handle.AssetType;
import com.krnl32.jupiter.engine.asset.serializer.AssetSerializerRegistry;
import com.krnl32.jupiter.engine.asset.serializers.JTextureAssetSerializer;
import com.krnl32.jupiter.engine.asset.types.SceneAsset;
import com.krnl32.jupiter.engine.cloner.SceneCloner;
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
import com.krnl32.jupiter.engine.sceneserializer.ComponentSerializerRegistry;
import com.krnl32.jupiter.engine.sceneserializer.ComponentSerializerRegistryFactory;
import com.krnl32.jupiter.engine.sceneserializer.SceneSerializer;
import com.krnl32.jupiter.engine.sceneserializer.data.scene.DataSceneSerializer;
import org.joml.Vector4f;

import java.nio.file.Path;
import java.util.List;
import java.util.Map;

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

		EditorAssetManager editorAssetManager = (EditorAssetManager) ProjectContext.getInstance().getAssetManager();

		// Register Engine Components
		ComponentSerializerRegistry componentSerializerRegistry = ComponentSerializerRegistryFactory.createDataComponentSerializerRegistry();
		SceneSerializer<Map<String, Object>> sceneSerializer = new DataSceneSerializer(componentSerializerRegistry);

		EditorRegistry.registerAssetImporters(editorAssetManager, sceneSerializer);
		EditorRegistry.registerAssetLoaders(sceneSerializer);
		EditorRegistry.registerComponentCloners();
		EditorRegistry.registerComponentBinders();

		// Register Editor Components
		EditorRegistry.registerComponentFactories();
		EditorRegistry.registerComponentRenderers();
		EditorRegistry.registerAssetRenderers(sceneSerializer);

		// Register Editor Build Pipeline
		registerBuildPipelines();

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
		if (editorState == EditorState.STOP) {
			previousScene = sceneManager.getScene();
			Scene clone = SceneCloner.clone(sceneManager.getScene(), false);
			sceneManager.setScene(clone);
		}
		editorState = EditorState.PLAY;
	}

	private void pause() {
		editorState = EditorState.PAUSE;
	}

	private void stop() {
		if (previousScene != null) {
			sceneManager.setScene(previousScene);
		}
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

	private void registerBuildPipelines() {
		EditorAssetManager editorAssetManager = (EditorAssetManager) ProjectContext.getInstance().getAssetManager();
		Path assetBuildOutputDirectory = ProjectContext.getInstance().getAssetDirectory().resolve("Build");

		// Step Asset Serializers
		AssetSerializerRegistry assetSerializerRegistry = new AssetSerializerRegistry();
		assetSerializerRegistry.register(AssetType.TEXTURE, new JTextureAssetSerializer());

		// Setup Build Pipeline
		BuildPipeline buildPipeline = new BuildPipeline();
		buildPipeline.addStep(new AssetValidationStep(editorAssetManager));
		buildPipeline.addStep(new AssetSerializationStep(editorAssetManager, assetSerializerRegistry));
		buildPipeline.addStep(new AssetRegistryGenerationStep(ProjectContext.getInstance().getAssetRegistryPath()));

		// Setup Build Context
		BuildContext buildContext = new BuildContext();
		buildContext.setOutputDirectory(assetBuildOutputDirectory);

		// Register Editor Build Event
		EventBus.getInstance().register(EditorBuildEvent.class, event -> {
			buildPipeline.execute(buildContext);
		});
	}
}
