package com.krnl32.jupiter.factory.components;

import com.krnl32.jupiter.components.TagComponent;
import com.krnl32.jupiter.factory.ComponentFactory;

public class TagComponentFactory implements ComponentFactory<TagComponent> {
	@Override
	public TagComponent create() {
		return new TagComponent("entity");
	}
}
