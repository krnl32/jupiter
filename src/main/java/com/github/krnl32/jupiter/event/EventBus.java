package com.github.krnl32.jupiter.event;

import com.github.krnl32.jupiter.core.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EventBus {
	private static EventBus instance;
	private final Map<Class<? extends Event>, List<EventListener<? extends Event>>> listeners = new HashMap<>();

	private EventBus() {
	}

	public static EventBus getInstance() {
		if(instance == null)
			instance = new EventBus();
		return instance;
	}

	public <T extends Event> void register(Class<T> eventType, EventListener<T> listener) {
		Logger.info("EventBus Registering Event({}) with Listener({})", eventType.getSimpleName(), listener.getClass().getSimpleName());
		listeners.computeIfAbsent(eventType, k -> new ArrayList<>()).add(listener);
	}

	public <T extends Event> void unregister(Class<T> eventType, EventListener<T> listener) {
		var eventListeners = listeners.get(eventType);
		if (eventListeners != null) {
			eventListeners.remove(listener);
			if (eventListeners.isEmpty()) {
				listeners.remove(eventType);
			}
		}
	}

	@SuppressWarnings("unchecked")
	public <T extends Event> void emit(T event) {
		var eventListeners = listeners.get((event.getClass()));
		if(eventListeners != null) {
			for(var listener: eventListeners) {
				((EventListener<T>)listener).onEvent(event);
			}
		}
	}
}
