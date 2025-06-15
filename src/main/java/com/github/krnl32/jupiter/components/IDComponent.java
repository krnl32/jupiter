package com.github.krnl32.jupiter.components;

import com.github.krnl32.jupiter.ecs.Component;

import java.util.UUID;

public class IDComponent implements Component {
	public UUID id;

	public IDComponent() {
		this.id = UUID.randomUUID();
	}

	public IDComponent(UUID id) {
		this.id = id;
	}
}
