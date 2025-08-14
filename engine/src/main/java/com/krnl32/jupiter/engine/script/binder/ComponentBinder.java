package com.krnl32.jupiter.engine.script.binder;

import com.krnl32.jupiter.engine.ecs.Component;

public interface ComponentBinder<T extends Component, R> {
	R to(T component);
	T from(R data);
	void update(T component, R table);
}
