package com.krnl32.jupiter.editor.factory;

import com.krnl32.jupiter.engine.ecs.Component;

public interface ComponentFactory<T extends Component> {
	T create();
}
