package com.krnl32.jupiter.editor.renderer.component.components.renderer;

import com.krnl32.jupiter.editor.asset.EditorAssetManager;
import com.krnl32.jupiter.editor.renderer.component.ComponentRenderer;
import com.krnl32.jupiter.editor.utility.GUIUtils;
import com.krnl32.jupiter.engine.asset.handle.AssetType;
import com.krnl32.jupiter.engine.asset.types.TextureAsset;
import com.krnl32.jupiter.engine.components.renderer.SpriteRendererComponent;
import com.krnl32.jupiter.engine.project.ProjectContext;
import imgui.ImDrawList;
import imgui.ImGui;
import imgui.ImVec2;
import imgui.flag.ImGuiTreeNodeFlags;
import imgui.type.ImInt;
import imgui.type.ImString;
import org.joml.Vector2f;

import java.util.List;

public class SpriteRendererComponentRenderer implements ComponentRenderer<SpriteRendererComponent> {
	private final EditorAssetManager assetManager;
	private final Vector2f[] textureUVs;
	private final String[] textureUVLabels;
	private final ImString searchBuffer;

	public SpriteRendererComponentRenderer() {
		this.assetManager = (EditorAssetManager) ProjectContext.getInstance().getAssetManager();
		this.textureUVs = new Vector2f[] {new Vector2f(), new Vector2f(), new Vector2f(), new Vector2f()};
		this.textureUVLabels = new String[] {"Bottom Left", "Bottom Right", "Top Right", "Top Left"};
		this.searchBuffer = new ImString(128);
	}

	@Override
	public void render(SpriteRendererComponent component) {
		ImInt index = new ImInt(component.index);
		if (GUIUtils.renderIntInput("Index", index)) {
			component.index = index.get();
		}

		GUIUtils.renderColorPicker("Color", component.color);

		// Open Texture Picker
		if (ImGui.button("Select Texture")) {
			ImGui.openPopup("TexturePickerPopup");
		}

		// Little Preview
		if (component.textureAssetId != null) {
			TextureAsset currentTexture = assetManager.getAsset(component.textureAssetId);

			if (currentTexture != null) {
				ImGui.sameLine();
				int textureId = currentTexture.getTexture().getTextureID();
				ImGui.image(textureId, 30, 30, 0, 1, 1, 0);
			}
		}

		// Texture Picker
		if (ImGui.beginPopup("TexturePickerPopup")) {
			ImGui.inputText("Search", searchBuffer);
			ImGui.sameLine();

			if (ImGui.smallButton("X")) {
				searchBuffer.set("");
			}

			final int columns = 4;
			ImGui.columns(columns, "TextureGrid", false);

			List<TextureAsset> textureAssets = assetManager.getAssetsByType(AssetType.TEXTURE);

			for (TextureAsset textureAsset : textureAssets) {
				// Search Texture by Asset Path
				String textureName = assetManager.getAssetPath(textureAsset.getId()).getFileName().toString();
				String searchLower = searchBuffer.toString().toLowerCase();

				if (!textureName.toLowerCase().contains(searchLower)) {
					continue;
				}

				// Texture Picker Previews
				int textureId = textureAsset.getTexture().getTextureID();

				if (ImGui.imageButton("##Thumbnail" + textureId, textureId, 64, 64, 0, 1, 1, 0)) {
					component.textureAssetId = textureAsset.getId();
					ImGui.closeCurrentPopup();
				}

				if (ImGui.isItemHovered()) {
					ImGui.beginTooltip();
					ImGui.text(textureName);
					ImGui.endTooltip();
				}

				ImGui.nextColumn();
			}

			ImGui.columns(1);
			ImGui.endPopup();
		}

		if (ImGui.collapsingHeader("Texture UVs", ImGuiTreeNodeFlags.DefaultOpen)) {
			// Texture UV Modifiers
			for (int i = 0; i < 4; i++) {
				textureUVs[i].set(component.textureUV[i * 2], component.textureUV[i * 2 + 1]);

				if (GUIUtils.renderVector2fClamped(textureUVLabels[i], textureUVs[i], 0.0f, 1.0f)) {
					component.textureUV[i * 2] = textureUVs[i].x;
					component.textureUV[i * 2 + 1] = textureUVs[i].y;
				}
			}

			// Texture UV Preview
			if (component.textureAssetId != null && assetManager.isAssetLoaded(component.textureAssetId)) {
				TextureAsset currentTexture = assetManager.getAsset(component.textureAssetId);

				if (currentTexture != null) {
					ImGui.separator();
					ImGui.text("UV Preview:");

					float previewSize = 256f;
					int textureId = currentTexture.getTexture().getTextureID();

					ImGui.image(textureId, previewSize, previewSize, 0, 1, 1, 0);

					// The UV Modifier Preview Box
					ImVec2 pos = ImGui.getItemRectMin();
					ImVec2 size = new ImVec2(previewSize, previewSize);

					float u0 = component.textureUV[0];
					float v0 = component.textureUV[1];
					float u1 = component.textureUV[2];
					float v1 = component.textureUV[3];
					float u2 = component.textureUV[4];
					float v2 = component.textureUV[5];
					float u3 = component.textureUV[6];
					float v3 = component.textureUV[7];

					ImVec2 p0 = new ImVec2(pos.x + u0 * size.x, pos.y + (1 - v0) * size.y);
					ImVec2 p1 = new ImVec2(pos.x + u1 * size.x, pos.y + (1 - v1) * size.y);
					ImVec2 p2 = new ImVec2(pos.x + u2 * size.x, pos.y + (1 - v2) * size.y);
					ImVec2 p3 = new ImVec2(pos.x + u3 * size.x, pos.y + (1 - v3) * size.y);

					// Draw Green Polygon for UV
					int color = 0xFF00FF00;
					ImDrawList drawList = ImGui.getWindowDrawList();
					drawList.addPolyline(new ImVec2[]{p0, p1, p2, p3}, 4, color, 1, 3f);
				}
			}
		}
	}
}
