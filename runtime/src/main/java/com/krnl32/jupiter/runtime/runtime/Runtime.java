package com.krnl32.jupiter.runtime.runtime;

import com.krnl32.jupiter.engine.asset.handle.AssetType;
import com.krnl32.jupiter.engine.asset.loader.AssetLoaderRegistry;
import com.krnl32.jupiter.engine.asset.registry.AssetRegistry;
import com.krnl32.jupiter.engine.asset.registry.AssetRegistrySerializer;
import com.krnl32.jupiter.engine.asset.serializer.AssetSerializerRegistry;
import com.krnl32.jupiter.engine.asset.types.TextureAsset;
import com.krnl32.jupiter.engine.core.Engine;
import com.krnl32.jupiter.engine.core.EngineSettings;
import com.krnl32.jupiter.engine.core.Logger;
import com.krnl32.jupiter.engine.project.ProjectContext;
import com.krnl32.jupiter.engine.project.ProjectLoader;
import com.krnl32.jupiter.engine.project.model.Project;
import com.krnl32.jupiter.engine.renderer.Renderer;
import com.krnl32.jupiter.engine.scene.SceneManager;
import com.krnl32.jupiter.engine.utility.FileIO;
import com.krnl32.jupiter.runtime.asset.RuntimeAssetManager;
import com.krnl32.jupiter.runtime.asset.loaders.JTextureAssetLoader;
import com.krnl32.jupiter.runtime.asset.serializers.JTextureAssetSerializer;
import org.json.JSONObject;

import java.nio.file.Path;

public class Runtime extends Engine {
	private final Path projectDirectory;
	private SceneManager sceneManager;

	public Runtime(EngineSettings engineSettings, Path projectDirectory) {
		super(engineSettings);
		this.projectDirectory = projectDirectory;
	}

	@Override
	public boolean onInit() {
		// Register Engine Components
		registerAssetSerializers();
		registerAssetLoaders();
		registerECSComponentSerializers();

		// Initialize Project Context
		if (!loadProject(projectDirectory)) {
			Logger.critical("Runtime Failed to Initialize Project({})", projectDirectory);
			return false;
		}

		// Setup Scene
		sceneManager = new SceneManager();

		/*
		if (ProjectContext.getInstance().getProject().getStartup().getSceneName() != null && !ProjectContext.getInstance().getProject().getStartup().getSceneName().isEmpty()) {
			String scenePath = ProjectContext.getInstance().getProjectDirectory() + "/" + ProjectContext.getInstance().getProject().getPaths().getScenePath() + "/" + ProjectContext.getInstance().getProject().getStartup().getSceneName();

			try {
				// must be registered as asset?
				// then assetManager get by path
				Scene startupScene = sceneSerializer.deserialize(new JSONObject(FileIO.readFileContent(scenePath)));
				if (startupScene == null) {
					Logger.critical("Editor Failed to Deserialize Startup Scene({})", scenePath);
					return false;
				}

				currentSceneName = ProjectContext.getInstance().getProject().getStartup().getSceneName();
				sceneManager.addScene(currentSceneName, startupScene);
				sceneManager.switchScene(currentSceneName);
			} catch (IOException e) {
				Logger.critical("Editor Failed to Deserialize Startup Scene({}), File Doesn't Exist", scenePath);
				return false;
			}
		} else {
			currentSceneName = "editor";
			sceneManager.addScene(currentSceneName, new EditorScene());
			sceneManager.switchScene(currentSceneName);
		}
		currentSceneName = "editor";
		sceneManager.addScene(currentSceneName, new EditorSceneTmp("EditorSceneTMP"));
		sceneManager.switchScene(currentSceneName);
		 */


		return false;
	}

	@Override
	public void onUpdate(float dt) {
		sceneManager.onUpdate(dt);
	}

	@Override
	public void onRender(float dt, Renderer renderer) {
		sceneManager.onRender(dt, renderer);
	}

	private boolean loadProject(Path projectDirectory) {
		Project project = ProjectLoader.load(projectDirectory);
		if (project == null) {
			Logger.error("Editor Failed to Load Project({})", projectDirectory);
			return false;
		}

		try {
			Path assetRegistryPath = projectDirectory.resolve(project.getPaths().getAssetRegistryPath());
			JSONObject assetRegistryData = new JSONObject(FileIO.readFileContent(assetRegistryPath));
			AssetRegistry assetRegistry = AssetRegistrySerializer.deserialize(assetRegistryData);

			ProjectContext.init(projectDirectory, project, new RuntimeAssetManager(assetRegistry));
		} catch (Exception e) {
			Logger.error("Editor Failed to Load Project({}): {}", projectDirectory, e.getMessage());
			return false;
		}

		return true;
	}

	private void registerAssetSerializers() {
		AssetSerializerRegistry.register(AssetType.TEXTURE, TextureAsset.class, new JTextureAssetSerializer());
	}

	private void registerAssetLoaders() {
		AssetLoaderRegistry.register(AssetType.TEXTURE, TextureAsset.class, new JTextureAssetLoader());
	}

	private void registerECSComponentSerializers() {

	}
}
