package com.krnl32.jupiter.engine.components.ui;

import com.krnl32.jupiter.engine.asset.AssetID;
import com.krnl32.jupiter.engine.ecs.Component;
import com.krnl32.jupiter.engine.ui.text.TextHorizontalAlign;
import com.krnl32.jupiter.engine.ui.text.TextOverflow;
import com.krnl32.jupiter.engine.ui.text.TextVerticalAlign;
import org.joml.Vector4f;

public class UITextComponent implements Component {
	public String text;
	public Vector4f color;
	public AssetID fontAssetID;
	public TextHorizontalAlign horizontalAlignment;
	public TextVerticalAlign verticalAlignment;
	public TextOverflow overflow;

	public UITextComponent(String text, Vector4f color, AssetID fontAssetID) {
		this.text = text;
		this.color = color;
		this.fontAssetID = fontAssetID;
		this.horizontalAlignment = TextHorizontalAlign.LEFT;
		this.verticalAlignment = TextVerticalAlign.CENTER;
		this.overflow = TextOverflow.SCALE;
	}

	public UITextComponent(String text, Vector4f color, AssetID fontAssetID, TextHorizontalAlign horizontalAlignment, TextVerticalAlign verticalAlignment, TextOverflow overflow) {
		this.text = text;
		this.color = color;
		this.fontAssetID = fontAssetID;
		this.horizontalAlignment = horizontalAlignment;
		this.verticalAlignment = verticalAlignment;
		this.overflow = overflow;
	}
}
