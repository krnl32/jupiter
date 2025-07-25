package com.krnl32.jupiter.engine.ecs;

import com.krnl32.jupiter.engine.components.utility.TagComponent;

import java.util.Objects;
import java.util.Set;

public class Entity {
	private final int id;
	private final Registry registry;

	public Entity(int id, Registry registry) {
		this.id = id;
		this.registry = registry;
	}

	public int getId() {
		return id;
	}

	public Registry getRegistry() {
		return registry;
	}

	public String getTagOrId() {
		return hasComponent(TagComponent.class) ? getComponent(TagComponent.class).tag : String.valueOf(id);
	}

	public void setTag(String tag) {
		TagComponent tagComponent = getComponent(TagComponent.class);
		if (tagComponent == null) {
			addComponent(new TagComponent(tag));
		} else {
			tagComponent.tag = tag;
		}
	}

	public <T extends Component> void addComponent(T component) {
		registry.addComponent(this, component);
	}

	public <T extends Component> void removeComponent(Class<T> component) {
		registry.removeComponent(this, component);
	}

	public <T extends Component> T getComponent(Class<T> component) {
		return registry.getComponent(this, component);
	}

	public Set<Component> getComponents() {
		return registry.getComponents(this);
	}

	public <T extends Component> boolean hasComponent(Class<T> component) {
		return registry.hasComponent(this, component);
	}

	@Override
	public boolean equals(Object o) {
		if (o == null || getClass() != o.getClass()) return false;
		Entity entity = (Entity) o;
		return id == entity.id;
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(id);
	}

	@Override
	public String toString() {
		return "Entity{" +
			"id=" + id +
			'}';
	}
}
