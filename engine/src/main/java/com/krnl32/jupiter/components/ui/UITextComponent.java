package com.krnl32.jupiter.components.ui;

import com.krnl32.jupiter.asset.AssetID;
import com.krnl32.jupiter.ecs.Component;
import com.krnl32.jupiter.ui.text.TextAlign;
import org.joml.Vector4f;

public class UITextComponent implements Component {
	public String text;
	public Vector4f color;
	public AssetID fontAssetID;
	public TextAlign alignment;

	public UITextComponent(String text, Vector4f color, AssetID fontAssetID) {
		this.text = text;
		this.color = color;
		this.fontAssetID = fontAssetID;
		this.alignment = TextAlign.LEFT;
	}

	public UITextComponent(String text, Vector4f color, AssetID fontAssetID, TextAlign alignment) {
		this.text = text;
		this.color = color;
		this.fontAssetID = fontAssetID;
		this.alignment = alignment;
	}
}
