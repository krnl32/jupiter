package com.krnl32.jupiter.engine.serializer.utility;

import com.krnl32.jupiter.engine.script.ScriptInstance;
import org.json.JSONObject;

public class JupiterSerializerUtils {

	public static JSONObject serializeScriptInstance(ScriptInstance scriptInstance) {
//		ScriptAsset scriptAsset = ProjectContext.getInstance().getAssetManager().getAsset(scriptInstance.getScriptAssetId());
//		if (scriptAsset == null) {
//			Logger.error("serializeScriptInstance Failed, Invalid Script AssetId({})", scriptInstance.getScriptAssetId());
//			return null;
//		}
//
//		return new JSONObject()
//			.put("scriptAssetId", scriptAsset.getRelativePath())
//			.put("disabled", scriptInstance.isDisabled());
		return null;
	}

	public static ScriptInstance deserializeScriptInstance(JSONObject data) {
//		AssetId scriptAssetId = ProjectContext.getInstance().getAssetManager().registerAndLoad(data.getString("scriptAssetId"), () -> new ScriptAsset(data.getString("scriptAssetId")));
//		if (scriptAssetId == null) {
//			Logger.error("deserializeScriptInstance Failed, Invalid Script AssetPath({})", data.getString("scriptAssetId"));
//			return null;
//		}
//
//		return new ScriptInstance(scriptAssetId, data.getBoolean("disabled"));
		return null;
	}
}
