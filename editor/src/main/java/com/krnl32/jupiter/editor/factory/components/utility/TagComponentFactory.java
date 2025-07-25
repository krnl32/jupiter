package com.krnl32.jupiter.editor.factory.components.utility;

import com.krnl32.jupiter.engine.components.utility.TagComponent;
import com.krnl32.jupiter.editor.factory.ComponentFactory;

public class TagComponentFactory implements ComponentFactory<TagComponent> {
	@Override
	public TagComponent create() {
		return new TagComponent("entity");
	}
}
