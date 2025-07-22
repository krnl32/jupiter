package com.krnl32.jupiter.script;

import com.krnl32.jupiter.ecs.Component;
import org.luaj.vm2.LuaValue;

public interface ComponentBinder <T extends Component> {
	LuaValue toLua(T component);
	T fromLua(LuaValue table);
	void updateFromLua(T component, LuaValue table);
}
