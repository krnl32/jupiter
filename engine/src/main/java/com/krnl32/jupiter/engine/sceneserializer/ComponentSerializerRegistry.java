package com.krnl32.jupiter.engine.sceneserializer;

import com.krnl32.jupiter.engine.ecs.Component;

import java.util.HashMap;
import java.util.Map;

public class ComponentSerializerRegistry {
	private final Map<Class<? extends Component>, ComponentSerializer<? extends Component, ?>> serializers = new HashMap<>();
	private final Map<Class<? extends Component>, String> classToName = new HashMap<>();
	private final Map<String, Class<? extends Component>> nameToClass = new HashMap<>();

	public <T extends Component, R> void register(Class<T> componentType, ComponentSerializer<T, R> serializer) {
		serializers.put(componentType, serializer);
		classToName.put(componentType, componentType.getSimpleName());
		nameToClass.put(componentType.getSimpleName(), componentType);
	}

	public <T extends Component> void unregister(Class<T> componentType) {
		String name = classToName.get(componentType);

		if (name != null) {
			nameToClass.remove(name);
		}

		serializers.remove(componentType);
	}

	@SuppressWarnings("unchecked")
	public <T extends Component, R> ComponentSerializer<T, R> getSerializer(Class<? extends Component> componentType) {
		return (ComponentSerializer<T,R>) serializers.get(componentType);
	}

	@SuppressWarnings("unchecked")
	public <T extends Component, R> ComponentSerializer<T, R> getSerializer(String componentName) {
		return (ComponentSerializer<T,R>) serializers.get(nameToClass.get(componentName));
	}

	public boolean hasSerializer(Class<? extends Component> componentType) {
		return serializers.containsKey(componentType);
	}

	public boolean hasSerializer(String componentName) {
		return nameToClass.containsKey(componentName);
	}
}
