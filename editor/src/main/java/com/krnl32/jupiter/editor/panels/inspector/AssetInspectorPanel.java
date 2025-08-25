package com.krnl32.jupiter.editor.panels.inspector;

import com.krnl32.jupiter.editor.asset.EditorAssetManager;
import com.krnl32.jupiter.editor.ui.EditorPanel;
import com.krnl32.jupiter.editor.renderer.EditorRendererRegistry;
import com.krnl32.jupiter.editor.renderer.asset.AssetRenderer;
import com.krnl32.jupiter.editor.utility.GUIUtils;
import com.krnl32.jupiter.engine.asset.handle.Asset;
import com.krnl32.jupiter.engine.asset.handle.AssetMetadata;
import com.krnl32.jupiter.engine.project.ProjectContext;
import imgui.ImGui;
import imgui.flag.ImGuiStyleVar;

public class AssetInspectorPanel implements EditorPanel {
	private Asset selectedAsset;

	@Override
	public void onUpdate(float dt) {
	}

	@Override
	public void onRender(float dt) {
		ImGui.pushStyleVar(ImGuiStyleVar.FramePadding, 6, 4);
		ImGui.pushStyleVar(ImGuiStyleVar.ItemSpacing, 10, 6);

		ImGui.textColored(0.9f, 0.7f, 1.0f, 1.0f, "Asset Inspector");
		ImGui.separator();
		ImGui.spacing();

		if (selectedAsset != null) {
			GUIUtils.renderStringTextDisabled("Asset Type:", selectedAsset.getType().name());
			GUIUtils.renderStringTextDisabled("Asset ID:", selectedAsset.getId().getId().toString());

			AssetMetadata assetMetadata = ((EditorAssetManager) ProjectContext.getInstance().getAssetManager()).getAssetMetadata(selectedAsset.getId());
			if (assetMetadata != null) {
				GUIUtils.renderStringTextDisabled("Asset Path:", assetMetadata.getAssetPath());
				GUIUtils.renderStringTextDisabled("Last Modified:", String.valueOf(assetMetadata.getLastModified()));
			}

			ImGui.spacing();

			AssetRenderer<Asset> assetRenderer = EditorRendererRegistry.getAssetRenderer(selectedAsset.getType());
			if (assetRenderer != null) {
				assetRenderer.render(selectedAsset);
			}
		} else {
			ImGui.textDisabled("No Asset Selected");
		}

		ImGui.popStyleVar(2);
	}

	public void setSelectedAsset(Asset selectedAsset) {
		this.selectedAsset = selectedAsset;
	}
}
