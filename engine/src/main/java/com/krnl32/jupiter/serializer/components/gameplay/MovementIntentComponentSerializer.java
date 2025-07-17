package com.krnl32.jupiter.serializer.components.gameplay;

import com.krnl32.jupiter.components.gameplay.MovementIntentComponent;
import com.krnl32.jupiter.serializer.ComponentSerializer;
import com.krnl32.jupiter.serializer.resolvers.EntityResolver;
import com.krnl32.jupiter.serializer.utility.JOMLSerializerUtils;
import org.joml.Vector3f;
import org.json.JSONObject;

public class MovementIntentComponentSerializer implements ComponentSerializer<MovementIntentComponent> {
	@Override
	public JSONObject serialize(MovementIntentComponent component) {
		return new JSONObject()
			.put("translation", JOMLSerializerUtils.serializeVector3f(component.translation))
			.put("rotation", JOMLSerializerUtils.serializeVector3f(component.rotation))
			.put("jump", component.jump)
			.put("sprint", component.sprint);
	}

	@Override
	public MovementIntentComponent deserialize(JSONObject data, EntityResolver resolver) {
		return new MovementIntentComponent(
			JOMLSerializerUtils.deserializeVector3f(data.getJSONObject("translation")),
			JOMLSerializerUtils.deserializeVector3f(data.getJSONObject("rotation")),
			data.getBoolean("jump"),
			data.getBoolean("sprint")
		);
	}

	@Override
	public MovementIntentComponent clone(MovementIntentComponent component) {
		return new MovementIntentComponent(
			new Vector3f(component.translation),
			new Vector3f(component.rotation),
			component.jump,
			component.sprint
		);
	}
}
