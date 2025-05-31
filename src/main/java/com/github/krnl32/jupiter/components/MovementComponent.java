package com.github.krnl32.jupiter.components;

import com.github.krnl32.jupiter.core.Logger;
import com.github.krnl32.jupiter.game.Component;

public class MovementComponent extends Component {
	@Override
	public void onUpdate(float dt) {
		TransformComponent transform = getActor().getComponent(TransformComponent.class);
		Logger.info("MovementComponent Stalling({})", transform.toString());
	}

	@Override
	public void onRender(float dt) {

	}
}
