package com.krnl32.jupiter.editor.renderer.component.components.utility;

import com.krnl32.jupiter.editor.renderer.component.ComponentRenderer;
import com.krnl32.jupiter.editor.utility.GUIUtils;
import com.krnl32.jupiter.engine.components.utility.LifetimeComponent;
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
