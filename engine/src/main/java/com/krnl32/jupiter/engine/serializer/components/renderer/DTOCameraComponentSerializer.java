package com.krnl32.jupiter.engine.serializer.components.renderer;

import com.krnl32.jupiter.engine.components.renderer.CameraComponent;
import com.krnl32.jupiter.engine.renderer.Camera;
import com.krnl32.jupiter.engine.renderer.ProjectionType;
import com.krnl32.jupiter.engine.serializer.ComponentSerializer;
import com.krnl32.jupiter.engine.serializer.resolvers.EntityResolver;
import com.krnl32.jupiter.engine.serializer.utility.DTOComponentSerializerUtility;

import java.util.Map;

public class DTOCameraComponentSerializer implements ComponentSerializer<CameraComponent, Map<String, Object>> {
	@Override
	public Map<String, Object> serialize(CameraComponent component) {
		return Map.ofEntries(
			Map.entry("position", DTOComponentSerializerUtility.serializeVector3f(component.camera.getPosition())),
			Map.entry("worldUp", DTOComponentSerializerUtility.serializeVector3f(component.camera.getWorldUp())),
			Map.entry("yaw", component.camera.getYaw()),
			Map.entry("pitch", component.camera.getPitch()),
			Map.entry("roll", component.camera.getRoll()),
			Map.entry("zoom", component.camera.getZoom()),
			Map.entry("turnSpeed", component.camera.getTurnSpeed()),
			Map.entry("rollSpeed", component.camera.getRollSpeed()),
			Map.entry("zoomSpeed", component.camera.getZoomSpeed()),
			Map.entry("mouseEnabled", component.camera.isMouseEnabled()),
			Map.entry("projectionType", component.camera.getProjectionType().name()),
			Map.entry("projectionFOV", component.camera.getProjectionFOV()),
			Map.entry("projectionNear", component.camera.getProjectionNear()),
			Map.entry("projectionFar", component.camera.getProjectionFar()),
			Map.entry("projectionSize", component.camera.getProjectionSize()),
			Map.entry("aspectRatio", component.camera.getAspectRatio()),
			Map.entry("primary", component.primary)
		);
	}

	@Override
	public CameraComponent deserialize(Map<String, Object> data, EntityResolver resolver) {
		Camera camera = new Camera(
			DTOComponentSerializerUtility.deserializeVector3f((Map<String, Object>) data.get("position")),
			DTOComponentSerializerUtility.deserializeVector3f((Map<String, Object>) data.get("worldUp")),
			DTOComponentSerializerUtility.toFloat(data.get("yaw")),
			DTOComponentSerializerUtility.toFloat(data.get("pitch")),
			DTOComponentSerializerUtility.toFloat(data.get("roll")),
			DTOComponentSerializerUtility.toFloat(data.get("zoom")),
			DTOComponentSerializerUtility.toFloat(data.get("turnSpeed")),
			DTOComponentSerializerUtility.toFloat(data.get("rollSpeed")),
			DTOComponentSerializerUtility.toFloat(data.get("zoomSpeed")),
			(boolean) data.get("mouseEnabled")
		);

		float projectionNear = DTOComponentSerializerUtility.toFloat(data.get("projectionNear"));
		float projectionFar = DTOComponentSerializerUtility.toFloat(data.get("projectionFar"));

		ProjectionType projectionType = ProjectionType.valueOf(data.get("projectionType").toString());
		if (projectionType == ProjectionType.ORTHOGRAPHIC) {
			float projectionSize = DTOComponentSerializerUtility.toFloat(data.get("projectionSize"));
			camera.setOrthographic(projectionSize, projectionNear, projectionFar);
		}
		else if(projectionType == ProjectionType.PERSPECTIVE) {
			float projectionFOV = DTOComponentSerializerUtility.toFloat(data.get("projectionFOV"));
			camera.setPerspective(projectionFOV, projectionNear, projectionFar);
		}

		camera.setAspectRatio(DTOComponentSerializerUtility.toFloat(data.get("aspectRatio")));

		return new CameraComponent(camera, (boolean) data.get("primary"));
	}
}
