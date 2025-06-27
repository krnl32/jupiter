package com.krnl32.jupiter.serializer.components;

import com.krnl32.jupiter.components.ParticleComponent;
import com.krnl32.jupiter.serializer.ComponentSerializer;
import com.krnl32.jupiter.serializer.resolvers.EntityResolver;
import com.krnl32.jupiter.serializer.utility.JOMLSerializerUtils;
import org.json.JSONObject;

public class ParticleComponentSerializer implements ComponentSerializer<ParticleComponent> {
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
}
