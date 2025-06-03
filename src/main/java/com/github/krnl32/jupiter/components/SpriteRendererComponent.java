package com.github.krnl32.jupiter.components;

import com.github.krnl32.jupiter.game.Component;
import com.github.krnl32.jupiter.renderer.RenderSpriteCommand;
import com.github.krnl32.jupiter.renderer.Renderer;
import com.github.krnl32.jupiter.renderer.Sprite;

public class SpriteRendererComponent extends Component {
	private Sprite sprite;

	public SpriteRendererComponent(Sprite sprite) {
		this.sprite = sprite;
	}

	@Override
	public void onUpdate(float dt) {

	}

	@Override
	public void onRender(float dt, Renderer renderer) {
		TransformComponent transform = getGameObject().getComponent(TransformComponent.class);
		Sprite spriteRender = new Sprite((int)(transform.getScale().x * sprite.getWidth()), (int)(transform.getScale().y * sprite.getHeight()), sprite.getIndex(), sprite.getColor());
		renderer.submit(new RenderSpriteCommand(transform.getTranslation(), spriteRender));
	}
}
