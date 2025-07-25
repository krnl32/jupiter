package com.krnl32.jupiter.engine.ui.font;

public class Glyph {
	public float x0, y0, x1, y1;
	public float s0, t0, s1, t1;
	public float xAdvance, yAdvance;

	public Glyph(float x0, float y0, float x1, float y1, float s0, float t0, float s1, float t1, float xAdvance, float yAdvance) {
		this.x0 = x0;
		this.y0 = y0;
		this.x1 = x1;
		this.y1 = y1;
		this.s0 = s0;
		this.t0 = t0;
		this.s1 = s1;
		this.t1 = t1;
		this.xAdvance = xAdvance;
		this.yAdvance = yAdvance;
	}
}
