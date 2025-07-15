package com.krnl32.jupiter.renderer.components.physics;

import com.krnl32.jupiter.components.physics.RigidBody2DComponent;
import com.krnl32.jupiter.physics.BodyType;
import com.krnl32.jupiter.renderer.ComponentRenderer;
import com.krnl32.jupiter.utility.GUIUtils;
import imgui.ImGui;
import imgui.type.ImBoolean;
import imgui.type.ImFloat;
import imgui.type.ImInt;

public class RigidBody2DComponentRenderer implements ComponentRenderer<RigidBody2DComponent> {
	@Override
	public void render(RigidBody2DComponent component) {
		BodyType[] types = BodyType.values();
		String[] labels = new String[types.length];
		for (int i = 0; i < types.length; i++) {
			labels[i] = types[i].name();
		}

		ImInt selected = new ImInt(component.bodyType.ordinal());
		if (ImGui.combo("Body Type", selected, labels)) {
			component.bodyType = types[selected.get()];
		}

		GUIUtils.renderVector2f("Initial Velocity", component.initialVelocity);
		GUIUtils.renderFloatInputWithResetButton("Mass", new ImFloat(component.mass), 1.0f);
		GUIUtils.renderFloatInputWithResetButton("Gravity Scale", new ImFloat(component.gravityScale), 1.0f);
		GUIUtils.renderFloatInputWithResetButton("Angular Damping", new ImFloat(component.angularDamping), 0.8f);
		GUIUtils.renderFloatInputWithResetButton("Linear Damping", new ImFloat(component.linearDamping), 0.9f);

		ImBoolean fixedRotation = new ImBoolean(component.fixedRotation);
		if (ImGui.checkbox("Fixed Rotation", fixedRotation)) {
			component.fixedRotation = fixedRotation.get();
		}

		ImBoolean continuousCollision = new ImBoolean(component.continuousCollision);
		if (ImGui.checkbox("Continuous Collision", continuousCollision)) {
			component.continuousCollision = continuousCollision.get();
		}
	}
}
