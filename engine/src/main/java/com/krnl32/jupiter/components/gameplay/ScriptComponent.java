package com.krnl32.jupiter.components.gameplay;

import com.krnl32.jupiter.ecs.Component;
import com.krnl32.jupiter.script.ScriptInstance;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ScriptComponent implements Component {
	public List<ScriptInstance> scripts;

	public ScriptComponent() {
		scripts = new ArrayList<>();
	}

	public ScriptComponent(ScriptInstance... scripts) {
		this.scripts = new ArrayList<>(Arrays.asList(scripts));
	}

	public ScriptComponent(List<ScriptInstance> scripts) {
		this.scripts = scripts;
	}
}
