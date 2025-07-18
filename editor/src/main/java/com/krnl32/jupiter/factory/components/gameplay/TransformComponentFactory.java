package com.krnl32.jupiter.factory.components.gameplay;

import com.krnl32.jupiter.components.gameplay.TransformComponent;
import com.krnl32.jupiter.factory.ComponentFactory;
import org.joml.Vector3f;

public class TransformComponentFactory implements ComponentFactory<TransformComponent> {
	@Override
	public TransformComponent create() {
		return new TransformComponent(new Vector3f(0.0f, 0.0f, 0.0f), new Vector3f(0.0f, 0.0f, 0.0f), new Vector3f(1.0f, 1.0f, 1.0f));
	}
}
