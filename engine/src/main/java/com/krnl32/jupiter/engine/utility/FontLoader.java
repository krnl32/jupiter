package com.krnl32.jupiter.engine.utility;

import com.krnl32.jupiter.engine.core.Logger;
import com.krnl32.jupiter.engine.renderer.texture.*;
import com.krnl32.jupiter.engine.ui.font.Font;
import com.krnl32.jupiter.engine.ui.font.Glyph;
import org.lwjgl.BufferUtils;
import org.lwjgl.stb.STBTTAlignedQuad;
import org.lwjgl.stb.STBTTFontinfo;
import org.lwjgl.stb.STBTTPackContext;
import org.lwjgl.stb.STBTTPackedchar;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import static org.lwjgl.stb.STBTruetype.*;
import static org.lwjgl.system.MemoryUtil.NULL;

public class FontLoader {
	public static Font loadFont(String fontPath, int fontSize, int atlasWidth, int atlasHeight) {
		try {
			// Read Data
			byte[] data = Files.readAllBytes(Paths.get(fontPath));
			ByteBuffer ttf = BufferUtils.createByteBuffer(data.length);
			ttf.put(data);
			ttf.flip();

			STBTTFontinfo fontInfo = STBTTFontinfo.create();
			if (!stbtt_InitFont(fontInfo, ttf)) {
				Logger.info("FontLoader Failed to stbtt_InitFont Font File({})", fontPath);
				return null;
			}

			// Generate Glyphs
			ByteBuffer bitmap = BufferUtils.createByteBuffer(atlasWidth * atlasHeight);
			STBTTPackedchar.Buffer charData = STBTTPackedchar.malloc(96);

			STBTTPackContext pc = STBTTPackContext.malloc();
			stbtt_PackBegin(pc, bitmap, atlasWidth, atlasHeight, 0, 1, NULL);
			stbtt_PackSetOversampling(pc, 2, 2);
			stbtt_PackFontRange(pc, ttf, 0, fontSize, 32, charData);
			stbtt_PackEnd(pc);
			pc.free();
			bitmap.flip();

			Texture2D atlas = new Texture2D(new TextureSettings(TextureType.TEXTURE_2D, TextureFormat.R8, atlasWidth, atlasHeight, 1, TextureWrapMode.REPEAT, TextureFilterMode.NEAREST, true));
			atlas.setBuffer(bitmap);

			Map<Character, Glyph> glyphs = new HashMap<>();
			for (char c = 32; c < 128; c++) {
				STBTTAlignedQuad q = STBTTAlignedQuad.malloc();

				FloatBuffer xb = BufferUtils.createFloatBuffer(1).put(0, 0f);
				FloatBuffer yb = BufferUtils.createFloatBuffer(1).put(0, 0f);

				stbtt_GetPackedQuad(charData, atlasWidth, atlasHeight, c - 32, xb, yb, q, false);

				Glyph glyph = new Glyph(
					q.x0(), q.y0(), q.x1(), q.y1(),
					q.s0(), q.t0(), q.s1(), q.t1(),
					xb.get(0), yb.get(0)
				);

				glyphs.put(c, glyph);
				q.free();
			}
			charData.free();

			return new Font(atlas, glyphs, fontSize);

		} catch (IOException e) {
			Logger.info("TextRenderer Failed to open Font File({})", fontPath);
			return null;
		}
	}
}
