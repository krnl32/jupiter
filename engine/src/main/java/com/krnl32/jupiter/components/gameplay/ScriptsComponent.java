package com.krnl32.jupiter.components.gameplay;

import com.krnl32.jupiter.ecs.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ScriptsComponent implements Component {
	public List<ScriptComponent> scripts;

	public ScriptsComponent() {
		scripts = new ArrayList<>();
	}

	public ScriptsComponent(ScriptComponent... scripts) {
		this.scripts = new ArrayList<>(Arrays.asList(scripts));
	}

	public ScriptsComponent(List<ScriptComponent> scripts) {
		this.scripts = scripts;
	}
}
