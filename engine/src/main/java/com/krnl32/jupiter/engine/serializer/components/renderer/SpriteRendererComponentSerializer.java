package com.krnl32.jupiter.engine.serializer.components.renderer;

import com.krnl32.jupiter.engine.asset.handle.AssetId;
import com.krnl32.jupiter.engine.components.renderer.SpriteRendererComponent;
import com.krnl32.jupiter.engine.core.Logger;
import com.krnl32.jupiter.engine.project.ProjectContext;
import com.krnl32.jupiter.engine.serializer.ComponentSerializer;
import com.krnl32.jupiter.engine.serializer.resolvers.EntityResolver;
import com.krnl32.jupiter.engine.serializer.utility.ComponentSerializerUtility;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SpriteRendererComponentSerializer implements ComponentSerializer<SpriteRendererComponent, Map<String, Object>> {
	@Override
	public Map<String, Object> serialize(SpriteRendererComponent component) {
		Map<String, Object> map = new HashMap<>();
		map.put("index", component.index);
		map.put("color", ComponentSerializerUtility.serializeVector4f(component.color));
		map.put("textureAssetId", (component.textureAssetId != null ? component.textureAssetId.getId() : null));
		map.put("textureUV", component.textureUV);
		return map;
	}

	@Override
	public SpriteRendererComponent deserialize(Map<String, Object> data, EntityResolver resolver) {
		AssetId textureAssetId = null;
		if (data.get("textureAssetId") != null) {
			textureAssetId = new AssetId(UUID.fromString(data.get("textureAssetId").toString()));
			if (!ProjectContext.getInstance().getAssetManager().isAssetRegistered(textureAssetId)) {
				Logger.error("SpriteRendererComponentSerializer Failed to Load Invalid Texture({})", textureAssetId);
				return null;
			}
		}

		return new SpriteRendererComponent(
			(int) data.get("index"),
			ComponentSerializerUtility.deserializeVector4f((Map<String, Object>) data.get("color")),
			textureAssetId,
			ComponentSerializerUtility.toFloatArray(data.get("textureUV"))
		);
	}
}
