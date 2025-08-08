package com.krnl32.jupiter.engine.serializer.components.gameplay;

import com.krnl32.jupiter.engine.asset.handle.AssetId;
import com.krnl32.jupiter.engine.components.gameplay.ScriptComponent;
import com.krnl32.jupiter.engine.core.Logger;
import com.krnl32.jupiter.engine.script.ScriptInstance;
import com.krnl32.jupiter.engine.serializer.ComponentSerializer;
import com.krnl32.jupiter.engine.serializer.resolvers.EntityResolver;
import com.krnl32.jupiter.engine.serializer.utility.JupiterSerializerUtils;
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
				Logger.error("ScriptComponentSerializer Serialize Failed for AssetId({})", script.getScriptAssetId());
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
			scripts.add(new ScriptInstance(new AssetId(script.getScriptAssetId().getId()), script.isDisabled()));
		}

		return new ScriptComponent(scripts);
	}
}
