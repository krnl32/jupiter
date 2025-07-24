package com.krnl32.jupiter.renderer.components.gameplay;

import com.krnl32.jupiter.asset.AssetManager;
import com.krnl32.jupiter.asset.AssetType;
import com.krnl32.jupiter.asset.types.ScriptAsset;
import com.krnl32.jupiter.components.gameplay.ScriptComponent;
import com.krnl32.jupiter.renderer.ComponentRenderer;
import com.krnl32.jupiter.utility.GUIUtils;
import imgui.ImGui;

public class ScriptComponentRenderer implements ComponentRenderer<ScriptComponent> {
	@Override
	public void render(ScriptComponent component) {
		ScriptAsset scriptAsset = (component.scriptAssetID != null) ? AssetManager.getInstance().getAsset(component.scriptAssetID) : null;
		String scriptPath = (scriptAsset != null) ? scriptAsset.getRelativePath() : "<None>";

		GUIUtils.renderAssetCombo(AssetManager.getInstance().getRegisteredAssetsByType(AssetType.SCRIPT), "Script", scriptPath, component.scriptAssetID, newScriptAssetID -> {
			component.scriptAssetID = newScriptAssetID;
			component.lastModified = 0;  // Force Reload
		});

		if (component.scriptAssetID != null) {
			GUIUtils.renderLongReadOnly("Last Modified", component.lastModified);
			GUIUtils.renderStringReadOnly("Status", (component.disabled ? "Disabled" : (component.initialized ? "Active" : "Pending Init")));
		}

		if (ImGui.button("Force Reload")) {
			component.lastModified = 0;
		}
	}
}
