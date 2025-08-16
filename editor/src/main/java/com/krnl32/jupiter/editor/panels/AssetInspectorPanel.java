package com.krnl32.jupiter.editor.panels;

import com.krnl32.jupiter.editor.asset.EditorAssetManager;
import com.krnl32.jupiter.editor.editor.EditorPanel;
import com.krnl32.jupiter.editor.utility.GUIUtils;
import com.krnl32.jupiter.engine.asset.handle.Asset;
import com.krnl32.jupiter.engine.asset.handle.AssetMetadata;
import com.krnl32.jupiter.engine.asset.handle.AssetType;
import com.krnl32.jupiter.engine.asset.importsettings.types.TextureAssetImportSettings;
import com.krnl32.jupiter.engine.asset.types.TextureAsset;
import com.krnl32.jupiter.engine.project.ProjectContext;
import com.krnl32.jupiter.engine.renderer.texture.TextureFilterMode;
import com.krnl32.jupiter.engine.renderer.texture.TextureSettings;
import com.krnl32.jupiter.engine.renderer.texture.TextureWrapMode;
import imgui.ImGui;
import imgui.type.ImInt;

public class AssetInspectorPanel implements EditorPanel {
	private Asset selectedAsset;

	@Override
	public void onUpdate(float dt) {
	}

	@Override
	public void onRender(float dt) {
		ImGui.begin("Inspector");

		if (selectedAsset != null) {
			if (selectedAsset.getType() == AssetType.TEXTURE) {
				renderTextureAsset((TextureAsset) selectedAsset);
			}
		}

		ImGui.end();
	}

	public void setSelectedAsset(Asset selectedAsset) {
		this.selectedAsset = selectedAsset;
	}

	private void renderTextureAsset(TextureAsset asset) {
		ImGui.text("TextureAsset");

		TextureSettings settings = asset.getSettings();

		ImInt width = new ImInt(settings.getWidth());
		if (GUIUtils.renderIntInput("Width", width)) {
			settings.setWidth(width.get());
		}

		ImInt channels = new ImInt(settings.getChannels());
		if (GUIUtils.renderIntInput("Channels", channels)) {
			settings.setChannels(channels.get());
		}

		TextureWrapMode[] wrapModes = TextureWrapMode.values();
		ImInt wrapMode = new ImInt(settings.getWrapMode().ordinal());
		if (GUIUtils.renderEnumCombo("Wrap Mode", wrapModes, wrapMode)) {
			settings.setWrapMode(wrapModes[wrapMode.get()]);
		}

		TextureFilterMode[] filterModes = TextureFilterMode.values();
		ImInt filterMode = new ImInt(settings.getFilterMode().ordinal());
		if (GUIUtils.renderEnumCombo("Filter Mode", filterModes, filterMode)) {
			settings.setFilterMode(filterModes[filterMode.get()]);
		}

		if (GUIUtils.renderButton("Apply")) {
			EditorAssetManager editorAssetManager = ((EditorAssetManager) ProjectContext.getInstance().getAssetManager());
			AssetMetadata assetMetadata = editorAssetManager.getAssetMetadata(asset.getId());

			if (assetMetadata != null) {
				AssetMetadata updatedMetadata = new AssetMetadata(
					assetMetadata.getVersion(),
					assetMetadata.getAssetId(),
					assetMetadata.getAssetType(),
					assetMetadata.getAssetPath(),
					assetMetadata.getImporterName(),
					new TextureAssetImportSettings(settings).toMap(),
					assetMetadata.getLastModified()
				);

				editorAssetManager.saveAssetMetadata(asset.getId(), updatedMetadata);
				editorAssetManager.reloadAsset(asset.getId());
			}
		}
	}
}
