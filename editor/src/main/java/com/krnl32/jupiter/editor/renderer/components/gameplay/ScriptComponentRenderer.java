package com.krnl32.jupiter.editor.renderer.components.gameplay;

import com.krnl32.jupiter.engine.asset.AssetManager;
import com.krnl32.jupiter.engine.asset.AssetType;
import com.krnl32.jupiter.engine.asset.types.ScriptAsset;
import com.krnl32.jupiter.engine.script.ScriptInstance;
import com.krnl32.jupiter.engine.components.gameplay.ScriptComponent;
import com.krnl32.jupiter.editor.renderer.ComponentRenderer;
import com.krnl32.jupiter.editor.utility.GUIUtils;
import imgui.ImGui;
import imgui.flag.ImGuiCol;
import imgui.flag.ImGuiTreeNodeFlags;
import imgui.type.ImString;

import java.util.List;
import java.util.stream.Collectors;

public class ScriptComponentRenderer implements ComponentRenderer<ScriptComponent> {

	private final ImString addScriptFilter = new ImString(256);
	private ScriptInstance scriptToRemove = null;

	@Override
	public void render(ScriptComponent component) {
		renderScriptComponent(component);

		ImGui.spacing();
		ImGui.separator();
		ImGui.spacing();

		renderAddScriptSection(component);

		if (scriptToRemove != null) {
			component.scripts.remove(scriptToRemove);
			scriptToRemove = null;
		}
	}

	private void renderScriptComponent(ScriptComponent scriptComponent) {
		for (int i = 0; i < scriptComponent.scripts.size(); i++) {
			ScriptInstance script = scriptComponent.scripts.get(i);
			ImGui.pushID(i);

			// Remove Button
			ImGui.pushStyleColor(ImGuiCol.Button, 0.8f, 0.1f, 0.1f, 1f);
			ImGui.pushStyleColor(ImGuiCol.ButtonHovered, 1f, 0.3f, 0.3f, 1f);
			ImGui.pushStyleColor(ImGuiCol.ButtonActive, 1f, 0.2f, 0.2f, 1f);
			if (ImGui.button("X##Remove" + i, 27, 27)) {
				scriptToRemove = script;
			}
			ImGui.popStyleColor(3);
			ImGui.sameLine();

			// Reload Button
			ImGui.pushStyleColor(ImGuiCol.Button, 0.2f, 0.6f, 0.9f, 1f);
			ImGui.pushStyleColor(ImGuiCol.ButtonHovered, 0.3f, 0.7f, 1f, 1f);
			ImGui.pushStyleColor(ImGuiCol.ButtonActive, 0.1f, 0.5f, 0.8f, 1f);
			if (ImGui.button("R##Reload" + i, 27, 27)) {
				script.setLastModified(0);
			}
			ImGui.popStyleColor(3);
			ImGui.sameLine();

			String headerLabel = "\uf121 Script " + (i + 1);
			if (ImGui.collapsingHeader(headerLabel, ImGuiTreeNodeFlags.DefaultOpen)) {
				renderScript(script, scriptComponent);
			}

			ImGui.popID();
		}
	}

	private void renderScript(ScriptInstance script, ScriptComponent scriptComponent) {
		List<ScriptAsset> filteredScripts = AssetManager.getInstance().getRegisteredAssetsByType(AssetType.SCRIPT)
			.stream()
			.map(asset -> (ScriptAsset) asset)
			.filter(scriptAsset -> scriptComponent.scripts.stream()
				.filter(existing -> !existing.equals(script))
				.noneMatch(existing -> existing.getScriptAssetID() != null && existing.getScriptAssetID().equals(scriptAsset.getId())))
			.collect(Collectors.toList());

		ScriptAsset scriptAsset = (script.getScriptAssetID() != null) ? AssetManager.getInstance().getAsset(script.getScriptAssetID()) : null;
		String scriptPath = (scriptAsset != null) ? scriptAsset.getRelativePath() : "<None>";

		GUIUtils.renderAssetCombo(
			filteredScripts,
			"Script",
			scriptPath,
			script.getScriptAssetID(),
			newID -> {
				script.setScriptAssetID(newID);
				script.setLastModified(0);
			});

		if (script.getScriptAssetID() != null) {
			GUIUtils.renderLongReadOnly("Last Modified", script.getLastModified());
			GUIUtils.renderStringReadOnly("Status", script.isDisabled() ? "Disabled" : (script.isInitialized() ? "Active" : "Pending Init"));
		}
	}

	private void renderAddScriptSection(ScriptComponent scriptComponent) {
		ImGui.spacing();

		ImGui.pushStyleColor(ImGuiCol.Button, 0.2f, 0.6f, 0.9f, 1f);
		ImGui.pushStyleColor(ImGuiCol.ButtonHovered, 0.3f, 0.7f, 1f, 1f);
		ImGui.pushStyleColor(ImGuiCol.ButtonActive, 0.1f, 0.5f, 0.8f, 1f);

		if (ImGui.button("Add Script")) {
			ImGui.openPopup("AddScriptPopup");
		}
		if (ImGui.isItemHovered()) {
			ImGui.setTooltip("Add a new Script to the Entity");
		}

		ImGui.popStyleColor(3);

		renderAddScriptPopup(scriptComponent);
	}

	private void renderAddScriptPopup(ScriptComponent scriptComponent) {
		if (!ImGui.beginPopup("AddScriptPopup"))
			return;

		ImGui.text("Select Script to Add:");
		ImGui.separator();
		ImGui.spacing();

		ImGui.pushItemWidth(-1);
		ImGui.inputText("##scriptFilter", addScriptFilter);
		ImGui.popItemWidth();

		ImGui.spacing();

		String filterLower = addScriptFilter.get().toLowerCase();

		AssetManager.getInstance().getRegisteredAssetsByType(AssetType.SCRIPT).stream()
			.map(asset -> (ScriptAsset) asset)
			.filter(script -> {
				String scriptPathLower = script.getRelativePath().toLowerCase();
				boolean matchesSearch = filterLower.isEmpty() || scriptPathLower.contains(filterLower);
				boolean alreadyAdded = scriptComponent.scripts.stream()
					.anyMatch(existing -> existing.getScriptAssetID() != null && existing.getScriptAssetID().equals(script.getId()));
				return matchesSearch && !alreadyAdded;
			})
			.forEach(script -> {
				if (ImGui.menuItem(script.getRelativePath())) {
					ScriptInstance newScript = new ScriptInstance(script.getId());
					newScript.setScriptAssetID(script.getId());
					newScript.setLastModified(0);
					scriptComponent.scripts.add(newScript);
					ImGui.closeCurrentPopup();
					addScriptFilter.set("");
				}
			});

		ImGui.endPopup();
	}
}
