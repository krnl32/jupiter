package com.krnl32.jupiter.engine.cloner;

import com.krnl32.jupiter.engine.ecs.Component;

import java.util.HashMap;
import java.util.Map;

public class ComponentClonerRegistry {
	private static final Map<Class<? extends Component>, ComponentCloner<? extends Component>> cloners = new HashMap<>();

	public static <T extends Component> void register(Class<T> componentType, ComponentCloner<T> cloner) {
		cloners.put(componentType, cloner);
	}

	public static <T extends Component> void unregister(Class<T> componentType) {
		cloners.remove(componentType);
	}

	@SuppressWarnings("unchecked")
	public static <T extends Component> ComponentCloner<T> getCloner(Class<T> componentType) {
		return (ComponentCloner<T>) cloners.get(componentType);
	}

	public static boolean hasCloner(Class<? extends Component> componentType) {
		return cloners.containsKey(componentType);
	}
}
