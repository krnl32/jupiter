package com.krnl32.jupiter.serializer.components.gameplay;

import com.krnl32.jupiter.asset.AssetID;
import com.krnl32.jupiter.asset.AssetManager;
import com.krnl32.jupiter.asset.types.ScriptAsset;
import com.krnl32.jupiter.components.gameplay.ScriptComponent;
import com.krnl32.jupiter.components.gameplay.ScriptsComponent;
import com.krnl32.jupiter.core.Logger;
import com.krnl32.jupiter.serializer.ComponentSerializer;
import com.krnl32.jupiter.serializer.resolvers.EntityResolver;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ScriptsComponentSerializer implements ComponentSerializer<ScriptsComponent> {
	@Override
	public JSONObject serialize(ScriptsComponent component) {
		JSONArray scriptsData = new JSONArray();

		for (ScriptComponent script : component.scripts) {
			ScriptAsset scriptAsset = AssetManager.getInstance().getAsset(script.scriptAssetID);
			if (scriptAsset == null) {
				Logger.error("ScriptsComponentSerializer Serialize Failed, Invalid Script AssetID({})", script.scriptAssetID);
				return null;
			}

			scriptsData.put(
				new JSONObject()
					.put("scriptAssetID", scriptAsset.getRelativePath())
					.put("disabled", script.disabled)
			);
		}

		return new JSONObject().put("scripts", scriptsData);
	}

	@Override
	public ScriptsComponent deserialize(JSONObject data, EntityResolver resolver) {
		List<ScriptComponent> scriptComponents = new ArrayList<>();

		JSONArray scriptsData = data.getJSONArray("scripts");
		if (scriptsData != null) {
			for (int i = 0; i < scriptsData.length(); i++) {
				JSONObject scriptData = scriptsData.getJSONObject(i);

				AssetID scriptAssetID = AssetManager.getInstance().registerAndLoad(scriptData.getString("scriptAssetID"), () -> new ScriptAsset(scriptData.getString("scriptAssetID")));
				if (scriptAssetID == null) {
					Logger.error("ScriptComponentSerializer Deserialize Failed, Invalid Script AssetPath({})", scriptData.getString("scriptAssetID"));
					return null;
				}

				scriptComponents.add(new ScriptComponent(scriptAssetID, scriptData.getBoolean("disabled")));
			}
		}

		return new ScriptsComponent(scriptComponents);
	}

	@Override
	public ScriptsComponent clone(ScriptsComponent component) {
		List<ScriptComponent> scriptComponents = new ArrayList<>();

		for (ScriptComponent script : component.scripts) {
			scriptComponents.add(new ScriptComponent(new AssetID(script.scriptAssetID.getId()), script.disabled));
		}

		return new ScriptsComponent(scriptComponents);
	}
}
