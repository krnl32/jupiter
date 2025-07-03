package com.krnl32.jupiter.components.ui;

import com.krnl32.jupiter.ecs.Component;
import com.krnl32.jupiter.ecs.Entity;

import java.util.ArrayList;
import java.util.List;

public class UIHierarchyComponent implements Component {
	public Entity parent;
	public List<Entity> children;

	public UIHierarchyComponent() {
		this.parent = null;
		this.children = new ArrayList<>();
	}

	public UIHierarchyComponent(Entity parent) {
		this.parent = parent;
		this.children = new ArrayList<>();
	}

	public void addChild(Entity child) {
		if (!children.contains(child))
			children.add(child);
	}

	public void removeChild(Entity child) {
		children.remove(child);
	}
}
