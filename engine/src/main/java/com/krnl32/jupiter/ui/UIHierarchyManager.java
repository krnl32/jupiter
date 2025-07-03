package com.krnl32.jupiter.ui;

import com.krnl32.jupiter.components.ui.UIHierarchyComponent;
import com.krnl32.jupiter.ecs.Entity;

public class UIHierarchyManager {
	public static void attach(Entity parent, Entity child) {
		if (!parent.hasComponent(UIHierarchyComponent.class)) {
			parent.addComponent(new UIHierarchyComponent());
		}

		if (!child.hasComponent(UIHierarchyComponent.class)) {
			child.addComponent(new UIHierarchyComponent());
		}

		UIHierarchyComponent parentHierarchy = parent.getComponent(UIHierarchyComponent.class);
		UIHierarchyComponent childHierarchy = child.getComponent(UIHierarchyComponent.class);

		childHierarchy.parent = parent;
		parentHierarchy.children.add(child);
	}

	public static void detach(Entity child) {
		if (!child.hasComponent(UIHierarchyComponent.class))
			return;

		UIHierarchyComponent childHierarchy = child.getComponent(UIHierarchyComponent.class);
		if (childHierarchy.parent != null && childHierarchy.parent.hasComponent(UIHierarchyComponent.class)) {
			UIHierarchyComponent parentHierarchy = childHierarchy.parent.getComponent(UIHierarchyComponent.class);
			parentHierarchy.removeChild(child);
		}

		childHierarchy.parent = null;
	}

	public static void clear(Entity parent) {
		if (!parent.hasComponent(UIHierarchyComponent.class))
			return;

		UIHierarchyComponent parentHierarchy = parent.getComponent(UIHierarchyComponent.class);
		for (Entity child : parentHierarchy.children) {
			UIHierarchyComponent childHierarchy = child.getComponent(UIHierarchyComponent.class);
			if (childHierarchy != null)
				childHierarchy.parent = null;
		}
		parentHierarchy.children.clear();
	}
}
