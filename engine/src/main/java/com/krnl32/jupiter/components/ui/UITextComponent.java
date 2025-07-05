package com.krnl32.jupiter.components.ui;

import com.krnl32.jupiter.asset.AssetID;
import com.krnl32.jupiter.ecs.Component;
import com.krnl32.jupiter.ui.text.TextHorizontalAlign;
import com.krnl32.jupiter.ui.text.TextOverflow;
import com.krnl32.jupiter.ui.text.TextVerticalAlign;
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
