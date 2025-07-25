package com.krnl32.jupiter.editor.factory.components.utility;

import com.krnl32.jupiter.engine.components.utility.UUIDComponent;
import com.krnl32.jupiter.editor.factory.ComponentFactory;

public class UUIDComponentFactory implements ComponentFactory<UUIDComponent> {
	@Override
	public UUIDComponent create() {
		return new UUIDComponent();
	}
}
