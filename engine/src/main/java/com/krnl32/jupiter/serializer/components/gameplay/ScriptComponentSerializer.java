package com.krnl32.jupiter.serializer.components.gameplay;

import com.krnl32.jupiter.asset.AssetID;
import com.krnl32.jupiter.components.gameplay.ScriptComponent;
import com.krnl32.jupiter.core.Logger;
import com.krnl32.jupiter.script.ScriptInstance;
import com.krnl32.jupiter.serializer.ComponentSerializer;
import com.krnl32.jupiter.serializer.resolvers.EntityResolver;
import com.krnl32.jupiter.serializer.utility.JupiterSerializerUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ScriptComponentSerializer implements ComponentSerializer<ScriptComponent> {
	@Override
	public JSONObject serialize(ScriptComponent component) {
		JSONArray scriptsData = new JSONArray();

		for (ScriptInstance script : component.scripts) {
			JSONObject scriptData = JupiterSerializerUtils.serializeScriptInstance(script);
			if (scriptData == null) {
				Logger.error("ScriptComponentSerializer Serialize Failed for AssetID({})", script.getScriptAssetID());
				return null;
			}
			scriptsData.put(scriptData);
		}

		return new JSONObject().put("scripts", scriptsData);
	}

	@Override
	public ScriptComponent deserialize(JSONObject data, EntityResolver resolver) {
		List<ScriptInstance> scripts = new ArrayList<>();

		JSONArray scriptsData = data.getJSONArray("scripts");
		if (scriptsData != null) {
			for (int i = 0; i < scriptsData.length(); i++) {
				scripts.add(JupiterSerializerUtils.deserializeScriptInstance(scriptsData.getJSONObject(i)));
			}
		}

		return new ScriptComponent(scripts);
	}

	@Override
	public ScriptComponent clone(ScriptComponent component) {
		List<ScriptInstance> scripts = new ArrayList<>();

		for (ScriptInstance script : component.scripts) {
			scripts.add(new ScriptInstance(new AssetID(script.getScriptAssetID().getId()), script.isDisabled()));
		}

		return new ScriptComponent(scripts);
	}
}
