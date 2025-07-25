package com.krnl32.jupiter.engine.renderer;

import java.util.Objects;

public class ClipRect {
	private final int x,y;
	private final int width, height;

	public ClipRect(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public ClipRect intersect(ClipRect other) {
		int newX = Math.max(this.x, other.x);
		int newY = Math.max(this.y, other.y);
		int newWidth = Math.max(0, Math.min(this.x + this.width, other.x + other.width) - newX);
		int newHeight = Math.max(0, Math.min(this.y + this.height, other.y + other.height) - newY);
		return new ClipRect(newX, newY, newWidth, newHeight);
	}

	public static int compareClipRect(ClipRect a, ClipRect b) {
		if (a == b)
			return 0;
		if (a == null)
			return -1;
		if (b == null)
			return 1;
		int cmp = Integer.compare(a.x, b.x);
		if (cmp != 0)
			return cmp;
		cmp = Integer.compare(a.y, b.y);
		if (cmp != 0)
			return cmp;
		cmp = Integer.compare(a.width, b.width);
		if (cmp != 0)
			return cmp;
		return Integer.compare(a.height, b.height);
	}

	@Override
	public boolean equals(Object o) {
		if (o == null || getClass() != o.getClass()) return false;
		ClipRect clipRect = (ClipRect) o;
		return x == clipRect.x && y == clipRect.y && width == clipRect.width && height == clipRect.height;
	}

	@Override
	public int hashCode() {
		return Objects.hash(x, y, width, height);
	}
}
