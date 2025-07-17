package com.krnl32.jupiter.ecs;

import com.krnl32.jupiter.components.utility.UUIDComponent;
import com.krnl32.jupiter.renderer.Renderer;

import java.util.*;

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
		Entity entity = entityManager.create(this);
		//Logger.info("Registry Create Entity({})", entity.getId());
		return entity;
	}

	public void destroyEntity(Entity entity) {
		for(ComponentPool<?> pool : componentPools.values()) {
			//Logger.info("Registry Remove Entity({}) from ComponentPool({}): ", entity.getId(), pool.getComponents().getClass().getSimpleName());
			pool.remove(entity);
		}
		entityManager.remove(entity);
		//Logger.info("Registry Destroy Entity({})", entity.getId());
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

	public Entity getEntityByUUID(UUID uuid) {
		for (Entity entity : getEntities()) {
			UUIDComponent uuidComponent = entity.getComponent(UUIDComponent.class);
			if (uuidComponent != null && uuidComponent.uuid.equals(uuid)) {
				return entity;
			}
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public <T extends Component> void addComponent(Entity entity, T component) {
		getComponentPool((Class<T>) component.getClass()).set(entity, component);
		//Logger.info("Registry Add Component({}) to Entity({})", component.getClass().getSimpleName(), entity.getId());
	}

	public <T extends Component> void removeComponent(Entity entity, Class<T> component) {
		getComponentPool(component).remove(entity);
		//Logger.info("Registry Remove Component({}) from Entity({})", component.getSimpleName(), entity.getId());
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
