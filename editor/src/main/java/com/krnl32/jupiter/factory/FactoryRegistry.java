package com.krnl32.jupiter.factory;

import com.krnl32.jupiter.ecs.Component;

import java.util.HashMap;
import java.util.Map;

public class FactoryRegistry {
	private static final Map<Class<? extends Component>, ComponentFactory<?>> componentFactories = new HashMap<>();

	public static <T extends Component> void registerComponentFactory(Class<T> componentType, ComponentFactory<T> factory) {
		componentFactories.put(componentType, factory);
	}

	public static <T extends Component> void unregisterComponentFactory(Class<T> componentType) {
		componentFactories.remove(componentType);
	}

	@SuppressWarnings("unchecked")
	public static <T extends Component> ComponentFactory<T> getComponentFactory(Class<T> componentType) {
		return (ComponentFactory<T>) componentFactories.get(componentType);
	}

	public static Map<Class<? extends Component>, ComponentFactory<?>> getComponentFactories() {
		return componentFactories;
	}

	public static boolean hasComponentFactory(Class<? extends Component> componentType) {
		return componentFactories.containsKey(componentType);
	}
}
