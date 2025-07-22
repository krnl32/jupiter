package com.krnl32.jupiter.script;

import com.krnl32.jupiter.ecs.Component;

import java.util.HashMap;
import java.util.Map;

public class BinderRegistry {
	private static final Map<Class<? extends Component>, ComponentBinder<? extends Component>> componentBinders = new HashMap<>();

	public static <T extends Component> void registerComponentBinder(Class<T> componentType, ComponentBinder<T> componentBinder) {
		componentBinders.put(componentType, componentBinder);
	}

	public static <T extends Component> void unregisterComponentBinder(Class<T> componentType) {
		componentBinders.remove(componentType);
	}

	@SuppressWarnings("unchecked")
	public static <T extends Component> ComponentBinder<T> getComponentBinder(Class<T> componentType) {
		return (ComponentBinder<T>) componentBinders.get(componentType);
	}

	public static Map<Class<? extends Component>, ComponentBinder<? extends Component>> getComponentBinders() {
		return componentBinders;
	}

	public static boolean hasComponentBinder(Class<? extends Component> componentType) {
		return componentBinders.containsKey(componentType);
	}
}
