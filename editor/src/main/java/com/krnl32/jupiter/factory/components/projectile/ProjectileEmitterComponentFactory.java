package com.krnl32.jupiter.factory.components.projectile;

import com.krnl32.jupiter.components.projectile.ProjectileEmitterComponent;
import com.krnl32.jupiter.factory.ComponentFactory;
import com.krnl32.jupiter.input.devices.KeyCode;
import com.krnl32.jupiter.model.Sprite;
import org.joml.Vector4f;

public class ProjectileEmitterComponentFactory implements ComponentFactory<ProjectileEmitterComponent> {
	@Override
	public ProjectileEmitterComponent create() {
		return new ProjectileEmitterComponent(KeyCode.LEFT_CONTROL, 15.55f, 25.0f, new Sprite(-1, new Vector4f(1.0f, 1.0f, 1.0f, 1.0f), null));
	}
}
