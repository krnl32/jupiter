package com.krnl32.jupiter.engine.components.ui;

import com.krnl32.jupiter.engine.ecs.Component;
import org.joml.Vector2f;

public class UIScrollComponent implements Component {
	public Vector2f offset;
	public boolean scrollX, scrollY;

	public UIScrollComponent(boolean scrollX, boolean scrollY) {
		this.offset = new Vector2f();
		this.scrollX = scrollX;
		this.scrollY = scrollY;
	}

	public UIScrollComponent(Vector2f offset, boolean scrollX, boolean scrollY) {
		this.offset = offset;
		this.scrollX = scrollX;
		this.scrollY = scrollY;
	}
}
