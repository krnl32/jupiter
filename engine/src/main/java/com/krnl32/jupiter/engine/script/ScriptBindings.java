package com.krnl32.jupiter.engine.script;

public interface ScriptBindings {
	boolean onInit() throws Exception;
	void onDestroy() throws Exception;
	void onUpdate(float dt) throws Exception;
}
