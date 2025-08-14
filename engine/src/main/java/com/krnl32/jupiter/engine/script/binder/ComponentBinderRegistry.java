package com.krnl32.jupiter.engine.script.binder;

import com.krnl32.jupiter.engine.ecs.Component;

import java.util.HashMap;
import java.util.Map;

public class ComponentBinderRegistry {
	private static final Map<Class<? extends Component>, ComponentBinder<? extends Component, ?>> binders = new HashMap<>();
	private static final Map<Class<? extends Component>, String> classToName = new HashMap<>();
	private static final Map<String, Class<? extends Component>> nameToClass = new HashMap<>();

	public static <T extends Component, R> void register(Class<T> componentType, ComponentBinder<T,R> componentBinder) {
		binders.put(componentType, componentBinder);
		classToName.put(componentType, componentType.getSimpleName());
		nameToClass.put(componentType.getSimpleName(), componentType);
	}

	public static <T extends Component> void unregister(Class<T> componentType) {
		String name = classToName.get(componentType);
		if (name != null) {
			nameToClass.remove(name);
		}
		binders.remove(componentType);
	}

	@SuppressWarnings("unchecked")
	public static <T extends Component, R> ComponentBinder<T,R> getBinder(Class<T> componentType) {
		return (ComponentBinder<T,R>) binders.get(componentType);
	}

	@SuppressWarnings("unchecked")
	public static <T extends Component, R> ComponentBinder<T,R> getBinder(String componentName) {
		return (ComponentBinder<T,R>) binders.get(nameToClass.get(componentName));
	}

	public static Class<? extends Component> getComponentClass(String componentName) {
		return nameToClass.get(componentName);
	}

	public static boolean hasBinder(Class<? extends Component> componentType) {
		return binders.containsKey(componentType);
	}

	public static boolean hasBinder(String componentName) {
		return nameToClass.containsKey(componentName);
	}
}
