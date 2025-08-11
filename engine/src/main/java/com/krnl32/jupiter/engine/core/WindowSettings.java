package com.krnl32.jupiter.engine.core;

public class WindowSettings {
	private String name;
	private int width;
	private int height;
	private boolean vSync;

	public WindowSettings(String name, int width, int height, boolean vSync) {
		this.name = name;
		this.width = width;
		this.height = height;
		this.vSync = vSync;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public boolean isVSync() {
		return vSync;
	}

	public void setVSync(boolean vSync) {
		this.vSync = vSync;
	}

	@Override
	public String toString() {
		return "WindowSettings{" +
			"name='" + name + '\'' +
			", width=" + width +
			", height=" + height +
			", vSync=" + vSync +
			'}';
	}
}
