package com.krnl32.jupiter.factory.components.utility;

import com.krnl32.jupiter.components.utility.TagComponent;
import com.krnl32.jupiter.factory.ComponentFactory;

public class TagComponentFactory implements ComponentFactory<TagComponent> {
	@Override
	public TagComponent create() {
		return new TagComponent("entity");
	}
}
