package com.krnl32.jupiter.editor.panels;

import com.krnl32.jupiter.editor.editor.EditorPanel;
import com.krnl32.jupiter.editor.events.asset.AssetSelectedEvent;
import com.krnl32.jupiter.editor.events.scene.EntitySelectedEvent;
import com.krnl32.jupiter.engine.event.EventBus;
import com.krnl32.jupiter.engine.events.entity.EntityDestroyedEvent;
import imgui.ImGui;

public class InspectorPanel implements EditorPanel {
	private final EntityInspectorPanel entityInspector = new EntityInspectorPanel();
	private final AssetInspectorPanel assetInspector = new AssetInspectorPanel();
	private InspectorMode inspectorMode;

	public InspectorPanel() {
		EventBus.getInstance().register(EntitySelectedEvent.class, event -> {
			entityInspector.setSelectedEntity(event.getEntity());
			inspectorMode = InspectorMode.ENTITY;
		});

		EventBus.getInstance().register(EntityDestroyedEvent.class, event -> {
			entityInspector.setSelectedEntity(null);
		});

		EventBus.getInstance().register(AssetSelectedEvent.class, event -> {
			assetInspector.setSelectedAsset(event.getAsset());
			inspectorMode = InspectorMode.ASSET;
		});
	}

	@Override
	public void onUpdate(float dt) {
	}

	@Override
	public void onRender(float dt) {
		ImGui.begin("Inspector");

		if (inspectorMode == InspectorMode.ENTITY) {
			entityInspector.onRender(dt);
		} else if (inspectorMode == InspectorMode.ASSET) {
			assetInspector.onRender(dt);
		} else {
			ImGui.textDisabled("None");
		}

		ImGui.end();
	}
}

enum InspectorMode {
	ENTITY,
	ASSET
};
