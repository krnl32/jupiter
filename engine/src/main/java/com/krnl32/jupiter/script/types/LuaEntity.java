package com.krnl32.jupiter.script.types;

import com.krnl32.jupiter.components.gameplay.ScriptComponent;
import com.krnl32.jupiter.core.Logger;
import com.krnl32.jupiter.ecs.Component;
import com.krnl32.jupiter.ecs.Entity;
import com.krnl32.jupiter.script.BinderRegistry;
import com.krnl32.jupiter.script.ComponentBinder;
import com.krnl32.jupiter.script.utility.ScriptLoader;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;

import java.io.File;

public class LuaEntity extends LuaValue {
	private final Entity entity;

	public LuaEntity(Entity entity) {
		this.entity = entity;
	}

	@Override
	public LuaValue get(LuaValue key) {
		return switch (key.checkjstring()) {
			case "addComponent" -> new VarArgFunction() {
				@Override
				public Varargs invoke(Varargs args) {
					LuaValue componentName = args.arg(2);
					LuaValue componentData = args.arg(3);
					for (var entry : BinderRegistry.getComponentBinders().entrySet()) {
						if (entry.getKey().getSimpleName().equals(componentName.checkjstring())) {
							if (entity.hasComponent(entry.getKey())) {
								Logger.warn("LuaEntity Failed to AddComponent({}) for Entity({}), Already Exists!", componentName.checkjstring(), entity.getTagOrId());
								return LuaValue.NIL;
							}

							ComponentBinder<? extends Component> binder = entry.getValue();
							Component component = binder.fromLua(componentData);
							entity.addComponent(component);
							break;
						}
					}
					return LuaValue.NIL;
				}
			};
			case "setComponent" -> new VarArgFunction() {
				@Override
				public Varargs invoke(Varargs args) {
					LuaValue componentName = args.arg(2);
					LuaValue componentData = args.arg(3);
					for (var entry : BinderRegistry.getComponentBinders().entrySet()) {
						if (entry.getKey().getSimpleName().equals(componentName.checkjstring())) {
							ComponentBinder binder = entry.getValue();
							Component component = entity.getComponent(entry.getKey());
							if (component == null) {
								Logger.warn("LuaEntity Failed to SetComponent({}) for Entity({}), Component Doesn't Exist!", componentName.checkjstring(), entity.getTagOrId());
								return LuaValue.NIL;
							}

							binder.updateFromLua(component, componentData);
							entity.addComponent(component);
							break;
						}
					}
					return LuaValue.NIL;
				}
			};
			case "removeComponent" -> new VarArgFunction() {
				@Override
				public Varargs invoke(Varargs args) {
					LuaValue componentName = args.arg(2);
					for (var entry : BinderRegistry.getComponentBinders().entrySet()) {
						if (entry.getKey().getSimpleName().equals(componentName.checkjstring())) {
							entity.removeComponent(entry.getKey());
							break;
						}
					}
					return LuaValue.NIL;
				}
			};
			case "getComponent" -> new VarArgFunction() {
				@Override
				public Varargs invoke(Varargs args) {
					LuaValue componentName = args.arg(2);
					for (var entry : BinderRegistry.getComponentBinders().entrySet()) {
						if (entry.getKey().getSimpleName().equals(componentName.checkjstring())) {
							ComponentBinder binder = entry.getValue();
							Component component = entity.getComponent(entry.getKey());
							return component != null ? binder.toLua(component) : LuaValue.NIL;
						}
					}
					return LuaValue.NIL;
				}
			};
			case "hasComponent" -> new VarArgFunction() {
				@Override
				public Varargs invoke(Varargs args) {
					LuaValue componentName = args.arg(2);
					for (var entry : BinderRegistry.getComponentBinders().entrySet()) {
						if (entry.getKey().getSimpleName().equals(componentName.checkjstring())) {
							return entity.hasComponent(entry.getKey()) ? LuaValue.TRUE : LuaValue.FALSE;
						}
					}
					return LuaValue.FALSE;
				}
			};

			case "enableScript" -> new VarArgFunction() {
				@Override
				public Varargs invoke(Varargs args) {
					ScriptComponent script = entity.getComponent(ScriptComponent.class);
					if (script != null) {
						script.disabled = false;
					}
					return LuaValue.NIL;
				}
			};
			case "disableScript" -> new VarArgFunction() {
				@Override
				public Varargs invoke(Varargs args) {
					ScriptComponent script = entity.getComponent(ScriptComponent.class);
					if (script != null) {
						script.disabled = true;
					}
					return LuaValue.NIL;
				}
			};
			case "reloadScript" -> new VarArgFunction() {
				@Override
				public Varargs invoke(Varargs args) {
					ScriptComponent script = entity.getComponent(ScriptComponent.class);
					if (script != null) {
						ScriptComponent reloaded = ScriptLoader.loadScript(script.scriptPath, entity);
						script.onInit = reloaded.onInit;
						script.onUpdate = reloaded.onUpdate;
						script.onDestroy = reloaded.onDestroy;
						script.initialized = false;
						script.disabled = false;
						script.lastModified = new File(script.scriptPath).lastModified();
					}
					return LuaValue.NIL;
				}
			};
			default -> LuaValue.NIL;
		};
	}

	@Override
	public int type() {
		return LuaValue.TUSERDATA;
	}

	@Override
	public String typename() {
		return "LuaEntity";
	}
}
