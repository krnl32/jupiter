package com.krnl32.jupiter.renderer.components.effects;

import com.krnl32.jupiter.components.effects.ParticleComponent;
import com.krnl32.jupiter.renderer.ComponentRenderer;
import com.krnl32.jupiter.utility.GUIUtils;
import imgui.type.ImFloat;

public class ParticleComponentRenderer implements ComponentRenderer<ParticleComponent> {
	@Override
	public void render(ParticleComponent component) {
		GUIUtils.renderVector3f("Velocity", component.velocity);

		ImFloat duration = new ImFloat(component.duration);
		if (GUIUtils.renderFloatInput("Duration", duration)) {
			component.duration = duration.get();
		}

		ImFloat remainingTime = new ImFloat(component.remainingTime);
		if (GUIUtils.renderFloatInput("Remaining Time", remainingTime)) {
			component.remainingTime = remainingTime.get();
		}
	}
}
