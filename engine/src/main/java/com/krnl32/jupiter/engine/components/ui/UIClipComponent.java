package com.krnl32.jupiter.engine.components.ui;

import com.krnl32.jupiter.engine.ecs.Component;

public class UIClipComponent implements Component {
	public int x,y;
	public int width, height;

	public UIClipComponent(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
}
