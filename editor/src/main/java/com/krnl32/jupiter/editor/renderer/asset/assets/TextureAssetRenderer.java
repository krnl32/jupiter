package com.krnl32.jupiter.editor.renderer.asset.assets;

import com.krnl32.jupiter.editor.asset.EditorAssetManager;
import com.krnl32.jupiter.editor.renderer.asset.AssetRenderer;
import com.krnl32.jupiter.editor.utility.GUIUtils;
import com.krnl32.jupiter.engine.asset.handle.AssetMetadata;
import com.krnl32.jupiter.engine.asset.importsettings.types.TextureAssetImportSettings;
import com.krnl32.jupiter.engine.asset.types.TextureAsset;
import com.krnl32.jupiter.engine.project.ProjectContext;
import com.krnl32.jupiter.engine.renderer.texture.*;
import imgui.ImGui;
import imgui.flag.ImGuiCol;
import imgui.flag.ImGuiStyleVar;
import imgui.flag.ImGuiTreeNodeFlags;
import imgui.type.ImBoolean;
import imgui.type.ImInt;

public class TextureAssetRenderer implements AssetRenderer<TextureAsset> {
	@Override
	public void render(TextureAsset asset) {
		ImGui.spacing();
		ImGui.text("Texture Settings");
		ImGui.separator();

		TextureSettings settings = asset.getSettings();

		// General
		if (ImGui.collapsingHeader("General", ImGuiTreeNodeFlags.DefaultOpen)) {
			TextureType[] textureTypes = TextureType.values();
			ImInt textureType = new ImInt(settings.getType().ordinal());

			if (GUIUtils.renderEnumCombo("Texture Type", textureTypes, textureType)) {
				settings.setType(textureTypes[textureType.get()]);
			}

			TextureFormat[] formats = TextureFormat.values();
			ImInt format = new ImInt(settings.getFormat().ordinal());

			if (GUIUtils.renderEnumCombo("Format", formats, format)) {
				settings.setFormat(formats[format.get()]);
			}
		}

		// Dimensions
		if (ImGui.collapsingHeader("Dimensions", ImGuiTreeNodeFlags.DefaultOpen)) {
			ImInt width = new ImInt(settings.getWidth());
			if (GUIUtils.renderIntInput("Width", width)) {
				settings.setWidth(width.get());
			}

			ImInt height = new ImInt(settings.getHeight());
			if (GUIUtils.renderIntInput("Height", height)) {
				settings.setHeight(height.get());
			}

			ImInt channels = new ImInt(settings.getChannels());
			if (GUIUtils.renderIntInput("Channels", channels)) {
				settings.setChannels(channels.get());
			}
		}

		// Wrapping & Filtering
		if (ImGui.collapsingHeader("Wrapping & Filtering", ImGuiTreeNodeFlags.DefaultOpen)) {
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
		}

		// Color & Compression
		if (ImGui.collapsingHeader("Color & Compression", ImGuiTreeNodeFlags.DefaultOpen)) {
			TextureColorSpace[] colorSpaces = TextureColorSpace.values();
			ImInt colorSpace = new ImInt(settings.getColorSpace().ordinal());

			if (GUIUtils.renderEnumCombo("Color Space", colorSpaces, colorSpace)) {
				settings.setColorSpace(colorSpaces[colorSpace.get()]);
			}

			TextureCompressionType[] compressionTypes = TextureCompressionType.values();
			ImInt compression = new ImInt(settings.getColorSpace().ordinal());

			if (GUIUtils.renderEnumCombo("Compression", compressionTypes, compression)) {
				settings.setCompressionType(compressionTypes[compression.get()]);
			}
		}

		// Advanced Settings
		if (ImGui.collapsingHeader("Advanced Settings", ImGuiTreeNodeFlags.DefaultOpen)) {
			ImInt mipmapCount = new ImInt(settings.getMipmapCount());
			if (GUIUtils.renderIntInput("Mipmap Count", mipmapCount)) {
				settings.setMipmapCount(mipmapCount.get());
			}

			ImInt anisotropicLevel = new ImInt(settings.getAnisotropicLevel());
			if (GUIUtils.renderIntInput("Anisotropic Level", anisotropicLevel)) {
				settings.setAnisotropicLevel(anisotropicLevel.get());
			}
		}

		// Flags
		if (ImGui.collapsingHeader("Flags", ImGuiTreeNodeFlags.DefaultOpen)) {
			ImBoolean mipmaps = new ImBoolean(settings.isGenerateMipmaps());
			if (ImGui.checkbox("Generate Mipmaps", mipmaps)) {
				settings.setGenerateMipmaps(mipmaps.get());
			}

			ImBoolean compressed = new ImBoolean(settings.isCompressed());
			if (ImGui.checkbox("Compressed", compressed)) {
				settings.setCompressed(compressed.get());
			}

			ImBoolean cubemap = new ImBoolean(settings.isCubemap());
			if (ImGui.checkbox("Cubemap", cubemap)) {
				settings.setCubemap(cubemap.get());
			}

			ImBoolean sRGB = new ImBoolean(settings.isSRGB());
			if (ImGui.checkbox("sRGB", sRGB)) {
				settings.setSRGB(sRGB.get());
			}

			ImBoolean alpha = new ImBoolean(settings.isAlpha());
			if (ImGui.checkbox("Alpha", alpha)) {
				settings.setAlpha(alpha.get());
			}
		}

		// Preview
		Texture2D previewTexture = asset.getTexture();
		if (previewTexture != null && previewTexture.getTextureID() != 0) {
			ImGui.spacing();
			ImGui.separator();
			ImGui.text("Preview");

			float previewSize = 256.0f;
			float windowWidth = ImGui.getWindowWidth();
			float cursorX = (windowWidth - previewSize) * 0.5f;
			ImGui.setCursorPosX(cursorX);

			ImGui.image(previewTexture.getTextureID(), previewSize, previewSize, 0, 1, 1, 0);
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
					new TextureAssetImportSettings(settings).toMap(),
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
