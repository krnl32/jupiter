package com.krnl32.jupiter.editor.renderer.components.gameplay;

import com.krnl32.jupiter.editor.renderer.ComponentRenderer;
import com.krnl32.jupiter.editor.utility.GUIUtils;
import com.krnl32.jupiter.engine.components.gameplay.ForceMovementComponent;
import imgui.type.ImFloat;

public class ForceMovementComponentRenderer implements ComponentRenderer<ForceMovementComponent> {
	@Override
	public void render(ForceMovementComponent component) {
		ImFloat moveForce = new ImFloat(component.moveForce);
		if (GUIUtils.renderFloatInput("Move Force", moveForce)) {
			component.moveForce = moveForce.get();
		}

		ImFloat sprintMultiplier = new ImFloat(component.sprintMultiplier);
		if (GUIUtils.renderFloatInput("Sprint Multiplier", sprintMultiplier)) {
			component.sprintMultiplier = sprintMultiplier.get();
		}

		ImFloat maxSpeed = new ImFloat(component.maxSpeed);
		if (GUIUtils.renderFloatInput("Max Speed", maxSpeed)) {
			component.maxSpeed = maxSpeed.get();
		}

		ImFloat rotationTorque = new ImFloat(component.rotationTorque);
		if (GUIUtils.renderFloatInput("Rotation Torque", rotationTorque)) {
			component.rotationTorque = rotationTorque.get();
		}

		ImFloat jumpImpulse = new ImFloat(component.jumpImpulse);
		if (GUIUtils.renderFloatInput("Jump Impulse", jumpImpulse)) {
			component.jumpImpulse = jumpImpulse.get();
		}
	}
}
