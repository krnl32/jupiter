package com.krnl32.jupiter.input.action;

import java.util.HashMap;
import java.util.Map;

public class InputActionMap {
	private final String name;
	private final Map<String, InputAction> actions;
	private boolean enabled;

	public InputActionMap(String name) {
		this.name = name;
		this.actions = new HashMap<>();
		this.enabled = true;
	}

	public void onUpdate() {
		for (InputAction action : actions.values()) {
			action.onUpdate();
		}
	}

	public void addAction(InputAction inputAction) {
		actions.put(inputAction.getName(), inputAction);
	}

	public void removeAction(String actionName) {
		actions.remove(actionName);
	}

	public InputAction getAction(String actionName) {
		return actions.get(actionName);
	}

	public String getName() {
		return name;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
}
