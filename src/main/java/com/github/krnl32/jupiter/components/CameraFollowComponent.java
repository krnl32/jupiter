package com.github.krnl32.jupiter.components;

import com.github.krnl32.jupiter.game.Component;
import com.github.krnl32.jupiter.game.GameObject;
import com.github.krnl32.jupiter.renderer.Renderer;

public class CameraFollowComponent extends Component {
	private GameObject target;

	public CameraFollowComponent(GameObject target) {
		this.target = target;
	}

	@Override
	public void onUpdate(float dt) {
	}

	@Override
	public void onRender(float dt, Renderer renderer) {
	}
}
