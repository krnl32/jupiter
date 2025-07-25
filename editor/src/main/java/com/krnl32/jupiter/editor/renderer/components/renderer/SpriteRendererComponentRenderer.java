package com.krnl32.jupiter.editor.renderer.components.renderer;

import com.krnl32.jupiter.engine.components.renderer.SpriteRendererComponent;
import com.krnl32.jupiter.editor.renderer.ComponentRenderer;
import com.krnl32.jupiter.editor.utility.GUIUtils;
import imgui.type.ImInt;

public class SpriteRendererComponentRenderer implements ComponentRenderer<SpriteRendererComponent> {
	@Override
	public void render(SpriteRendererComponent component) {
		ImInt index = new ImInt(component.index);
		if (GUIUtils.renderIntInput("Index", index)) {
			component.index = index.get();
		}

		GUIUtils.renderColorPicker("Color", component.color);
	}
}
