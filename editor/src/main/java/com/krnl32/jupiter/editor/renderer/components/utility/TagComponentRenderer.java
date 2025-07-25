package com.krnl32.jupiter.editor.renderer.components.utility;

import com.krnl32.jupiter.engine.components.utility.TagComponent;
import com.krnl32.jupiter.editor.renderer.ComponentRenderer;
import com.krnl32.jupiter.editor.utility.GUIUtils;
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
