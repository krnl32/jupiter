package com.krnl32.jupiter.editor.factory.components.projectile;

import com.krnl32.jupiter.engine.components.projectile.ProjectileComponent;
import com.krnl32.jupiter.editor.factory.ComponentFactory;

public class ProjectileComponentFactory implements ComponentFactory<ProjectileComponent> {
	@Override
	public ProjectileComponent create() {
		return new ProjectileComponent(null, 10.0f);
	}
}
