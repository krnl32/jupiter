package com.krnl32.jupiter.engine.ui.font;

import com.krnl32.jupiter.engine.renderer.texture.Texture2D;

import java.util.Map;

public class Font {
	private final Texture2D atlas;
	private final Map<Character, Glyph> glyphs;
	private final int size;

	public Font(Texture2D atlas, Map<Character, Glyph> glyphs, int size) {
		this.atlas = atlas;
		this.glyphs = glyphs;
		this.size = size;
	}

	public void destroy() {
		atlas.destroy();
	}

	public Texture2D getAtlas() {
		return atlas;
	}

	public Glyph getGlyph(char c) {
		return glyphs.getOrDefault(c, glyphs.get('?'));
	}

	public int getSize() {
		return size;
	}

	public float getLineHeight() {
		float height = 0.0f;

		for (char c = 32; c < 127; c++) {
			Glyph glyph = glyphs.get(c);

			if (glyph != null) {
				float glyphHeight = glyph.y1 - glyph.y0;

				if (glyphHeight > height) {
					height = glyphHeight;
				}
			}
		}

		return height;
	}

	public float getTextWidth(String text) {
		float width = 0.0f;

		for (char c : text.toCharArray()) {
			Glyph glyph = glyphs.get(c);

			if (glyph != null) {
				width += glyph.xAdvance;
			}
		}

		return width;
	}
}
