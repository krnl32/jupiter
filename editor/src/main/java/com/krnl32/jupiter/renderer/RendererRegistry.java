package com.krnl32.jupiter.renderer;

import com.krnl32.jupiter.ecs.Component;

import java.util.HashMap;
import java.util.Map;

public class RendererRegistry {
	private static final Map<Class<? extends Component>, ComponentRenderer<?>> componentRenderers = new HashMap<>();

	public static <T extends Component> void registerComponentRenderer(Class<T> componentType, ComponentRenderer<T> renderer) {
		componentRenderers.put(componentType, renderer);
	}

	public static <T extends Component> void unregisterComponentRenderer(Class<T> componentType) {
		componentRenderers.remove(componentType);
	}

	@SuppressWarnings("unchecked")
	public static <T extends Component> ComponentRenderer<T> getComponentRenderer(Class<T> componentType) {
		return (ComponentRenderer<T>) componentRenderers.get(componentType);
	}

	public static Map<Class<? extends Component>, ComponentRenderer<?>> getComponentRenderers() {
		return componentRenderers;
	}

	public static boolean hasComponentRenderer(Class<? extends Component> componentType) {
		return componentRenderers.containsKey(componentType);
	}
}
