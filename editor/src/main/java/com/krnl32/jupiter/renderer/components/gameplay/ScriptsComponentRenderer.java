package com.krnl32.jupiter.renderer.components.gameplay;

import com.krnl32.jupiter.asset.AssetManager;
import com.krnl32.jupiter.asset.AssetType;
import com.krnl32.jupiter.asset.types.ScriptAsset;
import com.krnl32.jupiter.components.gameplay.ScriptComponent;
import com.krnl32.jupiter.components.gameplay.ScriptsComponent;
import com.krnl32.jupiter.renderer.ComponentRenderer;
import com.krnl32.jupiter.utility.GUIUtils;
import imgui.ImGui;
import imgui.flag.ImGuiCol;
import imgui.flag.ImGuiTreeNodeFlags;
import imgui.type.ImString;

import java.util.List;
import java.util.stream.Collectors;

public class ScriptsComponentRenderer implements ComponentRenderer<ScriptsComponent> {

	private final ImString addScriptFilter = new ImString(256);
	private ScriptComponent scriptToRemove = null;

	@Override
	public void render(ScriptsComponent component) {
		renderScriptsComponent(component);

		ImGui.spacing();
		ImGui.separator();
		ImGui.spacing();

		renderAddScriptSection(component);

		if (scriptToRemove != null) {
			component.scripts.remove(scriptToRemove);
			scriptToRemove = null;
		}
	}

	private void renderScriptsComponent(ScriptsComponent scriptsComponent) {
		for (int i = 0; i < scriptsComponent.scripts.size(); i++) {
			ScriptComponent script = scriptsComponent.scripts.get(i);
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
				script.lastModified = 0;
			}
			ImGui.popStyleColor(3);
			ImGui.sameLine();

			String headerLabel = "\uf121 Script " + (i + 1);
			if (ImGui.collapsingHeader(headerLabel, ImGuiTreeNodeFlags.DefaultOpen)) {
				renderScript(script, scriptsComponent);
			}

			ImGui.popID();
		}
	}

	private void renderScript(ScriptComponent script, ScriptsComponent scriptsComponent) {
		List<ScriptAsset> filteredScripts = AssetManager.getInstance().getRegisteredAssetsByType(AssetType.SCRIPT)
			.stream()
			.map(asset -> (ScriptAsset) asset)
			.filter(scriptAsset -> scriptsComponent.scripts.stream()
				.filter(existing -> !existing.equals(script))
				.noneMatch(existing -> existing.scriptAssetID != null && existing.scriptAssetID.equals(scriptAsset.getId())))
			.collect(Collectors.toList());

		ScriptAsset scriptAsset = (script.scriptAssetID != null) ? AssetManager.getInstance().getAsset(script.scriptAssetID) : null;
		String scriptPath = (scriptAsset != null) ? scriptAsset.getRelativePath() : "<None>";

		GUIUtils.renderAssetCombo(
			filteredScripts,
			"Script",
			scriptPath,
			script.scriptAssetID,
			newID -> {
				script.scriptAssetID = newID;
				script.lastModified = 0;
			});

		if (script.scriptAssetID != null) {
			GUIUtils.renderLongReadOnly("Last Modified", script.lastModified);
			GUIUtils.renderStringReadOnly("Status", script.disabled ? "Disabled" : (script.initialized ? "Active" : "Pending Init"));
		}
	}

	private void renderAddScriptSection(ScriptsComponent scriptsComponent) {
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

		renderAddScriptPopup(scriptsComponent);
	}

	private void renderAddScriptPopup(ScriptsComponent scriptsComponent) {
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
				boolean alreadyAdded = scriptsComponent.scripts.stream()
					.anyMatch(existing -> existing.scriptAssetID != null && existing.scriptAssetID.equals(script.getId()));
				return matchesSearch && !alreadyAdded;
			})
			.forEach(script -> {
				if (ImGui.menuItem(script.getRelativePath())) {
					ScriptComponent newScript = new ScriptComponent(script.getId());
					newScript.scriptAssetID = script.getId();
					newScript.lastModified = 0;
					scriptsComponent.scripts.add(newScript);
					ImGui.closeCurrentPopup();
					addScriptFilter.set("");
				}
			});

		ImGui.endPopup();
	}
}
