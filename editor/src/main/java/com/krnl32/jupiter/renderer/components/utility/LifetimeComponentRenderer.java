package com.krnl32.jupiter.renderer.components.utility;

import com.krnl32.jupiter.components.utility.LifetimeComponent;
import com.krnl32.jupiter.renderer.ComponentRenderer;
import com.krnl32.jupiter.utility.GUIUtils;
import imgui.type.ImFloat;

public class LifetimeComponentRenderer implements ComponentRenderer<LifetimeComponent> {
	@Override
	public void render(LifetimeComponent component) {
		ImFloat remainingTime = new ImFloat(component.remainingTime);
		if (GUIUtils.renderFloatInput("Remaining Time", remainingTime)) {
			component.remainingTime = remainingTime.get();
		}
	}
}
