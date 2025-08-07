package com.krnl32.jupiter.engine.asset.handle;

import java.util.Objects;
import java.util.UUID;

public final class AssetId {
	private final UUID id;

	public AssetId() {
		this.id = UUID.randomUUID();
	}

	public AssetId(UUID id) {
		this.id = id;
	}

	public UUID getId() {
		return id;
	}

	@Override
	public boolean equals(Object o) {
		if (o == null || getClass() != o.getClass()) return false;
		AssetId assetId = (AssetId) o;
		return Objects.equals(id, assetId.id);
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(id);
	}

	@Override
	public String toString() {
		return "AssetId{" +
			"id=" + id +
			'}';
	}
}
