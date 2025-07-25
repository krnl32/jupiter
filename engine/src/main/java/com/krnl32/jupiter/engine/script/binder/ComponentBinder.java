package com.krnl32.jupiter.engine.script.binder;

import com.krnl32.jupiter.engine.ecs.Component;
import org.luaj.vm2.LuaValue;

public interface ComponentBinder <T extends Component> {
	LuaValue toLua(T component);
	T fromLua(LuaValue table);
	void updateFromLua(T component, LuaValue table);
}
