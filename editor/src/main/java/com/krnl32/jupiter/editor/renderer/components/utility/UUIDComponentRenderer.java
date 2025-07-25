package com.krnl32.jupiter.editor.renderer.components.utility;

import com.krnl32.jupiter.editor.renderer.ComponentRenderer;
import com.krnl32.jupiter.editor.utility.GUIUtils;
import com.krnl32.jupiter.engine.components.utility.UUIDComponent;

public class UUIDComponentRenderer implements ComponentRenderer<UUIDComponent> {
	@Override
	public void render(UUIDComponent component) {
		GUIUtils.renderStringReadOnly("UUID", component.uuid.toString());
	}
}
