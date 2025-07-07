package com.krnl32.jupiter.utility;

import com.krnl32.jupiter.components.ui.UIHierarchyComponent;
import com.krnl32.jupiter.components.ui.UITransformComponent;
import com.krnl32.jupiter.ecs.Entity;
import org.joml.Vector2f;
import org.joml.Vector3f;

public class UIUtils {
	public static Vector3f getWorldPosition(Entity entity) {
		Vector3f worldPosition = new Vector3f();
		while (entity != null && entity.hasComponent(UITransformComponent.class)) {
			UITransformComponent transform = entity.getComponent(UITransformComponent.class);
			worldPosition.add(transform.translation);

			if (entity.hasComponent(UIHierarchyComponent.class)) {
				UIHierarchyComponent hierarchy = entity.getComponent(UIHierarchyComponent.class);
				entity = hierarchy.parent;
			} else {
				entity = null;
			}
		}
		return worldPosition;
	}

	public static boolean isMouseOver(Vector2f mousePosition, UITransformComponent transformComponent) {
		return (
			mousePosition.x >= transformComponent.translation.x &&
				mousePosition.y >= transformComponent.translation.y &&
				mousePosition.x <= transformComponent.translation.x + transformComponent.scale.x &&
				mousePosition.y <= transformComponent.translation.y + transformComponent.scale.y
		);
	}

	public static boolean isMouseOver(Vector2f mousePosition, Vector3f translation, Vector3f scale) {
		return (
			mousePosition.x >= translation.x &&
				mousePosition.y >= translation.y &&
				mousePosition.x <= translation.x + scale.x &&
				mousePosition.y <= translation.y + scale.y
		);
	}

	public static boolean isMouseOver(Vector2f mousePosition, Entity entity) {
		UITransformComponent transform = entity.getComponent(UITransformComponent.class);
		return transform != null && (UIUtils.isMouseOver(mousePosition, UIUtils.getWorldPosition(entity), transform.scale));
	}
}
