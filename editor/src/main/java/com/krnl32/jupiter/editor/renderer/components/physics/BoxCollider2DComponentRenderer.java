package com.krnl32.jupiter.editor.renderer.components.physics;

import com.krnl32.jupiter.engine.components.physics.BoxCollider2DComponent;
import com.krnl32.jupiter.editor.renderer.ComponentRenderer;
import com.krnl32.jupiter.editor.utility.GUIUtils;
import imgui.ImGui;
import imgui.type.ImBoolean;
import imgui.type.ImFloat;

public class BoxCollider2DComponentRenderer implements ComponentRenderer<BoxCollider2DComponent> {
	@Override
	public void render(BoxCollider2DComponent component) {
		GUIUtils.renderVector2f("Size", component.size);
		GUIUtils.renderVector2f("Offset", component.offset);

		ImFloat friction = new ImFloat(component.friction);
		if (GUIUtils.renderFloatInput("Friction", friction)) {
			component.friction = friction.get();
		}

		ImFloat density = new ImFloat(component.density);
		if (GUIUtils.renderFloatInput("Density", density)) {
			component.density = density.get();
		}

		ImGui.columns(2, "Columns_Sensor", false);
		ImGui.setColumnWidth(0, 100);
		ImGui.text("Sensor");
		ImGui.nextColumn();

		ImBoolean sensor = new ImBoolean(component.sensor);
		if (ImGui.checkbox("##Sensor", sensor)) {
			component.sensor = sensor.get();
		}

		ImGui.columns(1);
	}
}
