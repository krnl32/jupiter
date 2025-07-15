package com.krnl32.jupiter.serializer.components;

import com.krnl32.jupiter.components.CameraComponent;
import com.krnl32.jupiter.renderer.Camera;
import com.krnl32.jupiter.renderer.ProjectionType;
import com.krnl32.jupiter.serializer.ComponentSerializer;
import com.krnl32.jupiter.serializer.resolvers.EntityResolver;
import com.krnl32.jupiter.serializer.utility.JOMLSerializerUtils;
import org.json.JSONObject;

public class CameraComponentSerializer implements ComponentSerializer<CameraComponent> {
	@Override
	public JSONObject serialize(CameraComponent component) {
		return new JSONObject()
			.put("position", JOMLSerializerUtils.serializeVector3f(component.camera.getPosition()))
			.put("worldUp", JOMLSerializerUtils.serializeVector3f(component.camera.getWorldUp()))
			.put("yaw", component.camera.getYaw())
			.put("pitch", component.camera.getPitch())
			.put("roll", component.camera.getRoll())
			.put("zoom", component.camera.getZoom())
			.put("turnSpeed", component.camera.getTurnSpeed())
			.put("rollSpeed", component.camera.getRollSpeed())
			.put("zoomSpeed", component.camera.getZoomSpeed())
			.put("mouseEnabled", component.camera.isMouseEnabled())
			.put("projectionType", component.camera.getProjectionType().name())
			.put("projectionFOV", component.camera.getProjectionFOV())
			.put("projectionNear", component.camera.getProjectionNear())
			.put("projectionFar", component.camera.getProjectionFar())
			.put("projectionSize", component.camera.getProjectionSize())
			.put("aspectRatio", component.camera.getAspectRatio())
			.put("primary", component.primary);
	}

	@Override
	public CameraComponent deserialize(JSONObject data, EntityResolver resolver) {
		Camera camera = new Camera(
			JOMLSerializerUtils.deserializeVector3f(data.getJSONObject("position")),
			JOMLSerializerUtils.deserializeVector3f(data.getJSONObject("worldUp")),
			data.getFloat("yaw"),
			data.getFloat("pitch"),
			data.getFloat("roll"),
			data.getFloat("zoom"),
			data.getFloat("turnSpeed"),
			data.getFloat("rollSpeed"),
			data.getFloat("zoomSpeed"),
			data.getBoolean("mouseEnabled"));

		ProjectionType projectionType = ProjectionType.valueOf(data.getString("projectionType"));
		if (projectionType == ProjectionType.ORTHOGRAPHIC)
			camera.setOrthographic(data.getFloat("projectionSize"), data.getFloat("projectionNear"), data.getFloat("projectionFar"));
		else if(projectionType == ProjectionType.PERSPECTIVE)
			camera.setPerspective(data.getFloat("projectionFOV"), data.getFloat("projectionNear"), data.getFloat("projectionFar"));

		camera.setAspectRatio(data.getFloat("aspectRatio"));

		return new CameraComponent(camera, data.getBoolean("primary"));
	}

	@Override
	public CameraComponent clone(CameraComponent component) {
		return new CameraComponent(
			new Camera(component.camera.getPosition(), component.camera.getWorldUp(), component.camera.getYaw(), component.camera.getPitch(), component.camera.getRoll(), component.camera.getZoom(), component.camera.getTurnSpeed(), component.camera.getRollSpeed(), component.camera.getZoomSpeed(), component.camera.isMouseEnabled()),
			component.primary
		);
	}
}
