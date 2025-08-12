package com.krnl32.jupiter.engine.cloner;

import com.krnl32.jupiter.engine.ecs.Component;

public interface ComponentCloner<T extends Component> {
	T clone(T component);
}
