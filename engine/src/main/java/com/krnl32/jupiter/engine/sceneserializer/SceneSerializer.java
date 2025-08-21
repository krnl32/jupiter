package com.krnl32.jupiter.engine.sceneserializer;

import com.krnl32.jupiter.engine.scene.Scene;

public interface SceneSerializer<T> {
	T serialize(Scene scene);
	Scene deserialize(T data);
}
