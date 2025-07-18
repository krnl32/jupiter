package com.krnl32.jupiter.factory.components.utility;

import com.krnl32.jupiter.components.utility.UUIDComponent;
import com.krnl32.jupiter.factory.ComponentFactory;

public class UUIDComponentFactory implements ComponentFactory<UUIDComponent> {
	@Override
	public UUIDComponent create() {
		return new UUIDComponent();
	}
}
