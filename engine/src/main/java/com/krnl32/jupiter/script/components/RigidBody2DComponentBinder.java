package com.krnl32.jupiter.script.components;

import com.krnl32.jupiter.components.physics.RigidBody2DComponent;
import com.krnl32.jupiter.core.Logger;
import com.krnl32.jupiter.physics.BodyType;
import com.krnl32.jupiter.script.ComponentBinder;
import org.joml.Vector2f;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;

public class RigidBody2DComponentBinder implements ComponentBinder<RigidBody2DComponent> {
	@Override
	public LuaValue toLua(RigidBody2DComponent component) {
		LuaTable table = new LuaTable();
		table.set("bodyType", LuaValue.valueOf(component.bodyType.name()));

		LuaTable velocity = new LuaTable();
		velocity.set("x", LuaValue.valueOf(component.initialVelocity.x));
		velocity.set("y", LuaValue.valueOf(component.initialVelocity.y));
		table.set("initialVelocity", velocity);

		table.set("angularDamping", LuaValue.valueOf(component.angularDamping));
		table.set("linearDamping", LuaValue.valueOf(component.linearDamping));
		table.set("mass", LuaValue.valueOf(component.mass));
		table.set("gravityScale", LuaValue.valueOf(component.gravityScale));
		table.set("fixedRotation", LuaValue.valueOf(component.fixedRotation));
		table.set("continuousCollision", LuaValue.valueOf(component.continuousCollision));
		return table;
	}

	@Override
	public RigidBody2DComponent fromLua(LuaValue table) {
		BodyType bodyType = BodyType.STATIC;
		try {
			String bodyTypeStr = table.get("bodyType").checkjstring().toUpperCase();
			bodyType = BodyType.valueOf(bodyTypeStr);
		} catch (IllegalArgumentException e) {
			Logger.error("RigidBody2DComponentBinder Invalid BodyType({})", table.get("bodyType").checkjstring());
		}

		LuaValue velocityTable = table.get("initialVelocity");
		float x = velocityTable.get("x").tofloat();
		float y = velocityTable.get("y").tofloat();
		Vector2f initialVelocity = new Vector2f(x, y);

		float angularDamping = table.get("angularDamping").tofloat();
		float linearDamping = table.get("linearDamping").tofloat();
		float mass = table.get("mass").tofloat();
		float gravityScale = table.get("gravityScale").tofloat();
		boolean fixedRotation = table.get("fixedRotation").toboolean();
		boolean continuousCollision = table.get("continuousCollision").toboolean();

		return new RigidBody2DComponent(
			bodyType,
			initialVelocity,
			angularDamping,
			linearDamping,
			mass,
			gravityScale,
			fixedRotation,
			continuousCollision
		);
	}
}
