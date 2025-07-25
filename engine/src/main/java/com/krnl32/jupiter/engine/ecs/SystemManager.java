package com.krnl32.jupiter.engine.ecs;

import com.krnl32.jupiter.engine.core.Logger;
import com.krnl32.jupiter.engine.renderer.Renderer;

import java.util.*;

public class SystemManager {
	private final Map<Class<? extends System>, ManagedSystem> systems = new HashMap<>();
	private final List<ManagedSystem> sortedSystems = new ArrayList<>();

	public void onUpdate(float dt) {
		for (var sys: sortedSystems) {
			if (sys.isEnabled())
				sys.getSystem().onUpdate(dt);
		}
	}

	public void onRender(float dt, Renderer renderer) {
		for (var sys: sortedSystems) {
			if (sys.isEnabled())
				sys.getSystem().onRender(dt, renderer);
		}
	}

	public void add(System system, int priority, boolean enabled) {
		if (systems.containsKey(system.getClass())) {
			Logger.error("SystemManager Failed to Add({}), Already Exists", system.getClass().getName());
			return;
		}

		ManagedSystem sys = new ManagedSystem(system, priority, enabled);
		systems.put(system.getClass(), sys);
		sortedSystems.add(sys);
		sortedSystems.sort(Comparator.comparingInt(ManagedSystem::getPriority));
	}

	public void add(System system) {
		add(system, 101, true);
	}

	public void remove(Class<? extends System> system) {
		ManagedSystem sys = systems.remove(system);
		if (sys != null)
			sortedSystems.remove(sys);
	}

	@SuppressWarnings("unchecked")
	public <T extends System> T get(Class<T> system) {
		ManagedSystem sys = systems.get(system);
		return sys == null ? null : (T) sys.getSystem();
	}

	public void setEnabled(Class<? extends System> system, boolean enabled) {
		ManagedSystem sys = systems.get(system);
		if (sys != null)
			sys.setEnabled(enabled);
	}
}

class ManagedSystem {
	private final System system;
	private int priority;
	private boolean enabled;

	public ManagedSystem(System system, int priority, boolean enabled) {
		this.system = system;
		this.priority = priority;
		this.enabled = enabled;
	}

	public System getSystem() {
		return system;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
}
