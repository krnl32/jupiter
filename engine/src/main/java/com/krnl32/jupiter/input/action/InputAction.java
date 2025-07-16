package com.krnl32.jupiter.input.action;

import java.util.ArrayList;
import java.util.List;

public class InputAction {
	private final String name;
	private final List<InputBinding> bindings;
	private boolean active;
	private boolean pressed;
	private boolean released;

	public InputAction(String name) {
		this.name = name;
		this.bindings = new ArrayList<>();
		this.active = false;
		this.pressed = false;
		this.released = false;
	}

	public void addBinding(InputBinding binding) {
		bindings.add(binding);
	}

	public String getName() {
		return name;
	}

	public boolean isActive() {
		return active;
	}

	public boolean isPressed() {
		return pressed;
	}

	public boolean isReleased() {
		return released;
	}

	protected void onUpdate() {
		active = pressed = released = false;

		for (InputBinding binding : bindings) {
			boolean down = binding.isDown();
			if (down && !active) {
				active = true;
			}
			if (binding.isPressed()) {
				pressed = true;
			}
			if (binding.isReleased()) {
				released = true;
			}
		}
	}
}
