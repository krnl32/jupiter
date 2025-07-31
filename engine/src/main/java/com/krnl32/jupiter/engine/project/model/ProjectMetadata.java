package com.krnl32.jupiter.engine.project.model;

import java.util.UUID;

public final class ProjectMetadata {
	private final UUID uuid;
	private final long lastModified;

	public ProjectMetadata(UUID uuid, long lastModified) {
		this.uuid = uuid;
		this.lastModified = lastModified;
	}

	public UUID getUUID() {
		return uuid;
	}

	public long getLastModified() {
		return lastModified;
	}

	@Override
	public String toString() {
		return "ProjectMetadata{" +
			"uuid=" + uuid +
			", lastModified=" + lastModified +
			'}';
	}
}
