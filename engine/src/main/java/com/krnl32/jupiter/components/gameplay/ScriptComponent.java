package com.krnl32.jupiter.components.gameplay;

import com.krnl32.jupiter.asset.AssetID;
import com.krnl32.jupiter.ecs.Component;
import org.luaj.vm2.LuaFunction;

public class ScriptComponent implements Component {
	public AssetID scriptAssetID;
	public LuaFunction onInit;
	public LuaFunction onUpdate;
	public LuaFunction onDestroy;
	public boolean initialized, disabled;
	public long lastModified;

	public ScriptComponent(AssetID scriptAssetID) {
		this.scriptAssetID = scriptAssetID;
		this.onInit = null;
		this.onUpdate = null;
		this.onDestroy = null;
		this.initialized = false;
		this.disabled = false;
		this.lastModified = 0;
	}

	public ScriptComponent(AssetID scriptAssetID, LuaFunction onInit, LuaFunction onUpdate, LuaFunction onDestroy, boolean initialized, boolean disabled, long lastModified) {
		this.scriptAssetID = scriptAssetID;
		this.onInit = onInit;
		this.onUpdate = onUpdate;
		this.onDestroy = onDestroy;
		this.initialized = initialized;
		this.disabled = disabled;
		this.lastModified = lastModified;
	}
}
