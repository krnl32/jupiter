package com.krnl32.jupiter.engine.input;

import com.krnl32.jupiter.engine.input.action.InputActionMap;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class InputActionSystem {
	private static InputActionSystem instance;
	private final List<InputActionMap> inputActionMaps = new ArrayList<>();

	private InputActionSystem() {
	}

	public static InputActionSystem getInstance() {
		if (instance == null)
			instance = new InputActionSystem();
		return instance;
	}

	public void onUpdate() {
		for (InputActionMap map : inputActionMaps) {
			if (map.isEnabled()) {
				map.onUpdate();
			}
		}
	}

	public void addInputActionMap(InputActionMap inputActionMap) {
		inputActionMaps.add(inputActionMap);
	}

	public void removeInputActionMap(String name) {
		inputActionMaps.removeIf(inputActionMap -> Objects.equals(inputActionMap.getName(), name));
	}

	public InputActionMap getInputActionMap(String name) {
		for (InputActionMap inputActionMap : inputActionMaps) {
			if (inputActionMap.getName().equals(name)) {
				return inputActionMap;
			}
		}
		return null;
	}

	public void setMapEnabled(String name, boolean enabled) {
		InputActionMap inputActionMap = getInputActionMap(name);
		if (inputActionMap != null) {
			inputActionMap.setEnabled(enabled);
		}
	}

	public boolean isMapEnabled(String name) {
		InputActionMap inputActionMap = getInputActionMap(name);
		return inputActionMap != null && inputActionMap.isEnabled();
	}
}
