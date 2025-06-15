package com.github.krnl32.jupiter.ecs;

import com.github.krnl32.jupiter.renderer.Renderer;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Registry {
	private final EntityManager entityManager = new EntityManager();
	private final Map<Class<? extends Component>, ComponentPool<?>> componentPools = new HashMap<>();
	private final SystemManager systemManager = new SystemManager();

	public void onUpdate(float dt) {
		systemManager.onUpdate(dt);
	}

	public void onRender(float dt, Renderer renderer) {
		systemManager.onRender(dt, renderer);
	}

	public Entity createEntity() {
		return entityManager.create(this);
	}

	public void destroyEntity(Entity entity) {
		for(ComponentPool<?> pool : componentPools.values())
			pool.remove(entity);
		entityManager.remove(entity);
	}

	public Set<Entity> getEntities() {
		Set<Entity> entities = new HashSet<>();
		for (var entityId : entityManager.getAllEntityIds())
			entities.add(new Entity(entityId, this));
		return entities;
	}

	@SuppressWarnings("unchecked")
	public <T extends Component> ComponentPool<T> getComponentPool(Class<T> component) {
		return (ComponentPool<T>) componentPools.computeIfAbsent(component, c -> new ComponentPool<>(component, 10000));
	}

	@SafeVarargs
	public final Set<Entity> getEntitiesWith(Class<? extends Component>... components) {
		Set<Integer> result = new HashSet<>(entityManager.getAllEntityIds());

		for (var component : components) {
			var pool = componentPools.get(component);
			if (pool == null)
				return Set.of();

			Component[] componentData = pool.getComponents();
			Set<Integer> idsWithComp = new HashSet<>();
			for (int i = 0; i < componentData.length; i++) {
				if (componentData[i] != null)
					idsWithComp.add(i);
			}
			result.retainAll(idsWithComp);
		}

		Set<Entity> entities = new HashSet<>();
		for (int id : result)
			entities.add(new Entity(id, this));
		return entities;
	}

	@SuppressWarnings("unchecked")
	public <T extends Component> void addComponent(Entity entity, T component) {
		getComponentPool((Class<T>) component.getClass()).set(entity, component);
	}

	public <T extends Component> void removeComponent(Entity entity, Class<T> component) {
		getComponentPool(component).remove(entity);
	}

	public <T extends Component> T getComponent(Entity entity, Class<T> component) {
		return getComponentPool(component).get(entity);
	}

	public Set<Component> getComponents(Entity entity) {
		Set<Component> components = new HashSet<>();
		for (var pool : componentPools.values()) {
			if (pool.has(entity)) {
				Component component = pool.get(entity);
				if (component != null)
					components.add(component);
			}
		}
		return components;
	}

	public <T extends Component> boolean hasComponent(Entity entity, Class<T> component) {
		return getComponentPool(component).has(entity);
	}

	public void addSystem(System system) {
		systemManager.add(system);
	}

	public void addSystem(System system, int priority, boolean enabled) {
		systemManager.add(system, priority, enabled);
	}

	public void removeSystem(Class<? extends System> system) {
		systemManager.remove(system);
	}

	public <T extends System> T getSystem(Class<T> system) {
		return systemManager.get(system);
	}

	public void setSystemEnabled(Class<? extends System> system, boolean enabled) {
		systemManager.setEnabled(system, enabled);
	}
}
