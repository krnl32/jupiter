package com.krnl32.jupiter.editor.factory.components.renderer;

import com.krnl32.jupiter.editor.factory.ComponentFactory;
import com.krnl32.jupiter.engine.components.renderer.SpriteRendererComponent;
import org.joml.Vector4f;

public class SpriteRendererComponentFactory implements ComponentFactory<SpriteRendererComponent> {
	@Override
	public SpriteRendererComponent create() {
		return new SpriteRendererComponent(-1, new Vector4f(1.0f, 1.0f, 1.0f, 1.0f), null);
	}
}
