package com.krnl32.jupiter.engine.script;

import java.nio.file.Path;

public interface ScriptDefinition {
	Path getScriptPath();
	ScriptSettings getSettings();
	ScriptBindings createBindings(ScriptContext context);
}
