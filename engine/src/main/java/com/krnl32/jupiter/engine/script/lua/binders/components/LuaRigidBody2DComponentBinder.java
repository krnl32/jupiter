package com.krnl32.jupiter.engine.script.lua.binders.components;

import com.krnl32.jupiter.engine.components.physics.RigidBody2DComponent;
import com.krnl32.jupiter.engine.core.Logger;
import com.krnl32.jupiter.engine.physics.BodyType;
import com.krnl32.jupiter.engine.script.binder.ComponentBinder;
import org.joml.Vector2f;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;

public class LuaRigidBody2DComponentBinder implements ComponentBinder<RigidBody2DComponent, LuaValue> {
	@Override
	public LuaValue to(RigidBody2DComponent component) {
		LuaTable table = new LuaTable();
		table.set("bodyType", LuaValue.valueOf(component.bodyType.name()));

		LuaTable velocity = new LuaTable();
		velocity.set("x", LuaValue.valueOf(component.velocity.x));
		velocity.set("y", LuaValue.valueOf(component.velocity.y));
		table.set("velocity", velocity);

		table.set("angularDamping", LuaValue.valueOf(component.angularDamping));
		table.set("linearDamping", LuaValue.valueOf(component.linearDamping));
		table.set("mass", LuaValue.valueOf(component.mass));
		table.set("gravityScale", LuaValue.valueOf(component.gravityScale));
		table.set("fixedRotation", LuaValue.valueOf(component.fixedRotation));
		table.set("continuousCollision", LuaValue.valueOf(component.continuousCollision));
		return table;
	}

	@Override
	public RigidBody2DComponent from(LuaValue table) {
		BodyType bodyType = BodyType.STATIC;

		try {
			String bodyTypeStr = table.get("bodyType").checkjstring().toUpperCase();
			bodyType = BodyType.valueOf(bodyTypeStr);
		} catch (IllegalArgumentException e) {
			Logger.error("RigidBody2DComponentBinder fromLua Invalid BodyType({})", table.get("bodyType").checkjstring());
		}

		LuaValue velocityTable = table.get("velocity");
		float x = velocityTable.get("x").tofloat();
		float y = velocityTable.get("y").tofloat();
		Vector2f velocity = new Vector2f(x, y);

		float linearDamping = table.get("linearDamping").tofloat();
		float angularDamping = table.get("angularDamping").tofloat();
		float mass = table.get("mass").tofloat();
		float gravityScale = table.get("gravityScale").tofloat();
		boolean fixedRotation = table.get("fixedRotation").toboolean();
		boolean continuousCollision = table.get("continuousCollision").toboolean();

		return new RigidBody2DComponent(
			bodyType,
			velocity,
			linearDamping,
			angularDamping,
			mass,
			gravityScale,
			fixedRotation,
			continuousCollision
		);
	}

	@Override
	public void update(RigidBody2DComponent component, LuaValue table) {
		if (!table.get("bodyType").isnil()) {
			try {
				String bodyTypeStr = table.get("bodyType").checkjstring().toUpperCase();
				component.bodyType = BodyType.valueOf(bodyTypeStr);
			} catch (IllegalArgumentException e) {
				Logger.error("RigidBody2DComponentBinder updateFromLua Invalid BodyType({})", table.get("bodyType").checkjstring());
			}
		}

		LuaValue velocityTable = table.get("velocity");
		if (velocityTable.istable()) {
			component.velocity.set(velocityTable.get("x").tofloat(), velocityTable.get("y").tofloat());
		}

		if (!table.get("linearDamping").isnil()) {
			component.linearDamping = table.get("linearDamping").tofloat();
		}

		if (!table.get("angularDamping").isnil()) {
			component.angularDamping = table.get("angularDamping").tofloat();
		}

		if (!table.get("mass").isnil()) {
			component.mass = table.get("mass").tofloat();
		}

		if (!table.get("gravityScale").isnil()) {
			component.gravityScale = table.get("gravityScale").tofloat();
		}

		if (!table.get("fixedRotation").isnil()) {
			component.fixedRotation = table.get("fixedRotation").toboolean();
		}

		if (!table.get("continuousCollision").isnil()) {
			component.continuousCollision = table.get("continuousCollision").toboolean();
		}

		component.resync = true;
	}
}
