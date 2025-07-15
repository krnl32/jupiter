package com.krnl32.jupiter.renderer.components.effects;

import com.krnl32.jupiter.components.effects.BlinkComponent;
import com.krnl32.jupiter.renderer.ComponentRenderer;
import com.krnl32.jupiter.utility.GUIUtils;
import imgui.ImGui;
import imgui.type.ImFloat;

public class BlinkComponentRenderer implements ComponentRenderer<BlinkComponent> {
	@Override
	public void render(BlinkComponent component) {
		ImFloat duration = new ImFloat(component.duration);
		if (GUIUtils.renderFloatInput("Duration", duration)) {
			component.duration = duration.get();
		}

		ImFloat interval = new ImFloat(component.interval);
		if (GUIUtils.renderFloatInput("Interval", interval)) {
			component.interval = interval.get();
		}

		// Read-only runtime info
		ImGui.spacing();
		ImGui.textColored(0.7f, 0.7f, 0.7f, 1f, "Runtime Info");
		ImGui.spacing();
		GUIUtils.renderFloatReadOnly("Elapsed Time", component.elapsedTime);
		GUIUtils.renderFloatReadOnly("Blink Time", component.blinkTime);
		GUIUtils.renderBoolReadOnly("Visible", component.visible);
	}
}
