package com.krnl32.jupiter.factory.components.projectile;

import com.krnl32.jupiter.components.projectile.ProjectileComponent;
import com.krnl32.jupiter.factory.ComponentFactory;

public class ProjectileComponentFactory implements ComponentFactory<ProjectileComponent> {
	@Override
	public ProjectileComponent create() {
		return new ProjectileComponent(null, 10.0f);
	}
}
