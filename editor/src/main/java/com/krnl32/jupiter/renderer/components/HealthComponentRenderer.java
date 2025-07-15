package com.krnl32.jupiter.renderer.components;

import com.krnl32.jupiter.components.HealthComponent;
import com.krnl32.jupiter.renderer.ComponentRenderer;
import com.krnl32.jupiter.utility.GUIUtils;
import imgui.type.ImFloat;

public class HealthComponentRenderer implements ComponentRenderer<HealthComponent> {
	@Override
	public void render(HealthComponent component) {
		ImFloat maxHealth = new ImFloat(component.maxHealth);
		if (GUIUtils.renderFloatInputWithResetButton("Max Health", maxHealth, component.maxHealth)) {
			component.maxHealth = maxHealth.get();
		}

		ImFloat currentHealth = new ImFloat(component.currentHealth);
		if (GUIUtils.renderFloatInputWithResetButton("Current Health", currentHealth, component.maxHealth)) {
			component.currentHealth = currentHealth.get();
		}
	}
}
