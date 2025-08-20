package com.krnl32.jupiter.editor.renderer.asset.assets;

import com.krnl32.jupiter.editor.asset.EditorAssetManager;
import com.krnl32.jupiter.editor.renderer.asset.AssetRenderer;
import com.krnl32.jupiter.engine.asset.handle.AssetMetadata;
import com.krnl32.jupiter.engine.asset.importsettings.types.ScriptAssetImportSettings;
import com.krnl32.jupiter.engine.asset.types.ScriptAsset;
import com.krnl32.jupiter.engine.project.ProjectContext;
import com.krnl32.jupiter.engine.script.ScriptSettings;
import com.krnl32.jupiter.engine.utility.FileIO;
import imgui.ImGui;
import imgui.flag.ImGuiCol;
import imgui.flag.ImGuiStyleVar;
import imgui.flag.ImGuiTreeNodeFlags;
import imgui.type.ImBoolean;

import java.nio.file.Path;

public class ScriptAssetRenderer implements AssetRenderer<ScriptAsset> {
	@Override
	public void render(ScriptAsset asset) {
		ImGui.spacing();
		ImGui.text("Script Settings");
		ImGui.separator();

		ScriptSettings settings = asset.getSettings();

		// General
		if (ImGui.collapsingHeader("General", ImGuiTreeNodeFlags.DefaultOpen)) {
			ImBoolean hotReload = new ImBoolean(settings.isHotReload());
			if (ImGui.checkbox("HotReload", hotReload)) {
				settings.setHotReload(hotReload.get());
			}

			ImBoolean compile = new ImBoolean(settings.isCompile());
			if (ImGui.checkbox("Compile", compile)) {
				settings.setCompile(compile.get());
			}
		}

		// Code Preview
		ImGui.spacing();
		ImGui.separator();
		ImGui.text("Preview");

		try {
			Path scriptPath = asset.getDefinition().getScriptPath();
			String code = FileIO.readFileContent(scriptPath);
			ImGui.beginChild("Code Preview", 0, 200, true);
			ImGui.textWrapped(code);
			ImGui.endChild();
		} catch (Exception e) {
			ImGui.textDisabled("No source code available.");
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
			AssetMetadata assetMetadata = editorAssetManager.getAssetMetadata(asset.getId());

			if (assetMetadata != null) {
				AssetMetadata updatedMetadata = new AssetMetadata(
					assetMetadata.getVersion(),
					assetMetadata.getAssetId(),
					assetMetadata.getAssetType(),
					assetMetadata.getAssetPath(),
					assetMetadata.getImporterName(),
					new ScriptAssetImportSettings(settings).toMap(),
					assetMetadata.getLastModified()
				);

				editorAssetManager.saveAssetMetadata(asset.getId(), updatedMetadata);
				editorAssetManager.reloadAsset(asset.getId());
			}
		}

		ImGui.popStyleVar();
		ImGui.popStyleColor(3);
	}
}
