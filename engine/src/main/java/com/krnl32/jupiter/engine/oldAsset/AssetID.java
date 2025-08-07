package com.krnl32.jupiter.engine.oldAsset;

import java.util.Objects;
import java.util.UUID;

public class AssetID {
	private final UUID id;

	public AssetID(UUID id) {
		this.id = id;
	}

	public UUID getId() {
		return id;
	}

	@Override
	public boolean equals(Object o) {
		if (o == null || getClass() != o.getClass()) return false;
		AssetID assetID = (AssetID) o;
		return Objects.equals(id, assetID.id);
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(id);
	}

	@Override
	public String toString() {
		return "AssetID{" +
			"id=" + id +
			'}';
	}
}
