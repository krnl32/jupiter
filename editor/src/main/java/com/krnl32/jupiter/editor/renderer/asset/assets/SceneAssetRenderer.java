package com.krnl32.jupiter.editor.renderer.asset.assets;

import com.krnl32.jupiter.editor.asset.EditorAssetManager;
import com.krnl32.jupiter.editor.renderer.asset.AssetRenderer;
import com.krnl32.jupiter.editor.utility.GUIUtils;
import com.krnl32.jupiter.engine.asset.types.SceneAsset;
import com.krnl32.jupiter.engine.core.Logger;
import com.krnl32.jupiter.engine.project.ProjectContext;
import com.krnl32.jupiter.engine.scene.SceneSettings;
import com.krnl32.jupiter.engine.serializer.SceneSerializer;
import com.krnl32.jupiter.engine.utility.FileIO;
import imgui.ImGui;
import imgui.flag.ImGuiCol;
import imgui.flag.ImGuiStyleVar;
import imgui.flag.ImGuiTreeNodeFlags;
import imgui.type.ImBoolean;
import imgui.type.ImString;
import org.joml.Vector3f;
import org.json.JSONObject;

import java.nio.file.Path;

public class SceneAssetRenderer implements AssetRenderer<SceneAsset> {
	private final Vector3f physicsGravity = new Vector3f();

	@Override
	public void render(SceneAsset asset) {
		ImGui.spacing();
		ImGui.text("Scene Settings");
		ImGui.separator();

		SceneSettings settings = asset.getSettings();

		// General
		if (ImGui.collapsingHeader("General", ImGuiTreeNodeFlags.DefaultOpen)) {
			ImString name = new ImString(asset.getScene().getName() == null ? "" : asset.getScene().getName(), 256);
			if (GUIUtils.renderStringInputWithClearButton("Name", name)) {
				asset.getScene().setName(name.get());
			}
		}

		// Physics
		if (ImGui.collapsingHeader("Physics", ImGuiTreeNodeFlags.DefaultOpen)) {
			ImBoolean enabled = new ImBoolean(settings.getPhysicsSettings().isEnabled());
			if (ImGui.checkbox("Enabled", enabled)) {
				settings.getPhysicsSettings().setEnabled(enabled.get());
			}

			physicsGravity.set(settings.getPhysicsSettings().getGravity());
			if (GUIUtils.renderVector3f("Gravity", physicsGravity)) {
				settings.getPhysicsSettings().setGravity(new Vector3f(physicsGravity));
			}
		}

		ImGui.spacing();
		ImGui.separator();

		// Apply Button
		float buttonWidth = ImGui.getContentRegionAvailX();
		float buttonHeight = 45.0f;

		ImGui.pushStyleColor(ImGuiCol.Button, 0.227f, 0.427f, 0.941f, 1f);
		ImGui.pushStyleColor(ImGuiCol.ButtonHovered, 0.290f, 0.490f, 1.0f, 1f);
		ImGui.pushStyleColor(ImGuiCol.ButtonActive, 0.180f, 0.349f, 0.851f, 1f);
		ImGui.pushStyleVar(ImGuiStyleVar.FrameRounding, 6.0f);

		ImGui.setCursorPosX((ImGui.getWindowWidth() - buttonWidth) * 0.5f);
		if (ImGui.button("Apply Changes & Reload", buttonWidth, buttonHeight)) {
			EditorAssetManager editorAssetManager = ((EditorAssetManager) ProjectContext.getInstance().getAssetManager());

			var jsonScene = new JSONObject(SceneSerializer.serialize(asset.getScene()));
			try {
				Path scenePath = ProjectContext.getInstance().getAssetDirectory().resolve(editorAssetManager.getAssetPath(asset.getId()));
				FileIO.writeFileContent(scenePath, jsonScene.toString(4));
				editorAssetManager.reloadAsset(asset.getId());
			} catch (Exception e) {
				Logger.error("AssetInspectorPanel Failed to Save Scene({})", asset.getScene().getName());
			}
		}

		ImGui.popStyleVar();
		ImGui.popStyleColor(3);
	}
}
