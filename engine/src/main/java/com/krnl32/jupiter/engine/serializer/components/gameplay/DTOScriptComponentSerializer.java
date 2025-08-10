package com.krnl32.jupiter.engine.serializer.components.gameplay;

import com.krnl32.jupiter.engine.components.gameplay.ScriptComponent;
import com.krnl32.jupiter.engine.core.Logger;
import com.krnl32.jupiter.engine.script.ScriptInstance;
import com.krnl32.jupiter.engine.serializer.ComponentSerializer;
import com.krnl32.jupiter.engine.serializer.resolvers.EntityResolver;
import com.krnl32.jupiter.engine.serializer.utility.DTOComponentSerializerUtility;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DTOScriptComponentSerializer implements ComponentSerializer<ScriptComponent, Map<String, Object>> {
	@Override
	public Map<String, Object> serialize(ScriptComponent component) {
		List<Object> scriptsData = new ArrayList<>();

		for (ScriptInstance script : component.scripts) {
			Map<String, Object> scriptData = DTOComponentSerializerUtility.serializeScriptInstance(script);
			if (scriptData == null) {
				Logger.error("ScriptComponentSerializer Serialize Failed for AssetId({})", script.getScriptAssetId());
				return null;
			}
			scriptsData.add(scriptData);
		}

		return Map.of("scripts", scriptsData);
	}

	@Override
	public ScriptComponent deserialize(Map<String, Object> data, EntityResolver resolver) {
		List<ScriptInstance> scripts = new ArrayList<>();

		List<Object> scriptsData = (List<Object>) data.get("scripts");
		if (scriptsData != null) {
			for (int i = 0; i < scriptsData.size(); i++) {
				scripts.add(DTOComponentSerializerUtility.deserializeScriptInstance((Map<String, Object>) scriptsData.get(i)));
			}
		}

		return new ScriptComponent(scripts);

//
//		JSONArray scriptsData = data.getJSONArray("scripts");
//		if (scriptsData != null) {
//			for (int i = 0; i < scriptsData.length(); i++) {
//				scripts.add(JupiterSerializerUtils.deserializeScriptInstance(scriptsData.getJSONObject(i)));
//			}
//		}
//
//		return new ScriptComponent(scripts);
	}
}
