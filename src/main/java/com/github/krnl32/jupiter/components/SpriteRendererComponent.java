package com.github.krnl32.jupiter.components;

import com.github.krnl32.jupiter.game.Component;
import com.github.krnl32.jupiter.renderer.RenderSpriteCommand;
import com.github.krnl32.jupiter.renderer.Renderer;
import org.joml.Vector4f;

public class SpriteRendererComponent extends Component {
	private Vector4f color;
	private int width;
	private int height;
	private int index;

	@Override
	public void onUpdate(float dt) {

	}

	@Override
	public void onRender(float dt, Renderer renderer) {
		TransformComponent transform = getGameObject().getComponent(TransformComponent.class);
		renderer.submit(new RenderSpriteCommand(transform.getTranslation().x, transform.getTranslation().y, (int)(transform.getScale().x * width), (int)(transform.getScale().y * height), index, color));
	}
}
