package com.krnl32.jupiter.serializer.components.gameplay;

import com.krnl32.jupiter.asset.AssetID;
import com.krnl32.jupiter.asset.AssetManager;
import com.krnl32.jupiter.asset.types.ScriptAsset;
import com.krnl32.jupiter.components.gameplay.ScriptComponent;
import com.krnl32.jupiter.core.Logger;
import com.krnl32.jupiter.serializer.ComponentSerializer;
import com.krnl32.jupiter.serializer.resolvers.EntityResolver;
import org.json.JSONObject;

public class ScriptComponentSerializer implements ComponentSerializer<ScriptComponent> {
	@Override
	public JSONObject serialize(ScriptComponent component) {
		ScriptAsset scriptAsset = AssetManager.getInstance().getAsset(component.scriptAssetID);
		if (scriptAsset == null) {
			Logger.error("ScriptComponentSerializer Serialize Failed, Invalid Script AssetID({})", component.scriptAssetID);
			return null;
		}

		return new JSONObject()
			.put("scriptAssetID", scriptAsset.getRelativePath())
			.put("disabled", component.disabled);
	}

	@Override
	public ScriptComponent deserialize(JSONObject data, EntityResolver resolver) {
		AssetID scriptAssetID = AssetManager.getInstance().registerAndLoad(data.getString("scriptAssetID"), () -> new ScriptAsset(data.getString("scriptAssetID")));
		if (scriptAssetID == null) {
			Logger.error("ScriptComponentSerializer Deserialize Failed, Invalid Script AssetPath({})", data.getString("scriptAssetID"));
			return null;
		}

		return new ScriptComponent(scriptAssetID, data.getBoolean("disabled"));
	}

	@Override
	public ScriptComponent clone(ScriptComponent component) {
		return new ScriptComponent(new AssetID(component.scriptAssetID.getId()));
	}
}
