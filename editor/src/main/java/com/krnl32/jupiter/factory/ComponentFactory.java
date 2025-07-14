package com.krnl32.jupiter.factory;

import com.krnl32.jupiter.ecs.Component;

public interface ComponentFactory<T extends Component> {
	T create();
}
