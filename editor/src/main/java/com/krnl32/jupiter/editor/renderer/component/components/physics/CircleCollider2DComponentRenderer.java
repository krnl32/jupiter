package com.krnl32.jupiter.editor.renderer.component.components.physics;

import com.krnl32.jupiter.editor.renderer.component.ComponentRenderer;
import com.krnl32.jupiter.editor.utility.GUIUtils;
import com.krnl32.jupiter.engine.components.physics.CircleCollider2DComponent;
import imgui.ImGui;
import imgui.type.ImBoolean;
import imgui.type.ImFloat;

public class CircleCollider2DComponentRenderer implements ComponentRenderer<CircleCollider2DComponent> {
	@Override
	public void render(CircleCollider2DComponent component) {
		ImFloat radius = new ImFloat(component.radius);
		if (GUIUtils.renderFloatInput("Radius", radius)) {
			component.radius = radius.get();
		}

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
