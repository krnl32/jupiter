package com.krnl32.jupiter.renderer.components.utility;

import com.krnl32.jupiter.components.utility.UUIDComponent;
import com.krnl32.jupiter.renderer.ComponentRenderer;
import com.krnl32.jupiter.utility.GUIUtils;

public class UUIDComponentRenderer implements ComponentRenderer<UUIDComponent> {
	@Override
	public void render(UUIDComponent component) {
		GUIUtils.renderStringReadOnly("UUID", component.uuid.toString());
	}
}
