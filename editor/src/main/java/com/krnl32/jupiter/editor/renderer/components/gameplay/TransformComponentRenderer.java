package com.krnl32.jupiter.editor.renderer.components.gameplay;

import com.krnl32.jupiter.engine.components.gameplay.TransformComponent;
import com.krnl32.jupiter.editor.renderer.ComponentRenderer;
import com.krnl32.jupiter.editor.utility.GUIUtils;

public class TransformComponentRenderer implements ComponentRenderer<TransformComponent> {

	@Override
	public void render(TransformComponent component) {
		GUIUtils.renderVector3f("Translation", component.translation);
		GUIUtils.renderVector3f("Rotation", component.rotation);
		GUIUtils.renderVector3f("Scale", component.scale);
	}
}
