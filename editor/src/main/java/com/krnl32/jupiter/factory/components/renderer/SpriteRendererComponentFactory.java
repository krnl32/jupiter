package com.krnl32.jupiter.factory.components.renderer;

import com.krnl32.jupiter.components.renderer.SpriteRendererComponent;
import com.krnl32.jupiter.factory.ComponentFactory;
import org.joml.Vector4f;

public class SpriteRendererComponentFactory implements ComponentFactory<SpriteRendererComponent> {
	@Override
	public SpriteRendererComponent create() {
		return new SpriteRendererComponent(-1, new Vector4f(1.0f, 1.0f, 1.0f, 1.0f), null);
	}
}
