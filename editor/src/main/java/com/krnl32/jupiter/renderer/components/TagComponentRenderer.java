package com.krnl32.jupiter.renderer.components;

import com.krnl32.jupiter.components.TagComponent;
import com.krnl32.jupiter.renderer.ComponentRenderer;
import com.krnl32.jupiter.utility.GUIUtils;
import imgui.type.ImString;

public class TagComponentRenderer implements ComponentRenderer<TagComponent> {
	@Override
	public void render(TagComponent component) {
		ImString tagStr = new ImString(component.tag == null ? "" : component.tag, 256);
		boolean changed = GUIUtils.renderStringInputWithClearButton("Tag", tagStr);
		if (changed) {
			component.tag = tagStr.get();
		}
	}
}
