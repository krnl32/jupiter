package com.krnl32.jupiter.event;

public interface EventListener<T extends Event> {
	void onEvent(T event);
}
