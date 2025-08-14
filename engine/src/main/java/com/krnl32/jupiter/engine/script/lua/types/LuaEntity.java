package com.krnl32.jupiter.engine.script.lua.types;

import com.krnl32.jupiter.engine.core.Logger;
import com.krnl32.jupiter.engine.ecs.Component;
import com.krnl32.jupiter.engine.script.ScriptContext;
import com.krnl32.jupiter.engine.script.binder.ComponentBinderRegistry;
import com.krnl32.jupiter.engine.script.binder.ComponentBinder;
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
					var componentClass = ComponentBinderRegistry.getComponentClass(componentName.checkjstring());

					if (scriptContext.getEntity().hasComponent(componentClass)) {
						Logger.warn("LuaEntity Failed to AddComponent({}) for Entity({}), Already Exists!", componentName.checkjstring(), scriptContext.getEntity().getTagOrId());
						return LuaValue.NIL;
					}

					ComponentBinder<? extends Component> binder = ComponentBinderRegistry.getBinder(componentClass);
					Component component = binder.fromLua(componentData);
					scriptContext.getEntity().addComponent(component);
					return LuaValue.NIL;
				}
			};
			case "setComponent" -> new VarArgFunction() {
				@Override
				public Varargs invoke(Varargs args) {
					LuaValue componentName = args.arg(2);
					LuaValue componentData = args.arg(3);
					var componentClass = ComponentBinderRegistry.getComponentClass(componentName.checkjstring());

					Component component = scriptContext.getEntity().getComponent(componentClass);
					if (component == null) {
						Logger.warn("LuaEntity Failed to SetComponent({}) for Entity({}), Component Doesn't Exist!", componentName.checkjstring(), scriptContext.getEntity().getTagOrId());
						return LuaValue.NIL;
					}

					ComponentBinder binder = ComponentBinderRegistry.getBinder(componentClass);
					binder.updateFromLua(component, componentData);
					scriptContext.getEntity().addComponent(component);
					return LuaValue.NIL;
				}
			};
			case "removeComponent" -> new VarArgFunction() {
				@Override
				public Varargs invoke(Varargs args) {
					LuaValue componentName = args.arg(2);
					var componentClass = ComponentBinderRegistry.getComponentClass(componentName.checkjstring());
					scriptContext.getEntity().removeComponent(componentClass);
					return LuaValue.NIL;
				}
			};
			case "getComponent" -> new VarArgFunction() {
				@Override
				public Varargs invoke(Varargs args) {
					LuaValue componentName = args.arg(2);
					var componentClass = ComponentBinderRegistry.getComponentClass(componentName.checkjstring());
					Component component = scriptContext.getEntity().getComponent(componentClass);
					ComponentBinder binder = ComponentBinderRegistry.getBinder(componentClass);
					return component != null ? binder.toLua(component) : LuaValue.NIL;
				}
			};
			case "hasComponent" -> new VarArgFunction() {
				@Override
				public Varargs invoke(Varargs args) {
					LuaValue componentName = args.arg(2);
					var componentClass = ComponentBinderRegistry.getComponentClass(componentName.checkjstring());
					return scriptContext.getEntity().hasComponent(componentClass) ? LuaValue.TRUE : LuaValue.FALSE;
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
