package com.krnl32.jupiter.editor.renderer.components.physics;

import com.krnl32.jupiter.engine.components.physics.RigidBody2DComponent;
import com.krnl32.jupiter.engine.physics.BodyType;
import com.krnl32.jupiter.editor.renderer.ComponentRenderer;
import com.krnl32.jupiter.editor.utility.GUIUtils;
import imgui.ImGui;
import imgui.type.ImBoolean;
import imgui.type.ImFloat;
import imgui.type.ImInt;

public class RigidBody2DComponentRenderer implements ComponentRenderer<RigidBody2DComponent> {
	@Override
	public void render(RigidBody2DComponent component) {
		BodyType[] types = BodyType.values();
		ImInt selected = new ImInt(component.bodyType.ordinal());
		if (GUIUtils.renderEnumCombo("Body Type", types, selected)) {
			component.bodyType = types[selected.get()];
		}

		GUIUtils.renderVector2f("Velocity", component.velocity);

		ImFloat linearDamping = new ImFloat(component.linearDamping);
		if (GUIUtils.renderFloatInputWithResetButton("Linear Damping", linearDamping, 0.9f)) {
			component.linearDamping = linearDamping.get();
		}

		ImFloat angularDamping = new ImFloat(component.angularDamping);
		if (GUIUtils.renderFloatInputWithResetButton("Angular Damping", angularDamping, 0.8f)) {
			component.angularDamping = angularDamping.get();
		}

		ImFloat mass = new ImFloat(component.mass);
		if (GUIUtils.renderFloatInputWithResetButton("Mass", mass, 1.0f)) {
			component.mass = mass.get();
		}

		ImFloat gravityScale = new ImFloat(component.gravityScale);
		if (GUIUtils.renderFloatInputWithResetButton("Gravity Scale", gravityScale, 1.0f)) {
			component.gravityScale = gravityScale.get();
		}

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
