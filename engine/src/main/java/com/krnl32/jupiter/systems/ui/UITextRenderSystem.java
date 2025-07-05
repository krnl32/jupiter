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

import java.util.ArrayList;
import java.util.List;

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

			switch (text.overflow) {
				case CLIP -> renderTextClipped(renderer, text, transform, worldTransform);
				case ELLIPSIS -> renderTextEllipsis(renderer, text, transform, worldTransform);
				case SCALE -> renderTextScaled(renderer, text, transform, worldTransform);
				case WRAP -> renderTextWrapped(renderer, text, transform, worldTransform);
			}
		} else {
			Matrix4f localTransform = new Matrix4f().translate(transform.translation).rotateXYZ(transform.rotation).scale(transform.scale);
			worldTransform = new Matrix4f(parentTransform).mul(localTransform);
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

	private void renderTextClipped(Renderer renderer, UITextComponent textComponent, UITransformComponent transformComponent, Matrix4f worldTransform) {
		FontAsset fontAsset = AssetManager.getInstance().getAsset(textComponent.fontAssetID);
		if (fontAsset == null || !fontAsset.isLoaded()) {
			Logger.error("UITextRenderSystem Failed to get Font Asset({})", textComponent.fontAssetID);
			return;
		}

		// Parent Bound/Border
		float posX = 0.0f;
		float posY = 0.0f;
		float width = transformComponent.scale.x;
		float height = transformComponent.scale.y;

		// Calculate text width/height
		float textWidth = 0.0f;
		float textTop = Float.MAX_VALUE;
		float textBottom = Float.MIN_VALUE;
		float textLeft = Float.MAX_VALUE;

		for (char c : textComponent.text.toCharArray()) {
			Glyph glyph = fontAsset.getFont().getGlyph(c);
			if (glyph == null)
				continue;
			textWidth += glyph.xAdvance;
			textTop = Math.min(textTop, glyph.y0);
			textBottom = Math.max(textBottom, glyph.y1);
			textLeft = Math.min(textLeft, glyph.x0);
		}
		float textHeight = textBottom - textTop;

		// Horizontal Alignment
		float cursorX = switch (textComponent.horizontalAlignment) {
			case LEFT -> posX - textLeft;
			case RIGHT -> width - textWidth - textLeft;
			case CENTER -> (width - textWidth) / 2f - textLeft;
		};
		cursorX = Math.max(cursorX, 0.0f);


		// Vertical alignment
		float cursorY = switch (textComponent.verticalAlignment) {
			case TOP -> posY;
			case BOTTOM -> height - (textHeight);
			case CENTER -> (height - (textHeight)) / 2.0f;
		};

		// Render glyphs until overflow
		float drawnWidth = 0.0f;
		for (char c : textComponent.text.toCharArray()) {
			Glyph glyph = fontAsset.getFont().getGlyph(c);
			if (glyph == null)
				continue;

			// clip overflow
			if (drawnWidth + glyph.xAdvance > width)
				break;

			float glyphWidth = glyph.x1 - glyph.x0;
			float glyphHeight = glyph.y1 - glyph.y0;
			float x0 = cursorX + glyph.x0 + drawnWidth;
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
				new RenderPacket(-1, textComponent.color, fontAsset.getFont().getAtlas()),
				glyphTransform,
				uvs
			));

			drawnWidth += glyph.xAdvance;
		}
	}

	private void renderTextEllipsis(Renderer renderer, UITextComponent textComponent, UITransformComponent transformComponent, Matrix4f worldTransform) {
		FontAsset fontAsset = AssetManager.getInstance().getAsset(textComponent.fontAssetID);
		if (fontAsset == null || !fontAsset.isLoaded()) {
			Logger.error("UITextRenderSystem Failed to get Font Asset({})", textComponent.fontAssetID);
			return;
		}

		// Parent Bound/Border
		float posX = 0.0f;
		float posY = 0.0f;
		float width = transformComponent.scale.x;
		float height = transformComponent.scale.y;

		// Calculate text width/height
		float textWidth = 0.0f;
		float textTop = Float.MAX_VALUE;
		float textBottom = Float.MIN_VALUE;

		for (char c : textComponent.text.toCharArray()) {
			Glyph glyph = fontAsset.getFont().getGlyph(c);
			if (glyph == null)
				continue;
			textWidth += glyph.xAdvance;
			textTop = Math.min(textTop, glyph.y0);
			textBottom = Math.max(textBottom, glyph.y1);
		}
		float textHeight = textBottom - textTop;

		// Horizontal Alignment
		float cursorX = switch (textComponent.horizontalAlignment) {
			case LEFT -> posX;
			case RIGHT -> width - Math.min(textWidth, width);
			case CENTER -> (width - Math.min(textWidth, width)) / 2.0f;
		};

		// Vertical alignment
		float cursorY = switch (textComponent.verticalAlignment) {
			case TOP -> posY;
			case BOTTOM -> height - textHeight;
			case CENTER -> (height - textHeight) / 2.0f;
		};

		// Calculate Ellipsis
		Glyph dotGlyph = fontAsset.getFont().getGlyph('.');
		float ellipsisWidth = dotGlyph != null ? dotGlyph.xAdvance * 3 : 0f;

		float drawnWidth = 0.0f;
		boolean overflow = false;

		for (int i = 0; i < textComponent.text.length(); i++) {
			char c = textComponent.text.charAt(i);
			Glyph glyph = fontAsset.getFont().getGlyph(c);
			if (glyph == null) continue;;

			if (drawnWidth + glyph.xAdvance + ellipsisWidth > width) {
				overflow = true;
				break;
			}

			float glyphWidth = glyph.x1 - glyph.x0;
			float glyphHeight = glyph.y1 - glyph.y0;
			float x0 = cursorX + glyph.x0 + drawnWidth;
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
				new RenderPacket(-1, textComponent.color, fontAsset.getFont().getAtlas()),
				glyphTransform,
				uvs
			));

			drawnWidth += glyph.xAdvance;
		}

		// Draw ellipsis
		if (overflow && dotGlyph != null) {
			for (int i = 0; i < 3; i++) {
				float glyphWidth = dotGlyph.x1 - dotGlyph.x0;
				float glyphHeight = dotGlyph.y1 - dotGlyph.y0;
				float x0 = cursorX + drawnWidth + dotGlyph.x0 + i * dotGlyph.xAdvance;
				float y0 = cursorY + (dotGlyph.y0 - textTop);

				Matrix4f glyphTransform = new Matrix4f(worldTransform)
					.translate(x0, y0, 0)
					.scale(glyphWidth, glyphHeight, 1);

				float[] uvs = {
					dotGlyph.s0, dotGlyph.t0,
					dotGlyph.s1, dotGlyph.t0,
					dotGlyph.s1, dotGlyph.t1,
					dotGlyph.s0, dotGlyph.t1
				};

				renderer.submit(new RenderTextCommand(
					new RenderPacket(-1, textComponent.color, fontAsset.getFont().getAtlas()),
					glyphTransform,
					uvs
				));
			}
		}
	}

	private void renderTextScaled(Renderer renderer, UITextComponent textComponent, UITransformComponent transformComponent, Matrix4f worldTransform) {
		FontAsset fontAsset = AssetManager.getInstance().getAsset(textComponent.fontAssetID);
		if (fontAsset == null || !fontAsset.isLoaded()) {
			Logger.error("UITextRenderSystem Failed to get Font Asset({})", textComponent.fontAssetID);
			return;
		}

		// Parent Bound/Border
		float posX = 0.0f;
		float posY = 0.0f;
		float width = transformComponent.scale.x;
		float height = transformComponent.scale.y;

		// Calculate text width/height
		float textWidth = 0.0f;
		float textTop = Float.MAX_VALUE;
		float textBottom = Float.MIN_VALUE;

		for (char c : textComponent.text.toCharArray()) {
			Glyph glyph = fontAsset.getFont().getGlyph(c);
			if (glyph == null)
				continue;
			textWidth += glyph.xAdvance;
			textTop = Math.min(textTop, glyph.y0);
			textBottom = Math.max(textBottom, glyph.y1);
		}
		float textHeight = textBottom - textTop;

		float scale = 1.0f;
		if (textWidth > width) {
			scale = width / textWidth;
		}

		// Horizontal Alignment
		float cursorX = switch (textComponent.horizontalAlignment) {
			case LEFT -> posX;
			case RIGHT -> width - (textWidth * scale);
			case CENTER -> (width - textWidth * scale) / 2.0f;
		};

		// Vertical alignment
		float cursorY = switch (textComponent.verticalAlignment) {
			case TOP -> posY;
			case BOTTOM -> height - (textHeight * scale);
			case CENTER -> (height - (textHeight * scale)) / 2.0f;
		};

		// Render Glyphs
		for (char c : textComponent.text.toCharArray()) {
			Glyph glyph = fontAsset.getFont().getGlyph(c);
			if (glyph == null)
				continue;

			float glyphWidth = (glyph.x1 - glyph.x0) * scale;
			float glyphHeight = (glyph.y1 - glyph.y0) * scale;
			float x0 = cursorX + glyph.x0 * scale;
			float y0 = cursorY + (glyph.y0 - textTop) * scale;

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
				new RenderPacket(-1, textComponent.color, fontAsset.getFont().getAtlas()),
				glyphTransform,
				uvs
			));

			cursorX += glyph.xAdvance * scale;
		}
	}

	private void renderTextWrapped(Renderer renderer, UITextComponent textComponent, UITransformComponent transformComponent, Matrix4f worldTransform) {
		FontAsset fontAsset = AssetManager.getInstance().getAsset(textComponent.fontAssetID);
		if (fontAsset == null || !fontAsset.isLoaded()) {
			Logger.error("UITextRenderSystem Failed to get Font Asset({})", textComponent.fontAssetID);
			return;
		}

		float width = transformComponent.scale.x;
		float height = transformComponent.scale.y;

		// Calculate Ascent
		float maxAscent = Float.MIN_VALUE;
		float maxDescent = Float.MAX_VALUE;
		for (char c : textComponent.text.toCharArray()) {
			Glyph glyph = fontAsset.getFont().getGlyph(c);
			if (glyph == null) continue;
			maxAscent = Math.max(maxAscent, glyph.y1);
			maxDescent = Math.min(maxDescent, glyph.y0);
		}
		float unscaledLineHeight = (maxAscent - maxDescent);

		// Word wrapping
		List<String> lines = new ArrayList<>();
		StringBuilder currentLine = new StringBuilder();
		float currentLineWidth = 0f;

		for (String word : textComponent.text.split(" ")) {
			float wordWidth = 0f;
			for (char wc : word.toCharArray()) {
				Glyph g = fontAsset.getFont().getGlyph(wc);
				if (g != null) wordWidth += g.xAdvance;
			}
			Glyph space = fontAsset.getFont().getGlyph(' ');
			wordWidth += (space != null) ? space.xAdvance : 0f;

			if (currentLineWidth + wordWidth > width && !currentLine.isEmpty()) {
				lines.add(currentLine.toString());
				currentLine.setLength(0);
				currentLine.append(word).append(" ");
				currentLineWidth = wordWidth;
			} else {
				currentLine.append(word).append(" ");
				currentLineWidth += wordWidth;
			}
		}
		if (!currentLine.isEmpty())
			lines.add(currentLine.toString().stripTrailing());

		// Scale if total height overflows
		float totalUnscaledHeight = lines.size() * unscaledLineHeight;
		float scale = 1.0f;
		if (totalUnscaledHeight > height) {
			scale = height / totalUnscaledHeight;
		}

		// Vertical alignment
		float totalScaledHeight = totalUnscaledHeight * scale;
		float baseY = switch (textComponent.verticalAlignment) {
			case TOP -> maxAscent * scale;
			case CENTER -> (height + maxAscent * scale - totalScaledHeight) / 2f;
			case BOTTOM -> height - totalScaledHeight + maxAscent * scale;
		};

		// Render each line
		for (int lineIndex = 0; lineIndex < lines.size(); lineIndex++) {
			String line = lines.get(lineIndex);

			// Calculate line width
			float lineWidth = 0f;
			for (char c : line.toCharArray()) {
				Glyph g = fontAsset.getFont().getGlyph(c);
				if (g != null)
					lineWidth += g.xAdvance;
			}
			lineWidth *= scale;

			// Horizontal alignment
			float cursorX = switch (textComponent.horizontalAlignment) {
				case LEFT -> 0.0f;
				case CENTER -> (width - lineWidth) / 2f;
				case RIGHT -> width - lineWidth;
			};

			float cursorY = baseY + lineIndex * unscaledLineHeight * scale;

			// Draw line
			float drawnWidth = 0f;
			for (char c : line.toCharArray()) {
				Glyph glyph = fontAsset.getFont().getGlyph(c);
				if (glyph == null) continue;

				float glyphWidth = (glyph.x1 - glyph.x0) * scale;
				float glyphHeight = (glyph.y1 - glyph.y0) * scale;

				float x0 = cursorX + glyph.x0 * scale + drawnWidth;
				float y0 = cursorY + (glyph.y0 - maxDescent) * scale;

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
					new RenderPacket(-1, textComponent.color, fontAsset.getFont().getAtlas()),
					glyphTransform,
					uvs
				));

				drawnWidth += glyph.xAdvance * scale;
			}
		}
	}
}
