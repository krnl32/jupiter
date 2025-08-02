package com.krnl32.jupiter.launcher.editor;

import com.krnl32.jupiter.engine.core.Logger;
import com.krnl32.jupiter.engine.utility.FileIO;
import org.json.JSONArray;
import org.json.JSONObject;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class EditorManager {
	private final List<JEditor> editors;
	private final Path configFilePath;
	private String selectedEditorPath;

	public EditorManager() {
		this.editors = new ArrayList<>();
		this.configFilePath = Paths.get(System.getProperty("user.home"), ".jupiter", "launcher", "editors.json");

		if (!initConfig()) {
			Logger.error("EditorManager Failed to Init Config");
			return;
		}

		loadEditors();
	}

	public void addEditor(JEditor editor) {
		if (!editors.contains(editor)) {
			editors.add(editor);
			saveEditors();
		}
	}

	public void removeEditor(JEditor editor) {
		if (editors.contains(editor)) {
			editors.remove(editor);
			if (editor.getPath().equals(selectedEditorPath)) {
				selectedEditorPath = null;
			}
			saveEditors();
		}
	}

	public List<JEditor> getEditors() {
		return Collections.unmodifiableList(editors);
	}

	public void reloadEditors() {
		editors.clear();
		loadEditors();
	}

	public String getSelectedEditorPath() {
		return selectedEditorPath;
	}

	public void setSelectedEditorPath(String path) {
		selectedEditorPath = path;
		saveEditors();
	}

	public JEditor getSelectedEditor() {
		for (JEditor editor : editors) {
			if (editor.getPath().equals(selectedEditorPath)) {
				return editor;
			}
		}
		return null;
	}

	private void loadEditors() {
		if (!Files.exists(configFilePath)) {
			Logger.error("EditorManager Failed to Load Editors, ConfigFile({}) Doesn't Exist", configFilePath);
			return;
		}

		try {
			JSONObject configFileData = new JSONObject(FileIO.readFileContent(configFilePath.toString()));

			selectedEditorPath = configFileData.optString("selectedEditorPath", null);

			JSONArray editorsArrayData = configFileData.getJSONArray("editors");
			if (editorsArrayData != null) {
				for (int i = 0; i < editorsArrayData.length(); i++) {
					JSONObject editorData = editorsArrayData.getJSONObject(i);
					editors.add(new JEditor(editorData.getString("path"), editorData.getString("version")));
				}
			}
		} catch (Exception e) {
			Logger.error("EditorManager Failed to Load Editors: {}", e.getMessage());
		}
	}

	private void saveEditors() {
		JSONArray editorsArrayData = new JSONArray();
		for (JEditor editor : editors) {
			JSONObject editorData =
				new JSONObject()
					.put("path", editor.getPath())
					.put("version", editor.getVersion());
			editorsArrayData.put(editorData);
		}

		try {
			JSONObject configFileData = new JSONObject()
				.put("editors", editorsArrayData)
				.put("selectedEditorPath", selectedEditorPath != null ? selectedEditorPath : JSONObject.NULL);

			FileIO.writeFileContent(configFilePath.toString(), configFileData.toString(4));
		} catch (Exception e) {
			Logger.error("EditorManager Failed to Save Editors: {}", e.getMessage());
		}
	}

	private boolean initConfig() {
		try {
			Files.createDirectories(configFilePath.getParent());
			if (Files.notExists(configFilePath)) {
				Files.createFile(configFilePath);
				FileIO.writeFileContent(configFilePath.toString(), new JSONObject().put("editors", new JSONArray()).toString(4));
			}
		} catch (Exception e) {
			Logger.error("EditorManager Failed to Generate ConfigFile({}): {}", configFilePath, e.getMessage());
			return false;
		}
		return true;
	}
}
