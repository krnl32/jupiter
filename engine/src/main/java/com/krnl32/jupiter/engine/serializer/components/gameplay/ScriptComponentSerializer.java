package com.krnl32.jupiter.engine.serializer.components.gameplay;

import com.krnl32.jupiter.engine.components.gameplay.ScriptComponent;
import com.krnl32.jupiter.engine.serializer.ComponentSerializer;
import com.krnl32.jupiter.engine.serializer.resolvers.EntityResolver;

import java.util.Map;

public class ScriptComponentSerializer implements ComponentSerializer<ScriptComponent, Map<String, Object>> {
	@Override
	public Map<String, Object> serialize(ScriptComponent component) {
//		JSONArray scriptsData = new JSONArray();
//
//		for (ScriptInstance script : component.scripts) {
//			JSONObject scriptData = JupiterSerializerUtils.serializeScriptInstance(script);
//			if (scriptData == null) {
//				Logger.error("ScriptComponentSerializer Serialize Failed for AssetId({})", script.getScriptAssetId());
//				return null;
//			}
//			scriptsData.put(scriptData);
//		}
//
//		return new JSONObject().put("scripts", scriptsData);
		return null;
	}

	@Override
	public ScriptComponent deserialize(Map<String, Object> data, EntityResolver resolver) {
//		List<ScriptInstance> scripts = new ArrayList<>();
//
//		JSONArray scriptsData = data.getJSONArray("scripts");
//		if (scriptsData != null) {
//			for (int i = 0; i < scriptsData.length(); i++) {
//				scripts.add(JupiterSerializerUtils.deserializeScriptInstance(scriptsData.getJSONObject(i)));
//			}
//		}
//
//		return new ScriptComponent(scripts);
		return null;
	}
}
