package com.krnl32.jupiter.components.utility;

import com.krnl32.jupiter.ecs.Component;

import java.util.UUID;

public class UUIDComponent implements Component {
	public UUID uuid;

	public UUIDComponent() {
		this.uuid = UUID.randomUUID();
	}

	public UUIDComponent(UUID uuid) {
		this.uuid = uuid;
	}
}
