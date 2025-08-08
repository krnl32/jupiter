package com.krnl32.jupiter.engine.components.ui;

import com.krnl32.jupiter.engine.asset.handle.AssetId;
import com.krnl32.jupiter.engine.ecs.Component;
import com.krnl32.jupiter.engine.ui.text.TextHorizontalAlign;
import com.krnl32.jupiter.engine.ui.text.TextOverflow;
import com.krnl32.jupiter.engine.ui.text.TextVerticalAlign;
import org.joml.Vector4f;

public class UITextComponent implements Component {
	public String text;
	public Vector4f color;
	public AssetId fontAssetId;
	public TextHorizontalAlign horizontalAlignment;
	public TextVerticalAlign verticalAlignment;
	public TextOverflow overflow;

	public UITextComponent(String text, Vector4f color, AssetId fontAssetId) {
		this.text = text;
		this.color = color;
		this.fontAssetId = fontAssetId;
		this.horizontalAlignment = TextHorizontalAlign.LEFT;
		this.verticalAlignment = TextVerticalAlign.CENTER;
		this.overflow = TextOverflow.SCALE;
	}

	public UITextComponent(String text, Vector4f color, AssetId fontAssetId, TextHorizontalAlign horizontalAlignment, TextVerticalAlign verticalAlignment, TextOverflow overflow) {
		this.text = text;
		this.color = color;
		this.fontAssetId = fontAssetId;
		this.horizontalAlignment = horizontalAlignment;
		this.verticalAlignment = verticalAlignment;
		this.overflow = overflow;
	}
}
