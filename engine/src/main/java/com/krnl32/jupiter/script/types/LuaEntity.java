package com.krnl32.jupiter.script.types;

import com.krnl32.jupiter.core.Logger;
import com.krnl32.jupiter.ecs.Component;
import com.krnl32.jupiter.script.ScriptContext;
import com.krnl32.jupiter.script.binder.BinderRegistry;
import com.krnl32.jupiter.script.binder.ComponentBinder;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;

public class LuaEntity extends LuaValue {
	private final ScriptContext scriptContext;

	public LuaEntity(ScriptContext scriptContext) {
		this.scriptContext = scriptContext;
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
							if (scriptContext.getEntity().hasComponent(entry.getKey())) {
								Logger.warn("LuaEntity Failed to AddComponent({}) for Entity({}), Already Exists!", componentName.checkjstring(), scriptContext.getEntity().getTagOrId());
								return LuaValue.NIL;
							}

							ComponentBinder<? extends Component> binder = entry.getValue();
							Component component = binder.fromLua(componentData);
							scriptContext.getEntity().addComponent(component);
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
							Component component = scriptContext.getEntity().getComponent(entry.getKey());
							if (component == null) {
								Logger.warn("LuaEntity Failed to SetComponent({}) for Entity({}), Component Doesn't Exist!", componentName.checkjstring(), scriptContext.getEntity().getTagOrId());
								return LuaValue.NIL;
							}

							binder.updateFromLua(component, componentData);
							scriptContext.getEntity().addComponent(component);
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
							scriptContext.getEntity().removeComponent(entry.getKey());
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
							Component component = scriptContext.getEntity().getComponent(entry.getKey());
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
							return scriptContext.getEntity().hasComponent(entry.getKey()) ? LuaValue.TRUE : LuaValue.FALSE;
						}
					}
					return LuaValue.FALSE;
				}
			};

			case "enableScript" -> new VarArgFunction() {
				@Override
				public Varargs invoke(Varargs args) {
					scriptContext.setDisabled(false);
					return LuaValue.NIL;
				}
			};
			case "disableScript" -> new VarArgFunction() {
				@Override
				public Varargs invoke(Varargs args) {
					scriptContext.setDisabled(true);
					return LuaValue.NIL;
				}
			};
			case "reloadScript" -> new VarArgFunction() {
				@Override
				public Varargs invoke(Varargs args) {
					scriptContext.setInitialized(false);
					scriptContext.setDisabled(false);
					return LuaValue.NIL;
				}
			};
			case "forceReloadScript" -> new VarArgFunction() {
				@Override
				public Varargs invoke(Varargs args) {
					scriptContext.setLastModified(0);
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
