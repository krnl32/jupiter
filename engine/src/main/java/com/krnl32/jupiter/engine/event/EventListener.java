package com.krnl32.jupiter.engine.event;

public interface EventListener<T extends Event> {
	void onEvent(T event);
}
