package com.krnl32.jupiter.engine.serializer;

import com.krnl32.jupiter.engine.ecs.Component;

import java.util.HashMap;
import java.util.Map;

public class SerializerRegistry {
	private static final Map<Class<? extends Component>, ComponentSerializer<? extends Component>> componentSerializers = new HashMap<>();

	public static <T extends Component> void registerComponentSerializer(Class<T> componentType, ComponentSerializer<T> componentSerializer) {
		componentSerializers.put(componentType, componentSerializer);
	}

	public static <T extends Component> void unregisterComponentSerializer(Class<T> componentType) {
		componentSerializers.remove(componentType);
	}

	@SuppressWarnings("unchecked")
	public static <T extends Component> ComponentSerializer<T> getComponentSerializer(Class<T> componentType) {
		return (ComponentSerializer<T>) componentSerializers.get(componentType);
	}

	public static Map<Class<? extends Component>, ComponentSerializer<? extends Component>> getComponentSerializers() {
		return componentSerializers;
	}

	public static boolean hasComponentSerializer(Class<? extends Component> componentType) {
		return componentSerializers.containsKey(componentType);
	}
}
