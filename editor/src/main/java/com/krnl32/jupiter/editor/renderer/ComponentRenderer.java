package com.krnl32.jupiter.editor.renderer;

import com.krnl32.jupiter.engine.ecs.Component;

public interface ComponentRenderer<T extends Component> {
	void render(T component);
}
