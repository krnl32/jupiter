package com.krnl32.jupiter.engine.serializer.oldcomponents.effects;

import com.krnl32.jupiter.engine.components.effects.ParticleComponent;
import com.krnl32.jupiter.engine.serializer.OldComponentSerializer;
import com.krnl32.jupiter.engine.serializer.resolvers.EntityResolver;
import com.krnl32.jupiter.engine.serializer.utility.JOMLSerializerUtils;
import org.joml.Vector3f;
import org.json.JSONObject;

public class ParticleComponentSerializer implements OldComponentSerializer<ParticleComponent> {
	@Override
	public JSONObject serialize(ParticleComponent component) {
		return new JSONObject()
			.put("velocity", JOMLSerializerUtils.serializeVector3f(component.velocity))
			.put("duration", component.duration)
			.put("remainingTime", component.remainingTime);
	}

	@Override
	public ParticleComponent deserialize(JSONObject data, EntityResolver resolver) {
		return new ParticleComponent(
			JOMLSerializerUtils.deserializeVector3f(data.getJSONObject("velocity")),
			data.getFloat("duration"),
			data.getFloat("remainingTime")
		);
	}

	@Override
	public ParticleComponent clone(ParticleComponent component) {
		return new ParticleComponent(
			new Vector3f(component.velocity),
			component.duration,
			component.remainingTime
		);
	}
}
