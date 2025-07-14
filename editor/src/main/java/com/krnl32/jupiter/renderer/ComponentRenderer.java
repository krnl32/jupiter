package com.krnl32.jupiter.renderer;

import com.krnl32.jupiter.ecs.Component;

public interface ComponentRenderer<T extends Component> {
	void render(T component);
}
