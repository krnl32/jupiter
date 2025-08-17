package com.krnl32.jupiter.editor.renderer;

import com.krnl32.jupiter.editor.renderer.asset.AssetRenderer;
import com.krnl32.jupiter.editor.renderer.component.ComponentRenderer;
import com.krnl32.jupiter.engine.asset.handle.Asset;
import com.krnl32.jupiter.engine.asset.handle.AssetType;
import com.krnl32.jupiter.engine.ecs.Component;

import java.util.HashMap;
import java.util.Map;

public class EditorRendererRegistry {
	private static final Map<Class<? extends Component>, ComponentRenderer<?>> componentRenderers = new HashMap<>();
	private static final Map<AssetType, AssetRenderer<? extends Asset>> assetRenderers = new HashMap<>();

	// Components
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

	// Assets
	public static <T extends Asset> void registerAssetRenderer(AssetType assetType, AssetRenderer<T> renderer) {
		assetRenderers.put(assetType, renderer);
	}

	public static void unregisterAssetRenderer(AssetType assetType) {
		assetRenderers.remove(assetType);
	}

	public static <T extends Asset> AssetRenderer<T> getAssetRenderer(AssetType assetType) {
		return (AssetRenderer<T>) assetRenderers.get(assetType);
	}

	public static boolean hasAssetRenderer(AssetType assetType) {
		return assetRenderers.containsKey(assetType);
	}
}
