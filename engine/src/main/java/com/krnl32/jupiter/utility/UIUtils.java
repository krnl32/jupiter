package com.krnl32.jupiter.utility;

import com.krnl32.jupiter.components.ui.UITransformComponent;
import org.joml.Vector2f;

public class UIUtils {
	public static boolean isMouseOver(Vector2f mousePosition, UITransformComponent transformComponent) {
		return (
			mousePosition.x >= transformComponent.translation.x &&
				mousePosition.y >= transformComponent.translation.y &&
				mousePosition.x <= transformComponent.translation.x + transformComponent.scale.x &&
				mousePosition.y <= transformComponent.translation.y + transformComponent.scale.y
		);
	}
}
