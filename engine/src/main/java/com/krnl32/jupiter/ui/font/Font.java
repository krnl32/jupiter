package com.krnl32.jupiter.ui.font;

import com.krnl32.jupiter.renderer.Texture2D;

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
}
