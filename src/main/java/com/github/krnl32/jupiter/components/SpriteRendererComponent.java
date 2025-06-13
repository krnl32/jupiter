package com.github.krnl32.jupiter.components;

import com.github.krnl32.jupiter.ecs.Component;
import com.github.krnl32.jupiter.renderer.Sprite;

public class SpriteRendererComponent implements Component {
	public Sprite sprite;

	public SpriteRendererComponent(Sprite sprite) {
		this.sprite = sprite;
	}
}
