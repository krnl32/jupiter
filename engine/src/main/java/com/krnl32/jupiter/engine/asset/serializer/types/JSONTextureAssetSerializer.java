package com.krnl32.jupiter.engine.asset.serializer.types;

import com.krnl32.jupiter.engine.asset.serializer.AssetSerializer;
import com.krnl32.jupiter.engine.asset.types.TextureAsset;
import org.json.JSONObject;

// Makes No Sense, Delete....
public class JSONTextureAssetSerializer implements AssetSerializer<TextureAsset, JSONObject> {
	@Override
	public byte[] serialize(TextureAsset asset) {
		return new byte[0];
	}

	@Override
	public JSONObject deserialize(byte[] data) {
		return null;
	}
}
