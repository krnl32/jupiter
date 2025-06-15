package com.github.krnl32.jupiter.serializer.components;

import com.github.krnl32.jupiter.components.CameraComponent;
import com.github.krnl32.jupiter.renderer.Camera;
import com.github.krnl32.jupiter.renderer.ProjectionType;
import com.github.krnl32.jupiter.serializer.ComponentSerializer;
import com.github.krnl32.jupiter.serializer.JSONSerializerUtils;
import org.json.JSONObject;

public class CameraComponentSerializer implements ComponentSerializer<CameraComponent> {
	@Override
	public JSONObject serialize(CameraComponent component) {
		return new JSONObject()
			.put("position", JSONSerializerUtils.serializeVector3f(component.camera.getPosition()))
			.put("worldUp", JSONSerializerUtils.serializeVector3f(component.camera.getWorldUp()))
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
	public CameraComponent deserialize(JSONObject data) {
		Camera camera = new Camera(
			JSONSerializerUtils.deserializeVector3f(data.getJSONObject("position")),
			JSONSerializerUtils.deserializeVector3f(data.getJSONObject("worldUp")),
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
}
