package com.krnl32.jupiter.systems.ui;

import com.krnl32.jupiter.asset.AssetManager;
import com.krnl32.jupiter.asset.FontAsset;
import com.krnl32.jupiter.components.ui.UIHierarchyComponent;
import com.krnl32.jupiter.components.ui.UITextComponent;
import com.krnl32.jupiter.components.ui.UITransformComponent;
import com.krnl32.jupiter.core.Logger;
import com.krnl32.jupiter.ecs.Entity;
import com.krnl32.jupiter.ecs.Registry;
import com.krnl32.jupiter.ecs.System;
import com.krnl32.jupiter.renderer.RenderPacket;
import com.krnl32.jupiter.renderer.RenderTextCommand;
import com.krnl32.jupiter.renderer.Renderer;
import com.krnl32.jupiter.ui.font.Glyph;
import org.joml.Matrix4f;
import org.joml.Vector3f;

public class UITextRenderSystem implements System {
	private final Registry registry;

	public UITextRenderSystem(Registry registry) {
		this.registry = registry;
	}

	@Override
	public void onUpdate(float dt) {

	}

	@Override
	public void onRender(float dt, Renderer renderer) {
		for (Entity entity : registry.getEntitiesWith(UITransformComponent.class)) {
			UIHierarchyComponent uiHierarchy = entity.getComponent(UIHierarchyComponent.class);
			if (uiHierarchy == null || uiHierarchy.parent == null) {
				renderHierarchy(renderer, entity, new Matrix4f());
			}
		}
	}

	private void renderHierarchy(Renderer renderer, Entity entity, Matrix4f parentTransform) {
		UITransformComponent transform = entity.getComponent(UITransformComponent.class);
		UITextComponent text = entity.getComponent(UITextComponent.class);

		Matrix4f worldTransform;
		if (text != null) {
			// Remove scale from parent transform
			Vector3f parentScale = new Vector3f();
			parentTransform.getScale(parentScale);
			Matrix4f parentTransformNoScale = new Matrix4f(parentTransform).scale(1.0f / parentScale.x, 1.0f / parentScale.y, 1.0f / parentScale.z);
			Matrix4f localTransform = new Matrix4f().translate(transform.translation).rotateXYZ(transform.rotation);
			worldTransform = new Matrix4f(parentTransformNoScale).mul(localTransform);
		} else {
			Matrix4f localTransform = new Matrix4f().translate(transform.translation).rotateXYZ(transform.rotation).scale(transform.scale);
			worldTransform = new Matrix4f(parentTransform).mul(localTransform);
		}

		if (text != null) {
			FontAsset fontAsset = AssetManager.getInstance().getAsset(text.fontAssetID);
			if (fontAsset == null || !fontAsset.isLoaded()) {
				Logger.error("UITextRenderSystem Failed to get Font Asset({})", text.fontAssetID);
				return;
			}

			// Parent Bound/Border
			float posX = 0.0f;
			float posY = 0.0f;
			float width = transform.scale.x;
			float height = transform.scale.y;

			// Calculate Text Width/Height
			float textWidth = 0.0f;
			float textTop = Float.MAX_VALUE;
			float textBottom = Float.MIN_VALUE;

			for (char c : text.text.toCharArray()) {
				Glyph glyph = fontAsset.getFont().getGlyph(c);
				if (glyph == null)
					continue;

				textWidth += glyph.xAdvance;
				textTop = Math.min(textTop, glyph.y0);
				textBottom = Math.max(textBottom, glyph.y1);
			}

			float textHeight = textBottom - textTop;

			// Calculate Alignment
			float cursorX = switch (text.alignment) {
				case LEFT -> posX;
				case RIGHT -> posX + width - textWidth;
				default -> posX + width / 2.0f - textWidth / 2.0f;
			};
			// Center Text Baseline
			float cursorY = posY + height / 2.0f - textHeight / 2.0f;

			// Render Glyphs
			for (char c : text.text.toCharArray()) {
				Glyph glyph = fontAsset.getFont().getGlyph(c);
				if (glyph == null)
					continue;

				float glyphWidth = glyph.x1 - glyph.x0;
				float glyphHeight = glyph.y1 - glyph.y0;
				float x0 = cursorX + glyph.x0;
				float y0 = cursorY + (glyph.y0 - textTop);

				Matrix4f glyphTransform = new Matrix4f(worldTransform)
					.translate(x0, y0, 0)
					.scale(glyphWidth, glyphHeight, 1);

				float[] uvs = {
					glyph.s0, glyph.t0,
					glyph.s1, glyph.t0,
					glyph.s1, glyph.t1,
					glyph.s0, glyph.t1
				};

				renderer.submit(new RenderTextCommand(
					new RenderPacket(-1, text.color, fontAsset.getFont().getAtlas()),
					glyphTransform,
					uvs
				));

				cursorX += glyph.xAdvance;
			}
		}

		UIHierarchyComponent uiHierarchy = entity.getComponent(UIHierarchyComponent.class);
		if (uiHierarchy != null) {
			for (Entity child : uiHierarchy.children) {
				if (child.hasComponent(UITransformComponent.class)) {
					renderHierarchy(renderer, child, worldTransform);
				}
			}
		}
	}
}
