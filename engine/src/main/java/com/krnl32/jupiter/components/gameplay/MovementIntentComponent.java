package com.krnl32.jupiter.components.gameplay;

import com.krnl32.jupiter.ecs.Component;
import org.joml.Vector3f;

public class MovementIntentComponent implements Component {
	public Vector3f translation;
	public Vector3f rotation;
	public boolean jump;
	public boolean sprint;

	public MovementIntentComponent() {
		this.translation = new Vector3f();
		this.rotation = new Vector3f();
		this.jump = false;
		this.sprint = false;
	}

	public MovementIntentComponent(Vector3f translation, Vector3f rotation, boolean jump, boolean sprint) {
		this.translation = translation;
		this.rotation = rotation;
		this.jump = jump;
		this.sprint = sprint;
	}
}
